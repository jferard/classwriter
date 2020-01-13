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

import com.github.jferard.classwriter.encoded.EncodedMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.access.MethodAccess
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodEncodedWriter
import java.io.Writer

class TextMethodEncodedWriter(private val output: Writer,
                              private val entries: List<EncodedConstantPoolEntry>,
                              private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                              private val methodAttributeEncodedWriter: TextMethodAttributeEncodedWriter) :
        MethodEncodedWriter {
    override fun method(accessFlags: Int, nameIndex: Int, descriptorIndex: Int,
                        encodedAttributes: List<EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>>) {
        output.append("/** method ")
        entries[nameIndex - 1].write(summaryEncodedWriter)
        output.append(" ")
        entries[descriptorIndex - 1].write(summaryEncodedWriter)
        output.append("\n")
        TextEncodedWriterHelper.writeAccessFlags(output, MethodAccess.entries, accessFlags)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "nameIndex", nameIndex, entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "descriptorIndex", descriptorIndex, entries,
                summaryEncodedWriter)
        encodedAttributes.forEach { it.write(methodAttributeEncodedWriter) }
    }

    override fun methods(
            encodedMethods: List<EncodedMethod>) {
        output.write("/* METHODS */\n")
        encodedMethods.forEach { it.write(this) }
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   constantPoolEntriesSummaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter): TextMethodEncodedWriter {
            return TextMethodEncodedWriter(output, entries, constantPoolEntriesSummaryEncodedWriter,
                    TextMethodAttributeEncodedWriter.create(output, entries))
        }
    }

}