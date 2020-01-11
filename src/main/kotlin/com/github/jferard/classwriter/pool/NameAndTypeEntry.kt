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

import com.github.jferard.classwriter.api.Descriptor
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.pool.EncodedNameAndTypeEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

class NameAndTypeEntry(private val name: String,
                       private val descriptor: Descriptor) :
        ConstantPoolEntry {
    override fun addToPool(pool: GlobalContext): Int {
        val nameEntry =
                Utf8Entry(name)
        val nameIndex = pool!!.addToPool(nameEntry)
        val descriptorEntry =
                Utf8Entry(descriptor.toString())
        val descriptorIndex = pool.addToPool(descriptorEntry)
        return pool.addEncodedToPool(EncodedNameAndTypeEntry(nameIndex, descriptorIndex))
    }

    override fun size(): Int {
        return BytecodeHelper.BYTE_SIZE
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is NameAndTypeEntry) return false
        return other.name == name && other.descriptor == descriptor
    }

    override fun toVerificationType(): VerificationType {
        throw IllegalArgumentException()
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun hashCode(): Int {
        return 31 * name.hashCode() + descriptor.hashCode()
    }

    override fun encode(pool: GlobalContext,
                        codeContext: MethodContext): EncodedNameAndTypeEntry {
        val nameIndex = pool!!.addUtf8ToPool(name)
        val descriptorIndex = pool.addUtf8ToPool(descriptor.toString())
        return EncodedNameAndTypeEntry(nameIndex, descriptorIndex)
    }

}