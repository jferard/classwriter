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
package com.github.jferard.classwriter.api.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.TestHelper
import com.github.jferard.classwriter.api.FieldRef.Companion.create
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.MethodRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.base.PaddingHelper
import com.github.jferard.classwriter.pool.ConstantTags
import com.github.jferard.classwriter.pool.Utf8Entry
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import jdk.internal.org.objectweb.asm.Opcodes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.io.IOException
import java.io.PrintStream

internal class RawCodeBuilderTest {
    @Test
    @Throws(IOException::class)
    fun testIinc() {
        val code =
                RawCodeBuilder(PaddingHelper()).iconst(2).istore(1).iinc(1, 1)
                        .build()
        assertCodeEquals(code, OpCodes.ICONST_2,
                OpCodes.ISTORE_1,
                OpCodes.IINC, 1, 1)
        val codeContext = MethodContext.create(0)
        code.preprocess(GlobalContext.create(), codeContext)
        Assertions.assertEquals(1, codeContext.maxStack)
        Assertions.assertEquals(5, codeContext.curOffset)
        Assertions.assertEquals(2, codeContext.maxLocals)
    }

    @Test
    @Throws(IOException::class)
    fun testWideIinc1() {
        val code =
                RawCodeBuilder(PaddingHelper()).iinc(1000, 1).build()
        assertCodeEquals(code, OpCodes.WIDE,
                OpCodes.IINC, 1000 shr 8, 1000 and 255, 0, 1)
        val codeContext = MethodContext.create(0)
        val preprocess =
                Executable { code.preprocess(GlobalContext.create(), codeContext) }
        Assertions.assertThrows(
                ArrayIndexOutOfBoundsException::class.java, preprocess, "")
    }

    @Test
    @Throws(IOException::class)
    fun testWideIinc2() {
        val code =
                RawCodeBuilder(PaddingHelper()).iconst(2).istore(1).iinc(1, 1000).build()
        assertCodeEquals(code, OpCodes.ICONST_2,
                OpCodes.ISTORE_1,
                OpCodes.WIDE,
                OpCodes.IINC, 0, 1, 1000 shr 8, 1000 and 255)
        val codeContext = MethodContext.create(0)
        code.preprocess(GlobalContext.create(), codeContext)
        Assertions.assertEquals(1, codeContext.maxStack)
        Assertions.assertEquals(8, codeContext.curOffset)
        Assertions.assertEquals(2, codeContext.maxLocals)
    }

