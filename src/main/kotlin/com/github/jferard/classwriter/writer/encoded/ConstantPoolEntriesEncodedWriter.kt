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
package com.github.jferard.classwriter.writer.encoded

import com.github.jferard.classwriter.api.EncodedWriter

interface ConstantPoolEntriesEncodedWriter : EncodedWriter {
    fun classEntry(nameIndex: Int)
    fun utf8Entry(text: String)
    fun invokeDynamicEntry(bootstrapMethodIndex: Int, descriptorIndex: Int)
    fun nameAndTypeEntry(nameIndex: Int, descriptorIndex: Int)
    fun methodRefEntry(classIndex: Int, nameAndTypeIndex: Int)
    fun doubleEntry(value: Double)
    fun longEntry(value: Long)
    fun floatEntry(value: Float)
    fun integerEntry(value: Int)
    fun methodHandleEntry(kind: Int, index: Int)
    fun methodTypeEntry(descriptorIndex: Int)
    fun fieldRefEntry(classIndex: Int, nameAndTypeIndex: Int)
    fun stringEntry(index: Int)
}