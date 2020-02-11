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

import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedGotoInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedGotoWInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction

import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.EncodableWriter


/**
 * goto: goes to another instruction at branchoffset (signed short constructed from unsigned
 * bytes branchbyte1 << 8 + branchbyte2).
 * Other bytes (count: 2): branchbyte1, branchbyte2.
 */
class GotoInstruction(private val label: Label) :
        BaseInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(
                GotoInstruction.GOTO_SIZE)
        codeContext.storeGoto(label)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        val branch: Int = codeContext.getLabelOffset(label)
        return if (branch <= BytecodeHelper.SHORT_MAX) {
            EncodedGotoInstruction(
                    branch)
        } else {
            EncodedGotoWInstruction(
                    branch)
        }
    }

    companion object {
        private const val GOTO_SIZE = 3
    }

}