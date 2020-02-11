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
package com.github.jferard.classwriter.tool.byteviewer

import com.github.jferard.classwriter.Writable
import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.ConstantPoolEncodedEntryViewer
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.experimental.and

internal class ByteUTF8EntryViewer(private val text: String) :
        ConstantPoolEncodedEntryViewer {
    override fun view(
            entries: List<ConstantPoolEncodedEntryViewer>,
            i: Int): String {
        val bytes = text.toByteArray(StandardCharsets.UTF_8)
        val str = IntStream.range(0, bytes.size).mapToObj<String> { j: Int ->
            val b: Int = (bytes[j] and 0xff.toByte()).toInt()
            if (b >= 32) TextEncodedWriterHelper.chr(
                    b.toChar()) else TextEncodedWriterHelper.hex(b)
        }.collect(Collectors.joining(", "))
        val length = text.length
        return String.format(
                ByteEntryViewerFactory.NUM_FORMAT + "%s, %s, %s, %s, // %s", "#$i",
                "ConstantTags.UTF8", TextEncodedWriterHelper.hex(length shr 8),
                TextEncodedWriterHelper.hex(length), str, "u\"$text\"")
    }

    override fun viewSummary(
            entries: List<ConstantPoolEncodedEntryViewer>): String {
        return text
    }

    companion object {
        @Throws(IOException::class)
        private fun getBytes(writable: Writable<DataOutput>): ByteArray {
            val out = ByteArrayOutputStream()
            writable.write(DataOutputStream(out))
            return out.toByteArray()
        }
    }

}