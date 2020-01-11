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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.LocalVariableTypeTableAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter

/**
 * 4.7.14. The LocalVariableTypeTable Attribute
 *
 * ```
 * LocalVariableTypeTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_type_table_length;
 *     {
 *          u2 start_pc;
 *          u2 length;
 *          u2 name_index;
 *          u2 signature_index;
 *          u2 index;
 *     } local_variable_type_table[local_variable_type_table_length];
 * }
 * ```
 */
class EncodedLocalVariableTypeTableAttribute(private val attributeNameIndex: Int,
                                             private val encodedLocalVariableTypes: List<EncodedLocalVariableTypeTable>) :
        EncodedCodeAttributeAttribute<LocalVariableTypeTableAttribute, EncodedLocalVariableTypeTableAttribute, CodeAttributeAttributeEncodedWriter> {
    override fun write(
            encodedWriter: CodeAttributeAttributeEncodedWriter) {
        return encodedWriter.variableTypeTableAttribute(attributeNameIndex,
                encodedLocalVariableTypes)
    }

    override val size: Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + encodedLocalVariableTypes.map(
                    EncodedLocalVariableTypeTable::size).sum()

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): LocalVariableTypeTableAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}