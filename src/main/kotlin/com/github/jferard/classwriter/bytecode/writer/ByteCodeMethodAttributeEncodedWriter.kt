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
import com.github.jferard.classwriter.api.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import java.io.DataOutputStream
import java.util.logging.Logger

class ByteCodeMethodAttributeEncodedWriter(private val logger: Logger,
                                           private val output: DataOutputStream,
                                           private val instructionWriter: InstructionEncodedWriter,
                                           private val annotationWriter: AnnotationEncodedWriter,
                                           private val codeAttributeWriter: CodeAttributeAttributeEncodedWriter) :
        MethodAttributeEncodedWriter {

    /**
     * 4.7.3. The Code Attribute
     *```
     * Code_attribute {
     *     u2 attribute_name_index;
     *     u4 attribute_length;
     *     u2 max_stack;
     *     u2 max_locals;
     *     u4 code_length;
     *     u1 code[code_length];
     *     u2 exception_table_length;
     *     {   u2 start_pc;
     *         u2 end_pc;
     *         u2 handler_pc;
     *         u2 catch_type;
     *     } exception_table[exception_table_length];
     *     u2 attributes_count;
     *     attribute_info attributes[attributes_count];
     * }
     *```
     */
    override fun codeAttribute(attributeNameIndex: Int, maxStack: Int, maxLocals: Int,
                               encodedCode: EncodedInstruction,
                               encodedExceptions: List<EncodedExceptionInCode>,
                               encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>>) {
        val length: Int = 2 * BytecodeHelper.SHORT_SIZE + // max stack + locals
                BytecodeHelper.INT_SIZE + // code length
                encodedCode.getSize(0) + // code
                BytecodeHelper.SHORT_SIZE + // except length
                Sized.listSize(0, encodedExceptions) +
                BytecodeHelper.SHORT_SIZE + // attr count
                Sized.listSize(0, encodedAttributes)
        logger.finest(
                "Write code attribute: full len=$length, code size = ${encodedCode.getSize(0)}, exc size:${Sized.listSize(
                        0,
                        encodedExceptions)}, attr size: ${Sized.listSize(0, encodedAttributes)}")
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeShort(maxStack)
        output.writeShort(maxLocals)
        output.writeInt(encodedCode.getSize(0))
        encodedCode.write(instructionWriter)
        output.writeShort(encodedExceptions.size)
        encodedExceptions.forEach { it.write(this) }
        output.writeShort(encodedAttributes.size)
        encodedAttributes.forEach { it.write(codeAttributeWriter) }
    }

    override fun annotationsAttribute(annotationsNameIndex: Int,
                                      encodedAnnotations: List<EncodedAnnotation>) {
        val length = BytecodeHelper.SHORT_SIZE + Sized.listSize(0, encodedAnnotations)
        logger.finest("Write method annotation: $encodedAnnotations")
        output.writeShort(annotationsNameIndex)
        output.writeInt(length)
        output.writeShort(encodedAnnotations.size)
        encodedAnnotations.forEach {
            it.write(annotationWriter)
        }
    }

    /**
     * ```
     * RuntimeVisibleParameterAnnotations_attribute {
     *    u2 attribute_name_index;
     *    u4 attribute_length;
     *    u1 num_parameters;
     *    {   u2         num_annotations;
     *        annotation annotations[num_annotations];
     *    } parameter_annotations[num_parameters];
     * }
     * ```
     */
    // TODO: remove duplicate code
    override fun parameterAnnotationsAttribute(attributeNameIndex: Int,
                                               parameterAnnotations: List<List<EncodedAnnotation>>) {
        val length = BytecodeHelper.BYTE_SIZE + parameterAnnotations.map {
            BytecodeHelper.SHORT_SIZE + Sized.listSize(0, it)
        }.sum()
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeByte(parameterAnnotations.size)
        parameterAnnotations.forEach(this::paramAnnotations)
    }

    private fun paramAnnotations(
            encodedAnnotations: List<EncodedAnnotation>) {
        output.writeShort(encodedAnnotations.size)
        encodedAnnotations.forEach {
            it.write(this.annotationWriter)
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
        output.writeShort(attributeNameIndex)
        output.writeInt(2)
        output.writeShort(signatureIndex)
    }

    companion object {
        fun create(logger: Logger, output: DataOutputStream): MethodAttributeEncodedWriter {
            return ByteCodeMethodAttributeEncodedWriter(logger, output,
                    ByteCodeInstructionEncodedWriter(logger, output, 0),
                    ByteCodeClassAnnotationEncodedWriter(logger, output),
                    ByteCodeCodeAttributeAttributeEncodedWriter.create(logger, output))
        }
    }
}