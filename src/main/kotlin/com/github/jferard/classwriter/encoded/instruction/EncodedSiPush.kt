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

import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.api.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedSiPush(private val value: Int) : EncodedInstruction {
    override fun write(encodedWriter: InstructionEncodedWriter) {
        encodedWriter.siPush(value)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        TODO("Not yet implemented")
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE
}
