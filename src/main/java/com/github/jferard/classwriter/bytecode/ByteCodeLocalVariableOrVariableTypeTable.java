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

package com.github.jferard.classwriter.bytecode;

import com.github.jferard.classwriter.Writable;

import java.io.DataOutput;
import java.io.IOException;

/**
 * 4.7.13. The LocalVariableTable Attribute
 * <pre>{@code
 * {   u2 start_pc;
 *         u2 length;
 *         u2 name_index;
 *         u2 descriptor_index;
 *         u2 index;
 *     } local_variable_table
 * }</pre>
 */
public class ByteCodeLocalVariableOrVariableTypeTable implements ByteCode {
    private final int startPc;
    private final int length;
    private final int nameIndex;
    private final int descriptorOrSignatureIndex;
    private final int index;

    public ByteCodeLocalVariableOrVariableTypeTable(int startPc, int length, int nameIndex,
                                                    int descriptorOrSignatureIndex, int index) {
        this.startPc = startPc;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorOrSignatureIndex = descriptorOrSignatureIndex;
        this.index = index;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(startPc);
        output.writeShort(length);
        output.writeShort(nameIndex);
        output.writeShort(descriptorOrSignatureIndex);
        output.writeShort(index);
    }
}
