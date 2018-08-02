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

package com.github.jferard.classwriter.bytecode.pool;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeConstantPoolWritableFactory
        implements ConstantPoolWritableFactory<DataOutput> {
    @Override
    public Writable<DataOutput> constantPool(List<EncodedConstantPoolEntry> entries,
                                             List<EncodedBootstrapMethod> bootstrapMethods) {
        final List<ByteCode> writableEntries = entries.stream().map(e ->
                e == null ? null : (ByteCode) e
                        .toWritable(new ByteCodeConstantPoolEntriesWritableFactory()))
                .collect(Collectors.toList());
        return new ByteCodeConstantPool(writableEntries);
    }
}
