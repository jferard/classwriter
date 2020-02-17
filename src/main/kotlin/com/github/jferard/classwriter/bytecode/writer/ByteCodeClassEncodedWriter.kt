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

import com.github.jferard.classwriter.api.Header
import com.github.jferard.classwriter.encoded.*
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.writer.encoded.*
import java.io.DataOutputStream
import java.util.logging.Logger

class ByteCodeClassEncodedWriter(
        private val output: DataOutputStream,
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
        fun create(logger: Logger, output: DataOutputStream): ByteCodeClassEncodedWriter {
            return ByteCodeClassEncodedWriter(
                    output,
                    ByteCodeFieldEncodedWriter.create(output),
                    ByteCodeMethodEncodedWriter.create(logger, output),
                    ByteCodeClassFileAttributeEncodedWriter(logger, output,
                            ByteCodeClassAnnotationEncodedWriter(logger, output)),
                    ByteCodeConstantPoolEncodedWriter.create(output))
        }
    }
}