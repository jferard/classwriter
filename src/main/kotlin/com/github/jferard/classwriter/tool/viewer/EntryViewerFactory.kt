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

import java.io.IOException
import java.io.Writer

interface EntryViewerFactory {
    fun classEntryViewer(readShort: Int): ConstantPoolEncodedEntryViewer
    fun fieldRefEntryViewer(classIndex: Int,
                            nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer

    fun methodRefEntryViewer(classIndex: Int,
                             nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer

    fun interfaceMethodRefEntryViewer(classIndex: Int,
                                      nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer

    fun stringEntryViewer(stringIndex: Int): ConstantPoolEncodedEntryViewer
    fun integerEntryViewer(i: Int): ConstantPoolEncodedEntryViewer
    fun floatEntryViewer(f: Float): ConstantPoolEncodedEntryViewer
    fun longEntryViewer(l: Long): ConstantPoolEncodedEntryViewer
    fun doubleEntryViewer(d: Double): ConstantPoolEncodedEntryViewer
    fun nameAndTypeEntryViewer(nameIndex: Int,
                               descriptorIndex: Int): ConstantPoolEncodedEntryViewer

    fun utf8EntryViewer(utf: String): ConstantPoolEncodedEntryViewer
    fun methodHandleEntryViewer(referenceKind: Byte,
                                referenceIndex: Int): ConstantPoolEncodedEntryViewer

    fun methodTypeEntryViewer(descriptorIndex: Int): ConstantPoolEncodedEntryViewer
    fun invokeDynamicEntryViewer(bootstrapMethodAttrIndex: Int,
                                 nameAndTypeIndex: Int): ConstantPoolEncodedEntryViewer

    fun unknown(tag: Int): ConstantPoolEncodedEntryViewer
    @Throws(IOException::class)
    fun writeEntryCount(writer: Writer, entryCount: Int)
}