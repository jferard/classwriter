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

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

import java.io.DataOutput;
import java.io.IOException;

public class ByteCodeLdcInstruction implements Writable<DataOutput> {
    private int index;
    private int stackSize;

    public ByteCodeLdcInstruction(int index, int stackSize) {
        this.index = index;
        this.stackSize = stackSize;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        if (this.stackSize == 2) {
            output.writeByte(OpCodes.LDC2_W);
            output.writeShort(this.index);
        } else if (this.index <= BytecodeHelper.BYTE_MAX) {
            output.writeByte(OpCodes.LDC);
            output.writeByte(this.index);
        } else {
            output.writeByte(OpCodes.LDC_W);
            output.writeShort(this.index);
        }
    }

}
