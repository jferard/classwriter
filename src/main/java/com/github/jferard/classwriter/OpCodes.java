

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

package com.github.jferard.classwriter;

/**
 * Constants for the bytecode. Taken from https://en.wikipedia
 * .org/wiki/Java_bytecode_instruction_listings
 */
public class OpCodes {
    /**
     * aaload: load onto the stack a reference from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int AALOAD = 0x32;

    /**
     * aastore: store into a reference in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int AASTORE = 0x53;

    /**
     * aconst_null: push a null reference onto the stack.
     * Stack: () -> (null).
     */
    public static final int ACONST_NULL = 0x1;

    /**
     * aload: load a reference onto the stack from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (objectref).
     */
    public static final int ALOAD = 0x19;

    /**
     * aload_0: load a reference onto the stack from local variable 0.
     * Stack: () -> (objectref).
     */
    public static final int ALOAD_0 = 0x2a;

    /**
     * aload_1: load a reference onto the stack from local variable 1.
     * Stack: () -> (objectref).
     */
    public static final int ALOAD_1 = 0x2b;

    /**
     * aload_2: load a reference onto the stack from local variable 2.
     * Stack: () -> (objectref).
     */
    public static final int ALOAD_2 = 0x2c;

    /**
     * aload_3: load a reference onto the stack from local variable 3.
     * Stack: () -> (objectref).
     */
    public static final int ALOAD_3 = 0x2d;

    /**
     * anewarray: create a new array of references of length count and component type identified
     * by the class reference index (indexbyte1 << 8 + indexbyte2) in the access pool.
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (count) -> (arrayref).
     */
    public static final int ANEWARRAY = 0xbd;

    /**
     * areturn: return a reference from a method.
     * Stack: (objectref) -> ([empty]).
     */
    public static final int ARETURN = 0xb0;

    /**
     * arraylength: get the length of an array.
     * Stack: (arrayref) -> (length).
     */
    public static final int ARRAYLENGTH = 0xbe;

    /**
     * astore: store a reference into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (objectref) -> ().
     */
    public static final int ASTORE = 0x3a;

    /**
     * astore_0: store a reference into local variable 0.
     * Stack: (objectref) -> ().
     */
    public static final int ASTORE_0 = 0x4b;

    /**
     * astore_1: store a reference into local variable 1.
     * Stack: (objectref) -> ().
     */
    public static final int ASTORE_1 = 0x4c;

    /**
     * astore_2: store a reference into local variable 2.
     * Stack: (objectref) -> ().
     */
    public static final int ASTORE_2 = 0x4d;

    /**
     * astore_3: store a reference into local variable 3.
     * Stack: (objectref) -> ().
     */
    public static final int ASTORE_3 = 0x4e;

    /**
     * athrow: throws an error or exception (notice that the rest of the stack is cleared,
     * leaving only a reference to the Throwable).
     * Stack: (objectref) -> ([empty], objectref).
     */
    public static final int ATHROW = 0xbf;

    /**
     * baload: load a byte or Boolean value from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int BALOAD = 0x33;

    /**
     * bastore: store a byte or Boolean value into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int BASTORE = 0x54;

    /**
     * bipush: push a byte onto the stack as an integer value.
     * Other bytes (count: 1): byte.
     * Stack: () -> (value).
     */
    public static final int BIPUSH = 0x10;

    /**
     * breakpoint: reserved for breakpoints in Java debuggers; should not appear in any class file.
     */
    public static final int BREAKPOINT = 0xca;

    /**
     * caload: load a char from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int CALOAD = 0x34;

    /**
     * castore: store a char into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int CASTORE = 0x55;

    /**
     * checkcast: checks whether an objectref is of a certain type, the class reference of which
     * is in the access pool at index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (objectref).
     */
    public static final int CHECKCAST = 0xc0;

    /**
     * d2f: convert a double to a float.
     * Stack: (value) -> (result).
     */
    public static final int D2F = 0x90;

    /**
     * d2i: convert a double to an int.
     * Stack: (value) -> (result).
     */
    public static final int D2I = 0x8e;

    /**
     * d2l: convert a double to a long.
     * Stack: (value) -> (result).
     */
    public static final int D2L = 0x8f;

