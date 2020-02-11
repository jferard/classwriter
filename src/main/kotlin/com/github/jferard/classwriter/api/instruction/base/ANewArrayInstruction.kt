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
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.api.ValueType
import com.github.jferard.classwriter.encoded.instruction.EncodedIndexedInstruction
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ClassEntry
import com.github.jferard.classwriter.pool.EncodableWriter

/**
 * anewarray: create a new array of references of length count and component type identified
 * by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool.
 * Other bytes (count: 2): indexbyte1, indexbyte2.
 * Stack: (count) -> (arrayref).
 */
class ANewArrayInstruction(private val classRef: PlainClassRef) : BaseInstruction {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInstruction {
        val index: Int =
                context.addToPool(ClassEntry(classRef))
        return EncodedIndexedInstruction(
                OpCodes.ANEWARRAY, index)
    }

    override fun preprocess(context: GlobalContext,
                            codeContext: MethodContext) {
        codeContext.offsetDelta(3)
        codeContext.assertTypeEquals(VerificationType.INTEGER, codeContext.stackPop())
        val arrayType: ValueType = ValueType.array(classRef.toValueType())
        codeContext.stackPush(arrayType.verificationType)
    }

    override fun write(encodableWriter: EncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}