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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTableAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.CodeAttributeAttributeEncodableWriter


class LocalVariableTableAttribute(
        private val localVariables: MutableList<LocalVariableTable>) :
        CodeAttributeAttribute<LocalVariableTableAttribute, EncodedLocalVariableTableAttribute, CodeAttributeAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedLocalVariableTableAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(LOCAL_VARIABLE_TABLE)
        val encodedLocalVariables: List<EncodedLocalVariableTable> =
                localVariables.map { lv: LocalVariableTable ->
                    lv.encode(context, codeContext)
                }
        return EncodedLocalVariableTableAttribute(attributeNameIndex, encodedLocalVariables)
    }

    companion object {
        const val LOCAL_VARIABLE_TABLE: String = "LocalVariableTable"
    }

    override fun write(encodableWriter: CodeAttributeAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}