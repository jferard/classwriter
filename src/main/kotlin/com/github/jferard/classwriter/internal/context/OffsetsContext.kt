/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
package com.github.jferard.classwriter.internal.context

import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData
import com.github.jferard.classwriter.api.instruction.Instruction
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *
 * There is an issue with goto's: they can either have a size of 3 bytes (if the target offset is
 * normal) or 5 bytes (if the target offset is wide >= 65536). If a goto passes from 3 to 5
 * bytes (right shift of 2 bytes), because we discover that the target offset is wide, then
 * another index may become wide
 * and we must restart to make a previous goto wide and shift everything again.
 *
 *
 * Where things are getting worse: switch instructions have paddings to 4. A padding may
 * neutralize or not the widening of gotos.
 *
 *
 * I hoped to find a one pass algorithm, but ended with this.
 *
 * ```INITIALIZATION:
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
 * for label in reverse_sorted(targetItems):
 * if label.offset < MAX:
 * break
 * FIX_WIDE_OFFSETS(label)
 *
 * FIX_WIDE_OFFSETS(label):
 * cur_shift = 0
 * for item in items:
 * item.shift(cur_shift)
 * if item is goto and item.target = label:
 * cur_shift += 2
 * if item is padding:
 * FIX_PADDING_AND_SHIFT(item.padding, cur_shift)
 *
 * FIX_PADDING_AND_SHIFT(padding, cur_shift):
 * if cur_shift % 4 == 0:
 * // do nothing
 * else: // cur_shift % 4 == 2
 * if padding >= 2:    // x___    or xx__
 * padding -= 2    // xxx_    of xxxx
 * cur_shift -= 2
 * else:               // xxx_    or xxxx
 * padding += 2    // xxxx|x_ or xxxx|xx__
 * cur_shift += 2
 *
* ``` *
 *
 *
 * The key point is that the cur_shift never gets negative, thus offsets of targetItems are
 * growing or
 * stay equal on every step.
 * There is no way a wide label may become a normal label, but a normal label may become a wide
 * label and will be handled correctly
 * on a next iteration.
 */
class OffsetsContext internal constructor(val curOffset: Int,
                                          private val subroutines: MutableList<Instruction>) {
    private val positionedItems: MutableList<PositionedItem> = ArrayList()
    private val targetItems: Stack<TargetItem> = Stack()
    private val offsetByLabel: MutableMap<Label, Int> = HashMap()

    /**
     * Add a subroutine
     *
     * @param instruction the subroutine
     */
    fun addSubroutine(
            instruction: Instruction) {
        subroutines.add(instruction)
    }

    /**
     * should store the stackframe
     */
    fun storeGoto(label: Label) {
        positionedItems.add(GotoItem(curOffset, label))
    }

    fun storeJsr(
            instruction: Instruction) {
        positionedItems.add(JsrItem(curOffset, instruction))
    }

    fun storePadding(padding: Int) {
        positionedItems.add(PaddingItem(curOffset, padding))
    }

    fun storeLabel(label: Label,
                   stackMapFrameData: StackMapFrameData) {
        val labelItem = LabelItem(curOffset, label, stackMapFrameData)
        positionedItems.add(labelItem)
        targetItems.push(labelItem)
    }

    fun storeBranch(label: Label) {
        positionedItems.add(BranchItem(curOffset, label))
    }

    /**
     * Fix wide index goto and jsr
     * What about padding (lookupswitch, tableswitch)
     */
    fun normalize(context: GlobalContext, codeContext: MethodContext): Offsets {
        appendSubroutines(context, codeContext)
        fixOffsets()
        return extractOffsets()
    }

    /* subroutines are added after the main code */
    private fun appendSubroutines(context: GlobalContext,
                                  codeContext: MethodContext) {
        for (instruction in subroutines) {
            val targetItem = SubroutineItem(curOffset, instruction,
                    codeContext.stackMapFrameData)
            positionedItems.add(targetItem)
            targetItems.push(targetItem)
            instruction.preprocess(context, codeContext)
        }
    }

    private fun fixOffsets() {
        while (!targetItems.isEmpty()) {
            val targetItem: TargetItem = targetItems.pop()
            if (targetItem.offset <= BytecodeHelper.SHORT_MAX) {
                break
            }
            fixWideOffset(targetItem)
        }
    }

    private fun fixWideOffset(targetItem: TargetItem) {
        var curShift = 0
        for (item in targetItems) {
            item.shift(curShift)
            curShift = item.updateShift(curShift, targetItem)
        }
    }

    private fun extractOffsets(): Offsets {
        val builder = OffsetsBuilder()
        for (item in positionedItems) {
            item.extractOffsets(builder)
        }
        return builder.build()
    }

    fun clone(): OffsetsContext {
        return OffsetsContext(curOffset, subroutines)
    }

    /**
     * merge frame context
     */
    fun merge(otherContext: OffsetsContext) {
        TODO("not implemented")
    }

    companion object {
        fun create(nargs: Int): OffsetsContext {
            return OffsetsContext(0,
                    ArrayList())
        }
    }

}