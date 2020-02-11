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
package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import java.io.DataOutput

class ByteCodeCodeAttributeAttributeEncodedWriter(
        private val output: DataOutput,
        private val stackMapTableWritableFactory: StackMapFrameEncodedWriter) :
        CodeAttributeAttributeEncodedWriter {


    override fun stackMapTableAttribute(attributeNameIndex: Int,
                                        encodedStackMapFrames: List<EncodedStackMapFrame>) {
        val length = Sized.listSize(encodedStackMapFrames)
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeShort(encodedStackMapFrames.size)
        encodedStackMapFrames.forEach { it.write(stackMapTableWritableFactory) }
    }

    override fun lineNumberTableAttribute(nameIndex: Int,
                                          positionAndLineNumbers: List<PositionAndLineNumber>) {
        val attributeLength: Int = BytecodeHelper.SHORT_SIZE +
                Sized.listSize(positionAndLineNumbers)
        output.writeShort(nameIndex)
        output.writeInt(attributeLength)
        output.writeShort(positionAndLineNumbers.size)
        positionAndLineNumbers.forEach { it.write(this) }
    }

    override fun positionAndLineNumber(startPc: Int, lineNumber: Int) {
        output.writeShort(startPc)
        output.writeShort(lineNumber)
    }

    override fun localVariableTableAttribute(attributeNameIndex: Int,
                                             encodedLocalVariables: List<EncodedLocalVariableTable>) {
        val length = Sized.listSize(encodedLocalVariables)
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeShort(encodedLocalVariables.size)
        encodedLocalVariables.forEach { it.write(this) }
    }

    override fun localVariableTable(startPc: Int, length: Int, nameIndex: Int,
                                    descriptorIndex: Int,
                                    index: Int) {
        output.writeShort(startPc)
        output.writeShort(length)
        output.writeShort(nameIndex)
        output.writeShort(descriptorIndex)
        output.writeShort(index)
    }

    override fun localVariableTypeTable(startPc: Int, length: Int, nameIndex: Int,
                                        signatureIndex: Int, index: Int) {
        output.writeShort(startPc)
        output.writeShort(length)
        output.writeShort(nameIndex)
        output.writeShort(signatureIndex)
        output.writeShort(index)
    }

    override fun variableTypeTableAttribute(attributeNameIndex: Int,
                                            encodedLocalVariableTypes: List<EncodedLocalVariableTypeTable>) {
        val length = Sized.listSize(encodedLocalVariableTypes)
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeShort(encodedLocalVariableTypes.size)
        encodedLocalVariableTypes.forEach { it.write(this) }
    }

    companion object {
        fun create(output: DataOutput): CodeAttributeAttributeEncodedWriter {
            return ByteCodeCodeAttributeAttributeEncodedWriter(
                    output, ByteCodeStackMapFrameEncodedWriter.create(output))
        }
    }
}