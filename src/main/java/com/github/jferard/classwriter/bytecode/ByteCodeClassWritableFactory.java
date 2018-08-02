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

package com.github.jferard.classwriter.bytecode;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.api.ClassWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.api.FieldWritableFactory;
import com.github.jferard.classwriter.api.Header;
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.bytecode.pool.ByteCodeConstantPoolWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.EncodedMethods;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.tool.decoder.EncodedInterfaces;

import java.io.DataOutput;
import java.util.List;

public class ByteCodeClassWritableFactory implements ClassWritableFactory<DataOutput> {
    public static ByteCodeClassWritableFactory create() {
        return new ByteCodeClassWritableFactory(new ByteCodeFieldWritableFactory(),
                new ByteCodeMethodWritableFactory(new ByteCodeAttributeWritableFactory()),
                new ByteCodeAttributeWritableFactory(), new ByteCodeConstantPoolWritableFactory());
    }

    private final FieldWritableFactory<DataOutput> fieldWritableFactory;
    private final MethodWritableFactory<DataOutput> methodWritableFactory;
    private final AttributeWritableFactory<DataOutput> classAttributeWritableFactory;
    private ConstantPoolWritableFactory<DataOutput> constantPoolWritableFactory;

    public ByteCodeClassWritableFactory(FieldWritableFactory<DataOutput> fieldWritableFactory,
                                        MethodWritableFactory<DataOutput> methodWritableFactory,
                                        AttributeWritableFactory<DataOutput> classAttributeWritableFactory,
                                        ConstantPoolWritableFactory<DataOutput> constantPoolWritableFactory) {
        this.fieldWritableFactory = fieldWritableFactory;
        this.methodWritableFactory = methodWritableFactory;
        this.classAttributeWritableFactory = classAttributeWritableFactory;
        this.constantPoolWritableFactory = constantPoolWritableFactory;
    }

    @Override
    public ByteCode classFile(Header header, ConstantPool pool, int accessFlags, int thisIndex,
                              int superIndex, EncodedInterfaces encodedInterfaces,
                              EncodedFields encodedFields, EncodedMethods encodedMethods,
                              EncodedAttributes encodedAttributes) {
        ByteCode writableInterfaces = (ByteCode) encodedInterfaces.toWritable(this);
        ByteCode writableFields = (ByteCode) encodedFields.toWritable(this.fieldWritableFactory);
        ByteCode writableMethods = (ByteCode) encodedMethods.toWritable(this.methodWritableFactory);
        ByteCode writableAttributes = (ByteCode) encodedAttributes
                .toWritable(this.classAttributeWritableFactory);
        final ByteCode writablePool = (ByteCode) pool.toWritable(this.constantPoolWritableFactory);
        final ByteCode writableHeader = (ByteCode) header.toWritable(this);
        return new ByteCodeClassFile(writableHeader, writablePool, accessFlags, thisIndex,
                superIndex, writableInterfaces, writableFields, writableMethods, writableAttributes);
    }

    @Override
    public Writable<DataOutput> header(int minorVersion, int majorVersion) {
        return new ByteCodeHeader(minorVersion, majorVersion);
    }

    @Override
    public Writable<DataOutput> interfaces(List<Integer> encodedInterfaces) {
        return new ByteCodeInterfaces(encodedInterfaces);
    }
}
