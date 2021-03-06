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

import com.github.jferard.classwriter.encoded.EncodedMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodEncodedWriter
import java.io.DataOutputStream
import java.util.logging.Logger

class ByteCodeMethodEncodedWriter(
        private val output: DataOutputStream,
        private val methodAttributeWritableFactory: MethodAttributeEncodedWriter) :
        MethodEncodedWriter {

    override fun method(accessFlags: Int, nameIndex: Int, descriptorIndex: Int,
                        encodedAttributes: List<EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>>) {
        output.writeShort(accessFlags)
        output.writeShort(nameIndex)
        output.writeShort(descriptorIndex)
        output.writeShort(encodedAttributes.size)
        for (ea in encodedAttributes) {
            ea.write(methodAttributeWritableFactory)
        }
    }

    override fun methods(
            encodedMethods: List<EncodedMethod>) {
        output.writeShort(encodedMethods.size)
        encodedMethods.forEach { it.write(this) }
    }

    companion object {
        fun create(logger: Logger,
                output: DataOutputStream): ByteCodeMethodEncodedWriter {
            return ByteCodeMethodEncodedWriter(output,
                    ByteCodeMethodAttributeEncodedWriter.create(logger, output))
        }
    }
}