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

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.instruction.base.InstructionEncodedWriter
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper.hex
import jdk.internal.org.objectweb.asm.Opcodes
import java.io.Writer

class TextInstructionEncodedWriter(private val output: Writer,
                                   private val entries: List<EncodedConstantPoolEntry>,
                                   private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter) :
        InstructionEncodedWriter {
    override fun aLoadNInstruction(opcode: Int) {
        output.write("OpCodes.ALOAD_${opcode - OpCodes.ALOAD_0},\n")
    }

    override fun iStoreInstruction(index: Int) {
        output.write("OpCodes.ISTORE, %s // local: %s\n".format(TextEncodedWriterHelper.hex(index),
                index))
    }

    override fun iStoreNInstruction(opcode: Int) {
        output.write("OpCodes.ISTORE_${opcode - OpCodes.ISTORE_0},\n")
    }

    override fun ifNonNullInstruction(branch: Int) {
        output.write("OpCodes.IFNONNULL, ")
        TextEncodedWriterHelper.writeU1(output, "offset", branch)
    }

    override fun iConstNInstruction(opcode: Int) {
        output.write("OpCodes.ICONST_${opcode - OpCodes.ICONST_0},\n")
    }

    override fun invokeVirtualInstruction(methodIndex: Int) {
        output.write("OpCodes.INVOKEVIRTUAL, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "method", methodIndex, entries,
                summaryEncodedWriter)
    }

    override fun getStaticInstruction(fieldIndex: Int) {
        output.write("OpCodes.GETSTATIC, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "field", fieldIndex, entries,
                summaryEncodedWriter)
    }

    override fun aStoreNInstruction(opcode: Int) {
        output.write("OpCodes.ASTORE_${opcode - OpCodes.ASTORE_0},\n")
    }

    override fun gotoWInstruction(branch: Int) {
        TODO("not implemented")
    }

    override fun returnInstruction(opcode: Int) {
        output.write(when (opcode) {
            OpCodes.RETURN -> "OpCodes.RETURN\n"
            OpCodes.ARETURN -> "OpCodes.ARETURN\n"
            else -> throw IllegalArgumentException("Unexpected return opcode: $opcode")
        })
    }

    override fun aLoadInstruction(referenceIndex: Int) {
        output.write("OpCodes.ALOAD, ")
        TextEncodedWriterHelper.writeByteEntryIndex(output, "reference", referenceIndex, entries,
                summaryEncodedWriter)
    }

    override fun iLoadInstruction(referenceIndex: Int) {
        output.write("OpCodes.ILOAD, ")
        TextEncodedWriterHelper.writeByteEntryIndex(output, "integer", referenceIndex, entries,
                summaryEncodedWriter)
    }

    override fun iLoadNInstruction(opcode: Int) {
        output.write("OpCodes.ILOAD_${opcode - OpCodes.ILOAD_0},\n")
    }

    override fun getFieldInstruction(fieldIndex: Int) {
        output.write("OpCodes.GETFIELD, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "field", fieldIndex, entries,
                summaryEncodedWriter)
    }

    override fun putFieldInstruction(fieldIndex: Int) {
        output.write("OpCodes.PUTFIELD, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "field", fieldIndex, entries,
                summaryEncodedWriter)
    }

    override fun checkCastInstruction(referenceIndex: Int) {
        output.write("OpCodes.CHECKCAST, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "reference", referenceIndex, entries,
                summaryEncodedWriter)
    }

    override fun blockInstruction(
            encodedInstructions: List<EncodedInstruction>) {
        var i = 0
        encodedInstructions.forEach {
            output.write("/* $i */ ")
            it.write(this)
            i += it.size
        }
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
        when {
            stackSize == 2 -> {
                output.write("OpCodes.LDC2_W, ")
                TextEncodedWriterHelper.writeShortEntryIndex(output, "item", index, entries,
                        summaryEncodedWriter)
            }
            index <= BytecodeHelper.BYTE_MAX -> {
                output.write("OpCodes.LDC, ")
                TextEncodedWriterHelper.writeByteEntryIndex(output, "item", index, entries,
                        summaryEncodedWriter)
            }
            else -> {
                output.write("OpCodes.LDC_W, ")
                TextEncodedWriterHelper.writeShortEntryIndex(output, "item", index, entries,
                        summaryEncodedWriter)
            }
        }
    }

    override fun invokeStaticInstruction(methodIndex: Int) {
        output.write("OpCodes.INVOKESTATIC, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "method", methodIndex, entries,
                summaryEncodedWriter)
    }

    override fun biPushInstruction(value: Int) {
        output.write("OpCodes.BIPUSH, ")
        TextEncodedWriterHelper.writeU1(output, "value", value)
    }

    override fun ifInstruction(opcode: Int, branch: Int) {
        val op = when (opcode) {
            OpCodes.IFEQ -> "OpCodes.IFEQ"
            OpCodes.IFNE -> "OpCodes.IFNE"
            OpCodes.IFLT -> "OpCodes.IFLT"
            OpCodes.IFGE -> "OpCodes.IFGE"
            OpCodes.IFGT -> "OpCodes.IFGT"
            OpCodes.IFLE -> "OpCodes.IFLE"
            else -> throw java.lang.IllegalArgumentException("Unknown if opcode: $opcode")
        }
        output.write("$op, ")
        TextEncodedWriterHelper.writeU1(output, "offset", branch)
    }

    override fun popInstruction() {
        output.write("Opcodes.POP,\n")
    }

    override fun constInstruction(opcode: Int) {
        output.write("OpCodes.ACONST_NULL,\n")
    }

    override fun gotoInstruction(branch: Int) {
        output.write("OpCodes.GOTO, %s, %s // offset: %s\n".format(hex(branch shr 8), hex(branch),
                branch))
    }

    override fun aStoreInstruction(opcode: Int, referenceIndex: Int) {
        output.write("OpCodes.ASTORE, ")
        TextEncodedWriterHelper.writeByteEntryIndex(output, "reference", referenceIndex, entries,
                summaryEncodedWriter)
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
        output.write("OpCodes.DUP\n")
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

    override fun invokeInterfaceInstruction(interfaceIndex: Int,
                                            count: Int) {
        output.write("OpCodes.INVOKEINTERFACE, ")
        output.write(
                "%s, %s, %s // ${"interface"}: #%s -> ".format(
                        hex(interfaceIndex shr 8),
                        hex(interfaceIndex), hex(count), interfaceIndex))
        entries[interfaceIndex - 1].write(summaryEncodedWriter)
        output.write(" (args size: $count)\n")
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

    override fun newInstruction(classIndex: Int) {
        output.write("OpCodes.NEW, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "class", classIndex, entries,
                summaryEncodedWriter)
    }

    override fun invokeSpecialInstruction(methodIndex: Int) {
        output.write("OpCodes.INVOKESPECIAL, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "method", methodIndex, entries,
                summaryEncodedWriter)
    }

    override fun putStaticInstruction(fieldIndex: Int) {
        output.write("OpCodes.PUTSTATIC, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "field", fieldIndex, entries,
                summaryEncodedWriter)
    }

}