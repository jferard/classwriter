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

package com.github.jferard.classwriter.bytecode.instruction;

import com.github.jferard.classwriter.Writable;

import java.io.DataOutput;

public class ByteCodeTableSwitchInstruction implements Writable<DataOutput> {
    private final int defaultOffset;
    private final int low;
    private final int high;
    private final int[] jump_offsets;

    public ByteCodeTableSwitchInstruction(int defaultOffset, int low, int high,
                                          int[] jump_offsets) {
        this.defaultOffset = defaultOffset;
        this.low = low;
        this.high = high;
        this.jump_offsets = jump_offsets;
    }

    @Override
    public void write(DataOutput output) {
        /*         this.instructions.add(new ByteInstruction(OpCodes.TABLESWITCH); this
         * .baseWriter.padTo4();
         this.baseWriter.writeInt(default_);
         this.baseWriter.writeInt(low);
         this.baseWriter.writeInt(high);
         for (final int jump_offset : jump_offsets) {
         this.baseWriter.writeInt(jump_offset);
         }
         */
    }
}
