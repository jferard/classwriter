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
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.pool.EncodedStringEntry;

public class StringEntry implements ConstantPoolEntry {
    private final String text;

    public StringEntry(String text) {
        this.text = text;
    }

    @Override
    public int addToPool(GlobalContext pool) {
        return pool.addEncodedToPool(this.encode(pool, MethodContext.create(0)));
    }

    @Override
    public int size() {
        return BytecodeHelper.BYTE_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof StringEntry)) return false;

        StringEntry other = (StringEntry) o;
        return other.text.equals(this.text);
    }

    @Override
    public int hashCode() {
        return this.text.hashCode();
    }

    @Override
    public VerificationType toVerificationType() {
        return VerificationType.STRING;
    }

    @Override
    public EncodedStringEntry encode(GlobalContext pool, MethodContext codeContext) {
        int index = pool.addUtf8ToPool(this.text);
        return new EncodedStringEntry(index);
    }
}
