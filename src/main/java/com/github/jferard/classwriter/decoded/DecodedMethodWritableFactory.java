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

package com.github.jferard.classwriter.decoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class DecodedMethodWritableFactory implements MethodWritableFactory<Writer> {
    public static DecodedMethodWritableFactory create() {
        return new DecodedMethodWritableFactory(DecodedAttributeWritableFactory.create());
    }

    private DecodedAttributeWritableFactory decodedAttributeWritableFactory;

    public DecodedMethodWritableFactory(
            DecodedAttributeWritableFactory decodedAttributeWritableFactory) {
        this.decodedAttributeWritableFactory = decodedAttributeWritableFactory;
    }

    @Override
    public Writable<Writer> method(int accessFlags, int nameIndex, int descriptorIndex,
                                   List<? extends EncodedAttribute> encodedAttributes) {
        final List<Decoded> decodedAttributes = encodedAttributes.stream()
                .map(a -> (Decoded) a.toWritable(this.decodedAttributeWritableFactory))
                .collect(Collectors.toList());
        return new DecodedMethod(accessFlags, nameIndex, descriptorIndex, decodedAttributes);
    }

    @Override
    public Writable<Writer> exceptionInCode(int startPc, int endPc, int handlerPc,
                                            int catchTypeIndex) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> methods(List<EncodedMethod> encodedMethods) {
        final List<Decoded> decodedMethods = encodedMethods.stream()
                .map(em -> (Decoded) em.toWritable(this)).collect(Collectors.toList());
        return new DecodedMethods(decodedMethods);
    }
}
