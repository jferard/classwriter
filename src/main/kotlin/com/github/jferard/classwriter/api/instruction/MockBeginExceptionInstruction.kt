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
import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction

import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.base.BaseInstruction
import com.github.jferard.classwriter.api.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.pool.EncodableWriter


class MockBeginExceptionInstruction(private val classRef: PlainClassRef,
                                    private val label: Label) :
        BaseInstruction, EncodedInstruction {
    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.storeBeginException(classRef, label, codeContext.curOffset)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        return this
    }

    override fun write(encodedWriter: InstructionEncodedWriter) {
        return encodedWriter.mock()
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Instruction {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = 0

}