    /**
     * dadd: add two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DADD = 0x63;

    /**
     * daload: load a double from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int DALOAD = 0x31;

    /**
     * dastore: store a double into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int DASTORE = 0x52;

    /**
     * dcmpg: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DCMPG = 0x98;

    /**
     * dcmpl: compare two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DCMPL = 0x97;

    /**
     * dconst_0: push the access 0.0 (a double) onto the stack.
     * Stack: () -> (0.0).
     */
    public static final int DCONST_0 = 0x0e;

    /**
     * dconst_1: push the access 1.0 (a double) onto the stack.
     * Stack: () -> (1.0).
     */
    public static final int DCONST_1 = 0x0f;

    /**
     * ddiv: divide two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DDIV = 0x6f;

    /**
     * dload: load a double value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    public static final int DLOAD = 0x18;

    /**
     * dload_0: load a double from local variable 0.
     * Stack: () -> (value).
     */
    public static final int DLOAD_0 = 0x26;

    /**
     * dload_1: load a double from local variable 1.
     * Stack: () -> (value).
     */
    public static final int DLOAD_1 = 0x27;

    /**
     * dload_2: load a double from local variable 2.
     * Stack: () -> (value).
     */
    public static final int DLOAD_2 = 0x28;

    /**
     * dload_3: load a double from local variable 3.
     * Stack: () -> (value).
     */
    public static final int DLOAD_3 = 0x29;

    /**
     * dmul: multiply two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DMUL = 0x6b;

    /**
     * dneg: negate a double.
     * Stack: (value) -> (result).
     */
    public static final int DNEG = 0x77;

    /**
     * drem: get the remainder from a division between two doubles.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DREM = 0x73;

    /**
     * dreturn: return a double from a method.
     * Stack: (value) -> ([empty]).
     */
    public static final int DRETURN = 0xaf;

    /**
     * dstore: store a double value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    public static final int DSTORE = 0x39;

    /**
     * dstore_0: store a double into local variable 0.
     * Stack: (value) -> ().
     */
    public static final int DSTORE_0 = 0x47;

    /**
     * dstore_1: store a double into local variable 1.
     * Stack: (value) -> ().
     */
    public static final int DSTORE_1 = 0x48;

    /**
     * dstore_2: store a double into local variable 2.
     * Stack: (value) -> ().
     */
    public static final int DSTORE_2 = 0x49;

    /**
     * dstore_3: store a double into local variable 3.
     * Stack: (value) -> ().
     */
    public static final int DSTORE_3 = 0x4a;

    /**
     * dsub: subtract a double from another.
     * Stack: (value1, value2) -> (result).
     */
    public static final int DSUB = 0x67;

    /**
     * dup: duplicate the value on top of the stack. value1 and
     * value2 must not be of the type double or long.
     * Stack: (value) -> (value, value).
     */
    public static final int DUP = 0x59;

    /**
     * dup_x1: insert a copy of the top value into the stack two values from the top. value1 and
     * value2 must not be of the type double or long..
     * Stack: (value2, value1) -> (value1, value2, value1).
     */
    public static final int DUP_X1 = 0x5a;

    /**
     * dup_x2: insert a copy of the top value into the stack two (if value2 is double or long it
     * takes up the entry of value3, too) or three values (if value2 is neither double nor long)
     * from the top.
     * Stack: (value3, value2, value1) -> (value1, value3, value2, value1).
     */
    public static final int DUP_X2 = 0x5b;

    /**
     * dup2: duplicate top two stack words (two values, if value1 is not double nor long; a
     * single value, if value1 is double or long).
     * Stack: ({value2, value1}) -> ({value2, value1}, {value2, value1}).
     */
    public static final int DUP2 = 0x5c;

    /**
     * dup2_x1: duplicate two words and insert beneath third word (see explanation above).
     * Stack: (value3, {value2, value1}) -> ({value2, value1}, value3, {value2, value1}).
     */
    public static final int DUP2_X1 = 0x5d;

    /**
     * dup2_x2: duplicate two words and insert beneath fourth word.
     * Stack: ({value4, value3}, {value2, value1}) -> ({value2, value1}, {value4, value3},
     * {value2, value1}).
     */
    public static final int DUP2_X2 = 0x5e;

    /**
     * f2d: convert a float to a double.
     * Stack: (value) -> (result).
     */
    public static final int F2D = 0x8d;

