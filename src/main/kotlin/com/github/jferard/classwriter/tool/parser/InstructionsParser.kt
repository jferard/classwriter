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

package com.github.jferard.classwriter.tool.parser

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.base.BiPushInstruction
import com.github.jferard.classwriter.encoded.instruction.*
import com.github.jferard.classwriter.writer.encoded.EncodedInstanceOfInstruction
import java.io.DataInput
import java.util.logging.Logger

class InstructionsParser(private val logger: Logger) : Parser<EncodedInstruction> {
    override fun parse(input: DataInput): EncodedInstruction {
        logger.finer("Parse instructions")
        val codeLength = input.readInt().toLong()
        logger.finest("Code length: $codeLength")
        val ret: MutableList<EncodedInstruction> = mutableListOf()
        var i = 0
        while (i < codeLength) {
            val element = parseInstruction(input, i)
            logger.finest("Parsed: $element, ${element.getSize(i)} ($i/$codeLength")
            ret.add(element)
            i += element.getSize(i)
        }
        return EncodedBlockInstruction(ret.toList())
    }

    private fun parseInstruction(input: DataInput, i: Int): EncodedInstruction {
        val opcode = input.readUnsignedByte()
        return when (opcode) {
            // LOAD
            OpCodes.ALOAD -> EncodedALoadInstruction(input.readUnsignedByte())
            OpCodes.ALOAD_0, OpCodes.ALOAD_1, OpCodes.ALOAD_2, OpCodes.ALOAD_3 -> EncodedInstructionConstants.ALOAD_INSTRUCTIONS[opcode - OpCodes.ALOAD_0]
            OpCodes.DLOAD_0, OpCodes.DLOAD_1, OpCodes.DLOAD_2, OpCodes.DLOAD_3 -> EncodedInstructionConstants.DLOAD_INSTRUCTIONS[opcode - OpCodes.DLOAD_0]
            OpCodes.FLOAD_0, OpCodes.FLOAD_1, OpCodes.FLOAD_2, OpCodes.FLOAD_3 -> EncodedInstructionConstants.FLOAD_INSTRUCTIONS[opcode - OpCodes.FLOAD_0]
            OpCodes.ILOAD -> EncodedILoadInstruction(input.readUnsignedByte())
            OpCodes.ILOAD_0, OpCodes.ILOAD_1, OpCodes.ILOAD_2, OpCodes.ILOAD_3 -> EncodedInstructionConstants.ILOAD_INSTRUCTIONS[opcode - OpCodes.ILOAD_0]
            OpCodes.LLOAD -> EncodedLLoadInstruction(input.readUnsignedByte())
            OpCodes.LLOAD_0, OpCodes.LLOAD_1, OpCodes.LLOAD_2, OpCodes.LLOAD_3 -> EncodedInstructionConstants.LLOAD_INSTRUCTIONS[opcode - OpCodes.LLOAD_0]

            OpCodes.IALOAD -> EncodedInstructionConstants.IALOAD_INSTRUCTION

            // STORE
            OpCodes.ASTORE -> EncodedAStoreInstruction(input.readUnsignedByte())
            OpCodes.ASTORE_0, OpCodes.ASTORE_1, OpCodes.ASTORE_2, OpCodes.ASTORE_3 -> EncodedInstructionConstants.ASTORE_INSTRUCTIONS[opcode - OpCodes.ASTORE_0]
            OpCodes.DSTORE_0, OpCodes.DSTORE_1, OpCodes.DSTORE_2, OpCodes.DSTORE_3 -> EncodedInstructionConstants.DSTORE_INSTRUCTIONS[opcode - OpCodes.DSTORE_0]
            OpCodes.FSTORE_0, OpCodes.FSTORE_1, OpCodes.FSTORE_2, OpCodes.FSTORE_3 -> EncodedInstructionConstants.FSTORE_INSTRUCTIONS[opcode - OpCodes.FSTORE_0]
            OpCodes.ISTORE -> EncodedIStoreInstruction(input.readUnsignedByte())
            OpCodes.ISTORE_0, OpCodes.ISTORE_1, OpCodes.ISTORE_2, OpCodes.ISTORE_3 -> EncodedInstructionConstants.ISTORE_INSTRUCTIONS[opcode - OpCodes.ISTORE_0]

            OpCodes.FASTORE -> EncodedInstructionConstants.FASTORE_INSTRUCTION

            // CONST
            OpCodes.ACONST_NULL -> EncodedInstructionConstants.ACONST_NULL_INSTRUCTION
            OpCodes.FCONST_0, OpCodes.FCONST_1, OpCodes.FCONST_2 -> EncodedInstructionConstants.FCONST_N_INSTRUCTIONS[opcode - OpCodes.FCONST_0]
            OpCodes.ICONST_0, OpCodes.ICONST_1, OpCodes.ICONST_2, OpCodes.ICONST_3, OpCodes.ICONST_4, OpCodes.ICONST_5 -> EncodedInstructionConstants.ICONST_INSTRUCTIONS[opcode - OpCodes.ICONST_0]
            OpCodes.LCONST_0, OpCodes.LCONST_1 -> EncodedInstructionConstants.LCONST_N_INSTRUCTIONS[opcode - OpCodes.LCONST_0]

            // OTHER
            OpCodes.ARETURN -> EncodedInstructionConstants.ARETURN_INSTRUCTION
            OpCodes.ATHROW -> EncodedInstructionConstants.ATHROW_INSTRUCTION
            OpCodes.BIPUSH -> BiPushInstruction(input.readUnsignedByte())
            OpCodes.CHECKCAST -> EncodedCheckCastInstruction(
                    input.readUnsignedShort())
            OpCodes.GETFIELD -> EncodedGetFieldInstruction(
                    input.readUnsignedShort())
            OpCodes.GETSTATIC -> EncodedGetStaticInstruction(
                    input.readUnsignedShort())
            OpCodes.GOTO -> EncodedGotoInstruction(
                    input.readUnsignedShort())
            OpCodes.IFEQ, OpCodes.IFNE, OpCodes.IFLT, OpCodes.IFGE, OpCodes.IFGT, OpCodes.IFLE, OpCodes.IFNONNULL, OpCodes.IFNULL -> EncodedIfInstruction(
                    opcode, input.readUnsignedShort())
            OpCodes.IF_ACMPEQ, OpCodes.IF_ACMPNE -> EncodedIfACmpInstruction(opcode,
                    input.readUnsignedShort())
            OpCodes.INVOKEINTERFACE -> {
                val index = input.readUnsignedShort()
                val count = input.readUnsignedByte()
                check(0 == input.readUnsignedByte())
                EncodedInvokeInterfaceInstruction(
                        index, count)
            }
            OpCodes.INVOKESPECIAL -> EncodedInvokeSpecialInstruction(
                    input.readUnsignedShort())
            OpCodes.INVOKESTATIC -> EncodedInvokeStaticInstruction(
                    input.readUnsignedShort())
            OpCodes.INVOKEVIRTUAL -> EncodedInvokeVirtualInstruction(
                    input.readUnsignedShort())
            OpCodes.LDC -> EncodedLdcInstruction(opcode, input.readUnsignedByte())
            OpCodes.LDC_W -> EncodedLdcInstruction(opcode, input.readUnsignedShort())
            OpCodes.LDC2_W -> EncodedLdcInstruction(opcode, input.readUnsignedShort())
            OpCodes.DUP -> EncodedInstructionConstants.DUP_INSTRUCTION
            OpCodes.DUP2_X2 -> EncodedInstructionConstants.DUP2_X2_INSTRUCTION
            OpCodes.NEW -> EncodedNewInstruction(
                    input.readUnsignedShort())
            OpCodes.POP -> EncodedInstructionConstants.POP_INSTRUCTION
            OpCodes.PUTFIELD -> EncodedPutFieldInstruction(
                    input.readUnsignedShort())
            OpCodes.PUTSTATIC -> EncodedPutStaticInstruction(
                    input.readUnsignedShort())
            OpCodes.RETURN -> EncodedInstructionConstants.RETURN_INSTRUCTION
            OpCodes.IRETURN -> EncodedInstructionConstants.IRETURN_INSTRUCTION
            OpCodes.NOP -> EncodedInstructionConstants.NOP_INSTRUCTION
            OpCodes.ISHL -> EncodedInstructionConstants.ISHL_INSTRUCTION
            OpCodes.ISUB -> EncodedInstructionConstants.ISUB_INSTRUCTION
            OpCodes.IMUL -> EncodedInstructionConstants.IMUL_INSTRUCTION
            OpCodes.INSTANCEOF -> EncodedInstanceOfInstruction(input.readUnsignedShort())
            OpCodes.IF_ICMPEQ, OpCodes.IF_ICMPGE, OpCodes.IF_ICMPGT, OpCodes.IF_ICMPLE, OpCodes.IF_ICMPLT, OpCodes.IF_ICMPNE -> EncodedIfICmpInstruction(
                    opcode, input.readUnsignedShort())
            OpCodes.I2C -> EncodedInstructionConstants.I2C_INSTRUCTION
            OpCodes.L2I -> EncodedInstructionConstants.L2I_INSTRUCTION
            OpCodes.IADD -> EncodedInstructionConstants.IADD_INSTRUCTION
            OpCodes.ARRAYLENGTH -> EncodedInstructionConstants.ARRAYLENGTH_INSTRUCTION
            OpCodes.SIPUSH -> EncodedSiPush(input.readShort().toInt())
            OpCodes.IINC -> EncodedIIncInstruction(input.readUnsignedByte(),
                    input.readByte().toInt())
            OpCodes.TABLESWITCH -> {
                val padCount = 3 - (i % 4)
                input.skipBytes(padCount)
                val defaultOffset = input.readInt()
                val low = input.readInt()
                val high = input.readInt()
                val jumpOffsets = (low..high).map { input.readInt() }
                EncodedTableSwitchInstruction(defaultOffset, low, high, jumpOffsets)
            }
            OpCodes.SWAP -> EncodedInstructionConstants.SWAP_INSTRUCTION
            OpCodes.IREM -> EncodedInstructionConstants.IREM_INSTRUCTION
            else -> throw IllegalArgumentException("Unkwown opcode: ${OpCodes.toString(opcode)}")
        }
    }
}
