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
import com.github.jferard.classwriter.api.Descriptor;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.EncodedNameAndTypeEntry;

public class NameAndTypeEntry implements ConstantPoolEntry {
    private final String name;
    private final Descriptor descriptor;

    public NameAndTypeEntry(String name, Descriptor descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        Utf8Entry nameEntry = new Utf8Entry(this.name);
        int nameIndex = pool.addToPool(nameEntry);
        Utf8Entry descriptorEntry = new Utf8Entry(this.descriptor.toString());
        int descriptorIndex = pool.addToPool(descriptorEntry);
        return pool.addEncodedToPool(new EncodedNameAndTypeEntry(nameIndex, descriptorIndex));
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NameAndTypeEntry)) return false;

        NameAndTypeEntry other = (NameAndTypeEntry) o;
        return other.name.equals(this.name) && other.descriptor.equals(this.descriptor);
    }

    @Override
    public VerificationType toVerificationType() {
        throw new IllegalArgumentException();
    }

    @Override
    public int hashCode() {
        return 31 * this.name.hashCode() + this.descriptor.hashCode();
    }


    @Override
    public EncodedNameAndTypeEntry encode(GlobalContext pool, MethodContext codeContext) {
        int nameIndex = pool.addUtf8ToPool(this.name);
        int descriptorIndex = pool.addUtf8ToPool(this.descriptor.toString());
        return new EncodedNameAndTypeEntry(nameIndex, descriptorIndex);
    }
}
