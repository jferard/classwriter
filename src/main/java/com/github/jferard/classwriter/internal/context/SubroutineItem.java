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

import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData;
import com.github.jferard.classwriter.internal.instruction.Instruction;

public class SubroutineItem implements TargetItem {
    private final Instruction instruction;
    private StackMapFrameData stackMapFrameData;
    private int offset;

    public SubroutineItem(int offset, Instruction instruction,
                          StackMapFrameData stackMapFrameData) {
        this.offset = offset;
        this.instruction = instruction;
        this.stackMapFrameData = stackMapFrameData;
    }

    @Override
    public void shift(int curShift) {
        this.offset += curShift;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int updateShift(int curShift, TargetItem targetItem) {
        return curShift;
    }

    @Override
    public void extractOffsets(OffsetsBuilder builder) {
        builder.putSubroutine(this.instruction, this.offset);
        builder.putStackMapFrame(this.stackMapFrameData, this.offset);
    }

    @Override
    public boolean isItemForTarget(Object o) {
        return o == this.instruction;
    }
}
