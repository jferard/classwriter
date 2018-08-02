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
import com.github.jferard.classwriter.api.WritableFactory;

public interface ConstantPoolEntriesWritableFactory<O> extends WritableFactory<O> {
    Writable<O> classEntry(int nameIndex);

    Writable<O> utf8Entry(String text);

    Writable<O> invokeDynamicEntry(int bootstrapMethodIndex, int descriptorIndex);

    Writable<O> nameAndTypeEntry(int nameIndex, int descriptorIndex);

    Writable<O> methodRefEntry(int classIndex, int nameAndTypeIndex);

    Writable<O> doubleEntry(double value);

    Writable<O> longEntry(long value);

    Writable<O> floatEntry(float value);

    Writable<O> integerEntry(int value);

    Writable<O> methodHandleEntry(int kind, int index);

    Writable<O> methodTypeEntry(int descriptorIndex);

    Writable<O> fieldRefEntry(int classIndex, int nameAndTypeIndex);

    Writable<O> stringEntry(int index);
}
