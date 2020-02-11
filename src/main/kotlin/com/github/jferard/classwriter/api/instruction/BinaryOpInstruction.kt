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

package com.github.jferard.classwriter.api.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.base.BaseInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstructionConstants
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.EncodableWriter

class BinaryOpInstruction(private val opcode: Int, private val verificationType: VerificationType) :
        BaseInstruction {
    override fun preprocess(context: GlobalContext, codeContext: MethodContext) {
         codeContext.offsetDelta(1)
        val type1 = codeContext.stackPop();
        val type2 = codeContext.stackPop();
        //TODO: check type1 and type2
        codeContext.stackPush(verificationType)
    }

    override fun write(encodableWriter: EncodableWriter) {
        encodableWriter.binaryOp(opcode)
    }

    override fun encode(context: GlobalContext, codeContext: MethodContext): EncodedInstruction {
        return when (opcode) {
            OpCodes.DADD -> EncodedInstructionConstants.DADD_INSTRUCTION
            OpCodes.DCMPG -> EncodedInstructionConstants.DCMPG_INSTRUCTION
            OpCodes.DCMPL -> EncodedInstructionConstants.DCMPL_INSTRUCTION
            OpCodes.DDIV -> EncodedInstructionConstants.DDIV_INSTRUCTION
            OpCodes.DMUL -> EncodedInstructionConstants.DMUL_INSTRUCTION
            OpCodes.DREM -> EncodedInstructionConstants.DREM_INSTRUCTION
            OpCodes.DSUB -> EncodedInstructionConstants.DSUB_INSTRUCTION
            OpCodes.FADD -> EncodedInstructionConstants.FADD_INSTRUCTION
            OpCodes.FCMPG -> EncodedInstructionConstants.FCMPG_INSTRUCTION
            OpCodes.FCMPL -> EncodedInstructionConstants.FCMPL_INSTRUCTION
            OpCodes.FDIV -> EncodedInstructionConstants.FDIV_INSTRUCTION
            OpCodes.FMUL -> EncodedInstructionConstants.FMUL_INSTRUCTION
            OpCodes.FREM -> EncodedInstructionConstants.FREM_INSTRUCTION
            OpCodes.FSUB -> EncodedInstructionConstants.FSUB_INSTRUCTION
            OpCodes.IADD -> EncodedInstructionConstants.IADD_INSTRUCTION
            OpCodes.IDIV -> EncodedInstructionConstants.IDIV_INSTRUCTION
            OpCodes.IMUL -> EncodedInstructionConstants.IMUL_INSTRUCTION
            OpCodes.IREM -> EncodedInstructionConstants.IREM_INSTRUCTION
            OpCodes.ISUB -> EncodedInstructionConstants.ISUB_INSTRUCTION
            OpCodes.IAND -> EncodedInstructionConstants.IAND_INSTRUCTION
            OpCodes.IOR -> EncodedInstructionConstants.IOR_INSTRUCTION
            OpCodes.ISHL -> EncodedInstructionConstants.ISHL_INSTRUCTION
            OpCodes.ISHR -> EncodedInstructionConstants.ISHR_INSTRUCTION
            OpCodes.IUSHR -> EncodedInstructionConstants.IUSHR_INSTRUCTION
            OpCodes.IXOR -> EncodedInstructionConstants.IXOR_INSTRUCTION
            OpCodes.LADD -> EncodedInstructionConstants.LADD_INSTRUCTION
            OpCodes.LCMP -> EncodedInstructionConstants.LCMP_INSTRUCTION
            OpCodes.LDIV -> EncodedInstructionConstants.LDIV_INSTRUCTION
            OpCodes.LMUL -> EncodedInstructionConstants.LMUL_INSTRUCTION
            OpCodes.LREM -> EncodedInstructionConstants.LREM_INSTRUCTION
            OpCodes.LSUB -> EncodedInstructionConstants.LSUB_INSTRUCTION
            OpCodes.LAND -> EncodedInstructionConstants.LAND_INSTRUCTION
            OpCodes.LOR -> EncodedInstructionConstants.LOR_INSTRUCTION
            OpCodes.LSHL -> EncodedInstructionConstants.LSHL_INSTRUCTION
            OpCodes.LSHR -> EncodedInstructionConstants.LSHR_INSTRUCTION
            OpCodes.LUSHR -> EncodedInstructionConstants.LUSHR_INSTRUCTION
            OpCodes.LXOR -> EncodedInstructionConstants.LXOR_INSTRUCTION
            else -> throw IllegalArgumentException("binaryOp: $opcode")
        }
    }

}
