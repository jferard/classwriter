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

import com.github.jferard.classwriter.tool.parser.*
import com.github.jferard.classwriter.tool.viewer.*

class ByteViewerFactory(private val entryViewerFactory: EntryViewerFactory,
                        private val methodAttributeViewerFactory: MethodAttributeViewerFactory) :
        ViewerFactory {
    override fun header(magic: Int, minorVersion: Int, majorVersion: Int): HeaderViewer {
        return ByteHeaderViewer(magic, minorVersion, majorVersion)
    }

    override fun constantPool(
            entries: List<ConstantPoolEntryParser>): ConstantPoolViewer {
        return ByteConstantPoolViewer(
                entries.map { e -> e.decode(entryViewerFactory) as ConstantPoolEncodedEntryViewer
                })
    }

    override fun accessThisSuper(accessFlags: Short, thisIndex: Short,
                                 superIndex: Short): AccessThisSuperViewer {
        return ByteAccessThisSuperViewer(accessFlags.toInt(), thisIndex.toInt(), superIndex.toInt())
    }

    override fun interfaces(interfaceViewers: List<InterfaceViewer>): InterfacesViewer {
        return ByteInterfacesViewer(interfaceViewers)
    }

    override fun fields(fieldViewers: List<FieldViewer>): FieldsViewer {
        return ByteFieldsViewer(fieldViewers)
    }

    override fun methods(methods: List<MethodParser>): MethodsViewer {
        //return ByteMethodsViewer(methods.map { m -> m })
        //            m.parse(methodAttributeViewerFactory) as MethodViewer
        TODO()
    }

    override fun classAttributes(
            classAttributeViewers: List<ClassAttributeViewer>): ClassAttributesViewer {
        return ByteClassAttributesViewer(classAttributeViewers)
    }

    companion object {
        fun create(): ByteViewerFactory {
            return ByteViewerFactory(ByteEntryViewerFactory(),
                    ByteMethodAttributeViewerFactory())
        }

    }

}