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

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.List;

public class TableSwitchInstruction implements BaseInstruction {
    private PaddingHelper paddingHelper;
    private final Instruction defaultCase;
    private final int low;
    private final List<Instruction> instructions;

    public TableSwitchInstruction(PaddingHelper paddingHelper, Instruction defaultCase, int low,
                                  List<Instruction> instructions) {
        this.paddingHelper = paddingHelper;
        this.defaultCase = defaultCase;
        this.low = low;
        this.instructions = instructions;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        throw new IllegalArgumentException();
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int defaultOffset = 0;
        int low = this.low;
        int high = 0;
        int[] jump_offsets = {};
        return new EncodedTableSwitchInstruction(defaultOffset, low, high, jump_offsets);
    }
}
