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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.api.ValueType;

/**
 * 4.7.2. The ConstantValue Attribute
 * <pre>{@code
 * ConstantValue_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 constantvalue_index;
 * }
 * }</pre>
 */
public final class ConstantValueAttributeFactory {
    public static final String CONSTANT_VALUE_NAME = "ConstantValue";
    public static final String SIGNATURE_NAME = "Signature";

    /**
     * Table 4.7.2-A. Constant value attribute types
     */
    public static FieldAttribute create(ValueType type, Object value) {
        if (type == ValueType.LONG) {
            return new ConstantLongValueAttribute((long) value);
        } else if (type == ValueType.FLOAT) {
            return new ConstantFloatValueAttribute((float) value);
        } else if (type == ValueType.DOUBLE) {
            return new ConstantDoubleValueAttribute((double) value);
        } else if (type == ValueType.INTEGER || type == ValueType.SHORT || type == ValueType.CHAR ||
                type == ValueType.BYTE || type == ValueType.BOOLEAN) {
            return new ConstantIntegerValueAttribute((int) value);
        } else if (type.equals(ValueType.STRING)) {
            return new ConstantStringValueAttribute((String) value);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
