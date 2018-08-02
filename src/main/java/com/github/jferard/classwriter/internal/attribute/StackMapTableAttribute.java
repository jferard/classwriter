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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapTableAttribute;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame;
import com.github.jferard.classwriter.internal.context.MethodContext;

import java.util.List;
import java.util.stream.Collectors;

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
public class StackMapTableAttribute implements CodeAttributeAttribute {
    public static final String STACK_MAP_TABLE_NAME = "StackMapTable";
    private final List<StackMapFrame> stackMapFrames;

    StackMapTableAttribute(final List<StackMapFrame> stackMapFrames) {
        this.stackMapFrames = stackMapFrames;
    }

    @Override
    public EncodedAttribute encode(GlobalContext pool, MethodContext codeContext) {
        int attributeNameIndex = pool.addUtf8ToPool(STACK_MAP_TABLE_NAME);
        List<EncodedStackMapFrame> encodedStackMapFrames = this.stackMapFrames.stream()
                .map(smf -> smf.encode(pool, MethodContext.create(0))).collect(Collectors.toList());
        return new EncodedStackMapTableAttribute(attributeNameIndex, encodedStackMapFrames);
    }
}
