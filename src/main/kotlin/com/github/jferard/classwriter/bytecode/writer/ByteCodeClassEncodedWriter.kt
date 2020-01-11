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

import com.github.jferard.classwriter.api.*
import com.github.jferard.classwriter.encoded.EncodedClassFile
import com.github.jferard.classwriter.encoded.EncodedClassFileAttributes
import com.github.jferard.classwriter.encoded.EncodedFields
import com.github.jferard.classwriter.encoded.EncodedMethods
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.encoded.EncodedInterfaces
import com.github.jferard.classwriter.writer.encoded.*
import java.io.DataOutput

class ByteCodeClassEncodedWriter(
        private val output: DataOutput,
        private val fieldWriter: FieldEncodedWriter,
        private val methodWriter: MethodEncodedWriter,
        private val classAttributeWriter: ClassFileAttributeEncodedWriter,
        private val constantPoolWriter: ConstantPoolEncodedWriter) :
        ClassEncodedWriter {

    override fun classFile(header: Header,
                           pool: ConstantPool,
                           accessFlags: Int, thisIndex: Int,
                           superIndex: Int, encodedInterfaces: EncodedInterfaces,
                           encodedFields: EncodedFields, encodedMethods: EncodedMethods,
                           encodedAttributes: EncodedClassFileAttributes) {
        header.write(this)
        pool.write(constantPoolWriter)
        output.writeShort(accessFlags)
        output.writeShort(thisIndex)
        output.writeShort(superIndex)
        encodedInterfaces.write(this)
        encodedFields.write(fieldWriter)
        encodedMethods.write(methodWriter)
        encodedAttributes.write(classAttributeWriter)
    }

    override fun header(minorVersion: Int, majorVersion: Int) {
        output.writeInt(EncodedClassFile.MAGIC_NUMBER)
        output.writeShort(minorVersion)
        output.writeShort(majorVersion)
    }

    override fun interfaces(
            encodedInterfaces: List<Int>) {
        output.writeShort(encodedInterfaces.size)
        encodedInterfaces.forEach { ei -> output.writeShort(ei) }
    }

    override fun bootstrapMethod(methodRefIndex: Int, argumentIndexes: List<Int>) {


    }

    companion object {
        fun create(output: DataOutput): ByteCodeClassEncodedWriter {
            return ByteCodeClassEncodedWriter(
                    output,
                    ByteCodeFieldEncodedWriter(
                            output,
                            ByteCodeFieldAttributeEncodedWriter(
                                    output)),
                    ByteCodeMethodEncodedWriter(
                            output,
                            ByteCodeMethodAttributeEncodedWriter.create(
                                    output)),
                    ByteCodeClassFileAttributeEncodedWriter(
                            output),
                    ByteCodeConstantPoolEncodedWriter.create(
                            output))
        }
    }

}