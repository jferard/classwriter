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

package com.github.jferard.classwriter.encoded.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.api.instruction.InstructionConstants
import com.github.jferard.classwriter.api.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedBinaryOpInstruction(private val opcode: Int) : EncodedInstruction {
    override fun write(encodedWriter: InstructionEncodedWriter) {
        encodedWriter.binaryOp(opcode)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        return when (opcode) {
            OpCodes.DADD -> InstructionConstants.DADD_INSTRUCTION
            OpCodes.DCMPG -> InstructionConstants.DCMPG_INSTRUCTION
            OpCodes.DCMPL -> InstructionConstants.DCMPL_INSTRUCTION
            OpCodes.DDIV -> InstructionConstants.DDIV_INSTRUCTION
            OpCodes.DMUL -> InstructionConstants.DMUL_INSTRUCTION
            OpCodes.DREM -> InstructionConstants.DREM_INSTRUCTION
            OpCodes.DSUB -> InstructionConstants.DSUB_INSTRUCTION
            OpCodes.FADD -> InstructionConstants.FADD_INSTRUCTION
            OpCodes.FCMPG -> InstructionConstants.FCMPG_INSTRUCTION
            OpCodes.FCMPL -> InstructionConstants.FCMPL_INSTRUCTION
            OpCodes.FDIV -> InstructionConstants.FDIV_INSTRUCTION
            OpCodes.FMUL -> InstructionConstants.FMUL_INSTRUCTION
            OpCodes.FREM -> InstructionConstants.FREM_INSTRUCTION
            OpCodes.FSUB -> InstructionConstants.FSUB_INSTRUCTION
            OpCodes.IADD -> InstructionConstants.IADD_INSTRUCTION
            OpCodes.IDIV -> InstructionConstants.IDIV_INSTRUCTION
            OpCodes.IMUL -> InstructionConstants.IMUL_INSTRUCTION
            OpCodes.IREM -> InstructionConstants.IREM_INSTRUCTION
            OpCodes.ISUB -> InstructionConstants.ISUB_INSTRUCTION
            OpCodes.IAND -> InstructionConstants.IAND_INSTRUCTION
            OpCodes.IOR -> InstructionConstants.IOR_INSTRUCTION
            OpCodes.ISHL -> InstructionConstants.ISHL_INSTRUCTION
            OpCodes.ISHR -> InstructionConstants.ISHR_INSTRUCTION
            OpCodes.IUSHR -> InstructionConstants.IUSHR_INSTRUCTION
            OpCodes.IXOR -> InstructionConstants.IXOR_INSTRUCTION
            OpCodes.LADD -> InstructionConstants.LADD_INSTRUCTION
            OpCodes.LCMP -> InstructionConstants.LCMP_INSTRUCTION
            OpCodes.LDIV -> InstructionConstants.LDIV_INSTRUCTION
            OpCodes.LMUL -> InstructionConstants.LMUL_INSTRUCTION
            OpCodes.LREM -> InstructionConstants.LREM_INSTRUCTION
            OpCodes.LSUB -> InstructionConstants.LSUB_INSTRUCTION
            OpCodes.LAND -> InstructionConstants.LAND_INSTRUCTION
            OpCodes.LOR -> InstructionConstants.LOR_INSTRUCTION
            OpCodes.LSHL -> InstructionConstants.LSHL_INSTRUCTION
            OpCodes.LSHR -> InstructionConstants.LSHR_INSTRUCTION
            OpCodes.LUSHR -> InstructionConstants.LUSHR_INSTRUCTION
            OpCodes.LXOR -> InstructionConstants.LXOR_INSTRUCTION
            else -> throw IllegalArgumentException("binaryOp: $opcode")
        }
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE
}
