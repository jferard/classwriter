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
package com.github.jferard.classwriter.api.instruction.block

import com.github.jferard.classwriter.encoded.instruction.EncodedBlockInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.Instruction
import com.github.jferard.classwriter.encoded.instruction.EncodedMethodBlockInstruction
import com.github.jferard.classwriter.pool.EncodableWriter
import java.util.function.Consumer

/**
 * Should not contain any jmp
 */
class BaseBlockInstruction(
        val instructions: List<Instruction>) :
        Instruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        instructions.forEach(
                Consumer { i: Instruction ->
                    i.preprocess(context, codeContext)
                })
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return EncodedMethodBlockInstruction(
                instructions.map { i: Instruction ->
                    i.encode(context, MethodContext.create(0))
                })
    }

}