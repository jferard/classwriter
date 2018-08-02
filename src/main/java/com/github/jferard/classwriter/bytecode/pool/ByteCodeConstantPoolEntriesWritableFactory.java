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
import com.github.jferard.classwriter.encoded.pool.ConstantPoolEntriesWritableFactory;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.pool.ConstantTags;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeConstantPoolEntriesWritableFactory
        implements ConstantPoolEntriesWritableFactory<DataOutput> {
    @Override
    public ByteCode classEntry(int nameIndex) {
        return new ByteCodeClassEntry(nameIndex);
    }

    @Override
    public ByteCode utf8Entry(String text) {
        return new ByteCodeUtf8Entry(text);
    }

    @Override
    public ByteCode invokeDynamicEntry(int bootstrapMethodIndex, int descriptorIndex) {
        return new ByteCodeInvokeDynamicEntry(bootstrapMethodIndex, descriptorIndex);
    }

    @Override
    public ByteCode nameAndTypeEntry(int nameIndex, int descriptorIndex) {
        return new ByteCodeNameAndTypeEntry(nameIndex, descriptorIndex);
    }

    @Override
    public ByteCode methodRefEntry(int classIndex, int nameAndTypeIndex) {
        return new ByteCodeRefEntry(ConstantTags.METHODREF, classIndex, nameAndTypeIndex);
    }

    @Override
    public ByteCode doubleEntry(double value) {
        return new ByteCodeDoubleEntry(value);
    }

    @Override
    public ByteCode longEntry(long value) {
        return new ByteCodeLongEntry(value);
    }

    @Override
    public ByteCode floatEntry(float value) {
        return new ByteCodeFloatEntry(value);
    }

    @Override
    public ByteCode integerEntry(int value) {
        return new ByteCodeIntegerEntry(value);
    }

    @Override
    public ByteCode methodHandleEntry(int kind, int index) {
        return new ByteCodeMethodHandleEntry(kind, index);
    }

    @Override
    public ByteCode methodTypeEntry(int descriptorIndex) {
        return new ByteCodeMethodTypeEntry(descriptorIndex);
    }

    @Override
    public ByteCode fieldRefEntry(int classIndex, int nameAndTypeIndex) {
        return new ByteCodeRefEntry(ConstantTags.FIELDREF, classIndex, nameAndTypeIndex);
    }

    @Override
    public ByteCode stringEntry(int index) {
        return new ByteCodeStringEntry(index);
    }
}
