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

/**
 * 4.7.3. The Code Attribute > exception_table[]
 */
public class ByteCodeExceptionInCode implements ByteCode {
    public static final int SIZE = 4 * BytecodeHelper.SHORT_SIZE;
    private final int startPc;
    private final int endPc;
    private final int handlerPc;
    private final int catchTypeIndex;

    public ByteCodeExceptionInCode(int startPc, int endPc, int handlerPc, int catchTypeIndex) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchTypeIndex = catchTypeIndex;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.startPc);
        output.writeShort(this.endPc);
        output.writeShort(this.handlerPc);
        output.writeShort(this.catchTypeIndex);
    }
}
