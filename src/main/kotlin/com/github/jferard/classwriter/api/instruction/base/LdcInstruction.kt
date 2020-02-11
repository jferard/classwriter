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
package com.github.jferard.classwriter.api.instruction.base
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedLdcInstruction
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.pool.EncodableWriter

/**
 * ldc: push a access #index from a access pool (String, int, float, Class, kotlin.lang
 * .invoke.MethodType, or kotlin.lang.invoke.MethodHandle) onto the stack.
 * ldc_w: push a access #index from a access pool (String, int, float, Class, kotlin.lang
 * .invoke.MethodType, or kotlin.lang.invoke.MethodHandle) onto the stack (wide index is
 * constructed as indexbyte1 << 8 + indexbyte2).
 * ldc2_w: push a access #index from a access pool (double or long) onto the stack (wide
 * index is constructed as indexbyte1 << 8 + indexbyte2).
 * Other bytes (count: 1): index.
 * Stack: () -> (value).
 */
class LdcInstruction(private val entry: ConstantPoolEntry) : BaseInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(computeOffset(context))
        codeContext.stackPush(entry.toVerificationType())
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    private fun computeOffset(context: GlobalContext): Int {
        val offset: Int
        val index: Int = context.addToPool(entry)
        offset = if (index <= BytecodeHelper.BYTE_MAX) {
            2
        } else {
            3
        }
        return offset
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        val index: Int = context.addToPool(entry)
        return EncodedLdcInstruction(
                index, entry.size)
    }

}