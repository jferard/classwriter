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

package com.github.jferard.classwriter.decoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.instruction.base.InstructionWritableFactory;

import java.io.DataOutput;
import java.io.Writer;
import java.util.List;

public class DecodedInstructionWritableFactory implements InstructionWritableFactory<Writer> {
    @Override
    public Writable<Writer> aLoadInstruction(int opcode) {
        return null;
    }

    @Override
    public Writable<Writer> aStoreInstruction(int aStoreInstruction) {
        return null;
    }

    @Override
    public Writable<Writer> gotoWInstruction(int branch) {
        return null;
    }

    @Override
    public Writable<Writer> returnInstruction(int opcode) {
        return null;
    }

    @Override
    public Writable<Writer> blockInstruction(List<EncodedInstruction> encodedInstructions) {
        return null;
    }

    @Override
    public Writable<Writer> mock() {
        return null;
    }

    @Override
    public Writable<Writer> code(List<EncodedInstruction> encodeds) {
        return null;
    }

    @Override
    public Writable<Writer> storeNInstruction(int opcode, int localIndex,
                                              VerificationType verificationType) {
        return null;
    }

    @Override
    public Writable<Writer> multiNewArrayInstruction(int index, int dimensions) {
        return null;
    }

    @Override
    public Writable<Writer> retInstruction(int index) {
        return null;
    }

    @Override
    public Writable<Writer> wideIincInstruction(int index, int c) {
        return null;
    }

    @Override
    public Writable<Writer> ldcInstruction(int index, int stackSize) {
        return null;
    }

    @Override
    public Writable<Writer> biPushInstruction(byte b) {
        return null;
    }

    @Override
    public Writable<Writer> constInstruction(int opcode) {
        return null;
    }

    @Override
    public Writable<Writer> gotoInstruction(int branch) {
        return null;
    }

    @Override
    public Writable<Writer> storeInstruction(int opcode, int index) {
        return null;
    }

    @Override
    public Writable<Writer> convertInstruction(int opcode) {
        return null;
    }

    @Override
    public Writable<Writer> dup2Instruction() {
        return null;
    }

    @Override
    public Writable<Writer> dupX2Instruction() {
        return null;
    }

    @Override
    public Writable<Writer> dupInstruction() {
        return null;
    }

    @Override
    public Writable<Writer> dupX1Instruction() {
        return null;
    }

    @Override
    public Writable<DataOutput> dup2X1Instruction() {
        return null;
    }

    @Override
    public Writable<Writer> dup2X2Instruction() {
        return null;
    }

    @Override
    public Writable<Writer> indexedInstruction(int opcode, int index) {
        return null;
    }

    @Override
    public Writable<Writer> invokeDynamicInstruction(int index) {
        return null;
    }

    @Override
    public Writable<Writer> invokeInterfaceInstruction(int index, int count) {
        return null;
    }

    @Override
    public Writable<Writer> jsrInstruction(int branch) {
        return null;
    }

    @Override
    public Writable<Writer> jsrWInstruction(long branch) {
        return null;
    }

    @Override
    public Writable<Writer> ldcWInstruction(int index) {
        return null;
    }

    @Override
    public Writable<Writer> lookupSwitchInstruction(int defaultOffset, int[] match_and_offsets) {
        return null;
    }

    @Override
    public Writable<Writer> newArrayInstruction(byte atype) {
        return null;
    }

    @Override
    public Writable<Writer> tableSwitchInstruction(int defaultOffset, int low, int high,
                                                   int[] jump_offsets) {
        return null;
    }

    @Override
    public Writable<Writer> iincInstruction(int index, int c) {
        return null;
    }

    @Override
    public Writable<Writer> loadInstruction(int opcode, int index) {
        return null;
    }

    @Override
    public Writable<Writer> unaryInstuction(int opcode) {
        return null;
    }

    @Override
    public Writable<Writer> wideLoadInstruction(int opcode, int index) {
        return null;
    }

    @Override
    public Writable<Writer> wideRetInstruction(int index) {
        return null;
    }

    @Override
    public Writable<Writer> wideStoreInstruction(int opcode, int index) {
        return null;
    }
}
