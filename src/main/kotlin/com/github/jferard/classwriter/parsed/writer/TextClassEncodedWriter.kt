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

import com.github.jferard.classwriter.api.Header
import com.github.jferard.classwriter.encoded.*
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.internal.access.FieldAccess
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.writer.encoded.ClassEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import com.github.jferard.classwriter.writer.encoded.FieldEncodedWriter
import java.io.Writer

class TextClassEncodedWriter(private val output: Writer,
                             private val entries: List<EncodedConstantPoolEntry>,
                             private val constantPoolEntriesSummaryEncodedWriter: ConstantPoolEntriesEncodedWriter,
                             private val constantPoolEncodedWriter: ConstantPoolEncodedWriter,
                             private val fieldEncodedWriter: FieldEncodedWriter) :
        ClassEncodedWriter {
    override fun classFile(header: Header, constantPool: ConstantPool, accessFlags: Int,
                           thisIndex: Int,
                           superIndex: Int, encodedInterfaces: EncodedInterfaces,
                           encodedFields: EncodedFields, encodedMethods: EncodedMethods,
                           encodedAttributes: EncodedClassFileAttributes) {
        output.write("/** CLASS FILE */")
        header.write(this)
        constantPool.write(constantPoolEncodedWriter)
        TextEncodedWriterHelper.writeAccessFlags(output, ClassAccess.entries, accessFlags)
        writeThisAndSuper(thisIndex, superIndex)
        encodedInterfaces.write(this)
        encodedFields.write(fieldEncodedWriter)
    }

    private fun writeThisAndSuper(thisIndex: Int, superIndex: Int) {
        thisOrSuper(thisIndex, "this index")
        thisOrSuper(superIndex, "super index")
    }

    private fun thisOrSuper(index: Int, comment: String) {
        output.append(String.format("%s, %s, // $comment: #$index -> ",
                TextEncodedWriterHelper.hex(index shr 8),
                TextEncodedWriterHelper.hex(index)))
        entries[index - 1].write(constantPoolEntriesSummaryEncodedWriter)
        output.write("\n")
    }

    override fun header(minorVersion: Int, majorVersion: Int) {
        output.write("/** header */\n")
        TextEncodedWriterHelper.writeU4(output, "magic", EncodedClassFile.MAGIC_NUMBER)
        TextEncodedWriterHelper.writeU2(output, "minor version", minorVersion)
        TextEncodedWriterHelper.writeU2(output, "major version", majorVersion)
    }

    override fun interfaces(encodedInterfaces: List<Int>) {
        output.write("/** Interfaces */\n")
        TextEncodedWriterHelper.writeU2(output, "number of interfaces", encodedInterfaces.size)
        for ((i, encodedInterface) in encodedInterfaces.withIndex()) {
            output.write(
                    "%s, %s, // #%s -> ".format(TextEncodedWriterHelper.hex(encodedInterface shr 8),
                            TextEncodedWriterHelper.hex(encodedInterface),
                            encodedInterface))
            entries[encodedInterface - 1].write(constantPoolEntriesSummaryEncodedWriter)
            output.write("\n")
        }
    }

    override fun bootstrapMethod(methodRefIndex: Int, argumentIndexes: List<Int>) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>): TextClassEncodedWriter {
            return TextClassEncodedWriter(output, entries,
                    TextConstantPoolEntriesSummaryEncodedWriter(output, entries),
                    TextConstantPoolEncodedWriter.create(output, entries),
                    TextFieldEncodedWriter.create(output, entries))
        }
    }
}