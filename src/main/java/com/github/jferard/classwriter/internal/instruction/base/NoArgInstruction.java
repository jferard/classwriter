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
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

import java.util.Collections;
import java.util.List;

public class NoArgInstruction implements BaseInstruction, EncodedInstruction {
    /**
     * remove two operands of type t and add a result of type t
     */
    public static NoArgInstruction binaryOp(int opcode, VerificationType type) {
        return NoArgInstruction.noLocals(opcode, 2, Collections.singletonList(type));
    }

    /**
     * remove one operand of type t and add a result of type t
     */
    public static NoArgInstruction unary(int opcode, VerificationType type) {
        return NoArgInstruction.noLocals(opcode, 1, Collections.singletonList(type));
    }

    /**
     * convert
     */
    public static NoArgInstruction stdToStd(int opcode, VerificationType toType) {
        return NoArgInstruction.noLocals(opcode, 1, Collections.singletonList(toType));
    }

    public static BaseInstruction stdConst(int opcode, VerificationType type) {
        return NoArgInstruction.noLocals(opcode, 0, Collections.singletonList(type));
    }

    public static BaseInstruction stdDup(int opcode, VerificationType type) {
        return NoArgInstruction.noLocals(opcode, 0, Collections.singletonList(type));
    }

    public static NoArgInstruction noLocals(int opcode, int stackPopCount,
                                            List<VerificationType> stackPushTypes) {
        return new NoArgInstruction(opcode, stackPopCount, stackPushTypes, 0);
    }

    public static NoArgInstruction noLocalsNoSTack(int opcode) {
        return new NoArgInstruction(opcode, 0, Collections.emptyList(), 0);
    }

    public static NoArgInstruction stdALoad(int opcode, VerificationType type) {
        return NoArgInstruction.noLocals(opcode, 2, Collections.singletonList(type));
    }

    public static NoArgInstruction stdAStore(int opcode) {
        return NoArgInstruction.noLocals(opcode, -3, Collections.emptyList());
    }

    public static NoArgInstruction stdLoadN(int opcode, int n) {
        return loadOrStoreN(opcode, n, 1);
    }

    public static NoArgInstruction longLoadN(int opcode, int n) {
        return loadOrStoreN(opcode, n + 1, 2);
    }

    public static NoArgInstruction stdStoreN(int opcode, int n) {
        return loadOrStoreN(opcode, n, -1);
    }

    public static NoArgInstruction longStoreN(int opcode, int n) {
        return loadOrStoreN(opcode, n + 1, -2);
    }

    private static NoArgInstruction loadOrStoreN(int opcode, int n, int stackDelta) {
        return new NoArgInstruction(opcode, stackDelta, Collections.emptyList(), n);
    }

    public static BaseInstruction monitor(int opcode) {
        return new NoArgInstruction(opcode, 1, Collections.emptyList(), 0);
    }

    public static BaseInstruction pop(int opcode, int popStackCount) {
        return new NoArgInstruction(opcode, popStackCount, Collections.emptyList(), 0);
    }

    private final int opcode;
    private final int popStackCount;
    private final int maxLocal;
    private List<VerificationType> stackPushTypes;

    private NoArgInstruction(int opcode, int popStackCount, List<VerificationType> stackPushTypes,
                             int maxLocal) {
        this.opcode = opcode;
        this.popStackCount = popStackCount;
        this.stackPushTypes = stackPushTypes;
        this.maxLocal = maxLocal;
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(1);
        // codeContext.popStackCount(this.popStackCount);
        // codeContext.maxLocal(this.maxLocal);
    }

    @Override
    public Writable<?> toWritable(InstructionWritableFactory<?> writableFactory) {
        throw new IllegalStateException();
    }

    @Override
    public int getSize() {
        return 0;
    }
}
