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

package com.github.jferard.classwriter.bytecode.instruction;

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.base.InstructionWritableFactory;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeInstructionWritableFactory implements InstructionWritableFactory<DataOutput> {
    public ByteCode aLoadInstruction(int opcode) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public ByteCode aStoreInstruction(int opcode) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public ByteCode gotoWInstruction(int branch) {
        return new ByteCodeGotoWInstruction(branch);
    }

    @Override
    public ByteCode returnInstruction(int opcode) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public ByteCode blockInstruction(List<EncodedInstruction> encodedInstructions) {
        final List<ByteCode> writableInstructions = encodedInstructions.stream()
                .map(ei -> (ByteCode) ei.toWritable(this)).collect(Collectors.toList());
        return new ByteCodeBlockInstruction(writableInstructions);
    }

    @Override
    public ByteCode mock() {
        return new ByteCodeMock();
    }

    @Override
    public ByteCode code(List<EncodedInstruction> instructions) {
        List<ByteCode> writableInstructions = instructions.stream()
                .map(i -> (ByteCode) i.toWritable(this)).collect(Collectors.toList());
        return new ByteCodeCode(writableInstructions);
    }

    @Override
    public Writable<DataOutput> storeNInstruction(int opcode, int localIndex,
                                                  VerificationType verificationType) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public Writable<DataOutput> multiNewArrayInstruction(int index, int dimensions) {
        return new ByteCodeMultiNewArrayInstruction(index, dimensions);
    }

    @Override
    public Writable<DataOutput> retInstruction(int index) {
        return new ByteCodeRetInstruction(index);
    }

    @Override
    public Writable<DataOutput> wideIincInstruction(int index, int c) {
        return new ByteCodeWideIincInstruction(index, c);
    }

    @Override
    public Writable<DataOutput> ldcInstruction(int index, int stackSize) {
        return new ByteCodeLdcInstruction(index, stackSize);
    }

    @Override
    public Writable<DataOutput> biPushInstruction(byte b) {
        return new ByteCodeBiPushInstruction(b);
    }

    @Override
    public Writable<DataOutput> constInstruction(int opcode) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public Writable<DataOutput> gotoInstruction(int branch) {
        return new ByteCodeGotoInstruction(branch);
    }

    @Override
    public Writable<DataOutput> storeInstruction(int opcode, int index) {
        return new ByteCodeStoreInstruction(opcode, index);
    }

    @Override
    public Writable<DataOutput> convertInstruction(int opcode) {
        return new ByteCodeOneByteInstruction(opcode);
    }

    @Override
    public Writable<DataOutput> dup2Instruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP2);
    }

    @Override
    public Writable<DataOutput> dupX2Instruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP_X2);
    }

    @Override
    public Writable<DataOutput> dupInstruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP);
    }

    @Override
    public Writable<DataOutput> dupX1Instruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP_X1);
    }

    @Override
    public Writable<DataOutput> dup2X1Instruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP2_X1);
    }

    @Override
    public Writable<DataOutput> dup2X2Instruction() {
        return new ByteCodeOneByteInstruction(OpCodes.DUP2_X2);
    }

    @Override
    public Writable<DataOutput> indexedInstruction(int opcode, int index) {
        return new ByteCodeIndexedInstruction(opcode, index);
    }

    @Override
    public Writable<DataOutput> invokeDynamicInstruction(int index) {
        return new ByteCodeInvokeDynamicInstruction(index);
    }

    @Override
    public Writable<DataOutput> invokeInterfaceInstruction(int index, int count) {
        return new ByteCodeInvokeInterfaceInstruction(index, count);
    }

    @Override
    public Writable<DataOutput> jsrInstruction(int branch) {
        return new ByteCodeJsrInstruction(branch);
    }

    @Override
    public Writable<DataOutput> jsrWInstruction(long branch) {
        return new ByteCodeJsrWInstruction(branch);
    }

    @Override
    public Writable<DataOutput> ldcWInstruction(int index) {
        return new ByteCodeIndexedInstruction(OpCodes.LDC, index);
    }

    @Override
    public Writable<DataOutput> lookupSwitchInstruction(int defaultOffset,
                                                        int[] match_and_offsets) {
        return new ByteCodeLookupSwitchInstruction(defaultOffset, match_and_offsets);
    }

    @Override
    public Writable<DataOutput> newArrayInstruction(byte atype) {
        return new ByteCodeNewArrayInstruction(atype);
    }

    @Override
    public Writable<DataOutput> tableSwitchInstruction(int defaultOffset, int low, int high,
                                                       int[] jump_offsets) {
        return new ByteCodeTableSwitchInstruction(defaultOffset, low, high, jump_offsets);
    }

    @Override
    public Writable<DataOutput> iincInstruction(int index, int c) {
        return new ByteCodeIincInstruction(index, c);
    }

    @Override
    public Writable<DataOutput> loadInstruction(int opcode, int index) {
        return new ByteCodeLoadInstruction(opcode, index);
    }

    @Override
    public Writable<DataOutput> unaryInstuction(int opcode) {
        return new ByteCodeUnaryInstruction(opcode);
    }

    @Override
    public Writable<DataOutput> wideLoadInstruction(int opcode, int index) {
        return new ByteCodeWideLoadInstruction(opcode, index);
    }

    @Override
    public Writable<DataOutput> wideRetInstruction(int index) {
        return new ByteCodeWideRetInstruction(index);
    }

    @Override
    public Writable<DataOutput> wideStoreInstruction(int opcode, int index) {
        return  new ByteCodeWideStoreInstruction (opcode, index);
    }
}
