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

import com.github.jferard.classwriter.internal.access.MethodAccess;
import com.github.jferard.classwriter.api.MethodBuilder;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.internal.instruction.Instruction;
import com.github.jferard.classwriter.tool.FieldTypeHelper;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class ByteCodeMethodWriter implements ByteCode {
    public static final String INIT = "<init>";
    private static final String MAIN = "main";

    public static MethodBuilder builder(String methodName, final MethodDescriptor descriptor,
                                        final Instruction code) {
        return new MethodBuilder(methodName, descriptor, code);
    }

    public static MethodBuilder init(final MethodDescriptor descriptor, Instruction code) {
        return new MethodBuilder(INIT, descriptor, code);
    }

    public static MethodBuilder main(Instruction code) {
        int accessFlags = MethodAccess.builder().publicFlag().staticFlag().finalFlag().build();
        return new MethodBuilder(MAIN,
                MethodDescriptor.builder().params(FieldTypeHelper.get(String[].class)).build(),
                code).access(accessFlags);
    }

    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final List<ByteCode> attributeWriters;

    public ByteCodeMethodWriter(int accessFlags, int nameIndex, int descriptorIndex,
                                List<ByteCode> attributeWriters) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributeWriters = attributeWriters;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.accessFlags);
        output.writeShort(this.nameIndex);
        output.writeShort(this.descriptorIndex);
        output.writeShort(this.attributeWriters.size());
        for (ByteCode attributeWriter : this.attributeWriters) {
            attributeWriter.write(output);
        }
    }
}
