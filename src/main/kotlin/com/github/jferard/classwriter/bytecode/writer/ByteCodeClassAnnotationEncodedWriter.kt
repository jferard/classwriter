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

class ByteCodeClassAnnotationEncodedWriter(private val output: DataOutput) :
        AnnotationEncodedWriter {
    override fun annotation(descriptorIndex: Int,
                            encodedElementValuePairs: List<EncodedElementValuePair>) {
        output.writeShort(descriptorIndex)
        for (encodedElementValuePair in encodedElementValuePairs) {
            encodedElementValuePair.write(this)
        }
    }

    override fun elementValuePair(elementNameIndex: Int,
                                  encodedElementValue: EncodedElementValue) {
        output.writeShort(elementNameIndex)
        encodedElementValue.write(this)
    }

    override fun constantElementValue(tag: Int,
                                      elementValueIndex: Int) {
        output.writeByte(tag)
        output.writeShort(elementValueIndex)
    }

    override fun enumConstElementValue(typeNameIndex: Int,
                                       constNameIndex: Int) {
        output.writeByte('e'.toInt())
        output.writeShort(typeNameIndex)
        output.writeShort(constNameIndex)
    }
}