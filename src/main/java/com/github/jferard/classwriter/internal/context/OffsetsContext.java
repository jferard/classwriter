/*
 * ClassWriter - A minimal Java bytecode writer. Creates classes, methods, interfaces...
 *     Copyright (C) 2018 J. Férard <https://github.com/jferard>
 *
 * This file is part of ClassWriter.
 *
 * ClassWriter is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * ClassWriter is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.jferard.classwriter.internal.context;

import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.api.Label;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * <p>
 * There is an issue with goto's: they can either have a size of 3 bytes (if the target offset is
 * normal) or 5 bytes (if the target offset is wide >= 65536). If a goto passes from 3 to 5
 * bytes (right shift of 2 bytes), because we discover that the target offset is wide, then
 * another index may become wide
 * and we must restart to make a previous goto wide and shift everything again.
 * <p>
 * Where things are getting worse: switch instructions have paddings to 4. A padding may
 * neutralize or not the widening of gotos.
 * </p><p>
 * I hoped to find a one pass algorithm, but ended with this.
 * </p>
 * <pre>{@code
 * INITIALIZATION:
 * Preprocess the list of instructions:
 * - every goto has a size of 3.
 * - the paddings are computed normally (padding to 4: 0,1,2 or 3).
 * - all other instructions have a well kown offset.
 *
 * During the preprocess:
 * - store an ordered list of items depending on offsets (goto, jsr, targetItems, paddings)
 * - create a map label -> index in the list
 *
 * FIX_OFFSETS:
 *  for label in reverse_sorted(targetItems):
 *      if label.offset < MAX:
 *          break
 *      FIX_WIDE_OFFSETS(label)
 *
 *  FIX_WIDE_OFFSETS(label):
 *  cur_shift = 0
 *  for item in items:
 *      item.shift(cur_shift)
 *      if item is goto and item.target = label:
 *          cur_shift += 2
 *      if item is padding:
 *          FIX_PADDING_AND_SHIFT(item.padding, cur_shift)
 *
 *  FIX_PADDING_AND_SHIFT(padding, cur_shift):
 *  if cur_shift % 4 == 0:
 *      // do nothing
 *  else: // cur_shift % 4 == 2
 *      if padding >= 2:    // x___    or xx__
 *          padding -= 2    // xxx_    of xxxx
 *          cur_shift -= 2
 *      else:               // xxx_    or xxxx
 *          padding += 2    // xxxx|x_ or xxxx|xx__
 *          cur_shift += 2
 *
 * }</pre>
 * <p>
 * The key point is that the cur_shift never gets negative, thus offsets of targetItems are
 * growing or
 * stay equal on every step.
 * There is no way a wide label may become a normal label, but a normal label may become a wide
 * label and will be handled correctly
 * on a next iteration.
 */
public class OffsetsContext {
    public static OffsetsContext create(int nargs) {
        return new OffsetsContext(0, new ArrayList<>());
    }

    private final List<Instruction> subroutines;
    private int offset;
    private List<PositionedItem> positionedItems;
    private Stack<TargetItem> targetItems;
    private Map<Label, Integer> offsetByLabel;

    OffsetsContext(int offset, final List<Instruction> subroutines) {
        this.offset = offset;
        this.subroutines = subroutines;
    }

    /**
     * Add a subroutine
     *
     * @param instruction the subroutine
     */
    public void addSubroutine(Instruction instruction) {
        this.subroutines.add(instruction);
    }

    /**
     * should store the stackframe
     */
    public void storeGoto(Label label) {
        this.positionedItems.add(new GotoItem(this.offset, label));
    }

    public void storeJsr(Instruction instruction) {
        this.positionedItems.add(new JsrItem(this.offset, instruction));
    }

    public void storePadding(int padding) {
        this.positionedItems.add(new PaddingItem(this.offset, padding));
    }

    public void storeLabel(Label label, StackMapFrameData stackMapFrameData) {
        LabelItem labelItem = new LabelItem(this.offset, label, stackMapFrameData);
        this.positionedItems.add(labelItem);
        this.targetItems.push(labelItem);
    }

    public void storeBranch(Label label) {
        this.positionedItems.add(new BranchItem(this.offset, label));
    }

    /**
     * Fix wide index goto and jsr
     * What about padding (lookupswitch, tableswitch) ?
     */
    public Offsets normalize(GlobalContext context, MethodContext codeContext) {
        this.appendSubroutines(context, codeContext);
        this.fixOffsets();
        return this.extractOffsets();
    }

    /* subroutines are added after the main code */
    private void appendSubroutines(GlobalContext context, MethodContext codeContext) {
        for (Instruction instruction : this.subroutines) {
            final SubroutineItem targetItem = new SubroutineItem(this.offset, instruction,
                    codeContext.getStackMapFrameData());
            this.positionedItems.add(targetItem);
            this.targetItems.push(targetItem);
            instruction.preprocess(context, codeContext);
        }
    }

    private void fixOffsets() {
        while (!this.targetItems.isEmpty()) {
            TargetItem targetItem = this.targetItems.pop();
            if (targetItem.getOffset() <= BytecodeHelper.SHORT_MAX) {
                break;
            }

            this.fixWideOffset(targetItem);
        }
    }

    private void fixWideOffset(TargetItem targetItem) {
        int curShift = 0;
        for (PositionedItem item : this.targetItems) {
            item.shift(curShift);
            curShift = item.updateShift(curShift, targetItem);
        }
    }

    private Offsets extractOffsets() {
        OffsetsBuilder builder = new OffsetsBuilder();
        for (PositionedItem item : this.positionedItems) {
            item.extractOffsets(builder);
        }
        return builder.build();
    }

    public int getCurOffset() {
        return this.offset;
    }

    public OffsetsContext clone() {
        return new OffsetsContext(this.offset, this.subroutines);
    }

    /**
     * merge frame context
     */
    public void merge(OffsetsContext otherContext) {
        throw new IllegalStateException();
    }
}
