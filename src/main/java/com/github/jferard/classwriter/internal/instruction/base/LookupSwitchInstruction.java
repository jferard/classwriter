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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.SortedMap;


/**
 * lookupswitch: a target address is looked up from a table using a key and execution
 * continues from the instruction at that address.
 * Other bytes (count: 8+): <0–3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3,
 * defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs....
 * Stack: (key) -> ().
 */
public class LookupSwitchInstruction implements BaseInstruction {
    private PaddingHelper paddingHelper;
    private final Instruction defaultCase;
    private final SortedMap<Integer, Instruction> instructionByCase;

    public LookupSwitchInstruction(PaddingHelper paddingHelper, Instruction defaultCase,
                                   SortedMap<Integer, Instruction> instructionByCase) {
        this.paddingHelper = paddingHelper;
        this.defaultCase = defaultCase;
        this.instructionByCase = instructionByCase;
    }

    @Override
    public void preprocess(GlobalContext context, MethodContext codeContext) {
        this.paddingHelper.placeOffsetBeforePadding(codeContext);
        int padding = this.paddingHelper.computePadding(codeContext.getCurOffset());
        codeContext.storePadding(padding);
        this.paddingHelper.placeOffsetAfterLookupSwitch(codeContext, padding, this.instructionByCase.size());
        codeContext.assertTypeAssignable(VerificationType.INTEGER, codeContext.stackPop());
    }

    @Override
    public EncodedInstruction encode(GlobalContext context, MethodContext codeContext) {
        int defaultOffset = 0;
        int[] match_and_offsets = {};
        return new EncodedLookupSwitchInstruction(defaultOffset, match_and_offsets);
    }
}
