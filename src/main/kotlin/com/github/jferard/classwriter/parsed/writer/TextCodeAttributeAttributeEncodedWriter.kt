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
package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextCodeAttributeAttributeEncodedWriter(private val output: Writer,
                                              private val entries: List<EncodedConstantPoolEntry>,
                                              private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                                              private val stackMapFrameEncodedWriter: StackMapFrameEncodedWriter) :
        CodeAttributeAttributeEncodedWriter {
    override fun stackMapTableAttribute(attributeNameIndex: Int,
                                        encodedStackMapFrames: List<EncodedStackMapFrame>) {
        output.write("/** StackMapTable */\n")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "len",
                BytecodeHelper.SHORT_SIZE + encodedStackMapFrames.map(Encoded<*, *, *>::size).sum())
        TextEncodedWriterHelper.writeU2(output, "number of entries",
                encodedStackMapFrames.size)
        encodedStackMapFrames.forEach { it.write(stackMapFrameEncodedWriter) }
    }

    override fun lineNumberTableAttribute(attributeNameIndex: Int,
                                          positionAndLineNumbers: List<PositionAndLineNumber>) {
        output.write("/** LineNumberTable */\n")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "len",
                BytecodeHelper.SHORT_SIZE + positionAndLineNumbers.size * 2 * BytecodeHelper.SHORT_SIZE)
        TextEncodedWriterHelper.writeU2(output, "line number table length",
                positionAndLineNumbers.size)
        positionAndLineNumbers.forEach { it.write(this) }
    }

    override fun positionAndLineNumber(startPc: Int,
                                       lineNumber: Int) {
        TextEncodedWriterHelper.writeU2(output, "start_pc", startPc)
        TextEncodedWriterHelper.writeU2(output, "line_number", lineNumber)
    }

    override fun localVariableTableAttribute(attributeNameIndex: Int,
                                             encodedLocalVariables: List<EncodedLocalVariableTable>) {
        output.write("/** LocalVariableTable */\n")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "len",
                BytecodeHelper.SHORT_SIZE + encodedLocalVariables.map(Encoded<*,*,*>::size).sum())
        TextEncodedWriterHelper.writeU2(output, "local variable table length",
                encodedLocalVariables.size)
        encodedLocalVariables.forEach { it.write(this) }
    }

    override fun localVariableTable(startPc: Int, length: Int, nameIndex: Int,
                                    descriptorIndex: Int,
                                    index: Int) {
        TextEncodedWriterHelper.writeU2(output, "start pc", startPc)
        TextEncodedWriterHelper.writeU2(output, "length", length)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "name", nameIndex, entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "descriptor", descriptorIndex, entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU2(output, "local index", index)
    }

    override fun localVariableTypeTable(startPc: Int, length: Int, nameIndex: Int,
                                        signatureIndex: Int,
                                        index: Int) {
        TODO("not implemented")
    }

    override fun variableTypeTableAttribute(attributeNameIndex: Int,
                                            encodedLocalVariableTypes: List<EncodedLocalVariableTypeTable>) {
        TODO("not implemented")
    }
}