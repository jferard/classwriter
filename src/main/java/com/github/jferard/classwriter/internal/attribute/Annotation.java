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

import com.github.jferard.classwriter.api.FieldDescriptor;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation;
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValuePair;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 */
public class Annotation implements Encodable<EncodedAnnotation> {
    private final List<ElementValuePair> elementValuePairs;
    private final FieldDescriptor descriptor;

    public Annotation(FieldDescriptor descriptor, List<ElementValuePair> elementValuePairs) {
        this.descriptor = descriptor;
        this.elementValuePairs = elementValuePairs;
    }


    @Override
    public EncodedAnnotation encode(GlobalContext context, MethodContext codeContext) {
        int descriptorIndex = context.addToPool(this.descriptor.toPoolEntry());
        final List<EncodedElementValuePair> encodedElementValuePairs = this.elementValuePairs.stream()
                .map(evp -> evp.encode(context, codeContext)).collect(Collectors.toList());
        return new EncodedAnnotation(descriptorIndex,
                encodedElementValuePairs);
    }

    public int size() {
        return 1;
    }
}
