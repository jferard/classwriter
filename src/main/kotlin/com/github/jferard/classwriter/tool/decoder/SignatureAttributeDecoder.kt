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

import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedSignatureAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.EncodedSignatureAttributeWriter
import java.io.DataInput
import java.io.IOException

/**
 * 4.7.9. The Signature Attribute
 * ```Signature_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 signature_index;
 * }
 * ``` *
 */
class SignatureAttributeParser : ClassAttributeParser {
    @Throws(IOException::class)
    override fun decode(attributeNameIndex: Int,
                        input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        check(input!!.readInt() == 2)
        val signatureIndex = input.readShort().toInt()
        TODO()
//        return EncodedSignatureAttribute(attributeNameIndex, signatureIndex)
    }

    override fun parse(
            input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }
}