    /**
     * f2i: convert a float to an int.
     * Stack: (value) -> (result).
     */
    public static final int F2I = 0x8b;

    /**
     * f2l: convert a float to a long.
     * Stack: (value) -> (result).
     */
    public static final int F2L = 0x8c;

    /**
     * fadd: add two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FADD = 0x62;

    /**
     * faload: load a float from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int FALOAD = 0x30;

    /**
     * fastore: store a float in an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int FASTORE = 0x51;

    /**
     * fcmpg: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FCMPG = 0x96;

    /**
     * fcmpl: compare two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FCMPL = 0x95;

    /**
     * fconst_0: push 0.0f on the stack.
     * Stack: () -> (0.0f).
     */
    public static final int FCONST_0 = 0x0b;

    /**
     * fconst_1: push 1.0f on the stack.
     * Stack: () -> (1.0f).
     */
    public static final int FCONST_1 = 0x0c;

    /**
     * fconst_2: push 2.0f on the stack.
     * Stack: () -> (2.0f).
     */
    public static final int FCONST_2 = 0x0d;

    /**
     * fdiv: divide two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FDIV = 0x6e;

    /**
     * fload: load a float value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    public static final int FLOAD = 0x17;

    /**
     * fload_0: load a float value from local variable 0.
     * Stack: () -> (value).
     */
    public static final int FLOAD_0 = 0x22;

    /**
     * fload_1: load a float value from local variable 1.
     * Stack: () -> (value).
     */
    public static final int FLOAD_1 = 0x23;

    /**
     * fload_2: load a float value from local variable 2.
     * Stack: () -> (value).
     */
    public static final int FLOAD_2 = 0x24;

    /**
     * fload_3: load a float value from local variable 3.
     * Stack: () -> (value).
     */
    public static final int FLOAD_3 = 0x25;

    /**
     * fmul: multiply two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FMUL = 0x6a;

    /**
     * fneg: negate a float.
     * Stack: (value) -> (result).
     */
    public static final int FNEG = 0x76;

    /**
     * frem: get the remainder from a division between two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FREM = 0x72;

    /**
     * freturn: return a float.
     * Stack: (value) -> ([empty]).
     */
    public static final int FRETURN = 0xae;

    /**
     * fstore: store a float value into a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    public static final int FSTORE = 0x38;

    /**
     * fstore_0: store a float value into local variable 0.
     * Stack: (value) -> ().
     */
    public static final int FSTORE_0 = 0x43;

    /**
     * fstore_1: store a float value into local variable 1.
     * Stack: (value) -> ().
     */
    public static final int FSTORE_1 = 0x44;

    /**
     * fstore_2: store a float value into local variable 2.
     * Stack: (value) -> ().
     */
    public static final int FSTORE_2 = 0x45;

    /**
     * fstore_3: store a float value into local variable 3.
     * Stack: (value) -> ().
     */
    public static final int FSTORE_3 = 0x46;

    /**
     * fsub: subtract two floats.
     * Stack: (value1, value2) -> (result).
     */
    public static final int FSUB = 0x66;

    /**
     * getfield: get a field value of an object objectref, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (value).
     */
    public static final int GETFIELD = 0xb4;

    /**
     * getstatic: get a static field value of a class, where the field is identified by field
     * reference in the access pool index (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    public static final int GETSTATIC = 0xb2;

    /**
     * goto: goes to another instruction at branchoffset (signed short constructed from unsigned
     * bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     */
    public static final int GOTO = 0xa7;

    /**
     * goto_w: goes to another instruction at branchoffset (signed int constructed from unsigned
     * bytes branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4).
     * Other bytes (count: 4): branchbyte1, branchbyte2, branchbyte3, branchbyte4.
     */
    public static final int GOTO_W = 0xc8;

    /**
     * i2b: convert an int into a byte.
     * Stack: (value) -> (result).
     */
    public static final int I2B = 0x91;

    /**
     * i2c: convert an int into a character.
     * Stack: (value) -> (result).
     */
    public static final int I2C = 0x92;

    /**
     * i2d: convert an int into a double.
     * Stack: (value) -> (result).
     */
    public static final int I2D = 0x87;

    /**
     * i2f: convert an int into a float.
     * Stack: (value) -> (result).
     */
    public static final int I2F = 0x86;

