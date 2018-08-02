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

package com.github.jferard.classwriter.internal.context;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.BootstrapMethod;
import com.github.jferard.classwriter.internal.attribute.BootstrapMethodsAttributeBuilder;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.pool.Utf8Entry;

/**
 * 4.4 The Constant Pool
 */
public class GlobalContext {
    public static GlobalContext create() {
        return new GlobalContext(new ConstantPool(), new BootstrapMethodsAttributeBuilder());
    }

    private final ConstantPool pool;
    private final BootstrapMethodsAttributeBuilder bootstrapMethodsBuilder;

    public GlobalContext(ConstantPool pool,
                         BootstrapMethodsAttributeBuilder bootstrapMethodsAttributeBuilder) {
        this.pool = pool;
        this.bootstrapMethodsBuilder = bootstrapMethodsAttributeBuilder;
    }

    public int addToPool(ConstantPoolEntry entry) {
        return this.pool.add(entry, this);
    }

    public int addEncodedToPool(EncodedConstantPoolEntry encodedEntry) {
        return this.pool.addEncoded(encodedEntry);
    }

    public int addBootstrapMethod(BootstrapMethod bootstrapMethod) {
        return this.bootstrapMethodsBuilder.add(bootstrapMethod);
    }

    public int addUtf8ToPool(String text) {
        return this.addToPool(new Utf8Entry(text));
    }

    public Writable<?> getWritablePool(
            ConstantPoolWritableFactory<?> writableFactory) {
        return this.getEncodedPool().toWritable(writableFactory);
    }

    public ConstantPool getEncodedPool() {
        return this.pool.encode(null, null);
    }
}
