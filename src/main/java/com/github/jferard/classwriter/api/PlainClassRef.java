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
 * <p>
 * PlainClassRef is not an array "class".
 */
public class PlainClassRef implements ClassRef {
    private final String javaName;

    public PlainClassRef(String javaName) {
        this.javaName = javaName;
    }

    public String getJavaName() {
        return this.javaName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PlainClassRef)) return false;

        PlainClassRef other = (PlainClassRef) o;
        return other.javaName.equals(this.javaName);
    }

    @Override
    public String getInternalValueTypeName() {
        return "L" + this.getInternalName() + ";";
    }

    @Override
    public String getInternalName() {
        return this.javaName.replace('.', '/');
    }

    @Override
    public int hashCode() {
        return this.javaName.hashCode();
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
        return new FieldDescriptor(this.toValueType());
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public ValueType componentType() {
        throw new IllegalStateException();
    }

    public boolean isAnonymous() {
        return this.javaName.isEmpty();
    }
}
