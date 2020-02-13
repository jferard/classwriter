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
package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.api.instruction.base.InstructionEncodedWriter
import java.io.DataOutput
import java.util.logging.Logger

class ByteCodeInstructionEncodedWriter(private val logger: Logger, private val output: DataOutput) : InstructionEncodedWriter {
    override fun aLoadNInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun gotoWInstruction(branch: Int) {
        output.writeByte(OpCodes.GOTO)
        output.writeInt(branch)
    }

    override fun returnInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun blockInstruction(
            encodedInstructions: List<EncodedInstruction>) {
        var i=0
        encodedInstructions.forEach {
            logger.finer("Write instruction: $it ($i/${Sized.listSize(encodedInstructions)})");
            i += it.size
            it.write(this)
        }
    }

    override fun mock() {
        // do nothing
    }

    override fun code(encodedInstructions: List<EncodedInstruction>) {
        encodedInstructions.forEach { it.write(this) }
    }

    override fun storeNInstruction(opcode: Int, localIndex: Int,
                                   verificationType: VerificationType) {
        output.writeByte(opcode)
    }

    override fun multiNewArrayInstruction(index: Int,
                                          dimensions: Int) {
        output.writeByte(OpCodes.MULTIANEWARRAY)
        output.writeShort(index)
        output.writeByte(dimensions)
    }

    override fun retInstruction(index: Int) {
        output.writeByte(OpCodes.RET)
        output.writeByte(index)
    }

    override fun wideIincInstruction(index: Int, c: Int) {
        output.writeByte(OpCodes.WIDE)
        output.writeByte(OpCodes.IINC)
        output.writeShort(index)
        output.writeShort(c)
    }

    override fun ldcInstruction(index: Int, stackSize: Int) {
        when {
            stackSize == 2 -> {
                output.writeByte(OpCodes.LDC2_W)
                output.writeShort(index)
            }
            index <= BytecodeHelper.BYTE_MAX -> {
                output.writeByte(OpCodes.LDC)
                output.writeByte(index)
            }
            else -> {
                output.writeByte(OpCodes.LDC_W)
                output.writeShort(index)
            }
        }
    }

    override fun invokeStaticInstruction(methodIndex: Int) {
        output.write(OpCodes.INVOKESTATIC)
        output.writeShort(methodIndex)
    }

    override fun aLoadInstruction(referenceIndex: Int) {
        output.writeByte(OpCodes.ALOAD)
        output.writeByte(referenceIndex)
    }

    override fun iLoadInstruction(referenceIndex: Int) {
        output.writeByte(OpCodes.ILOAD)
        output.writeByte(referenceIndex)
    }

