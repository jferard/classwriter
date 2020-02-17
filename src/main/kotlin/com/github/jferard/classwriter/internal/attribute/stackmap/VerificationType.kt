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
package com.github.jferard.classwriter.internal.attribute.stackmap

import com.github.jferard.classwriter.api.ClassRef
import com.github.jferard.classwriter.api.FieldRef
import com.github.jferard.classwriter.api.PrimitiveValueType
import com.github.jferard.classwriter.api.ValueType
import com.github.jferard.classwriter.bytecode.ByteVerificationType
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType
import com.github.jferard.classwriter.pool.Encodable


/**
 * 4.7.4. The StackMapTable Attribute
 *
 * ```
 * union verification_type_info {
 *      Top_variable_info;
 *      Integer_variable_info;
 *      Float_variable_info;
 *      Long_variable_info;
 *      Double_variable_info;
 *      Null_variable_info;
 *      UninitializedThis_variable_info;
 *      Object_variable_info;
 *      Uninitialized_variable_info;
 * }
 * ```
 */
interface VerificationType : Encodable<VerificationType, EncodedVerificationType, VerificationTypeEncodableWriter> {
    val isLong: Boolean
    fun isAssignable(expectedType: VerificationType): Boolean

    companion object {
        fun fromFieldRef(fieldRef: FieldRef): VerificationType {
            return fieldRef.type.verificationType
        }

        fun fromClassRef(classRef: ClassRef): VerificationType {
            return classRef.toValueType().verificationType
        }

        fun fromValueType(type: ValueType?): VerificationType {
            return type?.verificationType ?: NULL
        }

        /**
         * Table 6.5.newarray-A. Array type codes
         */
        fun arrayFromPrimitiveType(valueType: PrimitiveValueType): VerificationType {
            return ValueType.array(valueType).verificationType
        }

        val TOP: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.TOP_CODE, null)
        val ONE_WORD: VerificationType =
                MockVerificationType(false, TOP)
        val TWO_WORDS: VerificationType =
                MockVerificationType(true, TOP)
        val INTEGER: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.INTEGER_CODE,
                        ONE_WORD)
        val FLOAT: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.FLOAT_CODE,
                        ONE_WORD)
        val REFERENCE: VerificationType =
                MockVerificationType(false, ONE_WORD)
        val UNITIALIZED: VerificationType =
                MockVerificationType(false, REFERENCE)
        val UNITIALIZED_THIS: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.UNINITIALIZED_THIS_CODE,
                        UNITIALIZED)
        val STRING =
                fromClassRef(ClassRef.STRING_CLASS_REF)
        val NULL: NullVerificationType = NullVerificationType()
        val LONG: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.LONG_CODE,
                        TWO_WORDS)
        val DOUBLE: ByteVerificationType =
                ByteVerificationType(
                        VerificationTypeConstants.DOUBLE_CODE,
                        TWO_WORDS)
    }
}