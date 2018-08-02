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

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.pool.FieldRefEntry;

/**
 * getstatic: get a static field value of a class, where the field is identified by field
 * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
 * Other bytes (count: 2): indexbyte1, indexbyte2.
 * Stack: () -> (value).
 */
public class GetStaticInstruction implements BaseInstruction {
    private final FieldRef field;

    public GetStaticInstruction(FieldRef field) {
        this.field = field;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        codeContext.offsetDelta(3);
        codeContext.stackPush(VerificationType.fromFieldRef(this.field));
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int index = context.addToPool(new FieldRefEntry(this.field));
        return new EncodedIndexedInstruction(OpCodes.GETSTATIC, index);
    }
}
