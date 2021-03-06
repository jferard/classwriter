/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
package com.github.jferard.classwriter

import java.lang.IllegalArgumentException

/**
 * Constants for the bytecode. Taken from https://en.wikipedia
 * .org/wiki/Java_bytecode_instruction_listings
 */
object OpCodes {
    /**
     * aaload: load onto the stack a reference from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val AALOAD = 0x32
    /**
     * aastore: store into a reference in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val AASTORE = 0x53
    /**
     * aconst_null: push a null reference onto the stack.
     * Stack: () -> (null).
     */
    const val ACONST_NULL = 0x1
    /**
     * aload: load a reference onto the stack from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (objectref).
     */
    const val ALOAD = 0x19
    /**
     * aload_0: load a reference onto the stack from local variable 0.
     * Stack: () -> (objectref).
     */
    const val ALOAD_0 = 0x2a
    /**
     * aload_1: load a reference onto the stack from local variable 1.
     * Stack: () -> (objectref).
     */
    const val ALOAD_1 = 0x2b
    /**
     * aload_2: load a reference onto the stack from local variable 2.
     * Stack: () -> (objectref).
     */
    const val ALOAD_2 = 0x2c
    /**
     * aload_3: load a reference onto the stack from local variable 3.
     * Stack: () -> (objectref).
     */
    const val ALOAD_3 = 0x2d
    /**
     * anewarray: create a new array of references of length count and component type identified
     * by the class reference index (indexbyte1 << 8 + indexbyte2) in the access pool.
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (count) -> (arrayref).
     */
    const val ANEWARRAY = 0xbd
    /**
     * areturn: return a reference from a method.
     * Stack: (objectref) -> ([empty]).
     */
    const val ARETURN = 0xb0
    /**
     * arraylength: get the length of an array.
     * Stack: (arrayref) -> (length).
     */
    const val ARRAYLENGTH = 0xbe
    /**
     * astore: store a reference into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (objectref) -> ().
     */
    const val ASTORE = 0x3a
    /**
     * astore_0: store a reference into local variable 0.
     * Stack: (objectref) -> ().
     */
    const val ASTORE_0 = 0x4b
    /**
     * astore_1: store a reference into local variable 1.
     * Stack: (objectref) -> ().
     */
    const val ASTORE_1 = 0x4c
    /**
     * astore_2: store a reference into local variable 2.
     * Stack: (objectref) -> ().
     */
    const val ASTORE_2 = 0x4d
    /**
     * astore_3: store a reference into local variable 3.
     * Stack: (objectref) -> ().
     */
    const val ASTORE_3 = 0x4e
    /**
     * athrow: throws an error or exception (notice that the rest of the stack is cleared,
     * leaving only a reference to the Throwable).
     * Stack: (objectref) -> ([empty], objectref).
     */
    const val ATHROW = 0xbf
    /**
     * baload: load a byte or Boolean value from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val BALOAD = 0x33
    /**
     * bastore: store a byte or Boolean value into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val BASTORE = 0x54
    /**
     * bipush: push a byte onto the stack as an integer value.
     * Other bytes (count: 1): byte.
     * Stack: () -> (value).
     */
    const val BIPUSH = 0x10
    /**
     * breakpoint: reserved for breakpoints in Java debuggers; should not appear in any class file.
     */
    const val BREAKPOINT = 0xca
    /**
     * caload: load a char from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val CALOAD = 0x34
    /**
     * castore: store a char into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val CASTORE = 0x55
    /**
     * checkcast: checks whether an objectref is of a certain type, the class reference of which
     * is in the access pool at index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (objectref).
     */
    const val CHECKCAST = 0xc0
    /**
     * d2f: convert a double to a float.
     * Stack: (value) -> (result).
     */
    const val D2F = 0x90
    /**
     * d2i: convert a double to an int.
     * Stack: (value) -> (result).
     */
    const val D2I = 0x8e
    /**
     * d2l: convert a double to a long.
     * Stack: (value) -> (result).
     */
    const val D2L = 0x8f
    /**
     * dadd: add two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DADD = 0x63
    /**
     * daload: load a double from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val DALOAD = 0x31
    /**
     * dastore: store a double into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val DASTORE = 0x52
    /**
     * dcmpg: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DCMPG = 0x98
    /**
     * dcmpl: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DCMPL = 0x97
    /**
     * dconst_0: push the access 0.0 (a double) onto the stack.
     * Stack: () -> (0.0).
     */
    const val DCONST_0 = 0x0e
    /**
     * dconst_1: push the access 1.0 (a double) onto the stack.
     * Stack: () -> (1.0).
     */
    const val DCONST_1 = 0x0f
    /**
     * ddiv: divide two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DDIV = 0x6f
    /**
     * dload: load a double value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    const val DLOAD = 0x18
    /**
     * dload_0: load a double from local variable 0.
     * Stack: () -> (value).
     */
    const val DLOAD_0 = 0x26
    /**
     * dload_1: load a double from local variable 1.
     * Stack: () -> (value).
     */
    const val DLOAD_1 = 0x27
    /**
     * dload_2: load a double from local variable 2.
     * Stack: () -> (value).
     */
    const val DLOAD_2 = 0x28
    /**
     * dload_3: load a double from local variable 3.
     * Stack: () -> (value).
     */
    const val DLOAD_3 = 0x29
    /**
     * dmul: multiply two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DMUL = 0x6b
    /**
     * dneg: negate a double.
     * Stack: (value) -> (result).
     */
    const val DNEG = 0x77
    /**
     * drem: get the remainder from a division between two doubles.
     * Stack: (value1, value2) -> (result).
     */
    const val DREM = 0x73
    /**
     * dreturn: return a double from a method.
     * Stack: (value) -> ([empty]).
     */
    const val DRETURN = 0xaf
    /**
     * dstore: store a double value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    const val DSTORE = 0x39
    /**
     * dstore_0: store a double into local variable 0.
     * Stack: (value) -> ().
     */
    const val DSTORE_0 = 0x47
    /**
     * dstore_1: store a double into local variable 1.
     * Stack: (value) -> ().
     */
    const val DSTORE_1 = 0x48
    /**
     * dstore_2: store a double into local variable 2.
     * Stack: (value) -> ().
     */
    const val DSTORE_2 = 0x49
    /**
     * dstore_3: store a double into local variable 3.
     * Stack: (value) -> ().
     */
    const val DSTORE_3 = 0x4a
    /**
     * dsub: subtract a double from another.
     * Stack: (value1, value2) -> (result).
     */
    const val DSUB = 0x67
    /**
     * dup: duplicate the value on top of the stack. value1 and
     * value2 must not be of the type double or long.
     * Stack: (value) -> (value, value).
     */
    const val DUP = 0x59
    /**
     * dup_x1: insert a copy of the top value into the stack two values from the top. value1 and
     * value2 must not be of the type double or long..
     * Stack: (value2, value1) -> (value1, value2, value1).
     */
    const val DUP_X1 = 0x5a
    /**
     * dup_x2: insert a copy of the top value into the stack two (if value2 is double or long it
     * takes up the entry of value3, too) or three values (if value2 is neither double nor long)
     * from the top.
     * Stack: (value3, value2, value1) -> (value1, value3, value2, value1).
     */
    const val DUP_X2 = 0x5b
    /**
     * dup2: duplicate top two stack words (two values, if value1 is not double nor long; a
     * single value, if value1 is double or long).
     * Stack: ({value2, value1}) -> ({value2, value1}, {value2, value1}).
     */
    const val DUP2 = 0x5c
    /**
     * dup2_x1: duplicate two words and insert beneath third word (see explanation above).
     * Stack: (value3, {value2, value1}) -> ({value2, value1}, value3, {value2, value1}).
     */
    const val DUP2_X1 = 0x5d
    /**
     * dup2_x2: duplicate two words and insert beneath fourth word.
     * Stack: ({value4, value3}, {value2, value1}) -> ({value2, value1}, {value4, value3},
     * {value2, value1}).
     */
    const val DUP2_X2 = 0x5e
    /**
     * f2d: convert a float to a double.
     * Stack: (value) -> (result).
     */
    const val F2D = 0x8d
    /**
     * f2i: convert a float to an int.
     * Stack: (value) -> (result).
     */
    const val F2I = 0x8b
    /**
     * f2l: convert a float to a long.
     * Stack: (value) -> (result).
     */
    const val F2L = 0x8c
    /**
     * fadd: add two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FADD = 0x62
    /**
     * faload: load a float from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val FALOAD = 0x30
    /**
     * fastore: store a float in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val FASTORE = 0x51
    /**
     * fcmpg: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FCMPG = 0x96
    /**
     * fcmpl: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FCMPL = 0x95
    /**
     * fconst_0: push 0.0f on the stack.
     * Stack: () -> (0.0f).
     */
    const val FCONST_0 = 0x0b
    /**
     * fconst_1: push 1.0f on the stack.
     * Stack: () -> (1.0f).
     */
    const val FCONST_1 = 0x0c
    /**
     * fconst_2: push 2.0f on the stack.
     * Stack: () -> (2.0f).
     */
    const val FCONST_2 = 0x0d
    /**
     * fdiv: divide two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FDIV = 0x6e
    /**
     * fload: load a float value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    const val FLOAD = 0x17
    /**
     * fload_0: load a float value from local variable 0.
     * Stack: () -> (value).
     */
    const val FLOAD_0 = 0x22
    /**
     * fload_1: load a float value from local variable 1.
     * Stack: () -> (value).
     */
    const val FLOAD_1 = 0x23
    /**
     * fload_2: load a float value from local variable 2.
     * Stack: () -> (value).
     */
    const val FLOAD_2 = 0x24
    /**
     * fload_3: load a float value from local variable 3.
     * Stack: () -> (value).
     */
    const val FLOAD_3 = 0x25
    /**
     * fmul: multiply two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FMUL = 0x6a
    /**
     * fneg: negate a float.
     * Stack: (value) -> (result).
     */
    const val FNEG = 0x76
    /**
     * frem: get the remainder from a division between two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FREM = 0x72
    /**
     * freturn: return a float.
     * Stack: (value) -> ([empty]).
     */
    const val FRETURN = 0xae
    /**
     * fstore: store a float value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    const val FSTORE = 0x38
    /**
     * fstore_0: store a float value into local variable 0.
     * Stack: (value) -> ().
     */
    const val FSTORE_0 = 0x43
    /**
     * fstore_1: store a float value into local variable 1.
     * Stack: (value) -> ().
     */
    const val FSTORE_1 = 0x44
    /**
     * fstore_2: store a float value into local variable 2.
     * Stack: (value) -> ().
     */
    const val FSTORE_2 = 0x45
    /**
     * fstore_3: store a float value into local variable 3.
     * Stack: (value) -> ().
     */
    const val FSTORE_3 = 0x46
    /**
     * fsub: subtract two floats.
     * Stack: (value1, value2) -> (result).
     */
    const val FSUB = 0x66
    /**
     * getfield: get a field value of an object objectref, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (value).
     */
    const val GETFIELD = 0xb4
    /**
     * getstatic: get a static field value of a class, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    const val GETSTATIC = 0xb2
    /**
     * goto: goes to another instruction at branchoffset (signed short constructed from unsigned
     * bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     */
    const val GOTO = 0xa7
    /**
     * goto_w: goes to another instruction at branchoffset (signed int constructed from unsigned
     * bytes branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4).
     * Other bytes (count: 4): branchbyte1, branchbyte2, branchbyte3, branchbyte4.
     */
    const val GOTO_W = 0xc8
    /**
     * i2b: convert an int into a byte.
     * Stack: (value) -> (result).
     */
    const val I2B = 0x91
    /**
     * i2c: convert an int into a character.
     * Stack: (value) -> (result).
     */
    const val I2C = 0x92
    /**
     * i2d: convert an int into a double.
     * Stack: (value) -> (result).
     */
    const val I2D = 0x87
    /**
     * i2f: convert an int into a float.
     * Stack: (value) -> (result).
     */
    const val I2F = 0x86
    /**
     * i2l: convert an int into a long.
     * Stack: (value) -> (result).
     */
    const val I2L = 0x85
    /**
     * i2s: convert an int into a short.
     * Stack: (value) -> (result).
     */
    const val I2S = 0x93
    /**
     * iadd: add two ints.
     * Stack: (value1, value2) -> (result).
     */
    const val IADD = 0x60
    /**
     * iaload: load an int from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val IALOAD = 0x2e
    /**
     * iand: perform a bitwise AND on two integers.
     * Stack: (value1, value2) -> (result).
     */
    const val IAND = 0x7e
    /**
     * iastore: store an int into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val IASTORE = 0x4f
    /**
     * iconst_m1: load the int value −1 onto the stack.
     * Stack: () -> (-1).
     */
    const val ICONST_M1 = 0x2
    /**
     * iconst_0: load the int value 0 onto the stack.
     * Stack: () -> (0).
     */
    const val ICONST_0 = 0x3
    /**
     * iconst_1: load the int value 1 onto the stack.
     * Stack: () -> (1).
     */
    const val ICONST_1 = 0x4
    /**
     * iconst_2: load the int value 2 onto the stack.
     * Stack: () -> (2).
     */
    const val ICONST_2 = 0x5
    /**
     * iconst_3: load the int value 3 onto the stack.
     * Stack: () -> (3).
     */
    const val ICONST_3 = 0x6
    /**
     * iconst_4: load the int value 4 onto the stack.
     * Stack: () -> (4).
     */
    const val ICONST_4 = 0x7
    /**
     * iconst_5: load the int value 5 onto the stack.
     * Stack: () -> (5).
     */
    const val ICONST_5 = 0x8
    /**
     * idiv: divide two integers.
     * Stack: (value1, value2) -> (result).
     */
    const val IDIV = 0x6c
    /**
     * if_acmpeq: if references are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ACMPEQ = 0xa5
    /**
     * if_acmpne: if references are not equal, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ACMPNE = 0xa6
    /**
     * if_icmpeq: if ints are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPEQ = 0x9f
    /**
     * if_icmpge: if value1 is greater than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPGE = 0xa2
    /**
     * if_icmpgt: if value1 is greater than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPGT = 0xa3
    /**
     * if_icmple: if value1 is less than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPLE = 0xa4
    /**
     * if_icmplt: if value1 is less than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPLT = 0xa1
    /**
     * if_icmpne: if ints are not equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    const val IF_ICMPNE = 0xa0
    /**
     * ifeq: if value is 0, branch to instruction at branchoffset (signed short constructed from
     * unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFEQ = 0x99
    /**
     * ifge: if value is greater than or equal to 0, branch to instruction at branchoffset
     * (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFGE = 0x9c
    /**
     * ifgt: if value is greater than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFGT = 0x9d
    /**
     * ifle: if value is less than or equal to 0, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFLE = 0x9e
    /**
     * iflt: if value is less than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFLT = 0x9b
    /**
     * ifne: if value is not 0, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFNE = 0x9a
    /**
     * ifnonnull: if value is not null, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFNONNULL = 0xc7
    /**
     * ifnull: if value is null, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    const val IFNULL = 0xc6
    /**
     * iinc: increment local variable #index by signed byte const.
     * Other bytes (count: 2): index, const.
     */
    const val IINC = 0x84
    /**
     * iload: load an int value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    const val ILOAD = 0x15
    /**
     * iload_0: load an int value from local variable 0.
     * Stack: () -> (value).
     */
    const val ILOAD_0 = 0x1a
    /**
     * iload_1: load an int value from local variable 1.
     * Stack: () -> (value).
     */
    const val ILOAD_1 = 0x1b
    /**
     * iload_2: load an int value from local variable 2.
     * Stack: () -> (value).
     */
    const val ILOAD_2 = 0x1c
    /**
     * iload_3: load an int value from local variable 3.
     * Stack: () -> (value).
     */
    const val ILOAD_3 = 0x1d
    /**
     * impdep1: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    const val IMPDEP1 = 0xfe
    /**
     * impdep2: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    const val IMPDEP2 = 0xff
    /**
     * imul: multiply two integers.
     * Stack: (value1, value2) -> (result).
     */
    const val IMUL = 0x68
    /**
     * ineg: negate int.
     * Stack: (value) -> (result).
     */
    const val INEG = 0x74
    /**
     * instanceof: determines if an object objectref is of a given type, identified by class
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (result).
     */
    const val INSTANCEOF = 0xc1
    /**
     * invokedynamic: invokes a dynamic method and puts the result on the stack (might be void);
     * the method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, 0, 0.
     * Stack: ([arg1, [arg2 ...]]) -> (result).
     */
    const val INVOKEDYNAMIC = 0xba
    /**
     * invokeinterface: invokes an interface method on object objectref and puts the result on
     * the stack (might be void); the interface method is identified by method reference index in
     * access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, count, 0.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    const val INVOKEINTERFACE = 0xb9
    /**
     * invokespecial: invoke instance method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    const val INVOKESPECIAL = 0xb7
    /**
     * invokestatic: invoke a static method and puts the result on the stack (might be void); the
     * method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: ([arg1, arg2, ...]) -> (result).
     */
    const val INVOKESTATIC = 0xb8
    /**
     * invokevirtual: invoke virtual method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    const val INVOKEVIRTUAL = 0xb6
    /**
     * ior: bitwise int OR.
     * Stack: (value1, value2) -> (result).
     */
    const val IOR = 0x80
    /**
     * irem: logical int remainder.
     * Stack: (value1, value2) -> (result).
     */
    const val IREM = 0x70
    /**
     * ireturn: return an integer from a method.
     * Stack: (value) -> ([empty]).
     */
    const val IRETURN = 0xac
    /**
     * ishl: int shift left.
     * Stack: (value1, value2) -> (result).
     */
    const val ISHL = 0x78
    /**
     * ishr: int arithmetic shift right.
     * Stack: (value1, value2) -> (result).
     */
    const val ISHR = 0x7a
    /**
     * istore: store int value into variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    const val ISTORE = 0x36
    /**
     * istore_0: store int value into variable 0.
     * Stack: (value) -> ().
     */
    const val ISTORE_0 = 0x3b
    /**
     * istore_1: store int value into variable 1.
     * Stack: (value) -> ().
     */
    const val ISTORE_1 = 0x3c
    /**
     * istore_2: store int value into variable 2.
     * Stack: (value) -> ().
     */
    const val ISTORE_2 = 0x3d
    /**
     * istore_3: store int value into variable 3.
     * Stack: (value) -> ().
     */
    const val ISTORE_3 = 0x3e
    /**
     * isub: int subtract.
     * Stack: (value1, value2) -> (result).
     */
    const val ISUB = 0x64
    /**
     * iushr: int logical shift right.
     * Stack: (value1, value2) -> (result).
     */
    const val IUSHR = 0x7c
    /**
     * ixor: int xor.
     * Stack: (value1, value2) -> (result).
     */
    const val IXOR = 0x82
    /**
     * jsr: jump to subroutine at branchoffset (signed short constructed from unsigned bytes
     * branchbyte1 << 8 + branchbyte2) and place the return address on the stack.
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: () -> (address).
     */
    const val JSR = 0xa8
    /**
     * jsr_w: jump to subroutine at branchoffset (signed int constructed from unsigned bytes
     * branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4) and place the
     * return address on the stack.
     * Other bytes (count: 4): branchbyte1, branchbyte2, branchbyte3, branchbyte4.
     * Stack: () -> (address).
     */
    const val JSR_W = 0xc9
    /**
     * l2d: convert a long to a double.
     * Stack: (value) -> (result).
     */
    const val L2D = 0x8a
    /**
     * l2f: convert a long to a float.
     * Stack: (value) -> (result).
     */
    const val L2F = 0x89
    /**
     * l2i: convert a long to a int.
     * Stack: (value) -> (result).
     */
    const val L2I = 0x88
    /**
     * ladd: add two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LADD = 0x61
    /**
     * laload: load a long from an array.
     * Stack: (arrayref, index) -> (value).
     */
    const val LALOAD = 0x2f
    /**
     * land: bitwise AND of two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LAND = 0x7f
    /**
     * lastore: store a long to an array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val LASTORE = 0x50
    /**
     * lcmp: push 0 if the two longs are the same, 1 if value1 is greater than value2, -1 otherwise.
     * Stack: (value1, value2) -> (result).
     */
    const val LCMP = 0x94
    /**
     * lconst_0: push 0L (the number zero with type long) onto the stack.
     * Stack: () -> (0L).
     */
    const val LCONST_0 = 0x9
    /**
     * lconst_1: push 1L (the number one with type long) onto the stack.
     * Stack: () -> (1L).
     */
    const val LCONST_1 = 0x0a
    /**
     * ldc: push a access #index from a access pool (String, int, float, Class, kotlin.lang
     * .invoke.MethodType, or kotlin.lang.invoke.MethodHandle) onto the stack.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    const val LDC = 0x12
    /**
     * ldc_w: push a access #index from a access pool (String, int, float, Class, kotlin.lang
     * .invoke.MethodType, or kotlin.lang.invoke.MethodHandle) onto the stack (wide index is
     * constructed as indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    const val LDC_W = 0x13
    /**
     * ldc2_w: push a access #index from a access pool (double or long) onto the stack (wide
     * index is constructed as indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    const val LDC2_W = 0x14
    /**
     * ldiv: divide two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LDIV = 0x6d
    /**
     * lload: load a long value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    const val LLOAD = 0x16
    /**
     * lload_0: load a long value from a local variable 0.
     * Stack: () -> (value).
     */
    const val LLOAD_0 = 0x1e
    /**
     * lload_1: load a long value from a local variable 1.
     * Stack: () -> (value).
     */
    const val LLOAD_1 = 0x1f
    /**
     * lload_2: load a long value from a local variable 2.
     * Stack: () -> (value).
     */
    const val LLOAD_2 = 0x20
    /**
     * lload_3: load a long value from a local variable 3.
     * Stack: () -> (value).
     */
    const val LLOAD_3 = 0x21
    /**
     * lmul: multiply two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LMUL = 0x69
    /**
     * lneg: negate a long.
     * Stack: (value) -> (result).
     */
    const val LNEG = 0x75
    /**
     * lookupswitch: a target address is looked up from a table using a key and execution
     * continues from the instruction at that address.
     * Other bytes (count: 8+): <0–3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs....
     * Stack: (key) -> ().
     */
    const val LOOKUPSWITCH = 0xab
    /**
     * lor: bitwise OR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LOR = 0x81
    /**
     * lrem: remainder of division of two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LREM = 0x71
    /**
     * lreturn: return a long value.
     * Stack: (value) -> ([empty]).
     */
    const val LRETURN = 0xad
    /**
     * lshl: bitwise shift left of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    const val LSHL = 0x79
    /**
     * lshr: bitwise shift right of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    const val LSHR = 0x7b
    /**
     * lstore: store a long value in a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    const val LSTORE = 0x37
    /**
     * lstore_0: store a long value in a local variable 0.
     * Stack: (value) -> ().
     */
    const val LSTORE_0 = 0x3f
    /**
     * lstore_1: store a long value in a local variable 1.
     * Stack: (value) -> ().
     */
    const val LSTORE_1 = 0x40
    /**
     * lstore_2: store a long value in a local variable 2.
     * Stack: (value) -> ().
     */
    const val LSTORE_2 = 0x41
    /**
     * lstore_3: store a long value in a local variable 3.
     * Stack: (value) -> ().
     */
    const val LSTORE_3 = 0x42
    /**
     * lsub: subtract two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LSUB = 0x65
    /**
     * lushr: bitwise shift right of a long value1 by int value2 positions, unsigned.
     * Stack: (value1, value2) -> (result).
     */
    const val LUSHR = 0x7d
    /**
     * lxor: bitwise XOR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    const val LXOR = 0x83
    /**
     * monitorenter: enter monitor for object ("grab the lock" – start of synchronized() section).
     * Stack: (objectref) -> ().
     */
    const val MONITORENTER = 0xc2
    /**
     * monitorexit: exit monitor for object ("release the lock" – end of synchronized() section).
     * Stack: (objectref) -> ().
     */
    const val MONITOREXIT = 0xc3
    /**
     * multianewarray: create a new array of dimensions dimensions of type identified by class
     * reference in access pool index (indexbyte1 << 8 + indexbyte2); the sizes of each
     * dimension is identified by count1, [count2, etc.].
     * Other bytes (count: 3): indexbyte1, indexbyte2, dimensions.
     * Stack: (count1, [count2,...]) -> (arrayref).
     */
    const val MULTIANEWARRAY = 0xc5
    /**
     * new: create new object of type identified by class reference in access pool index
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (objectref).
     */
    const val NEW = 0xbb
    /**
     * newarray: create new array with count elements of primitive type identified by atype.
     * Other bytes (count: 1): atype.
     * Stack: (count) -> (arrayref).
     */
    const val NEWARRAY = 0xbc
    /**
     * nop: perform no operation.
     */
    const val NOP = 0x0
    /**
     * pop: discard the top value on the stack.
     * Stack: (value) -> ().
     */
    const val POP = 0x57
    /**
     * pop2: discard the top two values on the stack (or one value, if it is a double or long).
     * Stack: ({value2, value1}) -> ().
     */
    const val POP2 = 0x58
    /**
     * putfield: set field to value in an object objectref, where the field is identified by a
     * field reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, value) -> ().
     */
    const val PUTFIELD = 0xb5
    /**
     * putstatic: set static field to value in a class, where the field is identified by a field
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (value) -> ().
     */
    const val PUTSTATIC = 0xb3
    /**
     * ret: continue execution from address taken from a local variable #index (the asymmetry
     * with jsr is intentional).
     * Other bytes (count: 1): index.
     */
    const val RET = 0xa9
    /**
     * return: return void from method.
     * Stack: () -> ([empty]).
     */
    const val RETURN = 0xb1
    /**
     * saload: load short from array.
     * Stack: (arrayref, index) -> (value).
     */
    const val SALOAD = 0x35
    /**
     * sastore: store short to array.
     * Stack: (arrayref, index, value) -> ().
     */
    const val SASTORE = 0x56
    /**
     * sipush: push a short onto the stack as an integer value.
     * Other bytes (count: 2): byte1, byte2.
     * Stack: () -> (value).
     */
    const val SIPUSH = 0x11
    /**
     * swap: swaps two top words on the stack (note that value1 and value2 must not be double or
     * long).
     * Stack: (value2, value1) -> (value1, value2).
     */
    const val SWAP = 0x5f
    /**
     * tableswitch: continue execution from an address in the table at offset index.
     * Other bytes (count: 16+): [0–3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3,
     * highbyte4, jump offsets....
     * Stack: (index) -> ().
     */
    const val TABLESWITCH = 0xaa
    /**
     * wide: execute opcode, where opcode is either iload, fload, aload, lload, dload, istore,
     * fstore, astore, lstore, dstore, or ret, but assume the index is 16 bit; or execute iinc,
     * where the index is 16 bits and the access to increment by is a signed 16 bit short.
     * Other bytes (count: 3/5): opcode, indexbyte1, indexbyte2 or iinc, indexbyte1, indexbyte2,
     * countbyte1, countbyte2.
     */
    const val WIDE = 0xc4

