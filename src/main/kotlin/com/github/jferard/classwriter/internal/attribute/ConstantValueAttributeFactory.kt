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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.api.ValueType

/**
 * 4.7.2. The ConstantValue Attribute
 * ```ConstantValue_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 constantvalue_index;
 * }
 * ``` *
 */
object ConstantValueAttributeFactory {
    const val CONSTANT_VALUE_NAME: String = "ConstantValue"
    const val SIGNATURE_NAME: String = "Signature"
    /**
     * Table 4.7.2-A. Constant value attribute types
     */
    fun create(type: ValueType, value: Any): ConstantFieldAttribute<*> {
        return when {
            type === ValueType.LONG -> ConstantLongValueAttribute(value as Long)
            type === ValueType.FLOAT -> ConstantFloatValueAttribute(value as Float)
            type === ValueType.DOUBLE -> ConstantDoubleValueAttribute(value as Double)
            type === ValueType.INTEGER || type === ValueType.SHORT || type === ValueType.CHAR || type === ValueType.BYTE || type === ValueType.BOOLEAN -> ConstantIntegerValueAttribute(
                    value as Int)
            type == ValueType.STRING -> ConstantStringValueAttribute(value as String)
            else -> throw IllegalArgumentException()
        }
    }
}