    /**
     * i2l: convert an int into a long.
     * Stack: (value) -> (result).
     */
    public static final int I2L = 0x85;

    /**
     * i2s: convert an int into a short.
     * Stack: (value) -> (result).
     */
    public static final int I2S = 0x93;

    /**
     * iadd: add two ints.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IADD = 0x60;

    /**
     * iaload: load an int from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int IALOAD = 0x2e;

    /**
     * iand: perform a bitwise AND on two integers.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IAND = 0x7e;

    /**
     * iastore: store an int into an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int IASTORE = 0x4f;

    /**
     * iconst_m1: load the int value −1 onto the stack.
     * Stack: () -> (-1).
     */
    public static final int ICONST_M1 = 0x2;

    /**
     * iconst_0: load the int value 0 onto the stack.
     * Stack: () -> (0).
     */
    public static final int ICONST_0 = 0x3;

    /**
     * iconst_1: load the int value 1 onto the stack.
     * Stack: () -> (1).
     */
    public static final int ICONST_1 = 0x4;

    /**
     * iconst_2: load the int value 2 onto the stack.
     * Stack: () -> (2).
     */
    public static final int ICONST_2 = 0x5;

    /**
     * iconst_3: load the int value 3 onto the stack.
     * Stack: () -> (3).
     */
    public static final int ICONST_3 = 0x6;

    /**
     * iconst_4: load the int value 4 onto the stack.
     * Stack: () -> (4).
     */
    public static final int ICONST_4 = 0x7;

    /**
     * iconst_5: load the int value 5 onto the stack.
     * Stack: () -> (5).
     */
    public static final int ICONST_5 = 0x8;

    /**
     * idiv: divide two integers.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IDIV = 0x6c;

    /**
     * if_acmpeq: if references are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ACMPEQ = 0xa5;

    /**
     * if_acmpne: if references are not equal, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ACMPNE = 0xa6;

    /**
     * if_icmpeq: if ints are equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPEQ = 0x9f;

    /**
     * if_icmpge: if value1 is greater than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPGE = 0xa2;

    /**
     * if_icmpgt: if value1 is greater than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPGT = 0xa3;

    /**
     * if_icmple: if value1 is less than or equal to value2, branch to instruction at
     * branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPLE = 0xa4;

    /**
     * if_icmplt: if value1 is less than value2, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPLT = 0xa1;

    /**
     * if_icmpne: if ints are not equal, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value1, value2) -> ().
     */
    public static final int IF_ICMPNE = 0xa0;

    /**
     * ifeq: if value is 0, branch to instruction at branchoffset (signed short constructed from
     * unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFEQ = 0x99;

    /**
     * ifge: if value is greater than or equal to 0, branch to instruction at branchoffset
     * (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFGE = 0x9c;

    /**
     * ifgt: if value is greater than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFGT = 0x9d;

    /**
     * ifle: if value is less than or equal to 0, branch to instruction at branchoffset (signed
     * short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFLE = 0x9e;

    /**
     * iflt: if value is less than 0, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFLT = 0x9b;

    /**
     * ifne: if value is not 0, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFNE = 0x9a;

    /**
     * ifnonnull: if value is not null, branch to instruction at branchoffset (signed short
     * constructed from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFNONNULL = 0xc7;

    /**
     * ifnull: if value is null, branch to instruction at branchoffset (signed short constructed
     * from unsigned bytes branchbyte1 << 8 + branchbyte2).
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: (value) -> ().
     */
    public static final int IFNULL = 0xc6;

    /**
     * iinc: increment local variable #index by signed byte const.
     * Other bytes (count: 2): index, const.
     */
    public static final int IINC = 0x84;

    /**
     * iload: load an int value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    public static final int ILOAD = 0x15;

    /**
     * iload_0: load an int value from local variable 0.
     * Stack: () -> (value).
     */
    public static final int ILOAD_0 = 0x1a;

    /**
     * iload_1: load an int value from local variable 1.
     * Stack: () -> (value).
     */
    public static final int ILOAD_1 = 0x1b;

    /**
     * iload_2: load an int value from local variable 2.
     * Stack: () -> (value).
     */
    public static final int ILOAD_2 = 0x1c;

