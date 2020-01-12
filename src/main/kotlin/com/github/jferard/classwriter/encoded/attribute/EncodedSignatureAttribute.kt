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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.attribute.SignatureAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.SignatureAttributeEncodedWriter

/**
 * 4.7.9. The Signature Attribute (ClassFile, field_info, or method_info structure)
 * ```
 * Signature_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 signature_index;
 * }
 * ```
 */
class EncodedSignatureAttribute(private val attributeNameIndex: Int,
                                private val signatureIndex: Int) :
        EncodedCFMAttribute<SignatureAttribute, EncodedSignatureAttribute, SignatureAttributeEncodedWriter> {
    override fun write(encodedWriter: SignatureAttributeEncodedWriter) {
        return encodedWriter.signatureAttribute(attributeNameIndex, signatureIndex)
    }

    override val size: Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        TODO("not implemented")
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): SignatureAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}