    override fun iLoadNInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun lLoadInstruction(referenceIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun lLoadNInstruction(opcode: Int) {
        TODO("Not yet implemented")
    }

    override fun putFieldInstruction(fieldIndex: Int) {
        output.writeByte(OpCodes.PUTFIELD)
        output.writeShort(fieldIndex)
    }

    override fun checkCastInstruction(referenceIndex: Int) {
        output.writeByte(OpCodes.CHECKCAST)
        output.writeShort(referenceIndex)
    }

    override fun getFieldInstruction(fieldIndex: Int) {
        output.writeByte(OpCodes.GETFIELD)
        output.writeShort(fieldIndex)
    }

    override fun aStoreNInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun ifInstruction(opcode: Int, branch: Int) {
        output.writeByte(opcode)
        output.writeShort(branch)
    }

    override fun popInstruction() {
        output.writeByte(OpCodes.POP)
    }

    override fun nopInstruction() {
        output.writeByte(OpCodes.NOP)
    }

    override fun xaLoadInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun xaStoreInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun binaryOp(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun ifACmpInstruction(opcode: Int, branch: Int) {
        output.writeByte(opcode)
        output.writeShort(branch)
    }

    override fun ifICmpInstruction(opcode: Int, branch: Int) {
        output.writeByte(opcode)
        output.writeShort(branch)
    }

    override fun arrayLength() {
        output.writeByte(OpCodes.ARRAYLENGTH)
    }

    override fun siPush(value: Int) {
        output.writeByte(OpCodes.SIPUSH)
        output.writeShort(value)
    }

    override fun instanceOfInstruction(typeIndex: Int) {
        output.writeByte(OpCodes.INSTANCEOF)
        output.writeShort(typeIndex)
    }

    override fun iStoreInstruction(index: Int) {
        output.writeByte(OpCodes.ISTORE)
        output.writeByte(index)
    }

    override fun iStoreNInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun ifNonNullInstruction(branch: Int) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun iConstNInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun aStoreInstruction(referenceIndex: Int) {
        output.writeByte(OpCodes.ASTORE)
        output.writeByte(referenceIndex)
    }

    override fun biPushInstruction(b: Int) {
        output.writeByte(OpCodes.BIPUSH)
        output.writeByte(b)
    }

    override fun constInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun gotoInstruction(branch: Int) {
        output.writeByte(OpCodes.GOTO)
        output.writeShort(branch)
    }

    override fun storeInstruction(opcode: Int, index: Int) {
        output.writeByte(opcode)
        output.writeByte(index)
    }

    override fun convertInstruction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun dup2Instruction() {
        output.writeByte(OpCodes.DUP2)
    }

    override fun dupX2Instruction() {
        output.writeByte(OpCodes.DUP_X2)
    }

    override fun dupInstruction() {
        output.writeByte(OpCodes.DUP)
    }

    override fun dupX1Instruction() {
        output.writeByte(OpCodes.DUP_X1)
    }

    override fun dup2X1Instruction() {
        output.writeByte(OpCodes.DUP2_X1)
    }

    override fun dup2X2Instruction() {
        output.writeByte(OpCodes.DUP2_X2)
    }

    override fun indexedInstruction(opcode: Int, index: Int) {
        output.writeByte(opcode)
        output.writeShort(index)
    }

    override fun invokeDynamicInstruction(index: Int) {
        output.writeByte(OpCodes.INVOKEDYNAMIC)
        output.writeShort(index)
        output.writeShort(0)
    }

    override fun invokeInterfaceInstruction(index: Int, count: Int) {
        output.writeByte(OpCodes.INVOKEINTERFACE)
        output.writeShort(index)
        output.writeByte(count)
        output.writeByte(0)
    }

    override fun jsrInstruction(branch: Int) {
        output.writeByte(OpCodes.JSR)
        output.writeShort(branch)
    }

    override fun jsrWInstruction(branch: Long) {
        output.writeByte(OpCodes.JSR_W)
        output.writeInt(branch.toInt())
    }

    override fun ldcWInstruction(index: Int) {
        output.writeByte(OpCodes.LDC)
        output.writeShort(index)
    }

    override fun lookupSwitchInstruction(defaultOffset: Int,
                                         match_and_offsets: IntArray) {
        output.writeByte(OpCodes.LOOKUPSWITCH)
        output.writeInt(defaultOffset)
        output.writeInt(match_and_offsets.size)
        match_and_offsets.forEach(output::writeInt)
    }

    override fun newArrayInstruction(atype: Byte) {
        output.writeByte(OpCodes.NEWARRAY)
        output.writeByte(atype.toInt())
    }

    override fun tableSwitchInstruction(defaultOffset: Int, low: Int, high: Int,
                                        jump_offsets: IntArray) {
        /*         this.instructions.add(new ByteInstruction(OpCodes.TABLESWITCH); this
        * .baseWriter.padTo4();
        this.baseWriter.writeInt(default_);
        this.baseWriter.writeInt(low);
        this.baseWriter.writeInt(high);
        for (final int jump_offset : jump_offsets) {
        this.baseWriter.writeInt(jump_offset);
        }
        */
    }

    override fun iincInstruction(index: Int, const: Int) {
        output.writeByte(OpCodes.IINC)
        output.writeByte(index)
        output.writeByte(const)
    }

    override fun loadInstruction(opcode: Int, index: Int) {
        output.writeByte(opcode)
        output.writeByte(index)
   }

    override fun unaryInstuction(opcode: Int) {
        output.writeByte(opcode)
    }

    override fun wideLoadInstruction(opcode: Int, index: Int) {
        output.writeByte(OpCodes.WIDE)
        output.writeByte(opcode)
        output.writeShort(index)
    }

    override fun wideRetInstruction(index: Int) {
        output.writeByte(OpCodes.WIDE)
        output.writeByte(OpCodes.RET)
        output.writeShort(index)
    }

    override fun wideStoreInstruction(opcode: Int, index: Int) {
        output.writeByte(OpCodes.WIDE)
        output.writeByte(opcode)
        output.writeShort(index)
    }

    override fun aThrowInstruction() {
        output.writeByte(OpCodes.ATHROW)
    }

    override fun newInstruction(classIndex: Int) {
        output.writeByte(OpCodes.NEW)
        output.writeShort(classIndex)
    }

    override fun invokeSpecialInstruction(classIndex: Int) {
        output.writeByte(OpCodes.INVOKESTATIC)
        output.writeShort(classIndex)
    }

    override fun putStaticInstruction(fieldIndex: Int) {
        output.writeByte(OpCodes.PUTSTATIC)
        output.writeShort(fieldIndex)
    }

    override fun getStaticInstruction(fieldIndex: Int) {
        output.writeByte(OpCodes.GETSTATIC)
        output.writeShort(fieldIndex)
    }

    override fun invokeVirtualInstruction(methodIndex: Int) {
        output.writeByte(OpCodes.INVOKEVIRTUAL)
        output.writeShort(methodIndex)
    }
}