    /**
     * iload_3: load an int value from local variable 3.
     * Stack: () -> (value).
     */
    public static final int ILOAD_3 = 0x1d;

    /**
     * impdep1: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    public static final int IMPDEP1 = 0xfe;

    /**
     * impdep2: reserved for implementation-dependent operations within debuggers; should not
     * appear in any class file.
     */
    public static final int IMPDEP2 = 0xff;

    /**
     * imul: multiply two integers.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IMUL = 0x68;

    /**
     * ineg: negate int.
     * Stack: (value) -> (result).
     */
    public static final int INEG = 0x74;

    /**
     * instanceof: determines if an object objectref is of a given type, identified by class
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref) -> (result).
     */
    public static final int INSTANCEOF = 0xc1;

    /**
     * invokedynamic: invokes a dynamic method and puts the result on the stack (might be void);
     * the method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, 0, 0.
     * Stack: ([arg1, [arg2 ...]]) -> (result).
     */
    public static final int INVOKEDYNAMIC = 0xba;

    /**
     * invokeinterface: invokes an interface method on object objectref and puts the result on
     * the stack (might be void); the interface method is identified by method reference index in
     * access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 4): indexbyte1, indexbyte2, count, 0.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    public static final int INVOKEINTERFACE = 0xb9;

    /**
     * invokespecial: invoke instance method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    public static final int INVOKESPECIAL = 0xb7;

    /**
     * invokestatic: invoke a static method and puts the result on the stack (might be void); the
     * method is identified by method reference index in access pool (indexbyte1 << 8 +
     * indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: ([arg1, arg2, ...]) -> (result).
     */
    public static final int INVOKESTATIC = 0xb8;

    /**
     * invokevirtual: invoke virtual method on object objectref and puts the result on the stack
     * (might be void); the method is identified by method reference index in access pool
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, [arg1, arg2, ...]) -> (result).
     */
    public static final int INVOKEVIRTUAL = 0xb6;

    /**
     * ior: bitwise int OR.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IOR = 0x80;

    /**
     * irem: logical int remainder.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IREM = 0x70;

    /**
     * ireturn: return an integer from a method.
     * Stack: (value) -> ([empty]).
     */
    public static final int IRETURN = 0xac;

    /**
     * ishl: int shift left.
     * Stack: (value1, value2) -> (result).
     */
    public static final int ISHL = 0x78;

    /**
     * ishr: int arithmetic shift right.
     * Stack: (value1, value2) -> (result).
     */
    public static final int ISHR = 0x7a;

    /**
     * istore: store int value into variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    public static final int ISTORE = 0x36;

    /**
     * istore_0: store int value into variable 0.
     * Stack: (value) -> ().
     */
    public static final int ISTORE_0 = 0x3b;

    /**
     * istore_1: store int value into variable 1.
     * Stack: (value) -> ().
     */
    public static final int ISTORE_1 = 0x3c;

    /**
     * istore_2: store int value into variable 2.
     * Stack: (value) -> ().
     */
    public static final int ISTORE_2 = 0x3d;

    /**
     * istore_3: store int value into variable 3.
     * Stack: (value) -> ().
     */
    public static final int ISTORE_3 = 0x3e;

    /**
     * isub: int subtract.
     * Stack: (value1, value2) -> (result).
     */
    public static final int ISUB = 0x64;

    /**
     * iushr: int logical shift right.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IUSHR = 0x7c;

    /**
     * ixor: int xor.
     * Stack: (value1, value2) -> (result).
     */
    public static final int IXOR = 0x82;

    /**
     * jsr: jump to subroutine at branchoffset (signed short constructed from unsigned bytes
     * branchbyte1 << 8 + branchbyte2) and place the return address on the stack.
     * Other bytes (count: 2): branchbyte1, branchbyte2.
     * Stack: () -> (address).
     */
    public static final int JSR = 0xa8;

    /**
     * jsr_w: jump to subroutine at branchoffset (signed int constructed from unsigned bytes
     * branchbyte1 << 24 + branchbyte2 << 16 + branchbyte3 << 8 + branchbyte4) and place the
     * return address on the stack.
     * Other bytes (count: 4): branchbyte1, branchbyte2, branchbyte3, branchbyte4.
     * Stack: () -> (address).
     */
    public static final int JSR_W = 0xc9;

