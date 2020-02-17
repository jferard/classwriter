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
package com.github.jferard.classwriter.tool

import com.github.jferard.classwriter.api.ArrayClassRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.api.ReferenceValueType
import com.github.jferard.classwriter.api.ValueType

object FieldTypeHelper {
    @kotlin.jvm.JvmStatic
    operator fun get(clazz: Class<*>): ValueType {
        val fieldType: ValueType = if (clazz!!.isArray) {
            ReferenceValueType(ArrayClassRef(clazz.name.replace('.', '/')))
        } else if (clazz.isPrimitive) {
            if (clazz == Boolean::class.javaPrimitiveType) {
                ValueType.BOOLEAN
            } else if (clazz == Byte::class.javaPrimitiveType) {
                ValueType.BYTE
            } else if (clazz == Char::class.javaPrimitiveType) {
                ValueType.CHAR
            } else if (clazz == Double::class.javaPrimitiveType) {
                ValueType.DOUBLE
            } else if (clazz == Float::class.javaPrimitiveType) {
                ValueType.FLOAT
            } else if (clazz == Int::class.javaPrimitiveType) {
                ValueType.INTEGER
            } else if (clazz == Long::class.javaPrimitiveType) {
                ValueType.LONG
            } else if (clazz == Short::class.javaPrimitiveType) {
                ValueType.SHORT
            } else {
                throw IllegalStateException()
            }
        } else {
            ValueType.fromClassRef(PlainClassRef(clazz.name))
        }
        return fieldType
    }
}