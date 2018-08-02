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

package com.github.jferard.classwriter.bytecode.attribute;

import com.github.jferard.classwriter.bytecode.ByteCode;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * 4.7.12. The LineNumberTable Attribute
 * <pre>{@code
 * LineNumberTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 line_number_table_length;
 *     {   u2 start_pc;
 *         u2 line_number;
 *     } line_number_table[line_number_table_length];
 * }
 * }</pre>
 */
public class ByteCodeLineNumberTableAttribute implements ByteCode {
    private final int nameIndex;
    private final List<ByteCode> writablePositionAndLineNumbers;
    private int attributeLength;

    public ByteCodeLineNumberTableAttribute(int nameIndex, int attributeLength,
                                            List<ByteCode> writablePositionAndLineNumbers) {
        this.nameIndex = nameIndex;
        this.attributeLength = attributeLength;
        this.writablePositionAndLineNumbers = writablePositionAndLineNumbers;
    }


    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.nameIndex);
        output.writeInt(attributeLength);
        output.writeShort(this.writablePositionAndLineNumbers.size());
        for (ByteCode positionAndLineNumber : this.writablePositionAndLineNumbers) {
            positionAndLineNumber.write(output);
        }
    }
}
