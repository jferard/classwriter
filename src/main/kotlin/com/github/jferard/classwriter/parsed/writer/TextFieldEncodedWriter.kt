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

import com.github.jferard.classwriter.encoded.EncodedField
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.access.FieldAccess
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.FieldEncodedWriter
import java.io.Writer

class TextFieldEncodedWriter(private val output: Writer,
                             private val entries: List<EncodedConstantPoolEntry>,
                             private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                             private val fieldAttributeEncodedWriter: TextFieldAttributeEncodedWriter) :
        FieldEncodedWriter {
    override fun fields(
            encodedFields: List<EncodedField>) {
        output.write("/** FIELDS */\n")
        for ((i, encodedField) in encodedFields.withIndex()) {
            encodedField.write(this)
        }
    }

    override fun field(accessFlags: Int, nameIndex: Int, descriptorIndex: Int,
                       encodedAttributes: List<EncodedFieldAttribute<*, *, FieldAttributeEncodedWriter>>) {
        TextEncodedWriterHelper.writeAccessFlags(output, FieldAccess.entries, accessFlags)
        output.write(
                "%s, %s // Field: #%s -> ".format(TextEncodedWriterHelper.hex(nameIndex shr 8), TextEncodedWriterHelper.hex(nameIndex),
                        nameIndex))
        entries[nameIndex - 1].write(summaryEncodedWriter)
        output.write("\n")
        for (encodedAttribute in encodedAttributes) {
            encodedAttribute.write(fieldAttributeEncodedWriter)
        }
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>): TextFieldEncodedWriter {
            return TextFieldEncodedWriter(output, entries,
                    TextConstantPoolEntriesSummaryEncodedWriter(output, entries),
                    TextFieldAttributeEncodedWriter.create(output, entries, TextConstantPoolEntriesSummaryEncodedWriter(output, entries)))
        }
    }

}