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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.api.BootstrapMethod;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.EncodedInvokeDynamicEntry;

/**
 * 4.4.10. The CONSTANT_InvokeDynamic_info Structure
 */
public class InvokeDynamicEntry implements ConstantPoolEntry {
    private final BootstrapMethod bootstrapMethod;
    private final String name;
    private final MethodDescriptor descriptor;

    public InvokeDynamicEntry(BootstrapMethod bootstrapMethod, String name, MethodDescriptor descriptor) {
        this.bootstrapMethod = bootstrapMethod;
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        EncodedInvokeDynamicEntry encodedInvokeDynamicEntry = this.encode(pool,
                MethodContext.create(0));
        return pool.addEncodedToPool(encodedInvokeDynamicEntry);
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof InvokeDynamicEntry)) return false;

        InvokeDynamicEntry other = (InvokeDynamicEntry) o;
        throw new IllegalStateException();
    }

    @Override
    public int hashCode() {
        throw new IllegalStateException();
    }

    @Override
    public VerificationType toVerificationType() {
        throw new IllegalArgumentException();
    }


    @Override
    public EncodedInvokeDynamicEntry encode(GlobalContext pool, MethodContext codeContext) {
        int bootstrapIndex = pool.addBootstrapMethod(this.bootstrapMethod);
        int nameAndTypeIndex = pool.addToPool(new NameAndTypeEntry(this.name, this.descriptor));
        return new EncodedInvokeDynamicEntry(bootstrapIndex, nameAndTypeIndex);
    }
}
