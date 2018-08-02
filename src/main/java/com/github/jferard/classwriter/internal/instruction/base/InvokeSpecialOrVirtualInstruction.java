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

import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.pool.MethodRefEntry;

/**
 * invokespecial: invoke instance method on object objectref and puts the result on the stack
 * (might be void); the method is identified by method reference index in access pool
 * (indexbyte1 << 8 + indexbyte2).
 * Other bytes (count: 2): indexbyte1, indexbyte2.
 * Stack: (objectref, [arg1, arg2, ...]) -> (result).
 */
public class InvokeSpecialOrVirtualInstruction implements BaseInstruction {
    private final MethodRef methodRef;
    private final int opcode;

    public InvokeSpecialOrVirtualInstruction(final int opcode, MethodRef methodRef) {
        this.opcode = opcode;
        this.methodRef = methodRef;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(3);
        codeContext.assertTypeAssignable(this.methodRef.getClassVerificationType(),
                codeContext.stackPop());
        for (ValueType type : this.methodRef.getArgTypes()) {
            codeContext.assertTypeAssignable(type.getVerificationType(), codeContext.stackPop());
        }
        final ValueType retType = this.methodRef.getRetType();
        if (retType != null) {
            codeContext.stackPush(VerificationType.fromValueType(retType));
        }
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int index = context.addToPool(new MethodRefEntry(this.methodRef));
        return new EncodedIndexedInstruction(this.opcode, index);
    }
}
