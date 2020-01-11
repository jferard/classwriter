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
package com.github.jferard.classwriter.internal.instruction.base
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.internal.instruction.Instruction
import com.github.jferard.classwriter.pool.EncodableWriter

/**
 * dup_x2: insert a copy of the top value into the stack two (if value2 is double or long it
 * takes up the entry of value3, too) or three values (if value2 is neither double nor long)
 * from the top.
 * Stack: (value3, value2, value1) -> (value1, value3, value2, value1).
 */
class DupX2Instruction : BaseInstruction, EncodedInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(1)
        val value1Type: VerificationType = codeContext.stackPop()
        val value2Type: VerificationType = codeContext.stackPop()
        codeContext.assertTypeAssignable(VerificationType.ONE_WORD, value1Type)
        if (value2Type.isAssignable(VerificationType.ONE_WORD)) {
            val value3Type: VerificationType = codeContext.stackPop()
            codeContext.assertTypeAssignable(VerificationType.ONE_WORD, value3Type)
            codeContext.stackPush(value1Type)
            codeContext.stackPush(value3Type)
        } else {
            codeContext.stackPush(value1Type)
        }
        codeContext.stackPush(value2Type)
        codeContext.stackPush(value1Type)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return this
    }

    override fun write(encodedWriter: InstructionEncodedWriter) {
        return encodedWriter.dupX2Instruction()
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = BytecodeHelper.BYTE_SIZE
}