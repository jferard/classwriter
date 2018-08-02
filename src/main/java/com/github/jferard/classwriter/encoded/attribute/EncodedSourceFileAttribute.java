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
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute;

import java.util.List;
import java.util.Optional;

/**
 * 4.7.10. The SourceFile Attribute
 * <pre>{@code
 * SourceFile_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 sourcefile_index;
 * }
 * }</pre>
 */
public class EncodedSourceFileAttribute implements EncodedClassFileAttribute {
    private final int attributeNameIndex;
    private final int sourceFileNameIndex;

    public EncodedSourceFileAttribute(int attributeNameIndex, int sourceFileNameIndex) {
        this.attributeNameIndex = attributeNameIndex;
        this.sourceFileNameIndex = sourceFileNameIndex;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.sourceFileAttribute(attributeNameIndex, sourceFileNameIndex);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE;
    }

    @Override
    public Optional<List<EncodedBootstrapMethod>> oGetBootstrapMethods() {
        return Optional.empty();
    }
}
