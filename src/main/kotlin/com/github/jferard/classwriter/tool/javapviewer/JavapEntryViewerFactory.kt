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
package com.github.jferard.classwriter.tool.javapviewer

import com.github.jferard.classwriter.tool.viewer.ConstantPoolEncodedEntryViewer
import com.github.jferard.classwriter.tool.viewer.EntryViewerFactory
import java.io.IOException
import java.io.Writer

class JavapEntryViewerFactory :
        EntryViewerFactory {
    @Throws(IOException::class)
    override fun writeEntryCount(writer: Writer, entryCount: Int) {
        writer!!.append(String.format("%s entries\n", entryCount))
    }

    override fun classEntryViewer(nameIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapIndexedEntryViewer("Class", nameIndex)
    }

    override fun fieldRefEntryViewer(classIndex: Int,
                                     nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapFieldOrMethodRefEntryViewer("FieldRef", classIndex, nameAndTypeIndex)
    }

    override fun methodRefEntryViewer(classIndex: Int,
                                      nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapFieldOrMethodRefEntryViewer("MethodRef", classIndex, nameAndTypeIndex)
    }

    override fun interfaceMethodRefEntryViewer(classIndex: Int,
                                               nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapFieldOrMethodRefEntryViewer("InterfaceMethodRef", classIndex,
                nameAndTypeIndex)
    }

    override fun stringEntryViewer(stringIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapIndexedEntryViewer("String", stringIndex)
    }

    override fun integerEntryViewer(i: Int): ConstantPoolEncodedEntryViewer {
        return JavapNumberEntryViewer("Integer", i)
    }

    override fun floatEntryViewer(f: Float): ConstantPoolEncodedEntryViewer {
        return JavapNumberEntryViewer("Float", f)
    }

    override fun longEntryViewer(l: Long): ConstantPoolEncodedEntryViewer {
        return JavapNumberEntryViewer("Long", l)
    }

    override fun doubleEntryViewer(d: Double): ConstantPoolEncodedEntryViewer {
        return JavapNumberEntryViewer("Double", d)
    }

    override fun nameAndTypeEntryViewer(nameIndex: Int,
                                        descriptorIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapNameAndTypeEntryViewer(nameIndex, descriptorIndex)
    }

    override fun utf8EntryViewer(utf: String): ConstantPoolEncodedEntryViewer {
        return JavapUTF8EntryViewer(utf)
    }

    override fun methodHandleEntryViewer(referenceKind: Byte,
                                         referenceIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapMethodHandleEntryViewer(referenceKind.toInt(), referenceIndex)
    }

    override fun methodTypeEntryViewer(
            descriptorIndex: Int): ConstantPoolEncodedEntryViewer {
        return JavapIndexedEntryViewer("MethodType", descriptorIndex)
    }

    override fun invokeDynamicEntryViewer(bootstrapMethodAttrIndex: Int,
                                          nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        throw IllegalArgumentException()
    }

    override fun unknown(tag: Int): ConstantPoolEncodedEntryViewer {
        throw IllegalArgumentException()
    }

    companion object {
        val CODE_FORMAT: String = "%-19s"
        val NUM_FORMAT: String = "%4s = "
    }
}