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

package com.github.jferard.classwriter.tool.decoder;

import com.github.jferard.classwriter.api.ClassWritableFactory;
import com.github.jferard.classwriter.api.Header;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.encoded.EncodedClassFile;
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.EncodedMethods;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.pool.ConstantPool;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class ClassFileDecoder implements Decoder<ClassWritableFactory<?>> {
    private final InterfacesDecoder interfacesDecoder;
    private HeaderDecoder headerDecoder;
    private ConstantPoolDecoder constantPoolDecoder;

    public ClassFileDecoder(HeaderDecoder headerDecoder, ConstantPoolDecoder constantPoolDecoder,
                            InterfacesDecoder interfacesDecoder) {
        this.headerDecoder = headerDecoder;
        this.constantPoolDecoder = constantPoolDecoder;
        this.interfacesDecoder = interfacesDecoder;
    }

    @Override
    public Encoded<ClassWritableFactory<?>> decode(DataInput input) throws IOException {
        final Header header = this.headerDecoder.decode(input);
        final ConstantPool constantPool = this.constantPoolDecoder.decode(input);
        int accessFlags = input.readShort();
        int thisIndex = input.readShort();
        int superIndex = input.readShort();
        final EncodedInterfaces encodedInterfaces = this.interfacesDecoder.decode(input);

        final List<EncodedConstantPoolEntry> entries = constantPool.getEntries();
        final FieldsDecoder fieldsDecoder = FieldsDecoder.create(entries);
        final EncodedFields encodedFields = fieldsDecoder.decode(input);
        final MethodsDecoder methodsDecoder = MethodsDecoder.create(entries);

        System.out.println(header);
        System.out.println(constantPool);
        System.out.println(
                String.format("accessFlags: %s, thisIndex: %s, superIndex: %s", accessFlags,
                        thisIndex, superIndex));
        System.out.println(encodedInterfaces);
        System.out.println(encodedFields);

        final EncodedMethods encodedMethods = methodsDecoder.decode(input);
        System.out.println(encodedMethods);

        final ClassAttributesDecoder classAttributesDecoder = ClassAttributesDecoder
                .create(entries);
        final EncodedAttributes encodedAttributes = classAttributesDecoder.decode(input);

        System.out.println(encodedAttributes);
        return new EncodedClassFile(header, constantPool, accessFlags, thisIndex, superIndex,
                encodedInterfaces, encodedFields, encodedMethods, encodedAttributes);
    }
}
