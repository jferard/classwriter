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

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

/**
 * 4.3.2. Field Descriptors
 */
interface ValueType : Sized {
    val verificationType: VerificationType

    companion object {
        fun fromClassRef(classRef: PlainClassRef): ReferenceValueType {
            return classRef!!.toValueType()
        }

        fun array(valueType: ValueType): ReferenceValueType {
            return ReferenceValueType(PlainClassRef("[" + valueType.toString()))
        }

        val BOOLEAN: PrimitiveValueType = PrimitiveValueType.integer("Z")
        val BYTE: PrimitiveValueType = PrimitiveValueType.integer("B")
        val CHAR: PrimitiveValueType = PrimitiveValueType.integer("C")
        val INTEGER: PrimitiveValueType = PrimitiveValueType.integer("I")
        val SHORT: PrimitiveValueType = PrimitiveValueType.integer("S")
        val DOUBLE: PrimitiveValueType =
                PrimitiveValueType.twoWords("D", VerificationType.DOUBLE)
        val FLOAT: PrimitiveValueType =
                PrimitiveValueType.oneWord("F", VerificationType.FLOAT)
        val LONG: PrimitiveValueType =
                PrimitiveValueType.twoWords("L", VerificationType.LONG)
        val STRING: ReferenceValueType = ClassRef.STRING_CLASS_REF.toValueType()
    }
}