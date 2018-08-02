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

package com.github.jferard.classwriter.api;

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;

/**
 * 4.3.2. Field Descriptors
 */
public interface ValueType {
    PrimitiveValueType BOOLEAN = PrimitiveValueType.integer("Z");
    PrimitiveValueType BYTE = PrimitiveValueType.integer("B");
    PrimitiveValueType CHAR = PrimitiveValueType.integer("C");
    PrimitiveValueType INTEGER = PrimitiveValueType.integer("I");
    PrimitiveValueType SHORT = PrimitiveValueType.integer("S");
    PrimitiveValueType DOUBLE = PrimitiveValueType.twoWords("D", VerificationType.DOUBLE);
    PrimitiveValueType FLOAT = PrimitiveValueType.oneWord("F", VerificationType.FLOAT);
    PrimitiveValueType LONG = PrimitiveValueType.twoWords("L", VerificationType.LONG);

    ReferenceValueType STRING = ClassRef.STRING_CLASS_REF.toValueType();

    static ReferenceValueType fromClassRef(PlainClassRef classRef) {
        return classRef.toValueType();
    }

    static ReferenceValueType array(final ValueType valueType) {
        return new ReferenceValueType(new PlainClassRef("[" + valueType.toString()));
    }

    int getSize();

    VerificationType getVerificationType();
}
