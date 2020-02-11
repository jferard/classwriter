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
import com.github.jferard.classwriter.api.instruction.base.XALoadInstruction
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import java.lang.IllegalArgumentException

class EncodedXALoadInstruction(val opcode: Int) : EncodedInstruction {
    override fun write(encodedWriter: InstructionEncodedWriter) {
        encodedWriter.xaLoadInstruction(opcode)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        return when (opcode) {
            OpCodes.AALOAD -> InstructionConstants.AALOAD_INSTRUCTION
            OpCodes.BALOAD -> InstructionConstants.BALOAD_INSTRUCTION
            OpCodes.CALOAD -> InstructionConstants.CALOAD_INSTRUCTION
            OpCodes.DALOAD -> InstructionConstants.DALOAD_INSTRUCTION
            OpCodes.FALOAD -> InstructionConstants.FALOAD_INSTRUCTION
            OpCodes.IALOAD -> InstructionConstants.IALOAD_INSTRUCTION
            OpCodes.LALOAD -> InstructionConstants.LALOAD_INSTRUCTION
            OpCodes.SALOAD -> InstructionConstants.SALOAD_INSTRUCTION
            else -> throw IllegalArgumentException("XAload: ${opcode}")
        }
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE

}
