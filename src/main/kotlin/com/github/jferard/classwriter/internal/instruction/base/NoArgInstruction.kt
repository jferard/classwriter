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

import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.internal.instruction.Instruction
import com.github.jferard.classwriter.pool.EncodableWriter

class NoArgInstruction private constructor(private val opcode: Int, private val popStackCount: Int,
                                           private val stackPushTypes: List<VerificationType>,
                                           private val maxLocal: Int) : BaseInstruction, EncodedInstruction {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return this
    }

    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(1)
        // codeContext.popStackCount(this.popStackCount);
// codeContext.maxLocal(this.maxLocal);
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(encodedWriter: InstructionEncodedWriter) {
        throw IllegalStateException()
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = 0

    companion object {
        /**
         * remove two operands of type t and add a result of type t
         */
        fun binaryOp(opcode: Int, type: VerificationType): NoArgInstruction {
            return noLocals(opcode, 2, listOf(type))
        }

        /**
         * remove one operand of type t and add a result of type t
         */
        fun unary(opcode: Int, type: VerificationType): NoArgInstruction {
            return noLocals(opcode, 1, listOf(type))
        }

        /**
         * convert
         */
        fun stdToStd(opcode: Int, toType: VerificationType): NoArgInstruction {
            return noLocals(opcode, 1, listOf(toType))
        }

        fun stdConst(opcode: Int, type: VerificationType): BaseInstruction {
            return noLocals(opcode, 0, listOf(type))
        }

        fun stdDup(opcode: Int, type: VerificationType): BaseInstruction {
            return noLocals(opcode, 0, listOf(type))
        }

        fun noLocals(opcode: Int, stackPopCount: Int,
                     stackPushTypes: List<VerificationType>): NoArgInstruction {
            return NoArgInstruction(opcode, stackPopCount, stackPushTypes, 0)
        }

        fun noLocalsNoSTack(opcode: Int): NoArgInstruction {
            return NoArgInstruction(opcode, 0, emptyList(), 0)
        }

        fun stdALoad(opcode: Int, type: VerificationType): NoArgInstruction {
            return noLocals(opcode, 2, listOf(type))
        }

        fun stdAStore(opcode: Int): NoArgInstruction {
            return noLocals(opcode, -3, emptyList())
        }

        fun stdLoadN(opcode: Int, n: Int): NoArgInstruction {
            return loadOrStoreN(opcode, n, 1)
        }

        fun longLoadN(opcode: Int, n: Int): NoArgInstruction {
            return loadOrStoreN(opcode, n + 1, 2)
        }

        fun stdStoreN(opcode: Int, n: Int): NoArgInstruction {
            return loadOrStoreN(opcode, n, -1)
        }

        fun longStoreN(opcode: Int, n: Int): NoArgInstruction {
            return loadOrStoreN(opcode, n + 1, -2)
        }

        private fun loadOrStoreN(opcode: Int, n: Int, stackDelta: Int): NoArgInstruction {
            return NoArgInstruction(opcode, stackDelta, emptyList(), n)
        }

        fun monitor(opcode: Int): BaseInstruction {
            return NoArgInstruction(opcode, 1, emptyList(), 0)
        }

        fun pop(opcode: Int, popStackCount: Int): BaseInstruction {
            return NoArgInstruction(opcode, popStackCount, emptyList(), 0)
        }
    }

}