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

package com.github.jferard.classwriter.tool;

import com.github.jferard.classwriter.api.ArrayClassRef;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.ReferenceValueType;
import com.github.jferard.classwriter.api.ValueType;

public class FieldTypeHelper {
    public static ValueType get(Class<?> clazz) {
        final ValueType fieldType;
        if (clazz.isArray()) {
            fieldType = new ReferenceValueType(new ArrayClassRef(clazz.getName().replace('.', '/')));
        } else if (clazz.isPrimitive()) {
            if (clazz == boolean.class) {
                fieldType = ValueType.BOOLEAN;
            } else if (clazz == byte.class) {
                fieldType = ValueType.BYTE;
            } else if (clazz == char.class) {
                fieldType = ValueType.CHAR;
            } else if (clazz == double.class) {
                fieldType = ValueType.DOUBLE;
            } else if (clazz == float.class) {
                fieldType = ValueType.FLOAT;
            } else if (clazz == int.class) {
                fieldType = ValueType.INTEGER;
            } else if (clazz == long.class) {
                fieldType = ValueType.LONG;
            } else if (clazz == short.class) {
                fieldType = ValueType.SHORT;
            } else {
                throw new IllegalStateException();
            }
        } else {
            fieldType = ValueType.fromClassRef(new PlainClassRef(clazz.getName()));
        }
        return fieldType;
    }

}
