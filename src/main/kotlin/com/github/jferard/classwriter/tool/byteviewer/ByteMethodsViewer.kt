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

import com.github.jferard.classwriter.tool.viewer.ConstantPoolViewer
import com.github.jferard.classwriter.tool.viewer.MethodViewer
import com.github.jferard.classwriter.tool.viewer.MethodsViewer
import java.io.IOException
import java.io.Writer

class ByteMethodsViewer(private val methodViewers: List<MethodViewer>) :
        MethodsViewer {
    private var constantPoolViewer: ConstantPoolViewer? = null
    @Throws(IOException::class)
    override fun view(w: Writer) {
        val count = methodViewers.size
        w.append(String.format("%s, %s, // methods count: %d\n",
                ByteViewerFactory.hex(count shr 8),
                ByteViewerFactory.hex(count), count))
        for (methodViewer in methodViewers) {
            methodViewer.setConstantPoolViewer(constantPoolViewer!!)
            methodViewer.view(w)
        }
    }

    override fun setConstantPoolViewer(constantPoolViewer: ConstantPoolViewer) {
        this.constantPoolViewer = constantPoolViewer
    }

}