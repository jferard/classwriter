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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.CodeAttributeEncodableWriter
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter

/**
 * 4.7.3. The Code Attribute
 */
class CodeAttribute(
        private val code: Instruction,
        private val argVerificationTypes: List<VerificationType>,
        private val attributes: List<CodeAttributeAttribute<*, EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>, *>>) :
        MethodAttribute<CodeAttribute, EncodedCodeAttribute, CodeAttributeEncodableWriter> {

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedCodeAttribute {
        val attributeNameIndex: Int = context.addUtf8ToPool(CODE_NAME)
        argVerificationTypes.forEach { avt: VerificationType ->
            codeContext.localsPush(avt)
        }
        code.preprocess(context, codeContext)
        val codeLength: Int = codeContext.curOffset
        val encodedCode = code.encode(context, MethodContext.create(0))
        val encodedExceptionsInCode: List<EncodedExceptionInCode> =
                codeContext.getExceptions().map { it.encode(context, MethodContext.create(0)) }
        val encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>> =
                attributes.map { it.encode(context, MethodContext.create(0)) }
        val attributesLength =
                Sized.listSize(encodedAttributes)
        return EncodedCodeAttribute(attributeNameIndex, codeContext.maxStack,
                codeContext.maxLocals, encodedCode, encodedExceptionsInCode,
                encodedAttributes)
    }

    companion object {
        const val CODE_NAME: String = "Code"
    }

    override fun write(encodableWriter: CodeAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}