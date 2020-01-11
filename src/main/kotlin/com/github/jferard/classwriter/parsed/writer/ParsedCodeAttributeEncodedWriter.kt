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

import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class ParsedCodeAttributeEncodedWriter(private val output: Writer,
                                       private val parsedBootstrapMethodsAttributeWritableFactory: ParsedBootstrapMethodsAttributeEncodedWriter) :
        CodeAttributeAttributeEncodedWriter {
    override fun stackMapTableAttribute(attributeNameIndex: Int,
                                        encodedStackMapFrames: List<EncodedStackMapFrame>) {
        TODO("not implemented")
    }

    override fun lineNumberTableAttribute(nameIndex: Int,
                                          positionAndLineNumbers: List<PositionAndLineNumber>) {
        TODO("not implemented")
    }

    override fun positionAndLineNumber(startPc: Int,
                                       lineNumber: Int) {
        TODO("not implemented")
    }

    override fun variableTableAttribute(attributeNameIndex: Int,
                                        encodedLocalVariables: List<EncodedLocalVariableTable>) {
        TODO("not implemented")
    }

    override fun localVariableTable(startPc: Int, length: Int, nameIndex: Int,
                                    descriptorIndex: Int,
                                    index: Int) {
        TODO("not implemented")
    }

    override fun localVariableTypeTable(startPc: Int, length: Int, nameIndex: Int,
                                        signatureIndex: Int,
                                        index: Int) {
        TODO("not implemented")
    }

    override fun variableTypeTableAttribute(attributeNameIndex: Int,
                                            encodedLocalVariableTypes: List<EncodedLocalVariableTypeTable>) {
        TODO("not implemented")
    }

    companion object {
        fun create(output: Writer): ParsedCodeAttributeEncodedWriter {
            return ParsedCodeAttributeEncodedWriter(
                    output,
                    ParsedBootstrapMethodsAttributeEncodedWriter(output))
        }
    }

}