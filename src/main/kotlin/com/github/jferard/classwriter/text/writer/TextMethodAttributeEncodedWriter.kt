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
package com.github.jferard.classwriter.text.writer

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import java.io.Writer

class TextMethodAttributeEncodedWriter(private val output: Writer,
                                       private val entries: List<EncodedConstantPoolEntry>,
                                       private val summaryEncodedWriter: ConstantPoolEntriesEncodedWriter,
                                       private val instructionEncodedWriter: TextInstructionEncodedWriter,
                                       private val cfmAttributeEncodedWriter: TextCFMAttributeEncodedWriter,
                                       private val annotationEncodedWriter: AnnotationEncodedWriter,
                                       private val codeAttributeAttributeEncodedWriter: CodeAttributeAttributeEncodedWriter) :
        MethodAttributeEncodedWriter {
    override fun codeAttribute(attributeNameIndex: Int, maxStack: Int, maxLocals: Int,
                               encodedCode: EncodedInstruction,
                               encodedExceptions: List<EncodedExceptionInCode>,
                               encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>>) {
        output.write("/** Code attribute */\n")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "code", attributeNameIndex, entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "length",
                2 * BytecodeHelper.SHORT_SIZE + encodedCode.getSize(0) + Sized.listSize(0,
                        encodedExceptions))
        TextEncodedWriterHelper.writeU2(output, "max_stack", maxStack)
        TextEncodedWriterHelper.writeU2(output, "max_locals", maxLocals)
        TextEncodedWriterHelper.writeU4(output, "code length", encodedCode.getSize(0))
        encodedCode.write(instructionEncodedWriter)
        encodedExceptions.forEach { it.write(this) }
        encodedAttributes.forEach { it.write(codeAttributeAttributeEncodedWriter) }
    }

    override fun deprecatedAttribute(attributeNameIndex: Int) {
        cfmAttributeEncodedWriter.deprecatedAttribute(attributeNameIndex)
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        cfmAttributeEncodedWriter.syntheticAttribute(attributeNameIndex)
    }

    override fun signatureAttribute(attributeNameIndex: Int,
                                    signatureIndex: Int) {
        cfmAttributeEncodedWriter.signatureAttribute(attributeNameIndex, signatureIndex)
    }

    override fun annotationsAttribute(annotationsNameIndex: Int,
                                      encodedAnnotations: List<EncodedAnnotation>) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", annotationsNameIndex,
                entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU2(output, "attribute length",
                Sized.listSize(0, encodedAnnotations))
        encodedAnnotations.forEach { it.write(annotationEncodedWriter) }
    }

    override fun parameterAnnotationsAttribute(attributeNameIndex: Int,
                                               parameterAnnotations: List<List<EncodedAnnotation>>) {
        val length = BytecodeHelper.BYTE_SIZE + parameterAnnotations.map {
            BytecodeHelper.SHORT_SIZE + Sized.listSize(0, it)
        }.sum()
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", attributeNameIndex,
                entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "length", length)
        TextEncodedWriterHelper.writeU1(output, "num parameters",
                parameterAnnotations.size)
        parameterAnnotations.forEach(this::paramAnnotations)
    }

    private fun paramAnnotations(encodedAnnotations: List<EncodedAnnotation>) {
        TextEncodedWriterHelper.writeU2(output, "num", encodedAnnotations.size)
        encodedAnnotations.forEach { it.write(this.annotationEncodedWriter) }
    }

    override fun exceptionInCode(startPc: Int, endPc: Int, handlerPc: Int,
                                 catchTypeIndex: Int) {
        TextEncodedWriterHelper.writeU2(output, "start_pc", startPc)
        TextEncodedWriterHelper.writeU2(output, "end_pc", endPc)
        TextEncodedWriterHelper.writeU2(output, "handler_pc", handlerPc)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "catch_type", catchTypeIndex, entries,
                summaryEncodedWriter)
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>): TextMethodAttributeEncodedWriter {
            val summaryEncodedWriter = TextConstantPoolEntriesSummaryEncodedWriter(output, entries)
            return TextMethodAttributeEncodedWriter(
                    output, entries,
                    summaryEncodedWriter,
                    TextInstructionEncodedWriter(output, entries, summaryEncodedWriter),
                    TextCFMAttributeEncodedWriter.create(output, entries, summaryEncodedWriter),
                    TextAnnotationEncodedWriter(
                            output, entries, summaryEncodedWriter),
                    TextCodeAttributeAttributeEncodedWriter.create(output, entries,
                            summaryEncodedWriter)
            )
        }
    }

}