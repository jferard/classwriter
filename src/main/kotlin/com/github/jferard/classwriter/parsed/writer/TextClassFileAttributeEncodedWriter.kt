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

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.writer.encoded.CommonAttributeEncodedWriter
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextClassFileAttributeEncodedWriter(private val output: Writer,
                                          private val parsedBootstrapMethodsAttributeWritableFactory: ParsedBootstrapMethodsAttributeEncodedWriter) :
        ClassFileAttributeEncodedWriter,
        CommonAttributeEncodedWriter {
    override fun innerClass(innerClassIndex: Int, outerClassNameIndex: Int,
                            innerClassNameIndex: Int,
                            innerAccessFlags: Int) {
        throw IllegalStateException()
    }

    override fun innerClassesAttribute(attributeNameIndex: Int,
                                       encodedInnerClasses: List<EncodedInnerClass>) {
        throw IllegalStateException()
    }

    override fun sourceFileAttribute(attributeNameIndex: Int,
                                     sourceFileNameIndex: Int) {
        throw IllegalStateException()
    }

    override fun bootstrapMethodsAttribute(nameIndex: Int,
                                           encodedBootstrapMethods: List<EncodedBootstrapMethod>) {
        return parsedBootstrapMethodsAttributeWritableFactory
                .bootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods)
    }

    override fun classFileAttributes(
            encodedAttributes: List<EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>>) {
        output.write("/* ATTRIBUTES */")
    }

    companion object {
        fun create(output: Writer): TextClassFileAttributeEncodedWriter {
            return TextClassFileAttributeEncodedWriter(
                    output,
                    ParsedBootstrapMethodsAttributeEncodedWriter(output))
        }
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}