    fun toString(opcode: Int) : String {
        return when(opcode) {
            AALOAD -> "OpCodes.AALOAD"
            AASTORE -> "OpCodes.AASTORE"
            ACONST_NULL -> "OpCodes.ACONST_NULL"
            ALOAD -> "OpCodes.ALOAD"
            ALOAD_0 -> "OpCodes.ALOAD_0"
            ALOAD_1 -> "OpCodes.ALOAD_1"
            ALOAD_2 -> "OpCodes.ALOAD_2"
            ALOAD_3 -> "OpCodes.ALOAD_3"
            ANEWARRAY -> "OpCodes.ANEWARRAY"
            ARETURN -> "OpCodes.ARETURN"
            ARRAYLENGTH -> "OpCodes.ARRAYLENGTH"
            ASTORE -> "OpCodes.ASTORE"
            ASTORE_0 -> "OpCodes.ASTORE_0"
            ASTORE_1 -> "OpCodes.ASTORE_1"
            ASTORE_2 -> "OpCodes.ASTORE_2"
            ASTORE_3 -> "OpCodes.ASTORE_3"
            ATHROW -> "OpCodes.ATHROW"
            BALOAD -> "OpCodes.BALOAD"
            BASTORE -> "OpCodes.BASTORE"
            BIPUSH -> "OpCodes.BIPUSH"
            BREAKPOINT -> "OpCodes.BREAKPOINT"
            CALOAD -> "OpCodes.CALOAD"
            CASTORE -> "OpCodes.CASTORE"
            CHECKCAST -> "OpCodes.CHECKCAST"
            D2F -> "OpCodes.D2F"
            D2I -> "OpCodes.D2I"
            D2L -> "OpCodes.D2L"
            DADD -> "OpCodes.DADD"
            DALOAD -> "OpCodes.DALOAD"
            DASTORE -> "OpCodes.DASTORE"
            DCMPG -> "OpCodes.DCMPG"
            DCMPL -> "OpCodes.DCMPL"
            DCONST_0 -> "OpCodes.DCONST_0"
            DCONST_1 -> "OpCodes.DCONST_1"
            DDIV -> "OpCodes.DDIV"
            DLOAD -> "OpCodes.DLOAD"
            DLOAD_0 -> "OpCodes.DLOAD_0"
            DLOAD_1 -> "OpCodes.DLOAD_1"
            DLOAD_2 -> "OpCodes.DLOAD_2"
            DLOAD_3 -> "OpCodes.DLOAD_3"
            DMUL -> "OpCodes.DMUL"
            DNEG -> "OpCodes.DNEG"
            DREM -> "OpCodes.DREM"
            DRETURN -> "OpCodes.DRETURN"
            DSTORE -> "OpCodes.DSTORE"
            DSTORE_0 -> "OpCodes.DSTORE_0"
            DSTORE_1 -> "OpCodes.DSTORE_1"
            DSTORE_2 -> "OpCodes.DSTORE_2"
            DSTORE_3 -> "OpCodes.DSTORE_3"
            DSUB -> "OpCodes.DSUB"
            DUP -> "OpCodes.DUP"
            DUP_X1 -> "OpCodes.DUP_X1"
            DUP_X2 -> "OpCodes.DUP_X2"
            DUP2 -> "OpCodes.DUP2"
            DUP2_X1 -> "OpCodes.DUP2_X1"
            DUP2_X2 -> "OpCodes.DUP2_X2"
            F2D -> "OpCodes.F2D"
            F2I -> "OpCodes.F2I"
            F2L -> "OpCodes.F2L"
            FADD -> "OpCodes.FADD"
            FALOAD -> "OpCodes.FALOAD"
            FASTORE -> "OpCodes.FASTORE"
            FCMPG -> "OpCodes.FCMPG"
            FCMPL -> "OpCodes.FCMPL"
            FCONST_0 -> "OpCodes.FCONST_0"
            FCONST_1 -> "OpCodes.FCONST_1"
            FCONST_2 -> "OpCodes.FCONST_2"
            FDIV -> "OpCodes.FDIV"
            FLOAD -> "OpCodes.FLOAD"
            FLOAD_0 -> "OpCodes.FLOAD_0"
            FLOAD_1 -> "OpCodes.FLOAD_1"
            FLOAD_2 -> "OpCodes.FLOAD_2"
            FLOAD_3 -> "OpCodes.FLOAD_3"
            FMUL -> "OpCodes.FMUL"
            FNEG -> "OpCodes.FNEG"
            FREM -> "OpCodes.FREM"
            FRETURN -> "OpCodes.FRETURN"
            FSTORE -> "OpCodes.FSTORE"
            FSTORE_0 -> "OpCodes.FSTORE_0"
            FSTORE_1 -> "OpCodes.FSTORE_1"
            FSTORE_2 -> "OpCodes.FSTORE_2"
            FSTORE_3 -> "OpCodes.FSTORE_3"
            FSUB -> "OpCodes.FSUB"
            GETFIELD -> "OpCodes.GETFIELD"
            GETSTATIC -> "OpCodes.GETSTATIC"
            GOTO -> "OpCodes.GOTO"
            GOTO_W -> "OpCodes.GOTO_W"
            I2B -> "OpCodes.I2B"
            I2C -> "OpCodes.I2C"
            I2D -> "OpCodes.I2D"
            I2F -> "OpCodes.I2F"
            I2L -> "OpCodes.I2L"
            I2S -> "OpCodes.I2S"
            IADD -> "OpCodes.IADD"
            IALOAD -> "OpCodes.IALOAD"
            IAND -> "OpCodes.IAND"
            IASTORE -> "OpCodes.IASTORE"
            ICONST_M1 -> "OpCodes.ICONST_M1"
            ICONST_0 -> "OpCodes.ICONST_0"
            ICONST_1 -> "OpCodes.ICONST_1"
            ICONST_2 -> "OpCodes.ICONST_2"
            ICONST_3 -> "OpCodes.ICONST_3"
            ICONST_4 -> "OpCodes.ICONST_4"
            ICONST_5 -> "OpCodes.ICONST_5"
            IDIV -> "OpCodes.IDIV"
            IF_ACMPEQ -> "OpCodes.IF_ACMPEQ"
            IF_ACMPNE -> "OpCodes.IF_ACMPNE"
            IF_ICMPEQ -> "OpCodes.IF_ICMPEQ"
            IF_ICMPGE -> "OpCodes.IF_ICMPGE"
            IF_ICMPGT -> "OpCodes.IF_ICMPGT"
            IF_ICMPLE -> "OpCodes.IF_ICMPLE"
            IF_ICMPLT -> "OpCodes.IF_ICMPLT"
            IF_ICMPNE -> "OpCodes.IF_ICMPNE"
            IFEQ -> "OpCodes.IFEQ"
            IFGE -> "OpCodes.IFGE"
            IFGT -> "OpCodes.IFGT"
            IFLE -> "OpCodes.IFLE"
            IFLT -> "OpCodes.IFLT"
            IFNE -> "OpCodes.IFNE"
            IFNONNULL -> "OpCodes.IFNONNULL"
            IFNULL -> "OpCodes.IFNULL"
            IINC -> "OpCodes.IINC"
            ILOAD -> "OpCodes.ILOAD"
            ILOAD_0 -> "OpCodes.ILOAD_0"
            ILOAD_1 -> "OpCodes.ILOAD_1"
            ILOAD_2 -> "OpCodes.ILOAD_2"
            ILOAD_3 -> "OpCodes.ILOAD_3"
            IMPDEP1 -> "OpCodes.IMPDEP1"
            IMPDEP2 -> "OpCodes.IMPDEP2"
            IMUL -> "OpCodes.IMUL"
            INEG -> "OpCodes.INEG"
            INSTANCEOF -> "OpCodes.INSTANCEOF"
            INVOKEDYNAMIC -> "OpCodes.INVOKEDYNAMIC"
            INVOKEINTERFACE -> "OpCodes.INVOKEINTERFACE"
            INVOKESPECIAL -> "OpCodes.INVOKESPECIAL"
            INVOKESTATIC -> "OpCodes.INVOKESTATIC"
            INVOKEVIRTUAL -> "OpCodes.INVOKEVIRTUAL"
            IOR -> "OpCodes.IOR"
            IREM -> "OpCodes.IREM"
            IRETURN -> "OpCodes.IRETURN"
            ISHL -> "OpCodes.ISHL"
            ISHR -> "OpCodes.ISHR"
            ISTORE -> "OpCodes.ISTORE"
            ISTORE_0 -> "OpCodes.ISTORE_0"
            ISTORE_1 -> "OpCodes.ISTORE_1"
            ISTORE_2 -> "OpCodes.ISTORE_2"
            ISTORE_3 -> "OpCodes.ISTORE_3"
            ISUB -> "OpCodes.ISUB"
            IUSHR -> "OpCodes.IUSHR"
            IXOR -> "OpCodes.IXOR"
            JSR -> "OpCodes.JSR"
            JSR_W -> "OpCodes.JSR_W"
            L2D -> "OpCodes.L2D"
            L2F -> "OpCodes.L2F"
            L2I -> "OpCodes.L2I"
            LADD -> "OpCodes.LADD"
            LALOAD -> "OpCodes.LALOAD"
            LAND -> "OpCodes.LAND"
            LASTORE -> "OpCodes.LASTORE"
            LCMP -> "OpCodes.LCMP"
            LCONST_0 -> "OpCodes.LCONST_0"
            LCONST_1 -> "OpCodes.LCONST_1"
            LDC -> "OpCodes.LDC"
            LDC_W -> "OpCodes.LDC_W"
            LDC2_W -> "OpCodes.LDC2_W"
            LDIV -> "OpCodes.LDIV"
            LLOAD -> "OpCodes.LLOAD"
            LLOAD_0 -> "OpCodes.LLOAD_0"
            LLOAD_1 -> "OpCodes.LLOAD_1"
            LLOAD_2 -> "OpCodes.LLOAD_2"
            LLOAD_3 -> "OpCodes.LLOAD_3"
            LMUL -> "OpCodes.LMUL"
            LNEG -> "OpCodes.LNEG"
            LOOKUPSWITCH -> "OpCodes.LOOKUPSWITCH"
            LOR -> "OpCodes.LOR"
            LREM -> "OpCodes.LREM"
            LRETURN -> "OpCodes.LRETURN"
            LSHL -> "OpCodes.LSHL"
            LSHR -> "OpCodes.LSHR"
            LSTORE -> "OpCodes.LSTORE"
            LSTORE_0 -> "OpCodes.LSTORE_0"
            LSTORE_1 -> "OpCodes.LSTORE_1"
            LSTORE_2 -> "OpCodes.LSTORE_2"
            LSTORE_3 -> "OpCodes.LSTORE_3"
            LSUB -> "OpCodes.LSUB"
            LUSHR -> "OpCodes.LUSHR"
            LXOR -> "OpCodes.LXOR"
            MONITORENTER -> "OpCodes.MONITORENTER"
            MONITOREXIT -> "OpCodes.MONITOREXIT"
            MULTIANEWARRAY -> "OpCodes.MULTIANEWARRAY"
            NEW -> "OpCodes.NEW"
            NEWARRAY -> "OpCodes.NEWARRAY"
            NOP -> "OpCodes.NOP"
            POP -> "OpCodes.POP"
            POP2 -> "OpCodes.POP2"
            PUTFIELD -> "OpCodes.PUTFIELD"
            PUTSTATIC -> "OpCodes.PUTSTATIC"
            RET -> "OpCodes.RET"
            RETURN -> "OpCodes.RETURN"
            SALOAD -> "OpCodes.SALOAD"
            SASTORE -> "OpCodes.SASTORE"
            SIPUSH -> "OpCodes.SIPUSH"
            SWAP -> "OpCodes.SWAP"
            TABLESWITCH -> "OpCodes.TABLESWITCH"
            WIDE -> "OpCodes.WIDE"
            else -> throw IllegalArgumentException("Unknown opcode: $opcode")
        }
    }
}