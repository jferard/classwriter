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
 * May be an array or an object.
 */
public final class ReferenceValueType implements ValueType {
    private ClassRef classRef;

    public ReferenceValueType(ClassRef classRef) {
        this.classRef = classRef;
    }

    @Override
    public String toString() {
        return this.classRef.getInternalValueTypeName();
    }

    public int getSize() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ReferenceValueType)) return false;

        ReferenceValueType other = (ReferenceValueType) o;
        return other.classRef.equals(this.classRef);
    }


    @Override
    public int hashCode() {
        return this.classRef.hashCode();
    }

    public VerificationType getVerificationType() {
        return this.classRef.toVerificationType();
    }

    public boolean isArray() {
        return this.classRef.isArray();
    }

    public ValueType componentType() {
        return classRef.componentType();
    }
}
