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
package com.github.jferard.classwriter.tool.decoder

import com.github.jferard.classwriter.encoded.attribute.*
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.AnnotationsAttribute
import com.github.jferard.classwriter.internal.attribute.CodeAttribute
import com.github.jferard.classwriter.internal.attribute.SignatureAttribute
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException

class MethodAttributeParser(
        private val codeAttributeAttributeParser: CodeAttributeAttributeParser,
        private val instructionsParser: InstructionsParser,
        private val annotationParser: AnnotationParser,
        val entries: List<EncodedConstantPoolEntry>) :
        Parser<EncodedMethodAttribute<*, *, *>> {

    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedMethodAttribute<*, *, *> {
        val attributeNameIndex = input.readUnsignedShort()
        val attributeName = entries[attributeNameIndex - 1].utf8Text()
        return when (attributeName) {
            CodeAttribute.CODE_NAME -> parseCodeAttribute(attributeNameIndex, input)
            SignatureAttribute.SIGNATURE_NAME -> parseSignatureAttribute(attributeNameIndex,
                    input)
            AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS -> parseInvisibleAnnotations(
                    attributeNameIndex, input)
            AnnotationsAttribute.RUNTIME_VISIBLE_ANNOTATIONS -> parseInvisibleAnnotations(
                    attributeNameIndex, input)
            AnnotationsAttribute.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS -> parseInvisibleParameterAnnotations(
                    attributeNameIndex, input)
            else -> throw IllegalStateException("Unknown method attribute: $attributeName")
        }
    }

    private fun parseInvisibleParameterAnnotations(attributeNameIndex: Int, input: DataInput): EncodedMethodAttribute<*, *, *> {
        val length = input.readInt().toLong()
        val numParameters = input.readUnsignedByte()
        val parameterAnnotations = (1..numParameters).map {
            val numAnnotations = input.readUnsignedShort()
            (1..numAnnotations).map { annotationParser.parseAnnotation(input) }
        }
        return EncodedParameterAnnotationsAttribute(attributeNameIndex, parameterAnnotations)
    }

    private fun parseInvisibleAnnotations(attributeNameIndex: Int,
                                          input: DataInput): EncodedMethodAttribute<*, *, *> {
        val length = input.readInt().toLong()
        val numAnnotations = input.readUnsignedShort()
        val annotations = (1..numAnnotations).map { annotationParser.parseAnnotation(input) }
        return EncodedAnnotationsAttribute(attributeNameIndex, annotations)
    }

    @Throws(IOException::class)
    private fun parseCodeAttribute(attributeNameIndex: Int,
                                   input: DataInput): EncodedMethodAttribute<*, *, *> {
        val length = input.readInt().toLong()
        val maxStack = input.readShort().toInt()
        val maxLocals = input.readShort().toInt()
        val encodedCode = instructionsParser.parse(input)
        val exceptionTableLength = input.readShort().toInt()
        val encodedExceptions: List<EncodedExceptionInCode> = (1..exceptionTableLength).map {
            parseEncodedExceptionInCode(input)
        }
        val attributesCount = input.readShort().toInt()
        val encodedAttributes: List<EncodedCodeAttributeAttribute<*, *,
                CodeAttributeAttributeEncodedWriter>> = (1..attributesCount).map {
            codeAttributeAttributeParser.parse(input)
        }
        return EncodedCodeAttribute(attributeNameIndex, maxStack, maxLocals,
                encodedCode, encodedExceptions, encodedAttributes)
    }

    private fun parseEncodedExceptionInCode(
            input: DataInput): EncodedExceptionInCode {
        val startPc = input.readShort().toInt()
        val endPc = input.readShort().toInt()
        val handlerPc = input.readShort().toInt()
        val catchType = input.readShort().toInt()
        val encodedExceptionInCode = EncodedExceptionInCode(startPc, endPc, handlerPc, catchType)
        return encodedExceptionInCode
    }

    /**
     * Signature_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 signature_index;
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     */
    @Throws(IOException::class)
    private fun parseSignatureAttribute(attributeNameIndex: Int,
                                        input: DataInput): EncodedMethodAttribute<*, *, *> {
        val length = input.readInt().toLong()
        val signatureIndex = input.readShort().toInt()
        return EncodedSignatureAttribute(attributeNameIndex, signatureIndex)
    }

}