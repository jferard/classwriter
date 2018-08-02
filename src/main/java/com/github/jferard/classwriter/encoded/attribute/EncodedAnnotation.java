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
import com.github.jferard.classwriter.internal.attribute.AnnotationWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;

import java.util.List;

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 * <pre>{@code
 * annotation {
 *     u2 type_index;
 *     u2 num_element_value_pairs;
 *     {   u2            element_name_index;
 *         element_value value;
 *     } element_value_pairs[num_element_value_pairs];
 * }
 * }</pre>
 */
public class EncodedAnnotation implements Encoded<AnnotationWritableFactory<?>> {
    private final int typeIndex;
    private final List<EncodedElementValuePair> encodedElementValuePairs;

    public EncodedAnnotation(int typeIndex,
                             List<EncodedElementValuePair> encodedElementValuePairs) {

        this.typeIndex = typeIndex;
        this.encodedElementValuePairs = encodedElementValuePairs;
    }

    @Override
    public Writable<?> toWritable(AnnotationWritableFactory<?> annotationWritableFactory) {
        return annotationWritableFactory.annotation(this.typeIndex, this.encodedElementValuePairs);
    }


    @Override
    public int getSize() {
        return 2 * BytecodeHelper.SHORT_SIZE +
                encodedElementValuePairs.stream().mapToInt(Encoded::getSize).sum();
    }
}
