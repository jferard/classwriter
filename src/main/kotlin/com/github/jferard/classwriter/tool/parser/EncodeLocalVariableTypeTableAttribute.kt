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
package com.github.jferard.classwriter.tool.parser

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTableAttribute
import com.github.jferard.classwriter.internal.attribute.LocalVariableTypeTableAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter

/**
 * ```
 * attribute_info {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 info[attribute_length];
 * }
 * ```
 */
class EncodeLocalVariableTypeTableAttribute(private val attributeNameIndex: Int,
                                            private val encodedLocalVariableTables: List<EncodedLocalVariableTypeTable>) :
        EncodedCodeAttributeAttribute<LocalVariableTypeTableAttribute, EncodedLocalVariableTypeTableAttribute, CodeAttributeAttributeEncodedWriter> {
    override fun write(
            encodedWriter: CodeAttributeAttributeEncodedWriter) {
        TODO()
    }

    override fun getSize(pos: Int): Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE + Sized.listSize(
                    0,
                    encodedLocalVariableTables)

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): LocalVariableTypeTableAttribute {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

}