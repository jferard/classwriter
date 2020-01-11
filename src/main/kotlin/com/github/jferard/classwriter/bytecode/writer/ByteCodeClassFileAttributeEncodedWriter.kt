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

import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import java.io.DataOutput

class ByteCodeClassFileAttributeEncodedWriter(private val output: DataOutput) :
        ClassFileAttributeEncodedWriter {
    override fun innerClassesAttribute(attributeNameIndex: Int,
                                       encodedInnerClasses: List<EncodedInnerClass>) {
        val length = encodedInnerClasses.map { obj: EncodedInnerClass -> obj.size }.sum()
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        encodedInnerClasses.forEach { writableInnerClass ->
            writableInnerClass.write(this)
        }
    }

    override fun sourceFileAttribute(attributeNameIndex: Int,
                                     sourceFileNameIndex: Int) {
        output.writeShort(attributeNameIndex)
        output.writeInt(
                BytecodeHelper.SHORT_SIZE)
        output.writeShort(sourceFileNameIndex)
    }

    override fun bootstrapMethodsAttribute(nameIndex: Int,
                                           encodedBootstrapMethods: List<EncodedBootstrapMethod>) {
        TODO("not implemented")
    }

    override fun classFileAttributes(
            encodedAttributes: List<EncodedClassFileAttribute<*,*, ClassFileAttributeEncodedWriter>>) {
        output.writeShort(encodedAttributes.size)
        for (byteCode in encodedAttributes) {
            byteCode.write(this)
        }
    }

    override fun innerClass(innerClassIndex: Int, outerClassNameIndex: Int,
                            innerClassNameIndex: Int,
                            innerAccessFlags: Int) {
        output.writeShort(innerClassIndex)
        output.writeShort(outerClassNameIndex)
        output.writeShort(innerClassNameIndex)
        output.writeShort(innerAccessFlags)
    }
}