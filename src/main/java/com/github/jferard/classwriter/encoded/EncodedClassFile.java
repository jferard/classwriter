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

package com.github.jferard.classwriter.encoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.ClassWritableFactory;
import com.github.jferard.classwriter.api.Header;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.tool.decoder.EncodedInterfaces;

public class EncodedClassFile implements Encoded<ClassWritableFactory<?>> {
    public static final int MAGIC_NUMBER = 0xcafebabe;
    private static final int THIS_CLASS_COUNT = 1;
    private static final int SUPER_CLASS_COUNT = 1;
    private final EncodedInterfaces encodedInterfaces;
    private Header header;
    private final ConstantPool pool;
    private final int accessFlags;
    private final int thisIndex;
    private final int superIndex;
    private final EncodedFields encodedFields;
    private final EncodedMethods encodedMethods;
    private final EncodedAttributes encodedAttributes;

    public EncodedClassFile(Header header, ConstantPool pool, int accessFlags,
                            int thisIndex, int superIndex, EncodedInterfaces encodedInterfaces, EncodedFields encodedFields,
                            EncodedMethods encodedMethods,
                            EncodedAttributes encodedAttributes) {
        this.header = header;
        this.pool = pool;
        this.accessFlags = accessFlags;
        this.thisIndex = thisIndex;
        this.superIndex = superIndex;
        this.encodedInterfaces = encodedInterfaces;
        this.encodedFields = encodedFields;
        this.encodedMethods = encodedMethods;
        this.encodedAttributes = encodedAttributes;
    }

    @Override
    public Writable<?> toWritable(ClassWritableFactory<?> writableFactory) {
        return writableFactory
                .classFile(this.header, this.pool, this.accessFlags, this.thisIndex, this.superIndex, this.encodedInterfaces, this.encodedFields,
                        this.encodedMethods, this.encodedAttributes);
    }

    @Override
    public int getSize() {
        return 0;
    }
}
