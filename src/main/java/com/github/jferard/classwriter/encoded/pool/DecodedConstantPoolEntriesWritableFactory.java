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

import com.github.jferard.classwriter.decoded.Decoded;
import com.github.jferard.classwriter.decoded.DecodedBootstrapMethodsAttributeWritableFactory;
import com.github.jferard.classwriter.decoded.DecodedPoolEntry;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.pool.DecodedClassEntry;
import com.github.jferard.classwriter.pool.DecodedPoolEntrySummary;

import java.io.Writer;
import java.util.List;

public class DecodedConstantPoolEntriesWritableFactory
        implements ConstantPoolEntriesWritableFactory<Writer> {
    public static DecodedConstantPoolEntriesWritableFactory create(
            final List<EncodedConstantPoolEntry> entries,
            final List<EncodedBootstrapMethod> bootstrapMethods) {
        return new DecodedConstantPoolEntriesWritableFactory(entries, bootstrapMethods,
                new DecodedConstantPoolEntriesSummaryWritableFactory(entries),
                new DecodedBootstrapMethodsAttributeWritableFactory());
    }

    private final DecodedConstantPoolEntriesSummaryWritableFactory decodedConstantPoolEntriesSummaryWritableFactory;
    private final List<EncodedConstantPoolEntry> entries;
    private final List<EncodedBootstrapMethod> bootstrapMethods;
    private final DecodedBootstrapMethodsAttributeWritableFactory decodedBootstrapMethodsAttributeWritableFactory;

    public DecodedConstantPoolEntriesWritableFactory(List<EncodedConstantPoolEntry> entries,
                                                     List<EncodedBootstrapMethod> bootstrapMethods,
                                                     DecodedConstantPoolEntriesSummaryWritableFactory decodedConstantPoolEntriesSummaryWritableFactory,
                                                     DecodedBootstrapMethodsAttributeWritableFactory decodedBootstrapMethodsAttributeWritableFactory) {
        this.entries = entries;
        this.bootstrapMethods = bootstrapMethods;
        this.decodedConstantPoolEntriesSummaryWritableFactory =
                decodedConstantPoolEntriesSummaryWritableFactory;
        this.decodedBootstrapMethodsAttributeWritableFactory =
                decodedBootstrapMethodsAttributeWritableFactory;
    }

    @Override
    public DecodedPoolEntry classEntry(int nameIndex) {
        return new DecodedClassEntry(nameIndex,
                decodedConstantPoolEntriesSummaryWritableFactory.classEntry(nameIndex));
    }

    @Override
    public DecodedPoolEntry utf8Entry(String text) {
        return new DecodedUtf8Entry(text);
    }

    @Override
    public DecodedPoolEntry invokeDynamicEntry(int bootstrapMethodIndex, int descriptorIndex) {
        final Decoded writableBootstrapMethod = (Decoded) bootstrapMethods.get(bootstrapMethodIndex)
                .toWritable(this.decodedBootstrapMethodsAttributeWritableFactory);

        final DecodedPoolEntrySummary writableDescriptor = (DecodedPoolEntrySummary) entries.get(descriptorIndex)
                .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory);
        return new DecodedInvokeDynamicEntry(bootstrapMethodIndex, descriptorIndex,
                writableBootstrapMethod, writableDescriptor);
    }

    @Override
    public DecodedPoolEntry nameAndTypeEntry(int nameIndex, int descriptorIndex) {
        final DecodedPoolEntrySummary writableName = (DecodedPoolEntrySummary) entries.get(nameIndex)
                .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory);
        final DecodedPoolEntrySummary writableDescriptor = (DecodedPoolEntrySummary) entries.get(descriptorIndex)
                .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory);

        return new DecodedNameAndTypeEntry(nameIndex, descriptorIndex, writableName,
                writableDescriptor);
    }

    @Override
    public DecodedPoolEntry methodRefEntry(int classIndex, int nameAndTypeIndex) {
        return createFieldOrMethdRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.METHODREF");
    }

    @Override
    public DecodedPoolEntry doubleEntry(double value) {
        throw new IllegalStateException();
    }

    @Override
    public DecodedPoolEntry longEntry(long value) {
        throw new IllegalStateException();
    }

    @Override
    public DecodedPoolEntry floatEntry(float value) {
        throw new IllegalStateException();
    }

    @Override
    public DecodedPoolEntry integerEntry(int value) {
        throw new IllegalStateException();
    }

    @Override
    public DecodedPoolEntry methodHandleEntry(int kind, int index) {
        return new DecodedMethodHandleEntry(kind, index);
    }

    @Override
    public DecodedPoolEntry methodTypeEntry(int descriptorIndex) {
        return new DecodedMethodTypeEntry(descriptorIndex);
    }

    @Override
    public DecodedPoolEntry fieldRefEntry(int classIndex, int nameAndTypeIndex) {
        return createFieldOrMethdRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.FIELDREF");
    }

    private DecodedPoolEntry createFieldOrMethdRefDecoded(int classIndex, int nameAndTypeIndex,
                                                          String code) {
        final Decoded writableClassSummary = (Decoded) this.entries.get(classIndex)
                .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory);
        final Decoded writableNameAndTypeSummary = (Decoded) this.entries.get(nameAndTypeIndex)
                .toWritable(this.decodedConstantPoolEntriesSummaryWritableFactory);
        return new DecodedFieldOrMethodRefEntry(code, classIndex, nameAndTypeIndex,
                writableClassSummary, writableNameAndTypeSummary);
    }

    @Override
    public DecodedPoolEntry stringEntry(int index) {
        throw new IllegalStateException();
    }
}
