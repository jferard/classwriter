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

import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.api.Label;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

/**
 * goto: goes to another instruction at branchoffset (signed short constructed from unsigned
 * bytes branchbyte1 << 8 + branchbyte2).
 * Other bytes (count: 2): branchbyte1, branchbyte2.
 */
public class GotoInstruction implements BaseInstruction {
    private static final int GOTO_SIZE = 3;
    private final Label label;

    public GotoInstruction(Label label) {
        this.label = label;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(GOTO_SIZE);
        codeContext.storeGoto(this.label);
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int branch = codeContext.getLabelOffset(this.label);
        if (branch <= BytecodeHelper.SHORT_MAX) {
            return new EncodedGotoInstruction(branch);
        } else {
            return new EncodedGotoWInstruction(branch);
        }
    }
}
