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
 * dup_x2: insert a copy of the top value into the stack two (if value2 is double or long it
 * takes up the entry of value3, too) or three values (if value2 is neither double nor long)
 * from the top.
 * Stack: (value3, value2, value1) -> (value1, value3, value2, value1).
 */
public class DupX2Instruction implements BaseInstruction, EncodedInstruction {
    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(1);

        VerificationType value1Type = codeContext.stackPop();
        VerificationType value2Type = codeContext.stackPop();

        codeContext.assertTypeAssignable(VerificationType.ONE_WORD, value1Type);
        if (value2Type.isAssignable(VerificationType.ONE_WORD)) {
            VerificationType value3Type = codeContext.stackPop();
            codeContext.assertTypeAssignable(VerificationType.ONE_WORD, value3Type);

            codeContext.stackPush(value1Type);
            codeContext.stackPush(value3Type);
        } else {
            codeContext.stackPush(value1Type);
        }
        codeContext.stackPush(value2Type);
        codeContext.stackPush(value1Type);
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    @Override
    public Writable<?> toWritable(InstructionWritableFactory<?> writableFactory) {
        return writableFactory.dupX2Instruction();
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE;
    }
}
