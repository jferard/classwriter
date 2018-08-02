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
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

import java.util.List;
import java.util.stream.Collectors;

public class EncodedMethods implements Encoded<MethodWritableFactory<?>> {
    private List<EncodedMethod> encodedMethods;

    public EncodedMethods(List<EncodedMethod> encodedMethods) {
        this.encodedMethods = encodedMethods;
    }

    @Override
    public Writable<?> toWritable(MethodWritableFactory<?> writableFactory) {
        return writableFactory.methods(encodedMethods);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE +
                this.encodedMethods.stream().mapToInt(EncodedMethod::getSize).sum();
    }

    @Override
    public String toString() {
        return String.format("Methods %s", this.encodedMethods);
    }
}
