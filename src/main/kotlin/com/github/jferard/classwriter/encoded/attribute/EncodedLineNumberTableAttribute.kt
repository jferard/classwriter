/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.LineNumberTableAttribute
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter

/**
 * 4.7.12. The LineNumberTable Attribute
 * ```
 * LineNumberTable_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 line_number_table_length;
 *      {
 *          u2 start_pc;
 *          u2 line_number;
 *       } line_number_table[line_number_table_length];
 * }
 * ```
 */
class EncodedLineNumberTableAttribute(private val nameIndex: Int,
                                      private val positionAndLineNumbers: List<PositionAndLineNumber>) :
        EncodedCodeAttributeAttribute<LineNumberTableAttribute, EncodedLineNumberTableAttribute, CodeAttributeAttributeEncodedWriter> {
    override fun write(
            encodedWriter: CodeAttributeAttributeEncodedWriter) {
        return encodedWriter.lineNumberTableAttribute(nameIndex, positionAndLineNumbers)
    }

    override fun getSize(pos: Int): Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE +
                    Sized.listSize(0, positionAndLineNumbers)

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): LineNumberTableAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}