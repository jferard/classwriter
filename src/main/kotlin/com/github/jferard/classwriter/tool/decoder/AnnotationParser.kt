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
import java.io.DataInput

class AnnotationParser {
    /** 4.7.16. The RuntimeVisibleAnnotations Attribute */
    fun parseAnnotation(input: DataInput): EncodedAnnotation {
        val typeIndex = input.readShort().toInt()
        val numElements = input.readShort()
        val encodedElementValuePairs = (1..numElements).map {
            val nameIndex = input.readShort().toInt()
            val elementValue = parseValue(input)
            EncodedElementValuePair(nameIndex, elementValue)
        }
        return EncodedAnnotation(typeIndex, encodedElementValuePairs)
    }

    /** 4.7.16.1. The element_value structure
     * Table 4.7.16.1-A. Interpretation of tag values as types
     */
    private fun parseValue(input: DataInput): EncodedElementValue {
        val tag = input.readUnsignedByte()
        return when (tag.toChar()) {
            'B', 'C', 'D', 'F', 'I', 'J', 'S', 'Z', 's' -> {
                val constValueIndex = input.readShort()
                EncodedConstantElementValue(tag.toInt(), constValueIndex.toInt())
            }
            'e' -> {
                val typeNameIndex = input.readShort()
                val constNameIndex = input.readShort()
                EncodedEnumConstElementValue(typeNameIndex.toInt(), constNameIndex.toInt())
            }
            'c' -> {
                val classIndex = input.readShort()
                EncodedClassElementValue(classIndex.toInt())
            }
            '@' -> {
                val annotation = parseAnnotation(input)
                EncodedAnnotationValue(annotation)
            }
            '[' -> {
                val numValues = input.readShort()
                val values = (1..numValues).map { parseValue(input) }
                EncodedArrayValue(values)
            }
            else -> throw IllegalArgumentException("Bad value tag: ${tag.toChar()}")
        }
    }
}
