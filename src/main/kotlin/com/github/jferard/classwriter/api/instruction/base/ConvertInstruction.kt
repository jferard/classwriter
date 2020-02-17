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
package com.github.jferard.classwriter.api.instruction.base

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.encoded.instruction.EncodedConvertInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstructionConstants
import com.github.jferard.classwriter.pool.EncodableWriter
import java.lang.IllegalArgumentException

/**
 * d2f: convert a double to a float.
 * Stack: (value) -> (result).
 */
class ConvertInstruction(private val opcode: Int, private val fromType: VerificationType,
                         private val toType: VerificationType) : BaseInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(1)
        val valueType: VerificationType = codeContext.stackPop()
        codeContext.assertTypeAssignable(fromType, valueType)
        codeContext.stackPush(toType)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return when(opcode) {
            OpCodes.I2C -> EncodedInstructionConstants.I2C_INSTRUCTION
            else -> throw IllegalArgumentException("$opcode")
        }
    }
}