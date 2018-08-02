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

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class ByteCodeCodeAttribute implements ByteCode {
    private final int attributeNameIndex;
    private final int maxStack;
    private final int maxLocals;
    private final int codeLength;
    private final ByteCode writableInstruction;
    private final List<ByteCode> writableExceptions;
    private final int attributesLength;
    private final List<ByteCode> writableAttributes;
    private int length;

    public ByteCodeCodeAttribute(int attributeNameIndex, int length, int maxStack, int maxLocals,
                                 int codeLength, ByteCode writableInstruction,
                                 List<ByteCode> writableExceptions, int attributesLength,
                                 List<ByteCode> writableAttributes) {
        this.attributeNameIndex = attributeNameIndex;
        this.length = length;
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.writableInstruction = writableInstruction;
        this.writableExceptions = writableExceptions;
        this.attributesLength = attributesLength;
        this.writableAttributes = writableAttributes;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.attributeNameIndex);
        output.writeInt(this.length);
        output.writeShort(this.maxStack);
        output.writeShort(this.maxLocals);
        output.writeInt(this.codeLength);
        this.writableInstruction.write(output);
        output.writeShort(this.writableExceptions.size());
        for (ByteCode writableException : this.writableExceptions) {
            writableException.write(output);
        }
        output.writeShort(this.writableAttributes.size());
        for (ByteCode writableAttribute : this.writableAttributes) {
            writableAttribute.write(output);
        }
    }
}
