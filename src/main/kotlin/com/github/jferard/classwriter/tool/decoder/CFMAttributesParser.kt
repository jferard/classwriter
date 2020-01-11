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
package com.github.jferard.classwriter.tool.decoder

import com.github.jferard.classwriter.encoded.attribute.*
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.AnnotationsAttribute
import com.github.jferard.classwriter.internal.attribute.ConstantValueAttributeFactory
import com.github.jferard.classwriter.internal.attribute.DeprecatedAttribute
import com.github.jferard.classwriter.internal.attribute.SyntheticAttribute
import java.io.DataInput
import java.io.IOException

class CFMAttributesParser(private val annotationParser: AnnotationParser,
                          private val entries: List<EncodedConstantPoolEntry>) {
    @Throws(IOException::class)
    fun parse(index: Int, input: DataInput): EncodedCFMAttribute<*, *, *> {
        val constantPoolEntry = entries[index - 1]
        return when (constantPoolEntry.utf8Text()) {
            SyntheticAttribute.SYNTHETIC_NAME -> {
                check(input.readInt() == 0)
                EncodedSyntheticAttribute(index)
            }
            DeprecatedAttribute.DEPRECATED_NAME -> {
                check(input.readInt() == 0)
                EncodedDeprecatedAttribute(index)
            }
            ConstantValueAttributeFactory.SIGNATURE_NAME -> {
                check(input.readInt() == 2)
                val signatureIndex = input.readShort().toInt()
                EncodedSignatureAttribute(index, signatureIndex)
            }
            AnnotationsAttribute.RUNTIME_VISIBLE_ANNOTATIONS -> {
                val length = input.readInt()
                val numAnnotations = input.readShort()
                val encodedAnnotations =
                        (1..numAnnotations).map { annotationParser.parseAnnotation(input) }
                EncodedAnnotationsAttribute(index, encodedAnnotations)
            }
            AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS -> {
                val length = input.readInt()
                val numAnnotations = input.readShort()
                val encodedAnnotations =
                        (1..numAnnotations).map { annotationParser.parseAnnotation(input) }
                EncodedAnnotationsAttribute(index, encodedAnnotations)
            }
            else -> throw IllegalStateException(constantPoolEntry.utf8Text())
        }
    }

    companion object {
        fun create(entries: List<EncodedConstantPoolEntry>): CFMAttributesParser {
            return CFMAttributesParser(AnnotationParser(), entries)
        }
    }
}