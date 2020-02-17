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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.internal.attribute.ConstantValueAttributeFactory
import com.github.jferard.classwriter.internal.attribute.FieldAttribute
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.pool.Utf8Entry

/**
 * 4.3.2. Field Descriptors
 */
class FieldDescriptor(private val fieldType: ValueType) :
        Descriptor {
    val size: Int
        get() = fieldType.getSize(0)

    override fun toString(): String {
        return fieldType.toString()
    }

    override fun toPoolEntry(): ConstantPoolEntry {
        return Utf8Entry(this.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is FieldDescriptor) return false
        return other.fieldType == fieldType
    }

    override fun hashCode(): Int {
        return fieldType.hashCode()
    }

    fun toConstantValueAttribute(o: Any): FieldAttribute<*, *, *> {
        return ConstantValueAttributeFactory.create(fieldType, o)
    }

}