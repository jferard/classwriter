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
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.writer.encoded.AnnotationsEncodedWriter

class EncodedParameterAnnotationsAttribute(private val attributeNameIndex: Int, private val
parameterAnnotations: List<List<EncodedAnnotation>>) :
        EncodedCFMAttribute<ParameterAnnotationsAttribute, EncodedParameterAnnotationsAttribute, AnnotationsEncodedWriter> {
    override fun write(encodedWriter: AnnotationsEncodedWriter) {
        parameterAnnotations.forEach {
            encodedWriter.annotationAttribute(attributeNameIndex, it)
        }
    }

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): ParameterAnnotationsAttribute {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.BYTE_SIZE +
                    parameterAnnotations.map {
                        BytecodeHelper.SHORT_SIZE + it.map(EncodedAnnotation::size).sum()
                    }.sum()

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }
}