    @Test
    @Throws(IOException::class)
    fun testMain() {
        val mainCode =
                RawCodeBuilder.instance()
                        .getstatic(
                                create("kotlin.lang.System", "out", PrintStream::class.java))
                        .ldc(Utf8Entry("Hello, World!"))
                        .invokevirtual(
                                MethodRef(
                                        PlainClassRef("kotlin.io.PrintStream"), "println",
                                        builder().params(
                                                get(
                                                        String::class.java))
                                                .build())).return_().build()
        val context = GlobalContext.create()
        //        Writable code = mainCode.encode(context, MethodContext.create(0));
        val expectedPool = byteArrayOf( /* CONSTANT POOL */
                0x00, 0x0E,  // number of entries: 13
/*   #1 */
                ConstantTags.UTF8.toByte(), 0x00, 0x10, 'j'.toByte(), 'a'.toByte(), 'v'.toByte(),
                'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(), 'n'.toByte(), 'g'.toByte(),
                '/'.toByte(), 'S'.toByte(),
                'y'.toByte(), 's'.toByte(), 't'.toByte(), 'e'.toByte(),
                'm'.toByte(),  // u"kotlin/lang/System"
/*   #2 */
                ConstantTags.CLASS.toByte(), 0x00, 0x01,  // #1 -> "kotlin.lang.System"
/*   #3 */
                ConstantTags.UTF8.toByte(), 0x00, 0x03, 'o'.toByte(), 'u'.toByte(),
                't'.toByte(),  // u"out"
/*   #4 */
                ConstantTags.UTF8.toByte(), 0x00, 0x15, 'L'.toByte(), 'j'.toByte(), 'a'.toByte(),
                'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'i'.toByte(), 'o'.toByte(), '/'.toByte(),
                'P'.toByte(), 'r'.toByte(),
                'i'.toByte(), 'n'.toByte(), 't'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(),
                'e'.toByte(), 'a'.toByte(), 'm'.toByte(), ';'.toByte(),  // u"Ljava/io/PrintStream;"
/*   #5 */
                ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x03, 0x00,
                0x04,  // #3:#4 -> out:Ljava.io.PrintStream;
/*   #6 */
                ConstantTags.FIELD_REF.toByte(), 0x00, 0x02, 0x00,
                0x05,  // #2~#5 -> kotlin.lang.System~out:Ljava.io.PrintStream;
/*   #7 */
                ConstantTags.UTF8.toByte(), 0x00, 0x0D, 'H'.toByte(), 'e'.toByte(), 'l'.toByte(),
                'l'.toByte(), 'o'.toByte(), ','.toByte(), ' '.toByte(), 'W'.toByte(), 'o'.toByte(),
                'r'.toByte(), 'l'.toByte(),
                'd'.toByte(), '!'.toByte(),  // u"Hello, World!"
/*   #8 */
                ConstantTags.UTF8.toByte(), 0x00, 0x13, 'j'.toByte(), 'a'.toByte(), 'v'.toByte(),
                'a'.toByte(), '/'.toByte(), 'i'.toByte(), 'o'.toByte(), '/'.toByte(), 'P'.toByte(),
                'r'.toByte(), 'i'.toByte(),
                'n'.toByte(), 't'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(), 'e'.toByte(),
                'a'.toByte(), 'm'.toByte(),  // u"kotlin/io/PrintStream"
/*   #9 */
                ConstantTags.CLASS.toByte(), 0x00, 0x08,  // #8 -> "kotlin.io.PrintStream"
/*  #10 */
                ConstantTags.UTF8.toByte(), 0x00, 0x07, 'p'.toByte(), 'r'.toByte(), 'i'.toByte(),
                'n'.toByte(), 't'.toByte(), 'l'.toByte(), 'n'.toByte(),  // u"println"
/*  #11 */
                ConstantTags.UTF8.toByte(), 0x00, 0x15, '('.toByte(), 'L'.toByte(), 'j'.toByte(),
                'a'.toByte(), 'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                'n'.toByte(), 'g'.toByte(),
                '/'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(), 'i'.toByte(), 'n'.toByte(),
                'g'.toByte(), ';'.toByte(), ')'.toByte(), 'V'.toByte(),  // u"(Ljava/lang/String;)V"
/*  #12 */
                ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x0A, 0x00,
                0x0B,  // #10:#11 -> println:(Ljava.lang.String;)V
/*  #13 */
                ConstantTags.METHOD_REF.toByte(), 0x00, 0x09, 0x00, 0x0C)
        //        TestHelper.assertWritableEquals(expectedPool, context.getWritablePool());
        val expectedCode =
                byteArrayOf(OpCodes.GETSTATIC.toByte(), 0, 6,
                        OpCodes.LDC.toByte(), 7,
                        OpCodes.INVOKEVIRTUAL.toByte(), 0, 13,
                        OpCodes.RETURN.toByte())
        //        TestHelper.assertWritableEquals(expectedCode, code);
    }

    @Throws(IOException::class)
    private fun assertCodeEquals(
            code: Instruction,
            vararg opcodes: Int) {
        val bytes = TestHelper.toBytes(opcodes)
        val context = GlobalContext.create()
        //        Writable writable = code.encode(context, MethodContext.create(0));
//        TestHelper.assertWritableEquals(bytes, writable);
    }
}