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

import com.github.jferard.classwriter.api.FieldDescriptor;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * 4.5. Fields
 * <pre>{@code
 * field_info {
 *     u2             access_flags;
 *     u2             name_index;
 *     u2             descriptor_index;
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 * }</pre>
 */
public class ByteCodeField implements ByteCode {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final List<ByteCode> writableAttributes;

    public ByteCodeField(int accessFlags, int nameIndex, int descriptorIndex, List<ByteCode> writableAttributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.writableAttributes = writableAttributes;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(accessFlags);
        output.writeShort(nameIndex);
        output.writeShort(descriptorIndex);
        output.writeShort(writableAttributes.size());
        for (ByteCode writableAttribute : writableAttributes) {
            writableAttribute.write(output);
        }
    }
}
