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
package com.github.jferard.classwriter.api.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.*
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.api.instruction.base.*
import com.github.jferard.classwriter.api.instruction.block.BlockInstruction
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import java.util.*

class RawCodeBuilder(private val paddingHelper: PaddingHelper) : CodeBuilder {
    private val instructions: MutableList<BaseInstruction>
    /**
     * aaload: load onto the stack a reference from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun aaload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.AALOAD_INSTRUCTION)
        return this
    }

    /**
     * aastore: store into a reference in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun aastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.AASTORE_INSTRUCTION)
        return this
    }

    /**
     * aconst_null: push a null reference onto the stack.
     * Stack: () -> (null).
     */
    fun aconst_null(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ACONST_NULL_INSTRUCTION)
        return this
    }

    /**
     * aload_0...aload_3: load a reference onto the stack from local variable 0...3.
     * aload: load a reference onto the stack from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (objectref).
     */
    fun aload(index: Int): RawCodeBuilder {
        load(OpCodes.ALOAD,
                InstructionConstants.ALOAD_INSTRUCTIONS,
                index)
        return this
    }

    private fun load(loadCode: Int, loadN: Array<BaseInstruction>,
                     index: Int) {
        when {
            index <= 3 -> {
                instructions.add(loadN[index])
            }
            index <= BytecodeHelper.BYTE_MAX -> {
                instructions.add(
                        LoadInstruction(
                                loadCode, index))
            }
            else -> {
                instructions.add(WideLoadInstruction(loadCode, index))
            }
        }
    }

    private fun store(storeCode: Int, storeN: Array<BaseInstruction>,
                      index: Int) {
        when {
            index <= 3 -> {
                instructions.add(storeN[index])
            }
            index <= BytecodeHelper.BYTE_MAX -> {
                instructions.add(
                        StoreInstruction(
                                storeCode, index))
            }
            else -> {
                instructions.add(WideStoreInstruction(storeCode, index))
            }
        }
    }

    /**
     * anewarray: create a new array of references of length count and component type identified
     * by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool.
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (count) -> (arrayref).
     */
    fun anewarray(classRef: PlainClassRef): RawCodeBuilder {
        instructions.add(ANewArrayInstruction(classRef))
        return this
    }

    /**
     * areturn: return a reference from a method.
     * Stack: (objectref) -> -0-.
     */
    fun areturn(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ARETURN_INSTRUCTION)
        return this
    }

    /**
     * arraylength: get the length of an array.
     * Stack: (arrayref) -> (length).
     */
    fun arraylength(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ARRAYLENGTH_INSTRUCTION)
        return this
    }

    /**
     * astore_0...astore_3: store a reference into local variable 0...3.
     * astore: store a reference into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (objectref) -> ().
     */
    fun astore(index: Int): RawCodeBuilder {
        store(OpCodes.ASTORE,
                InstructionConstants.ASTORE_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * athrow: throws an error or exception (notice that the rest of the stack is cleared,
     * leaving only a reference to the Throwable).
     * Stack: (objectref) -> ([empty], objectref).
     */
    fun athrow(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ATHROW_INSTRUCTION)
        return this
    }

    /**
     * baload: load a byte or Boolean value from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun baload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.BALOAD_INSTRUCTION)
        return this
    }

    /**
     * bastore: store a byte or Boolean value into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun bastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.BASTORE_INSTRUCTION)
        return this
    }

    /**
     * bipush: push a byte onto the stack as an integer value.
     * Other bytes (count: 1): byte.
     * Stack: () -> (value).
     */
    fun bipush(b: Int): RawCodeBuilder {
        instructions.add(BiPushInstruction(b))
        return this
    }

    /**
     * breakpoint: reserved for breakpoints in Java debuggers; should not appear in any class file.
     */
    fun breakpoint(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.BREAKPOINT_INSTRUCTION)
        return this
    }

    /**
     * caload: load a char from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun caload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.CALOAD_INSTRUCTION)
        return this
    }

    /**
     * castore: store a char into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun castore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.CASTORE_INSTRUCTION)
        return this
    }

    /**
     * checkcast: checks whether an objectref is of a certain type, the class reference of which
     * is in the access pool at index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (objectref).
     */
    fun checkcast(classRef: PlainClassRef): RawCodeBuilder {
        instructions.add(CheckCastInstruction(classRef))
        return this
    }

    /**
     * d2f: convert a double to a float.
     * Stack: (value) -> (result).
     */
    fun d2f(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.D2F_INSTRUCTION)
        return this
    }

    /**
     * d2i: convert a double to an int.
     * Stack: (value) -> (result).
     */
    fun d2i(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.D2I_INSTRUCTION)
        return this
    }

    /**
     * d2l: convert a double to a long.
     * Stack: (value) -> (result).
     */
    fun d2l(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.D2L_INSTRUCTION)
        return this
    }

    /**
     * dadd: add two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun dadd(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DADD_INSTRUCTION)
        return this
    }

    /**
     * daload: load a double from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun daload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DALOAD_INSTRUCTION)
        return this
    }

    /**
     * dastore: store a double into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun dastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DASTORE_INSTRUCTION)
        return this
    }

    /**
     * dcmpg: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun dcmpg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DCMPG_INSTRUCTION)
        return this
    }

    /**
     * dcmpl: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun dcmpl(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DCMPL_INSTRUCTION)
        return this
    }

    /**
     * dconst_0: push the access 0.0 (a double) onto the stack.
     * Stack: () -> (0.0).
     */
    fun dconst_0(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DCONST_0_INSTRUCTION)
        return this
    }

    /**
     * dconst_1: push the access 1.0 (a double) onto the stack.
     * Stack: () -> (1.0).
     */
    fun dconst_1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DCONST_1_INSTRUCTION)
        return this
    }

    /**
     * ddiv: divide two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun ddiv(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DDIV_INSTRUCTION)
        return this
    }

    /**
     * dload_0: load a double from local variable 0.
     * dload: load a double value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    fun dload(index: Int): RawCodeBuilder {
        load(OpCodes.DLOAD,
                InstructionConstants.DLOAD_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * dmul: multiply two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun dmul(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DMUL_INSTRUCTION)
        return this
    }

    /**
     * dneg: negate a double.
     * Stack: (value) -> (result).
     */
    fun dneg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DNEG_INSTRUCTION)
        return this
    }

    /**
     * drem: get the remainder from a division between two doubles.
     * Stack: (value1, value2) -> (result).
     */
    fun drem(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DREM_INSTRUCTION)
        return this
    }

    /**
     * dreturn: return a double from a method.
     * Stack: (value) -> ([empty]).
     */
    fun dreturn(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DRETURN_INSTRUCTION)
        return this
    }

    /**
     * dstore_0: store a double into local variable 0.
     * dstore: store a double value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    fun dstore(index: Int): RawCodeBuilder {
        store(OpCodes.DSTORE,
                InstructionConstants.DSTORE_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * dsub: subtract a double from another.
     * Stack: (value1, value2) -> (result).
     */
    fun dsub(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DSUB_INSTRUCTION)
        return this
    }

    /**
     * dup: duplicate the value on top of the stack.
     * Stack: (value) -> (value, value).
     */
    fun dup(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP_INSTRUCTION)
        return this
    }

    /**
     * dup_x1: insert a copy of the top value into the stack two values from the top. value1 and
     * value2 must not be of the type double or long..
     * Stack: (value2, value1) -> (value1, value2, value1).
     */
    fun dup_x1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP_X1_INSTRUCTION)
        return this
    }

    /**
     * dup_x2: insert a copy of the top value into the stack two (if value2 is double or long it
     * takes up the entry of value3, too) or three values (if value2 is neither double nor long)
     * from the top.
     * Stack: (value3, value2, value1) -> (value1, value3, value2, value1).
     */
    fun dup_x2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP_X2_INSTRUCTION)
        return this
    }

    /**
     * dup2: duplicate top two stack words (two values, if value1 is not double nor long; a
     * single value, if value1 is double or long).
     * Stack: ({value2, value1}) -> ({value2, value1}, {value2, value1}).
     */
    fun dup2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP2_INSTRUCTION)
        return this
    }

    /**
     * dup2_x1: duplicate two words and insert beneath third word (see explanation above).
     * Stack: (value3, {value2, value1}) -> ({value2, value1}, value3, {value2, value1}).
     */
    fun dup2_x1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP2_X1_INSTRUCTION)
        return this
    }

    /**
     * dup2_x2: duplicate two words and insert beneath fourth word.
     * Stack: ({value4, value3}, {value2, value1}) -> ({value2, value1}, {value4, value3},
     * {value2, value1}).
     */
    fun dup2_x2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.DUP2_X2_INSTRUCTION)
        return this
    }

    /**
     * f2d: convert a float to a double.
     * Stack: (value) -> (result).
     */
    fun f2d(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.F2D_INSTRUCTION)
        return this
    }

    /**
     * f2i: convert a float to an int.
     * Stack: (value) -> (result).
     */
    fun f2i(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.F2I_INSTRUCTION)
        return this
    }

    /**
     * f2l: convert a float to a long.
     * Stack: (value) -> (result).
     */
    fun f2l(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.F2L_INSTRUCTION)
        return this
    }

    /**
     * fadd: add two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fadd(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FADD_INSTRUCTION)
        return this
    }

    /**
     * faload: load a float from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun faload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FALOAD_INSTRUCTION)
        return this
    }

    /**
     * fastore: store a float in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun fastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FASTORE_INSTRUCTION)
        return this
    }

    /**
     * fcmpg: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fcmpg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FCMPG_INSTRUCTION)
        return this
    }

    /**
     * fcmpl: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fcmpl(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FCMPL_INSTRUCTION)
        return this
    }

    /**
     * fconst_0: push 0.0f on the stack.
     * Stack: () -> (0.0f).
     */
    fun fconst_0(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FCONST_0_INSTRUCTION)
        return this
    }

    /**
     * fconst_1: push 1.0f on the stack.
     * Stack: () -> (1.0f).
     */
    fun fconst_1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FCONST_1_INSTRUCTION)
        return this
    }

    /**
     * fconst_2: push 2.0f on the stack.
     * Stack: () -> (2.0f).
     */
    fun fconst_2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FCONST_2_INSTRUCTION)
        return this
    }

    /**
     * fdiv: divide two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fdiv(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FDIV_INSTRUCTION)
        return this
    }

    /**
     * fload_0: load a float value from local variable 0.
     * fload: load a float value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    fun fload(index: Int): RawCodeBuilder {
        load(OpCodes.FLOAD,
                InstructionConstants.FLOAD_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * fmul: multiply two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fmul(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FMUL_INSTRUCTION)
        return this
    }

    /**
     * fneg: negate a float.
     * Stack: (value) -> (result).
     */
    fun fneg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FNEG_INSTRUCTION)
        return this
    }

    /**
     * frem: get the remainder from a division between two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun frem(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FREM_INSTRUCTION)
        return this
    }

    /**
     * freturn: return a float.
     * Stack: (value) -> ([empty]).
     */
    fun freturn(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FRETURN_INSTRUCTION)
        return this
    }

    /**
     * fstore_0: store a float value into local variable 0.
     * fstore: store a float value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    fun fstore(index: Byte): RawCodeBuilder {
        store(OpCodes.FSTORE,
                InstructionConstants.FSTORE_INSTRUCTIONS,
                index.toInt())
        return this
    }

    /**
     * fsub: subtract two floats.
     * Stack: (value1, value2) -> (result).
     */
    fun fsub(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.FSUB_INSTRUCTION)
        return this
    }

    /**
     * getfield: get a field value of an object objectref, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (value).
     */
    fun getfield(field: FieldRef): RawCodeBuilder {
        instructions.add(GetFieldInstruction(field))
        return this
    }

    /**
     * getstatic: get a static field value of a class, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    fun getstatic(field: FieldRef): RawCodeBuilder {
        instructions.add(GetStaticInstruction(field))
        return this
    }

    /**
     * goto: goes to another instruction at branchoffset (signed short constructed from unsigned
     * bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     */
    fun goto_(label: Label): RawCodeBuilder {
        instructions.add(
                GotoInstruction(label))
        return this
    }

    /**
     * i2b: convert an int into a byte.
     * Stack: (value) -> (result).
     */
    fun i2b(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2B_INSTRUCTION)
        return this
    }

    /**
     * i2c: convert an int into a character.
     * Stack: (value) -> (result).
     */
    fun i2c(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2C_INSTRUCTION)
        return this
    }

    /**
     * i2d: convert an int into a double.
     * Stack: (value) -> (result).
     */
    fun i2d(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2D_INSTRUCTION)
        return this
    }

    /**
     * i2f: convert an int into a float.
     * Stack: (value) -> (result).
     */
    fun i2f(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2F_INSTRUCTION)
        return this
    }

    /**
     * i2l: convert an int into a long.
     * Stack: (value) -> (result).
     */
    fun i2l(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2L_INSTRUCTION)
        return this
    }

    /**
     * i2s: convert an int into a short.
     * Stack: (value) -> (result).
     */
    fun i2s(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.I2S_INSTRUCTION)
        return this
    }

    /**
     * iadd: add two ints.
     * Stack: (value1, value2) -> (result).
     */
    fun iadd(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IADD_INSTRUCTION)
        return this
    }

    /**
     * iaload: load an int from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun iaload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IALOAD_INSTRUCTION)
        return this
    }

    /**
     * iand: perform a bitwise AND on two integers.
     * Stack: (value1, value2) -> (result).
     */
    fun iand(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IAND_INSTRUCTION)
        return this
    }

    /**
     * iastore: store an int into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun iastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IASTORE_INSTRUCTION)
        return this
    }

    /**
     * iconst_m1: load the int value −1 onto the stack.
     * iconst_<n>: load the int value <n> onto the stack. (<n> = 0..5)
     * Stack: () -> (-1).
    </n></n></n> */
    fun iconst(value: Int): RawCodeBuilder {
        require(!(value < -1 || value > 5)) { "Bad iconst value: $value" }
        instructions.add(
                InstructionConstants.ICONST_INSTRUCTIONS[value + 1])
        return this
    }

    /**
     * idiv: divide two integers.
     * Stack: (value1, value2) -> (result).
     */
    fun idiv(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IDIV_INSTRUCTION)
        return this
    }

    /**
     * if_acmpeq: if references are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_acmpeq(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ACMPEQ, label))
        return this
    }

    /**
     * if_acmpne: if references are not equal, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_acmpne(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ACMPNE, label))
        return this
    }

    /**
     * if_icmpeq: if ints are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmpeq(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPEQ, label))
        return this
    }

    /**
     * if_icmpge: if value1 is greater than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmpge(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPGE, label))
        return this
    }

    /**
     * if_icmpgt: if value1 is greater than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmpgt(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPGT, label))
        return this
    }

    /**
     * if_icmple: if value1 is less than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmple(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPLE, label))
        return this
    }

    /**
     * if_icmplt: if value1 is less than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmplt(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPLT, label))
        return this
    }

    /**
     * if_icmpne: if ints are not equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    fun if_icmpne(label: Label): RawCodeBuilder {
        instructions.add(
                BranchInstruction(
                        OpCodes.IF_ICMPNE, label))
        return this
    }

    /**
     * ifeq: if value is 0, branch to instruction at branchoffset (signed short constructed from
     * unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifeq(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFEQ, label))
        return this
    }

    /**
     * ifge: if value is greater than or equal to 0, branch to instruction at branchoffset
     * (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifge(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFGE, label))
        return this
    }

    /**
     * ifgt: if value is greater than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifgt(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFGT, label))
        return this
    }

    /**
     * ifle: if value is less than or equal to 0, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifle(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFLE, label))
        return this
    }

    /**
     * iflt: if value is less than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun iflt(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFLT, label))
        return this
    }

    /**
     * ifne: if value is not 0, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifne(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFNE, label))
        return this
    }

    /**
     * ifnonnull: if value is not null, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifnonnull(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFNONNULL, label))
        return this
    }

    /**
     * ifnull: if value is null, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    fun ifnull(label: Label): RawCodeBuilder {
        instructions.add(
                Branch0Instruction(OpCodes.IFNULL, label))
        return this
    }

    /**
     * iinc: increment local variable #index by signed byte const.
     * wide: execute iinc
     * Other bytes (count: 2): index, const.
     */
    fun iinc(index: Int, c: Int): RawCodeBuilder {
        if (index <= BytecodeHelper.BYTE_MAX && c <= BytecodeHelper.BYTE_MAX) {
            instructions.add(IincInstruction(index, c))
        } else {
            instructions.add(WideIincInstruction(index, c))
        }
        return this
    }

    /**
     * iload_0: load an int value from local variable 0.
     * iload: load an int value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    fun iload(index: Byte): RawCodeBuilder {
        load(OpCodes.ILOAD,
                InstructionConstants.ILOAD_INSTRUCTIONS,
                index.toInt())
        return this
    }

    /**
     * impdep1: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    fun impdep1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IMPDEP1_INSTRUCTION)
        return this
    }

    /**
     * impdep2: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    fun impdep2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IMPDEP2_INSTRUCTION)
        return this
    }

    /**
     * imul: multiply two integers.
     * Stack: (value1, value2) -> (result).
     */
    fun imul(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IMUL_INSTRUCTION)
        return this
    }

    /**
     * ineg: negate int.
     * Stack: (value) -> (result).
     */
    fun ineg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.INEG_INSTRUCTION)
        return this
    }

    /**
     * instanceof: determines if an object objectref is of a given type, identified by class
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (result).
     */
    fun instanceof_(classRef: PlainClassRef): RawCodeBuilder {
        instructions.add(InstanceofInstruction(classRef))
        return this
    }

    /**
     * invokedynamic: invokes a dynamic method and puts the result on the stack (might be void);
     * the method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, 0, 0.
     * Stack: ([arg1, [arg2 ...]]) -> (result).
     */
    fun invokedynamic(methodRef: MethodRef): RawCodeBuilder {
        instructions.add(InvokeDynamicInstruction(methodRef))
        return this
    }

    /**
     * invokeinterface: invokes an interface method on object objectref and puts the result on
     * the stack (might be void); the interface method is identified by method reference index in
     * access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, count, 0.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    fun invokeinterface(methodRef: MethodRef): RawCodeBuilder {
        instructions.add(InvokeInterfaceInstruction(methodRef))
        return this
    }

    /**
     * invokespecial: invoke instance method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    fun invokespecial(methodRef: MethodRef): RawCodeBuilder {
        instructions
                .add(InvokeSpecialOrVirtualInstruction(
                        OpCodes.INVOKESPECIAL, methodRef))
        return this
    }

    /**
     * invokestatic: invoke a static method and puts the result on the stack (might be void); the
     * method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: ([arg1, arg2, ...]) -> (result).
     */
    fun invokestatic(methodRef: MethodRef): RawCodeBuilder {
        instructions.add(
                InvokeStaticInstruction(OpCodes.INVOKESTATIC,
                        methodRef))
        return this
    }

    /**
     * invokevirtual: invoke virtual method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    fun invokevirtual(methodRef: MethodRef): RawCodeBuilder {
        instructions
                .add(InvokeSpecialOrVirtualInstruction(
                        OpCodes.INVOKEVIRTUAL, methodRef))
        return this
    }

    /**
     * ior: bitwise int OR.
     * Stack: (value1, value2) -> (result).
     */
    fun ior(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IOR_INSTRUCTION)
        return this
    }

    /**
     * irem: logical int remainder.
     * Stack: (value1, value2) -> (result).
     */
    fun irem(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IREM_INSTRUCTION)
        return this
    }

    /**
     * ireturn: return an integer from a method.
     * Stack: (value) -> ([empty]).
     */
    fun ireturn(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IRETURN_INSTRUCTION)
        return this
    }

    /**
     * ishl: int shift left.
     * Stack: (value1, value2) -> (result).
     */
    fun ishl(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ISHL_INSTRUCTION)
        return this
    }

    /**
     * ishr: int arithmetic shift right.
     * Stack: (value1, value2) -> (result).
     */
    fun ishr(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ISHR_INSTRUCTION)
        return this
    }

    /**
     * istore_0: store int value into variable 0.
     * istore: store int value into variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    fun istore(index: Int): RawCodeBuilder {
        store(OpCodes.ISTORE,
                InstructionConstants.ISTORE_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * isub: int subtract.
     * Stack: (value1, value2) -> (result).
     */
    fun isub(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.ISUB_INSTRUCTION)
        return this
    }

    /**
     * iushr: int logical shift right.
     * Stack: (value1, value2) -> (result).
     */
    fun iushr(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IUSHR_INSTRUCTION)
        return this
    }

    /**
     * ixor: int xor.
     * Stack: (value1, value2) -> (result).
     */
    fun ixor(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.IXOR_INSTRUCTION)
        return this
    }

    /**
     * jsr: jump to subroutine at branchoffset (signed short constructed from unsigned bytes
     * branchbyte1 << 8 + branchbyte2) and place the return address on the stack.
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: () -> (address).
     */
    @Deprecated("")
    fun jsr(instruction: Instruction): RawCodeBuilder {
        instructions.add(
                JsrInstruction(
                        instruction))
        return this
    }

    /**
     * l2d: convert a long to a double.
     * Stack: (value) -> (result).
     */
    fun l2d(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.L2D_INSTRUCTION)
        return this
    }

    /**
     * l2f: convert a long to a float.
     * Stack: (value) -> (result).
     */
    fun l2f(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.L2F_INSTRUCTION)
        return this
    }

    /**
     * l2i: convert a long to a int.
     * Stack: (value) -> (result).
     */
    fun l2i(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.L2I_INSTRUCTION)
        return this
    }

    /**
     * ladd: add two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun ladd(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LADD_INSTRUCTION)
        return this
    }

    /**
     * laload: load a long from an array.
     * Stack: (arrayref, index) -> (value).
     */
    fun laload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LALOAD_INSTRUCTION)
        return this
    }

    /**
     * land: bitwise AND of two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun land(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LAND_INSTRUCTION)
        return this
    }

    /**
     * lastore: store a long to an array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun lastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LASTORE_INSTRUCTION)
        return this
    }

    /**
     * lcmp: push 0 if the two longs are the same, 1 if value1 is greater than value2, -1 otherwise.
     * Stack: (value1, value2) -> (result).
     */
    fun lcmp(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LCMP_INSTRUCTION)
        return this
    }

    /**
     * lconst_0: push 0L (the number zero with type long) onto the stack.
     * Stack: () -> (0L).
     */
    fun lconst_0(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LCONST_0_INSTRUCTION)
        return this
    }

    /**
     * lconst_1: push 1L (the number one with type long) onto the stack.
     * Stack: () -> (1L).
     */
    fun lconst_1(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LCONST_1_INSTRUCTION)
        return this
    }

    /**
     * ldc: push a access #index from a access pool (String, int, float, Class, java.lang
     * .invoke.MethodType, or java.lang.invoke.MethodHandle) onto the stack.
     * ldc_w: push a access #index from a access pool (String, int, float, Class, kotlin.lang
     * .invoke.MethodType, or kotlin.lang.invoke.MethodHandle) onto the stack (wide index is
     * constructed as indexbyte1 << 8 + indexbyte2).
     * ldc2_w: push a access #index from a access pool (double or long) onto the stack (wide
     * index is constructed as indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    fun ldc(entry: ConstantPoolEntry): RawCodeBuilder {
        instructions.add(LdcInstruction(entry))
        return this
    }

    /**
     * ldiv: divide two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun ldiv(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LDIV_INSTRUCTION)
        return this
    }

    /**
     * lload_0: load a long value from a local variable 0.
     * lload: load a long value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    fun lload(index: Int): RawCodeBuilder {
        load(OpCodes.LLOAD,
                InstructionConstants.LLOAD_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * lmul: multiply two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun lmul(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LMUL_INSTRUCTION)
        return this
    }

    /**
     * lneg: negate a long.
     * Stack: (value) -> (result).
     */
    fun lneg(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LNEG_INSTRUCTION)
        return this
    }

    /**
     * lookupswitch: a target address is looked up from a table using a key and execution
     * continues from the instruction at that address.
     * Other bytes (count: 8+): <0–3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs....
     * Stack: (key) -> ().
     */
    fun lookupswitch(defaultCase: Instruction,
                     instructionByCase: SortedMap<Int, Instruction>): RawCodeBuilder {
        instructions.add(LookupSwitchInstruction(paddingHelper, defaultCase,
                instructionByCase))
        /*
        }*/return this
    }

    /**
     * lor: bitwise OR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun lor(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LOR_INSTRUCTION)
        return this
    }

    /**
     * lrem: remainder of division of two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun lrem(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LREM_INSTRUCTION)
        return this
    }

    /**
     * lreturn: return a long value.
     * Stack: (value) -> ([empty]).
     */
    fun lreturn(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LRETURN_INSTRUCTION)
        return this
    }

    /**
     * lshl: bitwise shift left of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    fun lshl(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LSHL_INSTRUCTION)
        return this
    }

    /**
     * lshr: bitwise shift right of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    fun lshr(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LSHR_INSTRUCTION)
        return this
    }

    /**
     * lstore_0: store a long value in a local variable 0.
     * lstore: store a long value in a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    fun lstore(index: Int): RawCodeBuilder {
        store(OpCodes.LSTORE,
                InstructionConstants.LSTORE_INSTRUCTIONS,
                index)
        return this
    }

    /**
     * lsub: subtract two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun lsub(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LSUB_INSTRUCTION)
        return this
    }

    /**
     * lushr: bitwise shift right of a long value1 by int value2 positions, unsigned.
     * Stack: (value1, value2) -> (result).
     */
    fun lushr(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LUSHR_INSTRUCTION)
        return this
    }

    /**
     * lxor: bitwise XOR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    fun lxor(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.LXOR_INSTRUCTION)
        return this
    }

    /**
     * monitorenter: enter monitor for object ("grab the lock" – start of synchronized() section).
     * Stack: (objectref) -> ().
     */
    fun monitorenter(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.MONITORENTER_INSTRUCTION)
        return this
    }

    /**
     * monitorexit: exit monitor for object ("release the lock" – end of synchronized() section).
     * Stack: (objectref) -> ().
     */
    fun monitorexit(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.MONITOREXIT_INSTRUCTION)
        return this
    }

    /**
     * multianewarray: create a new array of dimensions dimensions of type identified by class
     * reference in access pool index (indexbyte1 << 8 + indexbyte2); the sizes of each
     * dimension is identified by count1, [count2, etc.].
     * Other bytes (count: 3): indexbyte1, indexbyte2, dimensions.
     * Stack: (count1, [count2,...]) -> (arrayref).
     */
    fun multianewarray(classRef: PlainClassRef, dimensions: Int): RawCodeBuilder {
        instructions.add(MultiNewArrayInstruction(classRef, dimensions))
        return this
    }

    /**
     * new: create new object of type identified by class reference in access pool index
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (objectref).
     */
    fun new_(classRef: PlainClassRef): RawCodeBuilder {
        instructions.add(NewInstruction(classRef))
        return this
    }

    /**
     * newarray: create new array with count elements of primitive type identified by atype.
     * Other bytes (count: 1): atype.
     * Stack: (count) -> (arrayref).
     */
    fun newarray(type: PrimitiveValueType): RawCodeBuilder {
        instructions.add(NewArrayInstruction(type))
        return this
    }

    /**
     * nop: perform no operation.
     */
    fun nop(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.NOP_INSTRUCTION)
        return this
    }

    /**
     * pop: discard the top value on the stack.
     * Stack: (value) -> ().
     */
    fun pop(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.POP_INSTRUCTION)
        return this
    }

    /**
     * pop2: discard the top two values on the stack (or one value, if it is a double or long).
     * Stack: ({value2, value1}) -> ().
     */
    fun pop2(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.POP2_INSTRUCTION)
        return this
    }

    /**
     * putfield: set field to value in an object objectref, where the field is identified by a
     * field reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, value) -> ().
     */
    fun putfield(entry: ConstantPoolEntry): RawCodeBuilder {
        instructions.add(
                PutFieldInstruction(OpCodes.PUTFIELD, entry))
        return this
    }

    /**
     * putstatic: set static field to value in a class, where the field is identified by a field
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (value) -> ().
     */
    fun putstatic(entry: ConstantPoolEntry): RawCodeBuilder {
        instructions.add(
                PutStaticInstruction(OpCodes.PUTSTATIC, entry))
        return this
    }

    /**
     * ret: continue execution from address taken from a local variable #index (the asymmetry
     * with jsr is intentional).
     * Other bytes (count: 1): index.
     */
    fun ret(index: Int): RawCodeBuilder {
        if (index < BytecodeHelper.BYTE_MAX) {
            instructions.add(RetInstruction(index))
        } else {
            instructions.add(WideRetInstruction(index))
        }
        return this
    }

    /**
     * return: return void from method.
     * Stack: () -> ([empty]).
     */
    fun return_(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.RETURN_INSTRUCTION)
        return this
    }

    /**
     * saload: load short from array.
     * Stack: (arrayref, index) -> (value).
     */
    fun saload(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.SALOAD_INSTRUCTION)
        return this
    }

    /**
     * sastore: store short to array.
     * Stack: (arrayref, index, value) -> ().
     */
    fun sastore(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.SASTORE_INSTRUCTION)
        return this
    }

    /**
     * sipush: push a short onto the stack as an integer value.
     * Other bytes (count: 2): byte1, byte2.
     * Stack: () -> (value).
     */
    fun sipush(value: Short): RawCodeBuilder {
        instructions.add(SiPushInstruction(value.toInt()))
        return this
    }

    /**
     * swap: swaps two top words on the stack (note that value1 and value2 must not be double or
     * long).
     * Stack: (value2, value1) -> (value1, value2).
     */
    fun swap(): RawCodeBuilder {
        instructions.add(
                InstructionConstants.SWAP_INSTRUCTION)
        return this
    }

    /**
     * tableswitch: continue execution from an address in the table at offset index.
     * Other bytes (count: 16+): [0–3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3,
     * highbyte4, jump offsets....
     * Stack: (index) -> ().
     */
    fun tableswitch(defaultCase: Instruction,
                    low: Int,
                    instructions: MutableList<Instruction>): RawCodeBuilder {
        this.instructions.add(TableSwitchInstruction(paddingHelper, defaultCase, low,
                instructions))
        return this
    }

    fun label(label: Label): RawCodeBuilder {
        instructions.add(MockLabelInstruction(label))
        return this
    }

    fun build(): Instruction {
        return BlockInstruction(instructions)
    }

    companion object {
        fun instance(): RawCodeBuilder {
            return RawCodeBuilder(PaddingHelper())
        }
    }

    init {
        instructions = ArrayList()
    }
}