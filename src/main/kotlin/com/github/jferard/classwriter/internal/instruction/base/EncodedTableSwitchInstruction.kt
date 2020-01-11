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
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.internal.instruction.Instruction


class EncodedTableSwitchInstruction(private val defaultOffset: Int, private val low: Int,
                                    private val high: Int,
                                    private val jump_offsets: IntArray) :
        EncodedInstruction {
    override fun write(encodedWriter: InstructionEncodedWriter) {
        return encodedWriter.tableSwitchInstruction(defaultOffset, low, high, jump_offsets)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = -1000000

}