    /**
     * l2d: convert a long to a double.
     * Stack: (value) -> (result).
     */
    public static final int L2D = 0x8a;

    /**
     * l2f: convert a long to a float.
     * Stack: (value) -> (result).
     */
    public static final int L2F = 0x89;

    /**
     * l2i: convert a long to a int.
     * Stack: (value) -> (result).
     */
    public static final int L2I = 0x88;

    /**
     * ladd: add two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LADD = 0x61;

    /**
     * laload: load a long from an array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int LALOAD = 0x2f;

    /**
     * land: bitwise AND of two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LAND = 0x7f;

    /**
     * lastore: store a long to an array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int LASTORE = 0x50;

    /**
     * lcmp: push 0 if the two longs are the same, 1 if value1 is greater than value2, -1 otherwise.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LCMP = 0x94;

    /**
     * lconst_0: push 0L (the number zero with type long) onto the stack.
     * Stack: () -> (0L).
     */
    public static final int LCONST_0 = 0x9;

    /**
     * lconst_1: push 1L (the number one with type long) onto the stack.
     * Stack: () -> (1L).
     */
    public static final int LCONST_1 = 0x0a;

    /**
     * ldc: push a access #index from a access pool (String, int, float, Class, java.lang
     * .invoke.MethodType, or java.lang.invoke.MethodHandle) onto the stack.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    public static final int LDC = 0x12;

    /**
     * ldc_w: push a access #index from a access pool (String, int, float, Class, java.lang
     * .invoke.MethodType, or java.lang.invoke.MethodHandle) onto the stack (wide index is
     * constructed as indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    public static final int LDC_W = 0x13;

    /**
     * ldc2_w: push a access #index from a access pool (double or long) onto the stack (wide
     * index is constructed as indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (value).
     */
    public static final int LDC2_W = 0x14;

    /**
     * ldiv: divide two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LDIV = 0x6d;

    /**
     * lload: load a long value from a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: () -> (value).
     */
    public static final int LLOAD = 0x16;

    /**
     * lload_0: load a long value from a local variable 0.
     * Stack: () -> (value).
     */
    public static final int LLOAD_0 = 0x1e;

    /**
     * lload_1: load a long value from a local variable 1.
     * Stack: () -> (value).
     */
    public static final int LLOAD_1 = 0x1f;

    /**
     * lload_2: load a long value from a local variable 2.
     * Stack: () -> (value).
     */
    public static final int LLOAD_2 = 0x20;

    /**
     * lload_3: load a long value from a local variable 3.
     * Stack: () -> (value).
     */
    public static final int LLOAD_3 = 0x21;

    /**
     * lmul: multiply two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LMUL = 0x69;

    /**
     * lneg: negate a long.
     * Stack: (value) -> (result).
     */
    public static final int LNEG = 0x75;

    /**
     * lookupswitch: a target address is looked up from a table using a key and execution
     * continues from the instruction at that address.
     * Other bytes (count: 8+): <0–3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs....
     * Stack: (key) -> ().
     */
    public static final int LOOKUPSWITCH = 0xab;

    /**
     * lor: bitwise OR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LOR = 0x81;

    /**
     * lrem: remainder of division of two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LREM = 0x71;

    /**
     * lreturn: return a long value.
     * Stack: (value) -> ([empty]).
     */
    public static final int LRETURN = 0xad;

    /**
     * lshl: bitwise shift left of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LSHL = 0x79;

    /**
     * lshr: bitwise shift right of a long value1 by int value2 positions.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LSHR = 0x7b;

    /**
     * lstore: store a long value in a local variable #index.
     * Other bytes (count: 1): index.
     * Stack: (value) -> ().
     */
    public static final int LSTORE = 0x37;

    /**
     * lstore_0: store a long value in a local variable 0.
     * Stack: (value) -> ().
     */
    public static final int LSTORE_0 = 0x3f;

    /**
     * lstore_1: store a long value in a local variable 1.
     * Stack: (value) -> ().
     */
    public static final int LSTORE_1 = 0x40;

    /**
     * lstore_2: store a long value in a local variable 2.
     * Stack: (value) -> ().
     */
    public static final int LSTORE_2 = 0x41;

    /**
     * lstore_3: store a long value in a local variable 3.
     * Stack: (value) -> ().
     */
    public static final int LSTORE_3 = 0x42;

