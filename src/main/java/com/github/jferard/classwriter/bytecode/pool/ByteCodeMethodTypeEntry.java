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

import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.pool.ConstantTags;

import java.io.DataOutput;
import java.io.IOException;

/**
 * 4.4.9. The CONSTANT_MethodType_info Structure
 *
 * <pre>{@code
 * CONSTANT_MethodType_info {
 *     u1 tag;
 *     u2 descriptor_index;
 * }
 * }</pre>
 */
public class ByteCodeMethodTypeEntry implements ByteCode {
    private int descriptorIndex;

    public ByteCodeMethodTypeEntry(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(ConstantTags.METHODTYPE);
        output.writeShort(this.descriptorIndex);
    }
}
