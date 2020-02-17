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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.internal.attribute.ElementValuePair
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 * ```
 * {
 *     u2            element_name_index;
 *     element_value value;
 * } element_value_pairs
 * ```
 */
class EncodedElementValuePair(private val elementNameIndex: Int,
                              private val encodedElementValue: EncodedElementValue) :
        Encoded<ElementValuePair, EncodedElementValuePair, AnnotationEncodedWriter> {
    override fun write(writer: AnnotationEncodedWriter) {
        return writer.elementValuePair(elementNameIndex, encodedElementValue)
    }

    override fun getSize(
            pos: Int): Int = BytecodeHelper.SHORT_SIZE + encodedElementValue.getSize(0)

    override fun decode(context: GlobalContext, codeContext: MethodContext): ElementValuePair {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}