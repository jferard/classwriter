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

import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.pool.ClassEntry;

/**
 * multianewarray: create a new array of dimensions dimensions of type identified by class
 * reference in access pool index (indexbyte1 << 8 + indexbyte2); the sizes of each
 * dimension is identified by count1, [count2, etc.].
 * Other bytes (count: 3): indexbyte1, indexbyte2, dimensions.
 * Stack: (count1, [count2,...]) -> (arrayref).
 */
public class MultiNewArrayInstruction implements BaseInstruction {
    private final PlainClassRef classRef;
    private final int dimensions;

    public MultiNewArrayInstruction(PlainClassRef classRef, int dimensions) {
        this.classRef = classRef;
        this.dimensions = dimensions;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(4);
        ValueType type = this.classRef.toValueType();
        for (int i = 0; i < this.dimensions; i++) {
            codeContext.assertTypeAssignable(VerificationType.INTEGER, codeContext.stackPop());
            type = ValueType.array(type);
        }
        codeContext.stackPush(type.getVerificationType());
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int index = context.addToPool(new ClassEntry(this.classRef));
        return new EncodedMultiNewArrayInstruction(index, this.dimensions);
    }
}
