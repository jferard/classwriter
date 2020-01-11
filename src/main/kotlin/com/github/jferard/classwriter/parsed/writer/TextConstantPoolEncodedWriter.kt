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

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.tool.byteviewer.ByteViewerFactory
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import java.io.IOException
import java.io.Writer

class TextConstantPoolEncodedWriter(private val output: Writer,
                                    private val constantPoolEntriesEncodedWriter: ConstantPoolEntriesEncodedWriter) :
        ConstantPoolEncodedWriter {
    override fun constantPool(
            entries: List<EncodedConstantPoolEntry>,
            bootstrapMethods: List<EncodedBootstrapMethod>?) {
        /** We'll need this
        val decodedConstantPoolEntriesEncodedWriter: DecodedConstantPoolEntriesEncodedWriter =
        DecodedConstantPoolEntriesEncodedWriter.create(this.entries,
        this.bootstrapMethods)
         */
        output.append("/* CONSTANT POOL */\n")
        this.viewEntryCount(output, entries.size)
        for (i in 1 until entries.size) {
            val entry = entries[i - 1]
            output.append(String.format("/* %4s */ ", "#$i"))
            entry.write(constantPoolEntriesEncodedWriter)
            output.append('\n')
        }
    }

    @Throws(IOException::class)
    private fun viewEntryCount(writer: Writer, entryCount: Int) {
        writer.append(String.format("%s, %s, // number of writableEntries: %d\n",
                ByteViewerFactory.hex(entryCount + 1 shr 8),
                ByteViewerFactory.hex(entryCount + 1),
                entryCount))
    }

}