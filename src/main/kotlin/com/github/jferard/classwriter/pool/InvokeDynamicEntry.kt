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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.api.BootstrapMethod
import com.github.jferard.classwriter.api.MethodDescriptor
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.pool.EncodedInvokeDynamicEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

/**
 * 4.4.10. The CONSTANT_InvokeDynamic_info Structure
 */
class InvokeDynamicEntry(private val bootstrapMethod: BootstrapMethod, private val name: String,
                         private val descriptor: MethodDescriptor) :
        ConstantPoolEntry {
    override fun addToPool(pool: GlobalContext): Int {
        val encodedInvokeDynamicEntry = encode(pool,
                MethodContext.create(0))
        return pool.addEncodedToPool(encodedInvokeDynamicEntry)
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is InvokeDynamicEntry) return false
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
                        codeContext: MethodContext): EncodedInvokeDynamicEntry {
        val bootstrapIndex = pool.addBootstrapMethod(bootstrapMethod)
        val nameAndTypeIndex = pool.addToPool(NameAndTypeEntry(name, descriptor))
        return EncodedInvokeDynamicEntry(bootstrapIndex, nameAndTypeIndex)
    }

}