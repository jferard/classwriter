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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.encoded.attribute.EncodedSignatureAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.SignatureAttributeWriter

/**
 * 4.7.9. The Signature Attribute
 * ```Signature_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 signature_index;
 * }
 * ``` *
 */
class SignatureAttribute(
        private val signature: Signature) :
        FieldAttribute<SignatureAttribute, EncodedSignatureAttribute, SignatureAttributeWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedSignatureAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(SIGNATURE_NAME)
        val signatureIndex: Int = context.addUtf8ToPool(signature.toString())
        return EncodedSignatureAttribute(attributeNameIndex, signatureIndex)
    }

    companion object {
        const val SIGNATURE_NAME: String = "Signature"
    }

    override fun write(encodableWriter: SignatureAttributeWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}