    /**
     * lsub: subtract two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LSUB = 0x65;

    /**
     * lushr: bitwise shift right of a long value1 by int value2 positions, unsigned.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LUSHR = 0x7d;

    /**
     * lxor: bitwise XOR of two longs.
     * Stack: (value1, value2) -> (result).
     */
    public static final int LXOR = 0x83;

    /**
     * monitorenter: enter monitor for object ("grab the lock" – start of synchronized() section).
     * Stack: (objectref) -> ().
     */
    public static final int MONITORENTER = 0xc2;

    /**
     * monitorexit: exit monitor for object ("release the lock" – end of synchronized() section).
     * Stack: (objectref) -> ().
     */
    public static final int MONITOREXIT = 0xc3;

    /**
     * multianewarray: create a new array of dimensions dimensions of type identified by class
     * reference in access pool index (indexbyte1 << 8 + indexbyte2); the sizes of each
     * dimension is identified by count1, [count2, etc.].
     * Other bytes (count: 3): indexbyte1, indexbyte2, dimensions.
     * Stack: (count1, [count2,...]) -> (arrayref).
     */
    public static final int MULTIANEWARRAY = 0xc5;

    /**
     * new: create new object of type identified by class reference in access pool index
     * (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: () -> (objectref).
     */
    public static final int NEW = 0xbb;

    /**
     * newarray: create new array with count elements of primitive type identified by atype.
     * Other bytes (count: 1): atype.
     * Stack: (count) -> (arrayref).
     */
    public static final int NEWARRAY = 0xbc;

    /**
     * nop: perform no operation.
     */
    public static final int NOP = 0x0;

    /**
     * pop: discard the top value on the stack.
     * Stack: (value) -> ().
     */
    public static final int POP = 0x57;

    /**
     * pop2: discard the top two values on the stack (or one value, if it is a double or long).
     * Stack: ({value2, value1}) -> ().
     */
    public static final int POP2 = 0x58;

    /**
     * putfield: set field to value in an object objectref, where the field is identified by a
     * field reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (objectref, value) -> ().
     */
    public static final int PUTFIELD = 0xb5;

    /**
     * putstatic: set static field to value in a class, where the field is identified by a field
     * reference index in access pool (indexbyte1 << 8 + indexbyte2).
     * Other bytes (count: 2): indexbyte1, indexbyte2.
     * Stack: (value) -> ().
     */
    public static final int PUTSTATIC = 0xb3;

    /**
     * ret: continue execution from address taken from a local variable #index (the asymmetry
     * with jsr is intentional).
     * Other bytes (count: 1): index.
     */
    public static final int RET = 0xa9;

    /**
     * return: return void from method.
     * Stack: () -> ([empty]).
     */
    public static final int RETURN = 0xb1;

    /**
     * saload: load short from array.
     * Stack: (arrayref, index) -> (value).
     */
    public static final int SALOAD = 0x35;

    /**
     * sastore: store short to array.
     * Stack: (arrayref, index, value) -> ().
     */
    public static final int SASTORE = 0x56;

    /**
     * sipush: push a short onto the stack as an integer value.
     * Other bytes (count: 2): byte1, byte2.
     * Stack: () -> (value).
     */
    public static final int SIPUSH = 0x11;

    /**
     * swap: swaps two top words on the stack (note that value1 and value2 must not be double or
     * long).
     * Stack: (value2, value1) -> (value1, value2).
     */
    public static final int SWAP = 0x5f;

    /**
     * tableswitch: continue execution from an address in the table at offset index.
     * Other bytes (count: 16+): [0–3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3,
     * defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3,
     * highbyte4, jump offsets....
     * Stack: (index) -> ().
     */
    public static final int TABLESWITCH = 0xaa;

    /**
     * wide: execute opcode, where opcode is either iload, fload, aload, lload, dload, istore,
     * fstore, astore, lstore, dstore, or ret, but assume the index is 16 bit; or execute iinc,
     * where the index is 16 bits and the access to increment by is a signed 16 bit short.
     * Other bytes (count: 3/5): opcode, indexbyte1, indexbyte2 or iinc, indexbyte1, indexbyte2,
     * countbyte1, countbyte2.
     */
    public static final int WIDE = 0xc4;

}
