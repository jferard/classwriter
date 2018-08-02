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

import java.io.DataOutput;
import java.io.IOException;

public class ByteCodeClassFile implements ByteCode {
    public static final int MAGIC_NUMBER = 0xcafebabe;
    private static final int THIS_CLASS_COUNT = 1;
    private static final int SUPER_CLASS_COUNT = 1;
    private final int accessFlags;
    private final int thisIndex;
    private final int superIndex;
    private final ByteCode writableInterfaces;
    private final ByteCode writableFields;
    private final ByteCode writableMethods;
    private final ByteCode writableAttributes;
    private final ByteCode writablePool;
    private ByteCode writableHeader;

    public ByteCodeClassFile(ByteCode writableHeader, ByteCode writablePool, int accessFlags,
                             int thisIndex, int superIndex, ByteCode writableInterfaces,
                             ByteCode writableFields, ByteCode writableMethods,
                             ByteCode writableAttributes) {
        this.writableHeader = writableHeader;
        this.accessFlags = accessFlags;
        this.writablePool = writablePool;
        this.thisIndex = thisIndex;
        this.superIndex = superIndex;
        this.writableInterfaces = writableInterfaces;
        this.writableFields = writableFields;
        this.writableMethods = writableMethods;
        this.writableAttributes = writableAttributes;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        this.writableHeader.write(output);
        this.writablePool.write(output);
        output.writeShort(this.accessFlags);
        output.writeShort(this.thisIndex);
        output.writeShort(this.superIndex);
        this.writableInterfaces.write(output);
        this.writableFields.write(output);
        this.writableMethods.write(output);
        this.writableAttributes.write(output);
    }
}
