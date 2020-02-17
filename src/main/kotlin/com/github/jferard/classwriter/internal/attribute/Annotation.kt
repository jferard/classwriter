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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.api.FieldDescriptor
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValuePair
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.AnnotationEncodableWriter

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 */
class Annotation(private val descriptor: FieldDescriptor,
                 private val elementValuePairs: List<ElementValuePair>) :
        Encodable<Annotation, EncodedAnnotation, AnnotationEncodableWriter>, Sized {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedAnnotation {
        val descriptorIndex: Int = context.addToPool(descriptor.toPoolEntry())
        val encodedElementValuePairs: List<EncodedElementValuePair> =
                elementValuePairs.map { evp: ElementValuePair ->
                    evp.encode(context, codeContext)
                }
        return EncodedAnnotation(descriptorIndex,
                encodedElementValuePairs)
    }

    override fun getSize(pos: Int): Int = 1
    override fun write(encodableWriter: AnnotationEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}