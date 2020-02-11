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

import com.github.jferard.classwriter.internal.access.MethodAccess
import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.ConstantPoolViewer
import com.github.jferard.classwriter.tool.viewer.MethodAttributeViewer
import com.github.jferard.classwriter.tool.viewer.MethodViewer
import java.io.IOException
import java.io.Writer

class ByteMethodViewer(private val accessFlags: Int, private val nameIndex: Int,
                       private val descriptorIndex: Int,
                       private val methodAttributeViewers: List<MethodAttributeViewer>) :
        MethodViewer {
    private var constantPoolViewer: ConstantPoolViewer? = null
    @Throws(IOException::class)
    override fun view(w: Writer) {
        w.append(FlagJoiner.getAccessFlags(MethodAccess::class.java, accessFlags))
        w.append(String.format("%s, %s, // name:%s -> %s\n",
                TextEncodedWriterHelper.hex(nameIndex shr 8),
                TextEncodedWriterHelper.hex(nameIndex),
                "#" + nameIndex, constantPoolViewer!!.entrySummary(nameIndex)))
        w.append(String.format("%s, %s, // descriptor: %s -> %s\n",
                TextEncodedWriterHelper.hex(descriptorIndex shr 8),
                TextEncodedWriterHelper.hex(descriptorIndex), "#" + descriptorIndex,
                constantPoolViewer!!.entrySummary(descriptorIndex)))
        for (methodAttributeViewer in methodAttributeViewers) {
            methodAttributeViewer.view(w)
        }
    }

    override fun setConstantPoolViewer(constantPoolViewer: ConstantPoolViewer) {
        this.constantPoolViewer = constantPoolViewer
    }

}