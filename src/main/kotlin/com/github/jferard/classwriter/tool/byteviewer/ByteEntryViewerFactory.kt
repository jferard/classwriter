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

import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper
import com.github.jferard.classwriter.tool.viewer.ConstantPoolEncodedEntryViewer
import com.github.jferard.classwriter.tool.viewer.EntryViewerFactory
import java.io.IOException
import java.io.Writer

class ByteEntryViewerFactory :
        EntryViewerFactory {
    @Throws(IOException::class)
    override fun writeEntryCount(writer: Writer, entryCount: Int) {
        writer.append("/* CONSTANT POOL */\n")
        writer.append(String.format("%s, %s, // number of entries: %d\n",
                TextEncodedWriterHelper.hex(entryCount + 1 shr 8),
                TextEncodedWriterHelper.hex(entryCount + 1),
                entryCount))
    }

    override fun classEntryViewer(nameIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteIndexedEntryViewer("ConstantTags.CLASS", nameIndex)
    }

    override fun fieldRefEntryViewer(classIndex: Int,
                                     nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteFieldOrMethodRefEntryViewer("ConstantTags.FIELDREF", classIndex,
                nameAndTypeIndex)
    }

    override fun methodRefEntryViewer(classIndex: Int,
                                      nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteFieldOrMethodRefEntryViewer("ConstantTags.METHODREF", classIndex,
                nameAndTypeIndex)
    }

    override fun interfaceMethodRefEntryViewer(classIndex: Int,
                                               nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteFieldOrMethodRefEntryViewer("ConstantTags.INTERFACEMETHODREF", classIndex,
                nameAndTypeIndex)
    }

    override fun stringEntryViewer(stringIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteIndexedEntryViewer("ConstantTags.STRING", stringIndex)
    }

    override fun integerEntryViewer(i: Int): ConstantPoolEncodedEntryViewer {
        return ByteNumberEntryViewer("ConstantTags.INTEGER", i)
    }

    override fun floatEntryViewer(f: Float): ConstantPoolEncodedEntryViewer {
        return ByteNumberEntryViewer("ConstantTags.FLOAT", f)
    }

    override fun longEntryViewer(l: Long): ConstantPoolEncodedEntryViewer {
        return ByteNumberEntryViewer("ConstantTags.LONG", l)
    }

    override fun doubleEntryViewer(d: Double): ConstantPoolEncodedEntryViewer {
        return ByteNumberEntryViewer("ConstantTags.DOUBLE", d)
    }

    override fun nameAndTypeEntryViewer(nameIndex: Int,
                                        descriptorIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteNameAndTypeEntryViewer(nameIndex, descriptorIndex)
    }

    override fun utf8EntryViewer(utf: String): ConstantPoolEncodedEntryViewer {
        return ByteUTF8EntryViewer(utf)
    }

    override fun methodHandleEntryViewer(referenceKind: Byte,
                                         referenceIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteMethodHandleEntryViewer(referenceKind.toInt(), referenceIndex)
    }

    override fun methodTypeEntryViewer(
            descriptorIndex: Int): ConstantPoolEncodedEntryViewer {
        return ByteIndexedEntryViewer("ConstantTags.METHODTYPE", descriptorIndex)
    }

    override fun invokeDynamicEntryViewer(bootstrapMethodAttrIndex: Int,
                                          nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer {
        throw IllegalArgumentException()
    }

    override fun unknown(tag: Int): ConstantPoolEncodedEntryViewer {
        throw IllegalArgumentException()
    }

    companion object {
        const val NUM_FORMAT = "/* %4s */ "
    }
}