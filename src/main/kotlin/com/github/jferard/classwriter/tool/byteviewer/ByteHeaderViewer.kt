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

import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.HeaderViewer
import java.io.IOException
import java.io.Writer

class ByteHeaderViewer(private val magic: Int, private val minorVersion: Int,
                       private val majorVersion: Int) :
        HeaderViewer {
    @Throws(IOException::class)
    override fun view(w: Writer) {
        w.append("/* HEADER */\n")
        w.append(String.format("%s, %s, %s, %s, // magic number\n",
                TextEncodedWriterHelper.hex(magic shr 24),
                TextEncodedWriterHelper.hex(magic shr 16),
                TextEncodedWriterHelper.hex(magic shr 8),
                TextEncodedWriterHelper.hex(magic)))
        w.append(String.format("%s, %s, // %d -> minor version\n",
                TextEncodedWriterHelper.hex(minorVersion shr 8),
                TextEncodedWriterHelper.hex(minorVersion),
                minorVersion))
        w.append(String.format("%s, %s, // %d -> major version\n",
                TextEncodedWriterHelper.hex(majorVersion shr 8),
                TextEncodedWriterHelper.hex(majorVersion),
                majorVersion))
    }

}