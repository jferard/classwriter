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
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 4.4 The Constant Pool
 */
public class ConstantPool
        implements Encodable<ConstantPool>, Encoded<ConstantPoolWritableFactory<?>> {
    private final Map<EncodedConstantPoolEntry, Integer> indexByEncoded;
    private final Map<ConstantPoolEntry, Integer> indexByEntry;
    private int index;

    public ConstantPool() {
        this.index = 1;
        this.indexByEncoded = new HashMap<>();
        this.indexByEntry = new HashMap<>();
    }

    public int add(ConstantPoolEntry entry, GlobalContext context) {
        Integer previousIndex = this.indexByEntry.get(entry);
        if (previousIndex == null) {
            int index = entry.addToPool(context);
            this.indexByEntry.put(entry, index);
            return index;
        } else {
            return previousIndex;
        }
    }

    public int addEncoded(EncodedConstantPoolEntry encodedEntry) {
        this.indexByEncoded.put(encodedEntry, this.index);
        return this.index++;
    }

    /**
     * @return the size of the pool
     */
    public int getSize() {
        return this.indexByEntry.keySet().stream().mapToInt(ConstantPoolEntry::size).sum();
    }

    public int count() {
        return this.indexByEncoded.size();
    }

    @Override
    public Writable<?> toWritable(ConstantPoolWritableFactory<?> writableFactory) {
        return writableFactory.constantPool(this.getEntries(), null);
    }

    @Override
    public ConstantPool encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    public List<EncodedConstantPoolEntry> getEntries() {
        List<EncodedConstantPoolEntry> entries = new ArrayList<>();
        for (Map.Entry<EncodedConstantPoolEntry, Integer> e : this.indexByEncoded.entrySet()) {
            final Integer index = e.getValue();
            while (index > entries.size()-1)
                entries.add(null);
            entries.set(index, e.getKey());
        }
        return entries;
    }

    public String toString() {
        return String.format("Constant pool %s", this.getEntries());
    }
}
