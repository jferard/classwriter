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

package com.github.jferard.classwriter.tool.decoder

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.instruction.EncodedInstructionConstants
import com.github.jferard.classwriter.internal.instruction.base.*
import com.github.jferard.classwriter.internal.instruction.block.EncodedBlockInstruction
import java.io.DataInput

class InstructionsParser : Parser<EncodedInstruction> {
    override fun parse(input: DataInput): EncodedInstruction {
        val codeLength = input.readInt().toLong()
        val ret: MutableList<EncodedInstruction> = mutableListOf()
        var i = 1
        while (i <= codeLength) {
            val element = parseInstruction(input)
            ret.add(element)
            i += element.size
        }
        return EncodedBlockInstruction(ret.toList())
    }

    private fun parseInstruction(input: DataInput): EncodedInstruction {
        val opcode = input.readUnsignedByte()
        return when (opcode) {
            OpCodes.ARETURN -> EncodedInstructionConstants.ARETURN_INSTRUCTION
            OpCodes.ALOAD -> EncodedALoadInstruction(input.readUnsignedByte())
            OpCodes.ALOAD_0, OpCodes.ALOAD_1, OpCodes.ALOAD_2, OpCodes.ALOAD_3 -> EncodedInstructionConstants.ALOAD_INSTRUCTIONS[opcode - OpCodes.ALOAD_0]
            OpCodes.ASTORE, OpCodes.ASTORE_1, OpCodes.ASTORE_2, OpCodes.ASTORE_3 -> EncodedAStoreInstruction(
                    opcode, input.readUnsignedByte())
            OpCodes.ASTORE_0, OpCodes.ASTORE_1, OpCodes.ASTORE_2, OpCodes.ASTORE_3 -> EncodedInstructionConstants.ASTORE_INSTRUCTIONS[opcode - OpCodes.ASTORE_0]
            OpCodes.ACONST_NULL -> EncodedInstructionConstants.ACONST_NULL_INSTRUCTION
            OpCodes.BIPUSH -> BiPushInstruction(input.readUnsignedByte())
            OpCodes.CHECKCAST -> EncodedCheckCastInstruction(input.readUnsignedShort())
            OpCodes.GETFIELD -> EncodedGetFieldInstruction(input.readUnsignedShort())
            OpCodes.GETSTATIC -> EncodedGetStaticInstruction(input.readUnsignedShort())
            OpCodes.GOTO -> EncodedGotoInstruction(input.readUnsignedShort())
            OpCodes.ICONST_0, OpCodes.ICONST_1, OpCodes.ICONST_2, OpCodes.ICONST_3, OpCodes.ICONST_4, OpCodes.ICONST_5 -> EncodedInstructionConstants.ICONST_INSTRUCTIONS[opcode - OpCodes.ICONST_0]
            OpCodes.ILOAD -> EncodedILoadInstruction(input.readUnsignedByte())
            OpCodes.ILOAD_0, OpCodes.ILOAD_1, OpCodes.ILOAD_2, OpCodes.ILOAD_3 -> EncodedInstructionConstants.ILOAD_INSTRUCTIONS[opcode - OpCodes.ILOAD_0]
            OpCodes.IFEQ, OpCodes.IFNE, OpCodes.IFLT, OpCodes.IFGE, OpCodes.IFGT, OpCodes.IFLE -> EncodedIfInstruction(
                    opcode, input.readUnsignedShort())
            OpCodes.IFNONNULL -> EncodedIfNonNullInstruction(input.readUnsignedShort())
            OpCodes.INVOKEINTERFACE -> {
                val index = input.readUnsignedShort()
                val count = input.readUnsignedByte()
                check(0 == input.readUnsignedByte())
                EncodedInvokeInterfaceInstruction(index, count)
            }
            OpCodes.INVOKESPECIAL -> EncodedInvokeSpecialInstruction(input.readUnsignedShort())
            OpCodes.INVOKESTATIC -> EncodedInvokeStaticInstruction(input.readUnsignedShort())
            OpCodes.INVOKEVIRTUAL -> EncodedInvokeVirtualInstruction(input.readUnsignedShort())
            OpCodes.ISTORE -> EncodedIStoreInstruction(input.readUnsignedByte())
            OpCodes.ISTORE_0, OpCodes.ISTORE_1, OpCodes.ISTORE_2, OpCodes.ISTORE_3 -> EncodedInstructionConstants.ISTORE_INSTRUCTIONS[opcode - OpCodes.ISTORE_0]
            OpCodes.LDC -> EncodedLdcInstruction(input.readUnsignedByte(), 1)
            OpCodes.DUP -> EncodedInstructionConstants.DUP_INSTRUCTION
            OpCodes.NEW -> EncodedNewInstruction(input.readUnsignedShort())
            OpCodes.POP -> EncodedInstructionConstants.POP_INSTRUCTION
            OpCodes.PUTFIELD -> EncodedPutFieldInstruction(input.readUnsignedShort())
            OpCodes.PUTSTATIC -> EncodedPutStaticInstruction(input.readUnsignedShort())
            OpCodes.RETURN -> EncodedInstructionConstants.RETURN_INSTRUCTION
            else -> throw IllegalArgumentException("Unkwown opcode: ${OpCodes.toString(opcode)}")
        }
    }
}
