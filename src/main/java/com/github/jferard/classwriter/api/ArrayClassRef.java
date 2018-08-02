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

import com.github.jferard.classwriter.internal.context.ReferenceVerificationType;
import com.github.jferard.classwriter.pool.ClassEntry;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;

/**
 * 4.4.1. The CONSTANT_Class_info Structure
 * <p>
 * "Because arrays are objects, the opcodes anewarray and multianewarray - but not the opcode new
 * - can reference array "classes" via CONSTANT_Class_info structures in the constant_pool table.
 * For such array classes, the name of the class is the descriptor of the array type (§4.3.2)."
 *
 * ArrayClassRef is an array "class".
 */
public class ArrayClassRef implements ClassRef {
    private final String descriptor;

    public ArrayClassRef(String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ArrayClassRef)) return false;

        ArrayClassRef other = (ArrayClassRef) o;
        return other.descriptor.equals(this.descriptor);
    }

    @Override
    public String getInternalValueTypeName() {
        return this.descriptor;
    }

    @Override
    public String getInternalName() {
        return this.descriptor;
    }

    @Override
    public int hashCode() {
        return this.descriptor.hashCode();
    }

    @Override
    public ConstantPoolEntry toEntry() {
        return new ClassEntry(this);
    }

    @Override
    public ReferenceValueType toValueType() {
        return new ReferenceValueType(this);
    }

    @Override
    public ReferenceVerificationType toVerificationType() {
        return new ReferenceVerificationType(this);
    }

    @Override
    public FieldDescriptor getDescriptor() {
        return new FieldDescriptor(toValueType());
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public ValueType componentType() {
        final ArrayClassRef componentClassRef = new ArrayClassRef(this.descriptor.substring(1));
        return componentClassRef.toValueType();
    }
}
