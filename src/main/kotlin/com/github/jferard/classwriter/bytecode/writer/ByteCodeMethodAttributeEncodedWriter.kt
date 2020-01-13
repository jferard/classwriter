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

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.tool.decoder.EncodedInstructions
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodEncodedWriter
import java.io.DataOutput

class ByteCodeMethodAttributeEncodedWriter(
        private val output: DataOutput,
        private val instructionWriter: InstructionEncodedWriter,
        private val methodWriter: MethodEncodedWriter,
        private val annotationWriter: AnnotationEncodedWriter,
        private val codeAttributeWriter: CodeAttributeAttributeEncodedWriter) :
        MethodAttributeEncodedWriter {

    override fun codeAttribute(attributeNameIndex: Int, maxStack: Int, maxLocals: Int,
                               encodedCode: EncodedInstruction,
                               encodedExceptions: List<EncodedExceptionInCode>,
                               encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>>) {
        val length: Int = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE +
                encodedCode.size + BytecodeHelper.SHORT_SIZE +
                encodedExceptions.map(EncodedExceptionInCode::size).sum() +
                BytecodeHelper.SHORT_SIZE +
                encodedAttributes.map(EncodedCodeAttributeAttribute<*, *, *>::size).sum()

        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeShort(maxStack)
        output.writeShort(maxLocals)
        output.writeInt(encodedCode.size)
        encodedCode.write(instructionWriter)
        output.writeShort(encodedExceptions.size)
        encodedAttributes.forEach { it.write(codeAttributeWriter) }
        output.writeShort(encodedAttributes.size)
        encodedAttributes.forEach { it.write(codeAttributeWriter) }
    }

    override fun annotationAttribute(annotationsNameIndex: Int,
                                     encodedAnnotations: List<EncodedAnnotation>) {
        val length = encodedAnnotations.map(EncodedAnnotation::size).sum()
        output.writeShort(annotationsNameIndex)
        output.writeInt(length)
        for (writableAnnotation in encodedAnnotations) {
            writableAnnotation.write(annotationWriter)
        }
    }

    override fun exceptionInCode(startPc: Int, endPc: Int, handlerPc: Int,
                                 catchTypeIndex: Int) {
        output.writeShort(startPc)
        output.writeShort(endPc)
        output.writeShort(handlerPc)
        output.writeShort(catchTypeIndex)
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun deprecatedAttribute(attributeNameIndex: Int) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun signatureAttribute(attributeNameIndex: Int, signatureIndex: Int) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun create(output: DataOutput): MethodAttributeEncodedWriter {
            return ByteCodeMethodAttributeEncodedWriter(
                    output,
                    ByteCodeInstructionEncodedWriter(
                            output),
                    ByteCodeMethodEncodedWriter.create(
                            output),
                    ByteCodeClassAnnotationEncodedWriter(
                            output),
                    ByteCodeCodeAttributeAttributeEncodedWriter.create(output))
        }
    }
}