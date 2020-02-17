/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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

package com.github.jferard.classwriter.text.writer

import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import java.io.IOException
import java.io.Writer

object TextEncodedWriterHelper {
    @Throws(IOException::class)
    fun writeU1(writer: Writer, comment: String, u1: Int) {
        writer.write("%s, // %s: %s\n".format(
                hex(u1), comment, u1))
    }

    @Throws(IOException::class)
    fun writeU2(writer: Writer, comment: String, u2: Int) {
        writer.write("%s, %s, // %s: %s\n".format(
                hex(u2 shr 8), hex(u2), comment, u2))
    }

    @Throws(IOException::class)
    fun writeU4(writer: Writer, comment: String, u4: Int) {
        writer.write("%s, %s, %s, %s, // %s: %s\n".format(
                hex(u4 shr 24), hex(u4 shr 16), hex(u4 shr 8), hex(u4), comment, u4))
    }

    @Throws(IOException::class)
    fun writeAccessFlags(writer: Writer,
                         entries: List<Pair<Int, String>>,
                         accessFlags: Int) {
        val flags = entries.filter { (flag, _) -> accessFlags and flag != 0 }
                .joinToString(" | ") { (_, name) -> name }
        writer.write(
                "%s, %s, // access flags:#%s -> %s\n".format(
                        hex(accessFlags shr 8),
                        hex(accessFlags), accessFlags, flags))
    }

    fun writeShortEntryIndex(output: Writer, name: String,
                             attributeNameIndex: Int,
                             entries: List<EncodedConstantPoolEntry>,
                             summaryEncodedWriter: ConstantPoolEntriesEncodedWriter) {
        output.write(
                "%s, %s, // $name: #%s -> ".format(
                        hex(attributeNameIndex shr 8),
                        hex(attributeNameIndex), attributeNameIndex))
        entries[attributeNameIndex - 1].write(summaryEncodedWriter)
        output.write("\n")
    }

    fun writeByteEntryIndex(output: Writer, name: String,
                             attributeNameIndex: Int,
                             entries: List<EncodedConstantPoolEntry>,
                             summaryEncodedWriter: ConstantPoolEntriesEncodedWriter) {
        output.write(
                "%s, // $name: #%s -> ".format(
                        hex(attributeNameIndex), attributeNameIndex))
        entries[attributeNameIndex - 1].write(summaryEncodedWriter)
        output.write("\n")
    }

    fun hex(shifted: Int): String {
        val s: String
        val b = shifted and 0xff
        s = if (b > Byte.MAX_VALUE) {
            String.format("0x%02X.toByte()", b)
        } else {
            String.format("0x%02X", b)
        }
        return s
    }

    fun chr(b: Char): String {
        return if (b == '\\') {
            "'\\\\'"
        } else {
            String.format("'%c'", b)
        }
    }
}
