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
import com.github.jferard.classwriter.pool.DecodedClassName;
import com.github.jferard.classwriter.pool.DecodedFieldOrMethodRefEntrySummary;
import com.github.jferard.classwriter.pool.DecodedNameAndTypeEntrySummary;
import com.github.jferard.classwriter.pool.DecodedUt8EntryContent;

import java.io.Writer;
import java.util.List;

public class DecodedConstantPoolEntriesSummaryWritableFactory
        implements ConstantPoolEntriesWritableFactory<Writer> {
    private List<EncodedConstantPoolEntry> entries;

    public DecodedConstantPoolEntriesSummaryWritableFactory(
            List<EncodedConstantPoolEntry> entries) {
        this.entries = entries;
    }


    @Override
    public Writable<Writer> classEntry(int nameIndex) {
        final EncodedConstantPoolEntry encodedUtf8Entry = entries.get(nameIndex);
        final Writable<Writer> decodedName = (Writable<Writer>) encodedUtf8Entry.toWritable(this);
        return new DecodedClassName(decodedName);
    }

    @Override
    public Writable<Writer> utf8Entry(String text) {
        return new DecodedUt8EntryContent(text);
    }

    @Override
    public Writable<Writer> invokeDynamicEntry(int bootstrapMethodIndex, int descriptorIndex) {
        return new DecodedInvokeDynamicEntrySummary(bootstrapMethodIndex, descriptorIndex);
    }

    @Override
    public Writable<Writer> nameAndTypeEntry(int nameIndex, int descriptorIndex) {
        final EncodedConstantPoolEntry encodedNameUtf8Entry = entries.get(nameIndex);
        final Writable<Writer> decodedName = (Writable<Writer>) encodedNameUtf8Entry
                .toWritable(this);
        final EncodedConstantPoolEntry encodedDescriptorUtf8Entry = entries.get(descriptorIndex);
        final Writable<Writer> decodedDescriptor = (Writable<Writer>) encodedDescriptorUtf8Entry
                .toWritable(this);
        return new DecodedNameAndTypeEntrySummary(decodedName, decodedDescriptor);
    }

    @Override
    public Writable<Writer> methodRefEntry(int classIndex, int nameAndTypeIndex) {
        return getFieldOrMethodRefDecodedSummary(classIndex, nameAndTypeIndex);
    }

    private Writable<Writer> getFieldOrMethodRefDecodedSummary(int classIndex,
                                                               int nameAndTypeIndex) {
        EncodedConstantPoolEntry encodedClassNameUtf8Entry = entries.get(classIndex);
        final Writable<Writer> decodedClassName = (Writable<Writer>) encodedClassNameUtf8Entry
                .toWritable(this);
        EncodedConstantPoolEntry encodedNameAndTypeEntry = entries.get(nameAndTypeIndex);
        final Writable<Writer> decodedNameAndType = (Writable<Writer>) encodedNameAndTypeEntry
                .toWritable(this);
        return new DecodedFieldOrMethodRefEntrySummary(decodedClassName, decodedNameAndType);
    }

    @Override
    public Writable<Writer> doubleEntry(double value) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> longEntry(long value) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> floatEntry(float value) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> integerEntry(int value) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> methodHandleEntry(int kind, int index) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> methodTypeEntry(int descriptorIndex) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> fieldRefEntry(int classIndex, int nameAndTypeIndex) {
        return getFieldOrMethodRefDecodedSummary(classIndex, nameAndTypeIndex);
    }

    @Override
    public Writable<Writer> stringEntry(int index) {
        throw new IllegalStateException();
    }
}
