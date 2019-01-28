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

package com.github.jferard.classwriter;

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.EncodedCode;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.base.BaseInstruction;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.List;
import java.util.stream.Collectors;

// is this a block ?
public class Code implements Encodable<EncodedCode> { // implements instruction {
    private final List<BaseInstruction> instructions;

    public Code(List<BaseInstruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public EncodedCode encode(GlobalContext pool, MethodContext codeContext) {
        List<EncodedInstruction> encodeds = instructions.stream()
                .map(i -> i.encode(pool, codeContext)).collect(Collectors.toList());
        return new EncodedCode(encodeds);
    }
}
