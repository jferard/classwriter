/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.attribute.Annotation
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 * ```annotation {
 * u2 type_index;
 * u2 num_element_value_pairs;
 * {   u2            element_name_index;
 * element_value value;
 * } element_value_pairs[num_element_value_pairs];
 * }
 * ``` *
 */
class EncodedAnnotation(private val typeIndex: Int,
                        private val encodedElementValuePairs: List<EncodedElementValuePair>) :
        Encoded<Annotation, EncodedAnnotation, AnnotationEncodedWriter> {
    override fun write(
            encodedWriter: AnnotationEncodedWriter) {
        return encodedWriter.annotation(typeIndex, encodedElementValuePairs)
    }

    override fun getSize(pos: Int): Int =
            2 * BytecodeHelper.SHORT_SIZE + Sized.listSize(0, encodedElementValuePairs)

    override fun decode(context: GlobalContext, codeContext: MethodContext): Annotation {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}