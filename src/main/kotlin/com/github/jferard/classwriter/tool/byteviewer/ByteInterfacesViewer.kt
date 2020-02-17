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
import com.github.jferard.classwriter.tool.viewer.InterfaceViewer
import com.github.jferard.classwriter.tool.viewer.InterfacesViewer
import java.io.IOException
import java.io.Writer

class ByteInterfacesViewer(private val interfaceViewers: List<InterfaceViewer>) :
        InterfacesViewer {
    @Throws(IOException::class)
    override fun view(w: Writer) {
        val count = interfaceViewers.size
        w.append(String.format("%s, %s, // interfaces count: %d\n",
                TextEncodedWriterHelper.hex(count shr 8),
                TextEncodedWriterHelper.hex(count), count))
        for (interfaceViewer in interfaceViewers) {
            interfaceViewer.view(w)
        }
    }

}