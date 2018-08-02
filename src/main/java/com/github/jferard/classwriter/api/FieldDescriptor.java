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

import com.github.jferard.classwriter.internal.attribute.ConstantValueAttributeFactory;
import com.github.jferard.classwriter.internal.attribute.FieldAttribute;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;
import com.github.jferard.classwriter.pool.Utf8Entry;

public class FieldDescriptor implements Descriptor {
    private final ValueType fieldType;

    public FieldDescriptor(ValueType fieldType) {
        this.fieldType = fieldType;
    }

    public int getSize() {
        return this.fieldType.getSize();
    }

    @Override
    public String toString() {
        return this.fieldType.toString();
    }

    @Override
    public ConstantPoolEntry toPoolEntry() {
        return new Utf8Entry(this.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FieldDescriptor)) return false;

        FieldDescriptor other = (FieldDescriptor) o;
        return other.fieldType.equals(this.fieldType);
    }


    @Override
    public int hashCode() {
        return this.fieldType.hashCode();
    }

    public FieldAttribute toConstantValueAttribute(Object o) {
        return ConstantValueAttributeFactory.create(this.fieldType, o);
    }
}
