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

package com.github.jferard.classwriter.internal.attribute.stackmap;

/**
 * 4.7.4. The StackMapTable Attribute
 * "A verification type specifies the type of either one or two locations, where a location is
 * either a single local variable or a single operand stack entry. A verification type is
 * represented by a discriminated union, verification_type_info, that consists of a one-byte tag,
 * indicating which item of the union is in use, followed by zero or more bytes, giving more
 * information about the tag."
 * 4.10.1.2. Verification Type System
 */
public class VerificationTypeConstants {
    public static final int TOP_CODE = 0;

    public static final int ONE_WORD_CODE = -1;
    public static final int TWO_WORDS_CODE = -1;

    public static final int INTEGER_CODE = 1;
    public static final int FLOAT_CODE = 2;
    public static final int REFERENCE_CODE = -1;

    public static final int UNINITIALIZED_CODE = -1;
    public static final int UNINITIALIZED_OFFSET_CODE = 8;
    public static final int UNINITIALIZED_THIS_CODE = 6;
    public static final int OBJECT_CODE = 7;
    public static final int NULL_CODE = 5;

    public static final int LONG_CODE = 3;
    public static final int DOUBLE_CODE = 4;
}
