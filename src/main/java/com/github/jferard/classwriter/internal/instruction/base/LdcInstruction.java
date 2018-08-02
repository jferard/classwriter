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
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;

/**
 * ldc: push a access #index from a access pool (String, int, float, Class, java.lang
 * .invoke.MethodType, or java.lang.invoke.MethodHandle) onto the stack.
 * ldc_w: push a access #index from a access pool (String, int, float, Class, java.lang
 * .invoke.MethodType, or java.lang.invoke.MethodHandle) onto the stack (wide index is
 * constructed as indexbyte1 << 8 + indexbyte2).
 * ldc2_w: push a access #index from a access pool (double or long) onto the stack (wide
 * index is constructed as indexbyte1 << 8 + indexbyte2).
 * Other bytes (count: 1): index.
 * Stack: () -> (value).
 */
public class LdcInstruction implements BaseInstruction {
    private final ConstantPoolEntry entry;

    public LdcInstruction(ConstantPoolEntry entry) {
        this.entry = entry;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(this.computeOffset(context));
        codeContext.stackPush(this.entry.toVerificationType());
    }

    private int computeOffset(GlobalContext context) {
        final int offset;
        int index = context.addToPool(this.entry);
        if (index <= BytecodeHelper.BYTE_MAX) {
            offset = 2;
        } else {
            offset = 3;
        }
        return offset;
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int index = context.addToPool(this.entry);
        return new EncodedLdcInstruction(index, this.entry.size());
    }
}
