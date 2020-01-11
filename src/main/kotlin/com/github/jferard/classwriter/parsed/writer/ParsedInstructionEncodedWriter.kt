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
package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.instruction.base.InstructionEncodedWriter

class ParsedInstructionEncodedWriter :
        InstructionEncodedWriter {
    override fun aLoadInstruction(opcode: Int) {
        TODO("not implemented")
    }

    override fun aStoreInstruction(opcode: Int) {
        TODO("not implemented")
    }

    override fun gotoWInstruction(branch: Int) {
        TODO("not implemented")
    }

    override fun returnInstruction(opcode: Int) {
        TODO("not implemented")
    }

    override fun blockInstruction(
            encodedInstructions: List<EncodedInstruction>) {
        TODO("not implemented")
    }

    override fun mock() {
        TODO("not implemented")
    }

    override fun code(
            encodedInstructions: List<EncodedInstruction>) {
        TODO("not implemented")
    }

    override fun storeNInstruction(opcode: Int, localIndex: Int,
                                   verificationType: VerificationType) {
        TODO("not implemented")
    }

    override fun multiNewArrayInstruction(index: Int,
                                          dimensions: Int) {
        TODO("not implemented")
    }

    override fun retInstruction(index: Int) {
        TODO("not implemented")
    }

    override fun wideIincInstruction(index: Int, c: Int) {
        TODO("not implemented")
    }

    override fun ldcInstruction(index: Int, stackSize: Int) {
        TODO("not implemented")
    }

    override fun biPushInstruction(b: Byte) {
        TODO("not implemented")
    }

    override fun constInstruction(opcode: Int) {
        TODO("not implemented")
    }

    override fun gotoInstruction(branch: Int) {
        TODO("not implemented")
    }

    override fun storeInstruction(opcode: Int, index: Int) {
        TODO("not implemented")
    }

    override fun convertInstruction(opcode: Int) {
        TODO("not implemented")
    }

    override fun dup2Instruction() {
        TODO("not implemented")
    }

    override fun dupX2Instruction() {
        TODO("not implemented")
    }

    override fun dupInstruction() {
        TODO("not implemented")
    }

    override fun dupX1Instruction() {
        TODO("not implemented")
    }

    override fun dup2X1Instruction() {
        TODO("not implemented")
    }

    override fun dup2X2Instruction() {
        TODO("not implemented")
    }

    override fun indexedInstruction(opcode: Int, index: Int) {
        TODO("not implemented")
    }

    override fun invokeDynamicInstruction(index: Int) {
        TODO("not implemented")
    }

    override fun invokeInterfaceInstruction(index: Int,
                                            count: Int) {
        TODO("not implemented")
    }

    override fun jsrInstruction(branch: Int) {
        TODO("not implemented")
    }

    override fun jsrWInstruction(branch: Long) {
        TODO("not implemented")
    }

    override fun ldcWInstruction(index: Int) {
        TODO("not implemented")
    }

    override fun lookupSwitchInstruction(defaultOffset: Int,
                                         match_and_offsets: IntArray) {
        TODO("not implemented")
    }

    override fun newArrayInstruction(atype: Byte) {
        TODO("not implemented")
    }

    override fun tableSwitchInstruction(defaultOffset: Int, low: Int, high: Int,
                                        jump_offsets: IntArray) {
        TODO("not implemented")
    }

    override fun iincInstruction(index: Int, c: Int) {
        TODO("not implemented")
    }

    override fun loadInstruction(opcode: Int, index: Int) {
        TODO("not implemented")
    }

    override fun unaryInstuction(opcode: Int) {
        TODO("not implemented")
    }

    override fun wideLoadInstruction(opcode: Int, index: Int) {
        TODO("not implemented")
    }

    override fun wideRetInstruction(index: Int) {
        TODO("not implemented")
    }

    override fun wideStoreInstruction(opcode: Int, index: Int) {
        TODO("not implemented")
    }

    override fun aThrowInstruction() {
        TODO("not implemented")
    }
}