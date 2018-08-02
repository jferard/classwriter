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

import java.util.List;

/**
 * .7.16. The RuntimeVisibleAnnotations Attribute
 * <pre>{@code
 * Annotations_attribute {
 *     u2         attribute_name_index;
 *     u4         attribute_length;
 *     u2         num_annotations;
 *     annotation annotations[num_annotations];
 * }
 * }</pre>
 */
public class EncodedAnnotationAttribute implements EncodedAttribute {
    private final int annotationsNameIndex;
    private final int length;
    private final List<EncodedAnnotation> encodedAnnotations;

    public EncodedAnnotationAttribute(int annotationNameIndex, int length,
                                      List<EncodedAnnotation> encodedAnnotations) {
        this.annotationsNameIndex = annotationNameIndex;
        this.length = length;
        this.encodedAnnotations = encodedAnnotations;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory
                .annotationAttribute(this.annotationsNameIndex, this.encodedAnnotations);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE +
                encodedAnnotations.stream().mapToInt(EncodedAnnotation::getSize).sum();
    }
}
