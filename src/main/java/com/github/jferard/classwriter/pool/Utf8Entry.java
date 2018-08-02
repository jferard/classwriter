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

import java.nio.charset.StandardCharsets;

public class Utf8Entry implements ConstantPoolEntry, EncodedConstantPoolEntry {
    private final String text;

    public Utf8Entry(String text) {
        this.text = text;
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
        if (!(o instanceof Utf8Entry)) return false;

        Utf8Entry other = (Utf8Entry) o;
        return other.text.equals(this.text);
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }

    @Override
    public VerificationType toVerificationType() {
        throw new IllegalArgumentException();
    }

    @Override
    public EncodedConstantPoolEntry encode(GlobalContext pool, MethodContext codeContext) {
        return this;
    }

    @Override
    public String toString() {
        return "Utf8Entry[" + this.text + "]";
    }

    @Override
    public Writable<?> toWritable(ConstantPoolEntriesWritableFactory<?> writableFactory) {
        return writableFactory.utf8Entry(this.text);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE +
                this.text.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public String utf8Text() {
        return this.text;
    }
}
