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
import com.github.jferard.classwriter.api.WritableFactory;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

import java.io.DataOutput;
import java.util.List;

public interface InstructionWritableFactory<O> extends WritableFactory<O> {
    Writable<O> aLoadInstruction(int opcode);

    Writable<O> aStoreInstruction(int aStoreInstruction);

    Writable<O> gotoWInstruction(int branch);

    Writable<O> returnInstruction(int opcode);

    Writable<O> blockInstruction(
            List<EncodedInstruction> encodedInstructions);

    Writable<O> mock();

    Writable<O> code(List<EncodedInstruction> encodeds);

    Writable<O> storeNInstruction(int opcode, int localIndex, VerificationType verificationType);

    Writable<O> multiNewArrayInstruction(int index, int dimensions);

    Writable<O> retInstruction(int index);

    Writable<O> wideIincInstruction(int index, int c);

    Writable<O> ldcInstruction(int index, int stackSize);

    Writable<O> biPushInstruction(byte b);

    Writable<O> constInstruction(int opcode);

    Writable<O> gotoInstruction(int branch);

    Writable<O> storeInstruction(int opcode, int index);

    Writable<O> convertInstruction(int opcode);

    Writable<O> dup2Instruction();

    Writable<O> dupX2Instruction();

    Writable<O> dupInstruction();

    Writable<O> dupX1Instruction();

    Writable<DataOutput> dup2X1Instruction();

    Writable<O> dup2X2Instruction();

    Writable<O> indexedInstruction(int opcode, int index);

    Writable<O> invokeDynamicInstruction(int index);

    Writable<O> invokeInterfaceInstruction(int index, int count);

    Writable<O> jsrInstruction(int branch);

    Writable<O> jsrWInstruction(long branch);

    Writable<O> ldcWInstruction(int index);

    Writable<O> lookupSwitchInstruction(int defaultOffset, int[] match_and_offsets);

    Writable<O> newArrayInstruction(byte atype);

    Writable<O> tableSwitchInstruction(int defaultOffset, int low, int high, int[] jump_offsets);

    Writable<O> iincInstruction(int index, int c);

    Writable<O> loadInstruction(int opcode, int index);

    Writable<O> unaryInstuction(int opcode);

    Writable<O> wideLoadInstruction(int opcode, int index);

    Writable<O> wideRetInstruction(int index);

    Writable<O> wideStoreInstruction(int opcode, int index);
}
