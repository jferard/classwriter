/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
package com.github.jferard.classwriter

import com.github.jferard.classwriter.encoded.EncodedCode
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.base.BaseInstruction
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.pool.EncodableWriter

class Code(private val instructions: List<BaseInstruction>) :
        Encodable<Code, EncodedCode, EncodableWriter> {

    override fun encode(pool: GlobalContext, codeContext: MethodContext): EncodedCode {
        val encodeds: List<EncodedInstruction> = instructions.map { i: BaseInstruction ->
                    i.encode(pool, codeContext)
                }
        return EncodedCode(encodeds)
    }

    override fun write(encodableWriter: EncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

}