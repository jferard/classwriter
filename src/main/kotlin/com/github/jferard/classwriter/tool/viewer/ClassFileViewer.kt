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
package com.github.jferard.classwriter.tool.viewer

import com.github.jferard.classwriter.tool.viewer.*
import java.io.IOException
import java.io.Writer

class ClassFileViewer(private val headerViewer: HeaderViewer,
                      private val constantPoolViewer: ConstantPoolViewer,
                      private val accessViewer: AccessThisSuperViewer,
                      private val interfacesViewer: InterfacesViewer,
                      private val fieldsViewer: FieldsViewer,
                      private val methodsViewer: MethodsViewer,
                      private val classAttributesViewer: ClassAttributesViewer) :
        Viewer {
    @Throws(IOException::class)
    override fun view(w: Writer) {
        accessViewer!!.setConstantPoolViewer(constantPoolViewer)
        methodsViewer!!.setConstantPoolViewer(constantPoolViewer)
        w!!.append("/**************/\n")
        w.append("/* CLASS FILE */\n")
        w.append("/**************/\n")
        headerViewer!!.view(w)
        constantPoolViewer!!.view(w)
        accessViewer.view(w)
        interfacesViewer!!.view(w)
        fieldsViewer!!.view(w)
        methodsViewer.view(w)
        classAttributesViewer!!.view(w)
    }

}