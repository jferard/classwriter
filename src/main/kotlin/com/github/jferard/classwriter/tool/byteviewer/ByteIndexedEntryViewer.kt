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
package com.github.jferard.classwriter.tool.byteviewer

import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.ConstantPoolEncodedEntryViewer

internal class ByteIndexedEntryViewer(private val code: String, private val index: Int) :
        ConstantPoolEncodedEntryViewer {
    override fun view(
            entries: List<ConstantPoolEncodedEntryViewer>,
            i: Int): String {
        return kotlin.String.format(
                ByteEntryViewerFactory.NUM_FORMAT + "%s, %s, %s, // %s", "#$i",
                code,
                TextEncodedWriterHelper.hex(index shr 8),
                TextEncodedWriterHelper.hex(index),
                "#" + index + " -> \"" + viewSummary(entries) + "\"")
    }

    override fun viewSummary(
            entries: List<ConstantPoolEncodedEntryViewer>): String {
        return entries[index-1].viewSummary(entries).replace('/', '.')
    }

}