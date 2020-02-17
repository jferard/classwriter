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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

/**
 * 4.3.2. Field Descriptors
 * May be an array or an object.
 */
class ReferenceValueType(private val classRef: ClassRef) : ValueType {
    override fun toString(): String {
        return classRef.internalValueTypeName
    }

    override fun getSize(pos: Int): Int = 1

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is ReferenceValueType) return false
        val other = o as ReferenceValueType
        return other!!.classRef == classRef
    }

    override fun hashCode(): Int {
        return classRef.hashCode()
    }

    override val verificationType: VerificationType
        get() = classRef!!.toVerificationType()

    val isArray: Boolean
        get() = classRef!!.isArray

    fun componentType(): ValueType {
        return classRef!!.componentType()
    }

}