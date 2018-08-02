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

package com.github.jferard.classwriter.pool;

import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.api.FieldOrMethodRef;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedMethodRefEntry;

/**
 * 4.4.2. The CONSTANT_Fieldref_info, CONSTANT_Methodref_info, and
 * CONSTANT_InterfaceMethodref_info Structures
 */
public class MethodRefEntry implements ConstantPoolEntry {
    private final FieldOrMethodRef fieldOrMethodRef;

    public MethodRefEntry(FieldOrMethodRef fieldOrMethodRef) {
        this.fieldOrMethodRef = fieldOrMethodRef;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        EncodedConstantPoolEntry fieldOrMethodRefInfo = this.encode(pool, MethodContext.create(0));
        return pool.addEncodedToPool(fieldOrMethodRefInfo);
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public EncodedConstantPoolEntry encode(GlobalContext pool, MethodContext codeContext) {
        ClassEntry constantClass = new ClassEntry(this.fieldOrMethodRef.getClassRef());
        int classIndex = pool.addToPool(constantClass);
        NameAndTypeEntry constantNameAndType = new NameAndTypeEntry(this.fieldOrMethodRef.getName(),
                this.fieldOrMethodRef.toDescriptor());
        int nameAndTypeIndex = pool.addToPool(constantNameAndType);
        return new EncodedMethodRefEntry(classIndex, nameAndTypeIndex);
    }

    @Override
    public VerificationType toVerificationType() {
        throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MethodRefEntry)) return false;

        MethodRefEntry other = (MethodRefEntry) o;
        return other.fieldOrMethodRef.equals(this.fieldOrMethodRef);
    }

    @Override
    public int hashCode() {
        return this.fieldOrMethodRef.hashCode();
    }
}
