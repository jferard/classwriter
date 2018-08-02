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

package com.github.jferard.classwriter.decoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.ClassWritableFactory;
import com.github.jferard.classwriter.api.Header;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.EncodedMethods;
import com.github.jferard.classwriter.encoded.pool.DecodedConstantPoolEntriesSummaryWritableFactory;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.pool.DecodedConstantPoolWritableFactory;
import com.github.jferard.classwriter.tool.decoder.EncodedInterfaces;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class DecodedClassWritableFactory implements ClassWritableFactory<Writer> {
    public static DecodedClassWritableFactory create() {
        return new DecodedClassWritableFactory(new DecodedAttributeWritableFactory(
                new DecodedBootstrapMethodsAttributeWritableFactory(),
                new DecodedInstructionWritableFactory(), DecodedMethodWritableFactory.create()),
                DecodedFieldWritableFactory.create(),
                new DecodedMethodWritableFactory(DecodedAttributeWritableFactory.create()));
    }

    private final DecodedAttributeWritableFactory attributeWritableFactory;
    private final DecodedFieldWritableFactory fieldWritableFactory;
    private final DecodedMethodWritableFactory methodWritableFactory;
    private DecodedConstantPoolWritableFactory poolWritableFactory;
    private List<EncodedConstantPoolEntry> entries;
    private DecodedConstantPoolEntriesSummaryWritableFactory decodedConstantPoolEntriesSummaryWritableFactory; // new DecodedConstantPoolEntriesSummaryWritableFactory(entries)

    public DecodedClassWritableFactory(DecodedAttributeWritableFactory attributeWritableFactory,
                                       DecodedFieldWritableFactory fieldWritableFactory,
                                       DecodedMethodWritableFactory methodWritableFactory) {
        this.attributeWritableFactory = attributeWritableFactory;
        this.fieldWritableFactory = fieldWritableFactory;
        this.methodWritableFactory = methodWritableFactory;
    }

    @Override
    public Writable<Writer> classFile(Header header, ConstantPool pool, int accessFlags,
                                      int thisIndex, int superIndex,
                                      EncodedInterfaces encodedInterfaces,
                                      EncodedFields encodedFields, EncodedMethods encodedMethods,
                                      EncodedAttributes encodedAttributes) {
        this.entries = pool.getEntries();
        List<EncodedBootstrapMethod> bootstrapMethods = encodedAttributes.getBootstrapMethods();
        this.decodedConstantPoolEntriesSummaryWritableFactory =
                new DecodedConstantPoolEntriesSummaryWritableFactory(
                entries);
        poolWritableFactory = new DecodedConstantPoolWritableFactory(entries, bootstrapMethods);
        Decoded writableHeader = (Decoded) header.toWritable(this);
        Decoded writablePool = (Decoded) pool.toWritable(this.poolWritableFactory);
        final Writable<Writer> writableInterfaces = (Writable<Writer>) encodedInterfaces
                .toWritable(this);
        final Writable<Writer> writableFields = (Writable<Writer>) encodedFields
                .toWritable(this.fieldWritableFactory);
        final Writable<Writer> writableMethods = (Writable<Writer>) encodedMethods
                .toWritable(this.methodWritableFactory);

        final Writable<Writer> writableAttributes = (Writable<Writer>) encodedAttributes
                .toWritable(this.attributeWritableFactory);
        return new DecodedClassFile(writableHeader, writablePool, accessFlags, thisIndex,
                superIndex, writableInterfaces, writableFields, writableMethods,
                writableAttributes);
    }

    @Override
    public Writable<Writer> header(int minorVersion, int majorVersion) {
        return new DecodedHeader(minorVersion, majorVersion);
    }

    @Override
    public Writable<Writer> interfaces(List<Integer> encodedInterfaces) {
        List<DecodedInterface> decodedInterfaces = encodedInterfaces.stream()
                .map(ei -> new DecodedInterface(ei, (Decoded) entries.get(ei)
                        .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory)))
                .collect(Collectors.toList());
        return new DecodedInterfaces(decodedInterfaces);
    }
}
