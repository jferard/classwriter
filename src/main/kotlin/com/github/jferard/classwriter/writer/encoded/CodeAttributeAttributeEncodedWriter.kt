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
package com.github.jferard.classwriter.writer.encoded

import com.github.jferard.classwriter.api.EncodedWriter
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber

interface CodeAttributeAttributeEncodedWriter :
        EncodedWriter {
    fun positionAndLineNumber(startPc: Int, lineNumber: Int)
    fun localVariableTable(startPc: Int, length: Int, nameIndex: Int, descriptorIndex: Int,
                           index: Int)

    fun localVariableTypeTable(startPc: Int, length: Int, nameIndex: Int, signatureIndex: Int,
                               index: Int)

    fun lineNumberTableAttribute(attributeNameIndex: Int,
                                 positionAndLineNumbers: List<PositionAndLineNumber>)

    fun localVariableTableAttribute(attributeNameIndex: Int,
                                    encodedLocalVariables: List<EncodedLocalVariableTable>)

    fun variableTypeTableAttribute(attributeNameIndex: Int,
                                   encodedLocalVariableTypes: List<EncodedLocalVariableTypeTable>)

    fun stackMapTableAttribute(attributeNameIndex: Int,
                               encodedStackMapFrames: List<EncodedStackMapFrame>)
}