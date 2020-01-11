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

package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.api.Header
import com.github.jferard.classwriter.encoded.EncodedClassFileAttributes
import com.github.jferard.classwriter.encoded.EncodedFields
import com.github.jferard.classwriter.encoded.EncodedInterfaces
import com.github.jferard.classwriter.encoded.EncodedMethods
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.writer.encoded.ClassEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEncodedWriter
import java.io.Writer

class TextClassEncodedWriter(private val output: Writer, private val constantPoolEncodedWriter: ConstantPoolEncodedWriter) :
        ClassEncodedWriter {
    override fun classFile(header: Header, constantPool: ConstantPool, accessFlags: Int,
                           thisIndex: Int,
                           superIndex: Int, encodedInterfaces: EncodedInterfaces,
                           encodedFields: EncodedFields, encodedMethods: EncodedMethods,
                           encodedAttributes: EncodedClassFileAttributes) {
        output.write("/** CLASS FILE */")
        header.write(this)
        constantPool.write(constantPoolEncodedWriter)
    }

    override fun header(minorVersion: Int, majorVersion: Int) {
        output.write("/** header */\n")
        output.write("Minor version: $minorVersion\n")
        output.write("Major version: $majorVersion\n")
    }

    override fun interfaces(encodedInterfaces: List<Int>) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun bootstrapMethod(methodRefIndex: Int, argumentIndexes: List<Int>) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }
}