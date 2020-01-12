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

import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.AccessThisSuperViewer
import com.github.jferard.classwriter.tool.viewer.ConstantPoolViewer
import java.io.IOException
import java.io.Writer

class ByteAccessThisSuperViewer(private val accessFlags: Int, private val thisIndex: Int,
                                private val superIndex: Int) :
        AccessThisSuperViewer {
    private var viewer: ConstantPoolViewer? = null
    override fun setConstantPoolViewer(viewer: ConstantPoolViewer) {
        this.viewer = viewer
    }

    @Throws(IOException::class)
    override fun view(w: Writer) {
        w.append(FlagJoiner.getAccessFlags(ClassAccess::class.java, accessFlags))
        w.append(String.format("%s, %s, // this: %s -> %s\n",
                TextEncodedWriterHelper.hex(thisIndex shr 8),
                TextEncodedWriterHelper.hex(thisIndex),
                "#" + thisIndex, viewer!!.entrySummary(thisIndex)))
        w.append(String.format("%s, %s, // super: %s -> %s\n",
                TextEncodedWriterHelper.hex(superIndex),
                TextEncodedWriterHelper.hex(superIndex shr 8),
                "#" + superIndex, viewer!!.entrySummary(superIndex)))
    }

}