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

package com.github.jferard.classwriter.internal.instruction.base;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

public class WideLoadInstruction implements BaseInstruction, EncodedInstruction {
    private final int opcode;
    private final int index;

    public WideLoadInstruction(int opcode, int index) {
        this.opcode = opcode;
        this.index = index;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(4);
        // codeContext.stackDelta(this.depth);
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    @Override
    public Writable<?> toWritable(InstructionWritableFactory<?> writableFactory) {
        return writableFactory.wideLoadInstruction(this.opcode, this.index);
    }

    @Override
    public int getSize() {
        return 2 * BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE;
    }
}
