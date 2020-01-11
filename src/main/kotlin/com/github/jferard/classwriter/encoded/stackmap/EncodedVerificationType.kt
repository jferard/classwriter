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
package com.github.jferard.classwriter.encoded.stackmap

import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter

/**
 * 4.7.4. The StackMapTable Attribute
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
interface EncodedVerificationType :
        Encoded<VerificationType, EncodedVerificationType, VerificationTypeEncodedWriter>