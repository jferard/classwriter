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

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.writer.encoded.BootstrapMethodsAttributeEncodedWriter
import java.io.DataOutputStream

class ByteCodeBootstrapMethodsAttributeEncodedWriter(private val output: DataOutputStream) :
        BootstrapMethodsAttributeEncodedWriter {
    override fun bootstrapMethodsAttribute(nameIndex: Int,
                                           encodedBootstrapMethods: List<EncodedBootstrapMethod>) {
        val length = Sized.listSize(0, encodedBootstrapMethods)
        output.writeShort(nameIndex)
        output.writeInt(length)
        output.writeShort(encodedBootstrapMethods.size)
        encodedBootstrapMethods.forEach { it.write(this) }
    }

    override fun bootstrapMethod(methodRefIndex: Int,
                                 argumentIndexes: List<Int>) {
        output.writeShort(methodRefIndex)
        output.writeShort(argumentIndexes.size)
        argumentIndexes.forEach(output::writeShort)
    }
}