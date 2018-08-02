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
import com.github.jferard.classwriter.api.ClassRef;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.EncodedClassEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

public class ClassEntry implements ConstantPoolEntry {
    private final ClassRef classRef;

    public ClassEntry(final ClassRef classRef) {
        this.classRef = classRef;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        EncodedConstantPoolEntry encodedClassEntry = this.encode(pool, MethodContext.create(0));
        return pool.addEncodedToPool(encodedClassEntry);
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public EncodedClassEntry encode(GlobalContext pool, MethodContext codeContext) {
        int nameIndex = pool.addUtf8ToPool(this.classRef.getInternalName());
        return new EncodedClassEntry(nameIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClassEntry)) return false;

        ClassEntry other = (ClassEntry) o;
        return other.classRef.equals(this.classRef);
    }

    @Override
    public int hashCode() {
        return this.classRef.hashCode();
    }

    @Override
    public String toString() {
        return "ClassEntry[" + this.classRef.getInternalName() + "]";
    }

    @Override
    public VerificationType toVerificationType() {
        return VerificationType.fromClassRef(this.classRef);
    }

}
