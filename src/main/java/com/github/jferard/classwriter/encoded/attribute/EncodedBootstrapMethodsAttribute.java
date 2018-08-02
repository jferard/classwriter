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
 * 4.7.23. The BootstrapMethods Attribute
 * <pre>{@code
 * BootstrapMethods_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 num_bootstrap_methods;
 *     {   u2 bootstrap_method_ref;
 *         u2 num_bootstrap_arguments;
 *         u2 bootstrap_arguments[num_bootstrap_arguments];
 *     } bootstrap_methods[num_bootstrap_methods];
 * }
 * }</pre>
 */
public class EncodedBootstrapMethodsAttribute implements EncodedClassFileAttribute {
    private final int nameIndex;
    private final List<EncodedBootstrapMethod> encodedBootstrapMethods;

    public EncodedBootstrapMethodsAttribute(int nameIndex,
                                            List<EncodedBootstrapMethod> encodedBootstrapMethods) {
        this.nameIndex = nameIndex;
        this.encodedBootstrapMethods = encodedBootstrapMethods;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.bootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + this.getLength();
    }

    private int getLength() {
        return this.encodedBootstrapMethods.stream().mapToInt(EncodedBootstrapMethod::getSize)
                .sum();
    }

    @Override
    public Optional<List<EncodedBootstrapMethod>> oGetBootstrapMethods() {
        return Optional.of(this.encodedBootstrapMethods);
    }
}
