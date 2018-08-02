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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeExceptionInCode;
import com.github.jferard.classwriter.encoded.EncodedMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeMethodWritableFactory implements MethodWritableFactory<DataOutput> {
    private AttributeWritableFactory<DataOutput> classAttributeWritableFactory;

    public ByteCodeMethodWritableFactory(
            AttributeWritableFactory<DataOutput> classAttributeWritableFactory) {
        this.classAttributeWritableFactory = classAttributeWritableFactory;
    }

    @Override
    public ByteCode method(int accessFlags, int nameIndex, int descriptorIndex,
                           List<? extends EncodedAttribute> encodedAttributes) {
        List<ByteCode> writableAttributes = encodedAttributes.stream()
                .map(ea -> (ByteCode) ea.toWritable(this.classAttributeWritableFactory))
                .collect(Collectors.toList());
        return new ByteCodeMethodWriter(accessFlags, nameIndex, descriptorIndex,
                writableAttributes);
    }

    @Override
    public ByteCode exceptionInCode(int startPc, int endPc, int handlerPc, int catchTypeIndex) {
        return new ByteCodeExceptionInCode(startPc, endPc, handlerPc, catchTypeIndex);
    }

    @Override
    public Writable<DataOutput> methods(List<EncodedMethod> encodedMethods) {
        List<ByteCode> writableMethods = encodedMethods.stream()
                .map(ef -> (ByteCode) ef.toWritable(this)).collect(Collectors.toList());
        return new WritableMethods(writableMethods);
    }
}
