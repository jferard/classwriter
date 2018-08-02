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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.internal.attribute.AnnotationWritableFactory;
import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValue;
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValuePair;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeClassAnnotationWritableFactory
        implements AnnotationWritableFactory<DataOutput> {
    @Override
    public ByteCode annotation(int descriptorIndex,
                               List<EncodedElementValuePair> encodedElementValuePairs) {
        final List<ByteCodeElementValuePair> writableElementValuePairs = encodedElementValuePairs
                .stream().map(elvp -> (ByteCodeElementValuePair) elvp.toWritable(this))
                .collect(Collectors.toList());
        return new ByteCodeWritableAnnotation(descriptorIndex, writableElementValuePairs);
    }

    @Override
    public ByteCode elementValuePair(int elementNameIndex,
                                     EncodedElementValue encodedElementValue) {
        final ByteCode writableElementValue = (ByteCode) encodedElementValue.toWritable(this);
        return new ByteCodeElementValuePair(elementNameIndex, writableElementValue);
    }

    @Override
    public Writable<DataOutput> constantElementValue(int tag, int elementValueIndex) {
        return new ByteCodeConstantElementValue(tag, elementValueIndex);
    }

    @Override
    public Writable<DataOutput> enumConstElementValue(int typeNameIndex, int constNameIndex) {
        return new ByteEnumConstElementValue(typeNameIndex, constNameIndex);
    }
}
