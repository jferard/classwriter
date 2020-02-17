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
package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import com.github.jferard.classwriter.pool.ConstantTags
import java.io.DataOutputStream

class ByteCodeConstantPoolEntriesEncodedWriter(private val output: DataOutputStream) :
        ConstantPoolEntriesEncodedWriter {
    override fun classEntry(nameIndex: Int) {
        output.writeByte(ConstantTags.CLASS.toInt())
        output.writeShort(nameIndex)
    }

    override fun utf8Entry(text: String) {
        output.writeByte(ConstantTags.UTF8.toInt())
        output.writeUTF(text)
    }

    override fun invokeDynamicEntry(bootstrapMethodIndex: Int,
                                    descriptorIndex: Int) {
        output.writeByte(ConstantTags.INVOKE_DYNAMIC.toInt())
        output.writeShort(bootstrapMethodIndex)
        output.writeShort(descriptorIndex)
    }

    override fun nameAndTypeEntry(nameIndex: Int, descriptorIndex: Int) {
        output.writeByte(ConstantTags.NAME_AND_TYPE.toInt())
        output.writeShort(nameIndex)
        output.writeShort(descriptorIndex)
    }

    override fun methodRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        output.writeByte(ConstantTags.METHOD_REF.toInt())
        output.writeShort(classIndex)
        output.writeShort(nameAndTypeIndex)
    }

    override fun doubleEntry(value: Double) {
        output.writeByte(ConstantTags.DOUBLE.toInt())
        output.writeDouble(value)
    }

    override fun longEntry(value: Long) {
        output.writeByte(ConstantTags.LONG.toInt())
        output.writeLong(value)
    }

    override fun floatEntry(value: Float) {
        output.writeByte(ConstantTags.FLOAT.toInt())
        output.writeFloat(value)
    }

    override fun integerEntry(value: Int) {
        output.writeByte(ConstantTags.INTEGER.toInt())
        output.writeInt(value)
    }

    override fun methodHandleEntry(kind: Int, index: Int) {
        output.writeByte(ConstantTags.METHOD_HANDLE.toInt())
        output.writeByte(kind)
        output.writeShort(index)
   }

    override fun methodTypeEntry(descriptorIndex: Int) {
        output.writeByte(ConstantTags.METHOD_TYPE.toInt())
        output.writeShort(descriptorIndex)
    }

    override fun fieldRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        output.writeByte(ConstantTags.FIELD_REF.toInt())
        output.writeShort(classIndex)
        output.writeShort(nameAndTypeIndex)
    }

    override fun stringEntry(index: Int) {
        output.writeByte(ConstantTags.STRING.toInt())
        output.writeShort(index)
    }
}