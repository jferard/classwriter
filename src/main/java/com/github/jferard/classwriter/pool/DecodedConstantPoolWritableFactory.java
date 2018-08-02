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
import com.github.jferard.classwriter.decoded.Decoded;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.pool.DecodedConstantPoolEntriesWritableFactory;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class DecodedConstantPoolWritableFactory implements ConstantPoolWritableFactory<Writer> {
    private final List<EncodedConstantPoolEntry> entries;
    private final List<EncodedBootstrapMethod> bootstrapMethods;

    public DecodedConstantPoolWritableFactory(List<EncodedConstantPoolEntry> entries, List<EncodedBootstrapMethod> bootstrapMethods) {
        this.entries = entries;
        this.bootstrapMethods = bootstrapMethods;
    }

    @Override
    public Writable<Writer> constantPool(List<EncodedConstantPoolEntry> entries,
                                         final List<EncodedBootstrapMethod> bootstrapMethods) {
        final DecodedConstantPoolEntriesWritableFactory decodedConstantPoolEntriesWritableFactory = DecodedConstantPoolEntriesWritableFactory
                .create(this.entries, this.bootstrapMethods);
        final List<Decoded> writableEntries = entries.stream().map(e ->
                e == null ? null : (Decoded) e
                        .toWritable(decodedConstantPoolEntriesWritableFactory))
                .collect(Collectors.toList());
        return new DecodedConstantPool(writableEntries);
    }
}
