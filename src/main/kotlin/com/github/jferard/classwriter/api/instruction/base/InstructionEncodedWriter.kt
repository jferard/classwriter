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
package com.github.jferard.classwriter.api.instruction.base

import com.github.jferard.classwriter.api.EncodedWriter
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

/**
 * Writable instructions factory.
 */
interface InstructionEncodedWriter : EncodedWriter {
    /** Load reference from local variable */
    fun aLoadNInstruction(opcode: Int)
    fun aStoreInstruction(referenceIndex: Int)
    fun gotoWInstruction(branch: Int)
    fun returnInstruction(opcode: Int)
    fun blockInstruction(
            encodedInstructions: List<EncodedInstruction>)

    fun mock()
    fun code(encodedInstructions: List<EncodedInstruction>)
    fun storeNInstruction(opcode: Int, localIndex: Int,
                          verificationType: VerificationType)

    fun multiNewArrayInstruction(index: Int, dimensions: Int)
    fun retInstruction(index: Int)
    fun wideIincInstruction(index: Int, c: Int)
    fun ldcInstruction(index: Int, stackSize: Int)
    fun biPushInstruction(b: Int)
    fun constInstruction(opcode: Int)
    fun gotoInstruction(branch: Int)
    fun storeInstruction(opcode: Int, index: Int)
    fun convertInstruction(opcode: Int)
    fun dup2Instruction()
    fun dupX2Instruction()
    fun dupInstruction()
    fun dupX1Instruction()
    fun dup2X1Instruction()
    fun dup2X2Instruction()
    fun indexedInstruction(opcode: Int, index: Int)
    fun invokeDynamicInstruction(index: Int)
    fun invokeInterfaceInstruction(index: Int, count: Int)
    fun jsrInstruction(branch: Int)
    fun jsrWInstruction(branch: Long)
    fun ldcWInstruction(index: Int)
    fun lookupSwitchInstruction(defaultOffset: Int,
                                match_and_offsets: IntArray)

    fun newArrayInstruction(atype: Byte)
    fun tableSwitchInstruction(defaultOffset: Int, low: Int, high: Int,
                               jump_offsets: IntArray)

    fun iincInstruction(index: Int, const: Int)
    fun loadInstruction(opcode: Int, index: Int)
    fun unaryInstuction(opcode: Int)
    fun wideLoadInstruction(opcode: Int, index: Int)
    fun wideRetInstruction(index: Int)
    fun wideStoreInstruction(opcode: Int, index: Int)
    fun aThrowInstruction()
    fun newInstruction(classIndex: Int)
    fun invokeSpecialInstruction(classIndex: Int)
    fun putStaticInstruction(fieldIndex: Int)
    fun getStaticInstruction(fieldIndex: Int)
    fun invokeVirtualInstruction(methodIndex: Int)
    fun invokeStaticInstruction(methodIndex: Int)
    fun aLoadInstruction(referenceIndex: Int)
    fun putFieldInstruction(fieldIndex: Int)
    fun checkCastInstruction(referenceIndex: Int)
    fun getFieldInstruction(fieldIndex: Int)
    fun aStoreNInstruction(opcode: Int)
    fun iStoreInstruction(index: Int)
    fun iStoreNInstruction(opcode: Int)
    fun ifNonNullInstruction(branch: Int)
    fun iConstNInstruction(opcode: Int)
    fun ifInstruction(opcode: Int, branch: Int)
    fun popInstruction()
    fun iLoadInstruction(referenceIndex: Int)
    fun iLoadNInstruction(opcode: Int)
    fun lLoadInstruction(referenceIndex: Int)
    fun lLoadNInstruction(opcode: Int)
    fun nopInstruction()
    fun xaLoadInstruction(opcode: Int)
    fun xaStoreInstruction(opcode: Int)
    fun binaryOp(opcode: Int)
    fun ifACmpInstruction(opcode: Int, branch: Int)
    fun instanceOfInstruction(typeIndex: Int)
    fun ifICmpInstruction(opcode: Int, branch: Int)
    fun arrayLength()
    fun siPush(value: Int)
}