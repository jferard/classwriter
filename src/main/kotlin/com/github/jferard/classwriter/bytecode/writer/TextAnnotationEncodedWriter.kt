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
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.parsed.writer.TextConstantPoolEntriesSummaryEncodedWriter
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper.hex
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import java.io.Writer

class TextAnnotationEncodedWriter(private val output: Writer,
                                  private val entries: List<EncodedConstantPoolEntry>,
                                  private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter) :
        AnnotationEncodedWriter {
    override fun annotation(descriptorIndex: Int,
                            encodedElementValuePairs: List<EncodedElementValuePair>) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "type", descriptorIndex, entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU2(output, "num_element_value_pairs",
                encodedElementValuePairs.size)
        encodedElementValuePairs.forEach { it.write(this) }
    }

    override fun elementValuePair(elementNameIndex: Int,
                                  encodedElementValue: EncodedElementValue) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "name", elementNameIndex, entries,
                summaryEncodedWriter)
        encodedElementValue.write(this)
    }

    override fun constantElementValue(tag: Int,
                                      elementValueIndex: Int) {

        output.write("%s, // tag: %s\n".format(
                hex(tag), tag.toChar()))
        TextEncodedWriterHelper.writeShortEntryIndex(output, "name", elementValueIndex, entries,
                summaryEncodedWriter)
    }

    override fun enumConstElementValue(typeNameIndex: Int,
                                       constNameIndex: Int) {
        output.write('e'.toInt())
        output.write(typeNameIndex)
        output.write(constNameIndex)
    }

    override fun arrayValue(values: List<EncodedElementValue>) {
        output.write("%s, // tag: [\n".format(hex('['.toInt())))
        TextEncodedWriterHelper.writeU2(output, "num_values", values.size)
        for (encodedElementValuePair in values) {
            encodedElementValuePair.write(this)
        }
    }
}