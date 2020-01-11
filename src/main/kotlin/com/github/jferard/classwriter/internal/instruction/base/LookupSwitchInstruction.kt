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
import java.util.*

/**
 * lookupswitch: a target address is looked up from a table using a key and execution
 * continues from the instruction at that address.
 * Other bytes (count: 8+): <0–3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3,
 * defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs....
 * Stack: (key) -> ().
 */
class LookupSwitchInstruction(private val paddingHelper: PaddingHelper,
                              private val defaultCase: Instruction,
                              private val instructionByCase: SortedMap<Int, Instruction>) :
        BaseInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        paddingHelper.placeOffsetBeforePadding(codeContext)
        val padding: Int = paddingHelper.computePadding(codeContext.curOffset)
        codeContext.storePadding(padding)
        paddingHelper.placeOffsetAfterLookupSwitch(codeContext, padding,
                instructionByCase.size)
        codeContext.assertTypeAssignable(VerificationType.INTEGER, codeContext.stackPop())
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        val defaultOffset = 0
        val match_and_offsets = intArrayOf()
        return EncodedLookupSwitchInstruction(defaultOffset, match_and_offsets)
    }

}