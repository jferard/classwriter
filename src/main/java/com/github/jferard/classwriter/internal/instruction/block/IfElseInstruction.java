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

package com.github.jferard.classwriter.internal.instruction.block;

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.Instruction;

public class IfElseInstruction implements Instruction {
    private final Instruction ifInstruction;
    private final Instruction elseInstruction;
    private final int opcode;

    public IfElseInstruction(int opcode, Instruction ifInstruction, Instruction elseInstruction) {
        this.opcode = opcode;
        this.ifInstruction = ifInstruction;
        this.elseInstruction = elseInstruction;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        MethodContext elseCodeContext = codeContext.clone();
        this.ifInstruction.preprocess(context, codeContext);
        this.elseInstruction.preprocess(context, elseCodeContext);
        codeContext.merge(elseCodeContext);

    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return new EncodedIfElseInstruction(this.opcode, this.ifInstruction.encode(context,
                MethodContext.create(0)),
                this.elseInstruction.encode(context, MethodContext.create(0)));
    }
}
