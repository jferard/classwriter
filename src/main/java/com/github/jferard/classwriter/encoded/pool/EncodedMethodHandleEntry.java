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

package com.github.jferard.classwriter.encoded.pool;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

/**
 * 4.4.8. The CONSTANT_MethodHandle_info Structure
 *
 * <pre>{@code
 * CONSTANT_MethodHandle_info {
 *     u1 tag;
 *     u1 reference_kind;
 *     u2 reference_index;
 * }
 * }</pre>
 */
public class EncodedMethodHandleEntry implements EncodedConstantPoolEntry {
    private final int kind;
    private final int index;

    public EncodedMethodHandleEntry(int kind, int index) {
        this.kind = kind;
        this.index = index;
    }

    @Override
    public Writable<?> toWritable(ConstantPoolEntriesWritableFactory<?> writableFactory) {
        return writableFactory.methodHandleEntry(this.kind, this.index);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + 2 * BytecodeHelper.SHORT_SIZE;
    }

    @Override
    public String utf8Text() {
        throw new IllegalArgumentException();
    }
}
