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

package com.github.jferard.classwriter.encoded.attribute;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;

import java.util.List;
import java.util.Optional;

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
public class EncodedInnerClassesAttribute implements EncodedClassFileAttribute {
    private final int attributeNameIndex;
    private final List<EncodedInnerClass> encodedInnerClasses;

    public EncodedInnerClassesAttribute(int attributeNameIndex,
                                        List<EncodedInnerClass> encodedInnerClasses) {
        this.attributeNameIndex = attributeNameIndex;
        this.encodedInnerClasses = encodedInnerClasses;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory
                .innerClassesAttribute(this.attributeNameIndex, this.encodedInnerClasses);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE +
                this.encodedInnerClasses.stream().mapToInt(EncodedInnerClass::getSize).sum();
    }

    @Override
    public Optional<List<EncodedBootstrapMethod>> oGetBootstrapMethods() {
        return Optional.empty();
    }
}
