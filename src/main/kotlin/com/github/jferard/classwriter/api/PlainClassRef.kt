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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.internal.context.ReferenceVerificationType
import com.github.jferard.classwriter.pool.ClassEntry
import com.github.jferard.classwriter.pool.ConstantPoolEntry

/**
 * 4.4.1. The CONSTANT_Class_info Structure
 *
 *
 * "Because arrays are objects, the opcodes anewarray and multianewarray - but not the opcode new
 * - can reference array "classes" via CONSTANT_Class_info structures in the constant_pool table.
 * For such array classes, the name of the class is the descriptor of the array type (§4.3.2)."
 *
 * PlainClassRef is not an array "class".
 */
class PlainClassRef(val javaName: String) : ClassRef {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is PlainClassRef) return false
        return other.javaName == javaName
    }

    override val internalValueTypeName: String
        get() = "L$internalName;"

    override val internalName: String
        get() = javaName.replace('.', '/')

    override fun hashCode(): Int {
        return javaName.hashCode()
    }

    override fun toEntry(): ConstantPoolEntry {
        return ClassEntry(this)
    }

    override fun toValueType(): ReferenceValueType {
        return ReferenceValueType(this)
    }

    override fun toVerificationType(): ReferenceVerificationType {
        return ReferenceVerificationType(this)
    }

    override fun getDescriptor(): FieldDescriptor {
        return FieldDescriptor(toValueType())
    }

    override val isArray: Boolean
        get() = false

    override fun componentType(): ValueType {
        throw IllegalStateException()
    }

    val isAnonymous: Boolean
        get() = javaName.isEmpty()

}