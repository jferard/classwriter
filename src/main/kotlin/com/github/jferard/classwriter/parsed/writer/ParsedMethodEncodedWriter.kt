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

import com.github.jferard.classwriter.encoded.EncodedMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.tool.byteviewer.ByteViewerFactory
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodEncodedWriter
import java.io.Writer

class ParsedMethodEncodedWriter(private val output: Writer,
                                private val parsedAttributeWritableFactory: ParsedMethodAttributeEncodedWriter) :
        MethodEncodedWriter {
    override fun method(accessFlags: Int, nameIndex: Int, descriptorIndex: Int,
                        encodedAttributes: List<EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>>) {
        output.append("// method */\n")
        output.append(String.format("%s, %s, // accessFlags: %s\n", *byteTuple(accessFlags)))
        output.append(String.format("%s, %s, // nameIndex: %s\n", *byteTuple(nameIndex)))
        output.append(
                String.format("%s, %s, // descriptorIndex: %s\n", *byteTuple(descriptorIndex)))
        encodedAttributes.forEach { it.write(parsedAttributeWritableFactory) }
    }

    private fun byteTuple(descriptorIndex: Int): Array<Any> {
        return arrayOf(ByteViewerFactory.hex(descriptorIndex shr 8),
                ByteViewerFactory.hex(descriptorIndex),
                descriptorIndex)
    }

    override fun exceptionInCode(startPc: Int, endPc: Int, handlerPc: Int,
                                 catchTypeIndex: Int) {
        TODO("not implemented")
    }

    override fun methods(
            encodedMethods: List<EncodedMethod>) {
        output.write("/* METHODS */")
        encodedMethods.forEach { it.write(this) }
    }

    companion object {
        fun create(output: Writer): ParsedMethodEncodedWriter {
            return ParsedMethodEncodedWriter(output,
                    ParsedMethodAttributeEncodedWriter.create(output))
        }
    }

}