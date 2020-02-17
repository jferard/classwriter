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

package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import java.io.DataOutputStream

class ByteCodeFieldAttributeEncodedWriter(private val output: DataOutputStream) :
        FieldAttributeEncodedWriter {
    override fun constantValueAttribute(attributeNameIndex: Int,
                                        valueIndex: Int) {
        output.writeShort(attributeNameIndex)
        output.writeInt(2)
        output.writeShort(valueIndex)
    }

    override fun deprecatedAttribute(attributeNameIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signatureAttribute(attributeNameIndex: Int,
                                    signatureIndex: Int) {
        //TODO: factorization
        output.writeShort(attributeNameIndex)
        output.writeInt(2)
        output.writeShort(signatureIndex)
    }

    override fun annotationAttribute(annotationsNameIndex: Int,
                                     encodedAnnotations: List<EncodedAnnotation>) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

}
