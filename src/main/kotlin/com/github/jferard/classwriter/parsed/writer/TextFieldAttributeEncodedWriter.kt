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
package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.SignatureAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextFieldAttributeEncodedWriter(private val output: Writer,
                                      private val entries: List<EncodedConstantPoolEntry>,
                                      private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter) :
        FieldAttributeEncodedWriter,
        SignatureAttributeEncodedWriter {
    override fun constantValueAttribute(attributeNameIndex: Int,
                                        valueIndex: Int) {
        output.write("constant\n")
    }

    override fun deprecatedAttribute(attributeNameIndex: Int) {
        output.write("deprecated\n")
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        output.write("synthetic\n")
    }

    override fun signatureAttribute(attributeNameIndex: Int,
                                    signatureIndex: Int) {
        output.write(
                "%s, %s, // Attribute: #%s -> ".format(TextEncodedWriterHelper.hex(attributeNameIndex shr 8),
                        TextEncodedWriterHelper.hex(attributeNameIndex), attributeNameIndex))
        entries[attributeNameIndex - 1].write(summaryEncodedWriter)
        output.write("\n")
        TextEncodedWriterHelper.writeU2(output, "length", 2)
        output.write(
                "%s, %s, // Signature: #%s -> ".format(TextEncodedWriterHelper.hex(signatureIndex shr 8),
                        TextEncodedWriterHelper.hex(signatureIndex), signatureIndex))
        entries[signatureIndex - 1].write(summaryEncodedWriter)

    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter): TextFieldAttributeEncodedWriter {
            return TextFieldAttributeEncodedWriter(output, entries, summaryEncodedWriter)
        }
    }

}