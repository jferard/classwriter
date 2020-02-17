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

import com.github.jferard.classwriter.api.FieldOrMethodRef
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.encoded.pool.EncodedMethodRefEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

/**
 * 4.4.2. The CONSTANT_Fieldref_info, CONSTANT_Methodref_info, and
 * CONSTANT_InterfaceMethodref_info Structures
 */
class MethodRefEntry(private val fieldOrMethodRef: FieldOrMethodRef) : ConstantPoolEntry {
    override fun addToPool(pool: GlobalContext): Int {
        val fieldOrMethodRefInfo =
                encode(pool, MethodContext.create(0))
        return pool!!.addEncodedToPool(fieldOrMethodRefInfo)
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.BYTE_SIZE

    override fun encode(pool: GlobalContext,
                        codeContext: MethodContext): EncodedConstantPoolEntry {
        val constantClass =
                ClassEntry(fieldOrMethodRef.classRef)
        val classIndex = pool!!.addToPool(constantClass)
        val constantNameAndType =
                NameAndTypeEntry(fieldOrMethodRef.name,
                        fieldOrMethodRef!!.toDescriptor())
        val nameAndTypeIndex = pool.addToPool(constantNameAndType)
        return EncodedMethodRefEntry(classIndex, nameAndTypeIndex)
    }

    override fun toVerificationType(): VerificationType {
        throw IllegalArgumentException()
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is MethodRefEntry) return false
        return other.fieldOrMethodRef == fieldOrMethodRef
    }

    override fun hashCode(): Int {
        return fieldOrMethodRef.hashCode()
    }

}