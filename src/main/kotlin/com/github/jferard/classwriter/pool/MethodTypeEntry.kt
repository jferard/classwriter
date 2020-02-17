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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.api.MethodDescriptor
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.pool.EncodedMethodTypeEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

class MethodTypeEntry(
        private val descriptor: MethodDescriptor) : ConstantPoolEntry {
    override fun addToPool(pool: GlobalContext): Int {
        throw IllegalStateException()
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.BYTE_SIZE

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is MethodTypeEntry) return false
        val other =
                other as MethodTypeEntry
        throw IllegalStateException()
    }

    override fun hashCode(): Int {
        throw IllegalStateException()
    }

    override fun toVerificationType(): VerificationType {
        throw IllegalArgumentException()
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(pool: GlobalContext,
                        codeContext: MethodContext): EncodedMethodTypeEntry {
        val descriptorIndex = pool!!.addUtf8ToPool(descriptor.toString())
        return EncodedMethodTypeEntry(descriptorIndex)
    }

}