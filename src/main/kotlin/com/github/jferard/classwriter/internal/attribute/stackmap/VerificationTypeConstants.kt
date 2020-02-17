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


/**
 * 4.7.4. The StackMapTable Attribute
 * "A verification type specifies the type of either one or two locations, where a location is
 * either a single local variable or a single operand stack entry. A verification type is
 * represented by a discriminated union, verification_type_info, that consists of a one-byte tag,
 * indicating which item of the union is in use, followed by zero or more bytes, giving more
 * information about the tag."
 * 4.10.1.2. Verification Type System
 */
object VerificationTypeConstants {
    const val TOP_CODE = 0
    const val ONE_WORD_CODE = -1
    const val TWO_WORDS_CODE = -1
    const val INTEGER_CODE = 1
    const val FLOAT_CODE = 2
    const val REFERENCE_CODE = -1
    const val UNINITIALIZED_CODE = -1
    const val UNINITIALIZED_OFFSET_CODE = 8
    const val UNINITIALIZED_VARIABLE_CODE = 8
    const val UNINITIALIZED_THIS_CODE = 6
    const val OBJECT_CODE = 7
    const val NULL_CODE = 5
    const val LONG_CODE = 3
    const val DOUBLE_CODE = 4
}