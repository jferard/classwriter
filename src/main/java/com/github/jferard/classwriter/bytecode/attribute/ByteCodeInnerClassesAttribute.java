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
 * 4.7.6. The InnerClasses Attribute
 * <pre>{@code
 * InnerClasses_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_classes;
 *     {   u2 inner_class_info_index;
 *         u2 outer_class_info_index;
 *         u2 inner_name_index;
 *         u2 inner_class_access_flags;
 *     } classes[number_of_classes];
 * }
 * }</pre>
 */
public class ByteCodeInnerClassesAttribute implements ByteCode {
    private final int attributeNameIndex;
    private final List<ByteCode> writableInnerClasses;
    private int length;

    public ByteCodeInnerClassesAttribute(int attributeNameIndex, int length,
                                         List<ByteCode> writableInnerClasses) {
        this.attributeNameIndex = attributeNameIndex;
        this.length = length;
        this.writableInnerClasses = writableInnerClasses;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.attributeNameIndex);
        output.writeInt(this.length);
        for (ByteCode writableInnerClass : this.writableInnerClasses) {
            writableInnerClass.write(output);
        }
    }
}
