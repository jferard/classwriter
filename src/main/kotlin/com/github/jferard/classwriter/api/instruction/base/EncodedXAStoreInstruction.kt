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

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.api.instruction.InstructionConstants
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import java.lang.IllegalArgumentException

/**
 * aastore: store into a reference in an array.
 * Stack: (arrayref, index, value) -> ().
 */
class EncodedXAStoreInstruction(private val opcode: Int) :
        EncodedInstruction {
    override fun write(encodedWriter: InstructionEncodedWriter) {
        return encodedWriter.xaStoreInstruction(opcode)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        return when (opcode) {
            OpCodes.AASTORE -> InstructionConstants.AASTORE_INSTRUCTION
            OpCodes.BASTORE -> InstructionConstants.BASTORE_INSTRUCTION
            OpCodes.CASTORE -> InstructionConstants.CASTORE_INSTRUCTION
            OpCodes.DASTORE -> InstructionConstants.DASTORE_INSTRUCTION
            OpCodes.FASTORE -> InstructionConstants.FASTORE_INSTRUCTION
            OpCodes.IASTORE -> InstructionConstants.IASTORE_INSTRUCTION
            OpCodes.LASTORE -> InstructionConstants.LASTORE_INSTRUCTION
            OpCodes.SASTORE -> InstructionConstants.SASTORE_INSTRUCTION
            else -> throw IllegalArgumentException("xAstore: ${opcode}")
        }
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE
}