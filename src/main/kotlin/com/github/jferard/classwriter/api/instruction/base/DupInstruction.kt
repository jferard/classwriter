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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.pool.EncodableWriter

/**
 * dup: duplicate the value on top of the stack. value1 and
 * value2 must not be of the type double or long.
 * Stack: (value) -> (value, value).
 */
class DupInstruction : BaseInstruction, EncodedInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(1)
        val valueType: VerificationType = codeContext.stackPeek()
        codeContext.assertTypeAssignable(VerificationType.ONE_WORD, valueType)
        codeContext.stackPush(valueType)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return this
    }

    override fun write(encodedWriter: InstructionEncodedWriter) {
        return encodedWriter.dupInstruction()
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.BYTE_SIZE
}