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
package com.github.jferard.classwriter.writer.encodable

import com.github.jferard.classwriter.Field
import com.github.jferard.classwriter.api.*
import com.github.jferard.classwriter.encoded.EncodedClassFile
import com.github.jferard.classwriter.internal.attribute.ClassFileAttribute
import com.github.jferard.classwriter.text.writer.*
import java.io.IOException
import java.io.Writer

class TextClassEncodableWriter(private val output: Writer,
                               private val attributeWritableFactory: TextClassFileAttributeEncodedWriter,
                               private val fieldWritableFactory: TextFieldEncodedWriter,
                               private val methodWritableFactory: TextMethodEncodedWriter) :
        ClassEncodableWriter {

    override fun classFile(header: Header,
                           accessFlags: Int,
                           thisClassRef: PlainClassRef,
                           superClassRef: PlainClassRef?, interfaces: List<PlainClassRef>,
                           fields: List<Field>, methods: List<Method>,
                           attributes: List<ClassFileAttribute<*,*,*>>) {
        output.append("/**************/\n")
        output.append("/* CLASS FILE */\n")
        output.append("/**************/\n")
        header.write(this)
        output.write("/* pool omitted */\n")
        this.accessFlags(accessFlags)
        output.write("this: $thisClassRef\n")
        output.write("super: $superClassRef\n")
        output.write("/* interfaces */")
        interfaces.forEach { i -> this.winterface(i) }
        output.write("/* fields */")
        fields.forEach { f -> this.field(f) }
        output.write("/* methods */")
        methods.forEach { m -> this.method(m) }
        output.write("/* attributes */")
        attributes.forEach { a -> this.attribute(a) }
    }

    @Throws(IOException::class)
    override fun header(minorVersion: Int, majorVersion: Int) {
        output.append("/* HEADER */\n")
        output.append(String.format("%s, %s, %s, %s, // magic number\n",
                TextEncodedWriterHelper.hex(EncodedClassFile.MAGIC_NUMBER shr 24),
                TextEncodedWriterHelper.hex(EncodedClassFile.MAGIC_NUMBER shr 16),
                TextEncodedWriterHelper.hex(EncodedClassFile.MAGIC_NUMBER shr 8),
                TextEncodedWriterHelper.hex(EncodedClassFile.MAGIC_NUMBER)))
        output.append(String.format("%s, %s, // %d -> minor version\n",
                TextEncodedWriterHelper.hex(minorVersion shr 8),
                TextEncodedWriterHelper.hex(minorVersion), minorVersion))
        output.append(String.format("%s, %s, // %d -> major version\n",
                TextEncodedWriterHelper.hex(majorVersion shr 8),
                TextEncodedWriterHelper.hex(majorVersion), majorVersion))
    }

    override fun winterface(classRef: ClassRef) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun field(field: Field) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun method(method: Method) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun attribute(a: ClassFileAttribute<*, *, *>) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun deprecated() {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun binaryOp(opcode: Int) {
        TODO("Not yet implemented")
    }

    override fun bootstrapMethod(methodHandle: MethodHandle, arguments: List<Any>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun accessFlags(accessFlags: Int) {
        output.write("access flags: $accessFlags\n") //TODO: decode access flags
    }

}