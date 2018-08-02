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
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.pool.ConstantTags;

import java.io.DataOutput;
import java.io.IOException;

public class ByteCodeMethodHandleEntry implements ByteCode {
    private final int kind;
    private final int index;

    public ByteCodeMethodHandleEntry(int kind, int index) {
        this.kind = kind;
        this.index = index;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(ConstantTags.METHODHANDLE);
        output.writeByte(this.kind);
        output.writeShort(this.index);
    }
}
