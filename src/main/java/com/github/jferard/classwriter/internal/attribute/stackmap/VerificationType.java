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

import com.github.jferard.classwriter.api.ClassRef;
import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.PrimitiveValueType;
import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.bytecode.stackmap.ByteVerificationType;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;
import com.github.jferard.classwriter.pool.Encodable;

/**
 * 4.7.4. The StackMapTable Attribute
 * <pre>{@code
 * union verification_type_info {
 *     Top_variable_info;
 *     Integer_variable_info;
 *     Float_variable_info;
 *     Long_variable_info;
 *     Double_variable_info;
 *     Null_variable_info;
 *     UninitializedThis_variable_info;
 *     Object_variable_info;
 *     Uninitialized_variable_info;
 * }
 * }</pre>
 */
public interface VerificationType extends Encodable<EncodedVerificationType> {
    VerificationType TOP = new ByteVerificationType(VerificationTypeConstants.TOP_CODE, null);

    VerificationType ONE_WORD = new MockVerificationType(false, VerificationType.TOP);
    VerificationType TWO_WORDS = new MockVerificationType(true, VerificationType.TOP);

    VerificationType INTEGER = new ByteVerificationType(VerificationTypeConstants.INTEGER_CODE,
            VerificationType.ONE_WORD);
    VerificationType FLOAT = new ByteVerificationType(VerificationTypeConstants.FLOAT_CODE,
            VerificationType.ONE_WORD);
    VerificationType REFERENCE = new MockVerificationType(false, VerificationType.ONE_WORD);

    VerificationType UNITIALIZED = new MockVerificationType(false, VerificationType.REFERENCE);
    VerificationType UNITIALIZED_THIS = new ByteVerificationType(
            VerificationTypeConstants.UNINITIALIZED_THIS_CODE, VerificationType.UNITIALIZED);

    VerificationType STRING = VerificationType.fromClassRef(ClassRef.STRING_CLASS_REF);
    VerificationType NULL = new NullVerificationType();

    VerificationType LONG = new ByteVerificationType(VerificationTypeConstants.LONG_CODE,
            VerificationType.TWO_WORDS);
    VerificationType DOUBLE = new ByteVerificationType(VerificationTypeConstants.DOUBLE_CODE,
            VerificationType.TWO_WORDS);

    static VerificationType fromFieldRef(FieldRef fieldRef) {
        return fieldRef.getType().getVerificationType();
    }

    static VerificationType fromClassRef(ClassRef classRef) {
        return classRef.toValueType().getVerificationType();
    }

    static VerificationType fromValueType(ValueType type) {
        return type.getVerificationType();
    }

    /**
     * Table 6.5.newarray-A. Array type codes
     */
    static VerificationType arrayFromPrimitiveType(PrimitiveValueType valueType) {
        return ValueType.array(valueType).getVerificationType();
    }

    boolean isLong();

    boolean isAssignable(VerificationType expectedType);
}
