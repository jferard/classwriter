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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.ConstantPoolEntriesWritableFactory;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

public class IntegerEntry implements ConstantPoolEntry, EncodedConstantPoolEntry {
    private final int value;

    public IntegerEntry(int value) {
        this.value = value;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        return pool.addEncodedToPool(this);
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof IntegerEntry)) return false;

        IntegerEntry other = (IntegerEntry) o;
        return other.value == this.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    @Override
    public VerificationType toVerificationType() {
        return VerificationType.INTEGER;
    }

    @Override
    public EncodedConstantPoolEntry encode(GlobalContext pool, MethodContext codeContext) {
        return this;
    }

    @Override
    public Writable<?> toWritable(ConstantPoolEntriesWritableFactory<?> writableFactory) {
        return writableFactory.integerEntry(this.value);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + BytecodeHelper.INT_SIZE;
    }

    @Override
    public String utf8Text() {
        throw new IllegalArgumentException();
    }
}
