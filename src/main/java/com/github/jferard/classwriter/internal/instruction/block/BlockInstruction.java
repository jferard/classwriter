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

import java.util.List;
import java.util.stream.Collectors;

public class BlockInstruction implements Instruction {
    final List<? extends Instruction> instructions;

    public BlockInstruction(List<? extends Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        this.instructions.forEach(i -> i.preprocess(context, codeContext));
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return new EncodedBlockInstruction(this.instructions.stream().map(i -> i.encode(context,
                MethodContext.create(0)))
                .collect(Collectors.toList()));
    }


}
