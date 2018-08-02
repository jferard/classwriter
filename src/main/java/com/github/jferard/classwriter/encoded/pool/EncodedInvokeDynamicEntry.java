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
 * 4.4.10. The CONSTANT_InvokeDynamic_info Structure
 *
 * <pre>{@code
 * CONSTANT_InvokeDynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 * }</pre>
 */
public class EncodedInvokeDynamicEntry implements EncodedConstantPoolEntry {
    private final int bootstrapMethodIndex;
    private final int descriptorIndex;

    public EncodedInvokeDynamicEntry(int bootstrapMethodIndex, int descriptorIndex) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public Writable<?> toWritable(ConstantPoolEntriesWritableFactory<?> writableFactory) {
        return writableFactory.invokeDynamicEntry(bootstrapMethodIndex, descriptorIndex);
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
