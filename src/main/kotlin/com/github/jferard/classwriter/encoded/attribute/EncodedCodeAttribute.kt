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
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.CodeAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.tool.decoder.EncodedInstructions
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter

/**
 * 4.7.3. The Code Attribute
 * ```
 * Code_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 max_stack;
 *      u2 max_locals;
 *      u4 code_length;
 *      u1 code[code_length];
 *      u2 exception_table_length;
 *      {   u2 start_pc;
 *          u2 end_pc;
 *          u2 handler_pc;
 *          u2 catch_type;
 *      } exception_table[exception_table_length];
 *      u2 attributes_count;
 *      attribute_info encodedAttributes[attributes_count];
 * }
 * ```
 */
class EncodedCodeAttribute(private val attributeNameIndex: Int, private val maxStack: Int,
                           private val maxLocals: Int,
                           private val encodedCode: EncodedInstruction,
                           private val encodedExceptions: List<EncodedExceptionInCode>,
                           private val encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>>) :
        EncodedMethodAttribute<CodeAttribute, EncodedCodeAttribute, MethodAttributeEncodedWriter> {
    override fun write(
            encodedWriter: MethodAttributeEncodedWriter) {
        return encodedWriter.codeAttribute(attributeNameIndex, maxStack, maxLocals, encodedCode,
                encodedExceptions, encodedAttributes)
    }

    override val size: Int
        get() = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + MAX_STACK_FIELD_SIZE +
                MAX_LOCALS_FIELD_SIZE + CODE_LENGTH_FIELD_SIZE + encodedCode.size +
                EXCEPTION_TABLE_LENGTH_FIELD_SIZE +
                encodedExceptions.map(EncodedExceptionInCode::size).sum() +
                ATTRIBUTES_COUNT_FIELD_SIZE +
                encodedAttributes.map(EncodedCodeAttributeAttribute<*, *, *>::size).sum()

    companion object {
        private const val MAX_STACK_FIELD_SIZE = BytecodeHelper.SHORT_SIZE
        private const val MAX_LOCALS_FIELD_SIZE = BytecodeHelper.SHORT_SIZE
        private const val CODE_LENGTH_FIELD_SIZE = BytecodeHelper.INT_SIZE
        private const val EXCEPTION_TABLE_LENGTH_FIELD_SIZE = BytecodeHelper.SHORT_SIZE
        private const val ATTRIBUTES_COUNT_FIELD_SIZE = BytecodeHelper.SHORT_SIZE
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): CodeAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}