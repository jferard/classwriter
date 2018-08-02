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

package com.github.jferard.classwriter.bytecode.attribute;

import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * 4.7.23. The BootstrapMethods Attribute
 * <pre>{@code
 * BootstrapMethods_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 num_bootstrap_methods;
 *     {   u2 bootstrap_method_ref;
 *         u2 num_bootstrap_arguments;
 *         u2 bootstrap_arguments[num_bootstrap_arguments];
 *     } bootstrap_methods[num_bootstrap_methods];
 * }
 * }</pre>
 */
public class ByteCodeBootstrapMethodsAttribute implements ByteCode {
    private final int nameIndex;
    private int length;
    private final List<ByteCode> writableBootstrapMethods;

    public ByteCodeBootstrapMethodsAttribute(int nameIndex, int length,
                                             List<ByteCode> writableBootstrapMethods) {
        this.nameIndex = nameIndex;
        this.length = length;
        this.writableBootstrapMethods = writableBootstrapMethods;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.nameIndex);
        output.writeInt(this.length);
        output.writeShort(this.writableBootstrapMethods.size());
        for (ByteCode writableBootstrapMethod : this.writableBootstrapMethods) {
            writableBootstrapMethod.write(output);
        }
    }
}
