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
 * 4.4.1. The CONSTANT_Class_info Structure
 * <pre>{@code
 * CONSTANT_Class_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 * }</pre>
 */
public class EncodedClassEntry implements EncodedConstantPoolEntry {
    private final int nameIndex;

    public EncodedClassEntry(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    @Override
    public Writable<?> toWritable(ConstantPoolEntriesWritableFactory<?> writableFactory) {
        return writableFactory.classEntry(this.nameIndex);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE;
    }

    @Override
    public String utf8Text() {
        throw new IllegalArgumentException();
    }
}
