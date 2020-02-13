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
package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.encoded.attribute.EncodedElementValue
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValuePair
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import java.io.DataOutput
import java.util.logging.Logger

class ByteCodeClassAnnotationEncodedWriter(private val logger: Logger,
                                           private val output: DataOutput) :
        AnnotationEncodedWriter {
    /**
     * ```
     * annotation {
     *    u2 type_index;
     *    u2 num_element_value_pairs;
     *    {   u2            element_name_index;
     *        element_value value;
     *    } element_value_pairs[num_element_value_pairs];
     * }
     * ```
     */
    override fun annotation(descriptorIndex: Int,
                            encodedElementValuePairs: List<EncodedElementValuePair>) {
        logger.finer("Write annotation ($descriptorIndex, $encodedElementValuePairs)")
        output.writeShort(descriptorIndex)
        output.writeShort(encodedElementValuePairs.size)
        encodedElementValuePairs.forEach {
            it.write(this)
        }
    }

    /**
     * ```
     * {   u2            element_name_index;
     *     element_value value;
     * }
     * ```
     */
    override fun elementValuePair(elementNameIndex: Int,
                                  encodedElementValue: EncodedElementValue) {
        logger.finer("Write Element Value pair: $elementNameIndex -> $encodedElementValue")
        output.writeShort(elementNameIndex)
        encodedElementValue.write(this)
    }

    override fun constantElementValue(tag: Int,
                                      elementValueIndex: Int) {
        logger.finer("Write element: ${tag.toChar()} -> $elementValueIndex")
        output.writeByte(tag)
        output.writeShort(elementValueIndex)
    }

    override fun enumConstElementValue(typeNameIndex: Int,
                                       constNameIndex: Int) {
        output.writeByte('e'.toInt())
        output.writeShort(typeNameIndex)
        output.writeShort(constNameIndex)
    }

    override fun arrayValue(values: List<EncodedElementValue>) {
        logger.finer("Write array value")
        output.writeByte('['.toInt())
        output.writeShort(values.size)
        values.forEach { it.write(this) }
    }
}