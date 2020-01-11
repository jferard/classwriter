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
package com.github.jferard.classwriter.internal.instruction.block

import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction

import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.internal.instruction.Instruction
import com.github.jferard.classwriter.pool.EncodableWriter

class IfElseInstruction(private val opcode: Int,
                        private val ifInstruction: Instruction,
                        private val elseInstruction: Instruction) :
        Instruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        val elseCodeContext: MethodContext = codeContext.clone()
        ifInstruction.preprocess(context, codeContext)
        elseInstruction.preprocess(context, elseCodeContext)
        codeContext.merge(elseCodeContext)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return EncodedIfElseInstruction(opcode, ifInstruction.encode(context,
                MethodContext.create(0)),
                elseInstruction.encode(context, MethodContext.create(0)))
    }

}