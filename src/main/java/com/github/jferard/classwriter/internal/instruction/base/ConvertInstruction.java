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
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

/**
 * d2f: convert a double to a float.
 * Stack: (value) -> (result).
 */
public class ConvertInstruction implements BaseInstruction, EncodedInstruction {
    private final int opcode;
    private final VerificationType fromType;
    private final VerificationType toType;

    public ConvertInstruction(int opcode, VerificationType fromType, VerificationType toType) {
        this.opcode = opcode;
        this.fromType = fromType;
        this.toType = toType;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(1);

        final VerificationType valueType = codeContext.stackPop();

        codeContext.assertTypeAssignable(this.fromType, valueType);
        codeContext.stackPush(this.toType);
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    @Override
    public Writable<?> toWritable(InstructionWritableFactory<?> writableFactory) {
        return writableFactory.convertInstruction(this.opcode);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE;
    }
}
