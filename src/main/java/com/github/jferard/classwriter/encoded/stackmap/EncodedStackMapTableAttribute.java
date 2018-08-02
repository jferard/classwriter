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

package com.github.jferard.classwriter.encoded.stackmap;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.util.List;

/**
 * 4.7.4. The StackMapTable Attribute
 * <pre>{@code
 * StackMapTable_attribute {
 *     u2              attribute_name_index;
 *     u4              attribute_length;
 *     u2              number_of_entries;
 *     stack_map_frame entries[number_of_entries];
 * }
 * }</pre>
 */
public class EncodedStackMapTableAttribute implements EncodedAttribute {
    private final int attributeNameIndex;
    private List<EncodedStackMapFrame> encodedStackMapFrames;

    public EncodedStackMapTableAttribute(int attributeNameIndex,
                                         List<EncodedStackMapFrame> encodedStackMapFrames) {
        this.attributeNameIndex = attributeNameIndex;
        this.encodedStackMapFrames = encodedStackMapFrames;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory
                .stackMapTableAttribute(this.attributeNameIndex, this.encodedStackMapFrames);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE +
                this.encodedStackMapFrames.stream().mapToInt(Encoded::getSize).sum();
    }
}
