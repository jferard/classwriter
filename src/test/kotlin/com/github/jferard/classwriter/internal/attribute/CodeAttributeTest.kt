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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.FieldRef.Companion.create
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.MethodRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.api.instruction.RawCodeBuilder
import com.github.jferard.classwriter.pool.ConstantTags
import com.github.jferard.classwriter.pool.StringEntry
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import jdk.internal.org.objectweb.asm.Opcodes
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

class CodeAttributeTest {
    @Test
    @Throws(IOException::class)
    fun testMain() {
        val mainCode =
                RawCodeBuilder.instance()
                        .getstatic(
                                create("kotlin.lang.System", "out", PrintStream::class.java))
                        .ldc(StringEntry("Hello, World!"))
                        .invokevirtual(
                                MethodRef(
                                        PlainClassRef("kotlin.io.PrintStream"), "println",
                                        builder().params(
                                                get(
                                                        String::class.java))
                                                .build())).return_().build()
        val context = GlobalContext.create()
        val ca = CodeAttribute(mainCode, emptyList(), emptyList())
        // Writable eca = ca.encode(context, MethodContext.create(0));
        val expectedContext = byteArrayOf( /* CONSTANT POOL */
                0x00, 0x10,  // number of entries: 15
/*   #1 */
                ConstantTags.UTF8.toByte(), 0x00, 0x04, 'C'.toByte(), 'o'.toByte(), 'd'.toByte(),
                'e'.toByte(),  // u"Code"
/*   #2 */
                ConstantTags.UTF8.toByte(), 0x00, 0x0D, 'H'.toByte(), 'e'.toByte(), 'l'.toByte(),
                'l'.toByte(), 'o'.toByte(), ','.toByte(), ' '.toByte(), 'W'.toByte(), 'o'.toByte(),
                'r'.toByte(), 'l'.toByte(),
                'd'.toByte(), '!'.toByte(),  // u"Hello, World!"
/*   #3 */
                ConstantTags.STRING.toByte(), 0x00, 0x02,  // #2 -> "Hello, World!"
/*   #4 */
                ConstantTags.UTF8.toByte(), 0x00, 0x10, 'j'.toByte(), 'a'.toByte(), 'v'.toByte(),
                'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(), 'n'.toByte(), 'g'.toByte(),
                '/'.toByte(), 'S'.toByte(),
                'y'.toByte(), 's'.toByte(), 't'.toByte(), 'e'.toByte(),
                'm'.toByte(),  // u"kotlin/lang/System"
/*   #5 */
                ConstantTags.CLASS.toByte(), 0x00, 0x04,  // #4 -> "kotlin.lang.System"
/*   #6 */
                ConstantTags.UTF8.toByte(), 0x00, 0x03, 'o'.toByte(), 'u'.toByte(),
                't'.toByte(),  // u"out"
/*   #7 */
                ConstantTags.UTF8.toByte(), 0x00, 0x15, 'L'.toByte(), 'j'.toByte(), 'a'.toByte(),
                'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'i'.toByte(), 'o'.toByte(), '/'.toByte(),
                'P'.toByte(), 'r'.toByte(),
                'i'.toByte(), 'n'.toByte(), 't'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(),
                'e'.toByte(), 'a'.toByte(), 'm'.toByte(), ';'.toByte(),  // u"Ljava/io/PrintStream;"
/*   #8 */
                ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x06, 0x00,
                0x07,  // #6:#7 -> out:Ljava.io.PrintStream;
/*   #9 */
                ConstantTags.FIELD_REF.toByte(), 0x00, 0x05, 0x00,
                0x08,  // #5~#8 -> kotlin.lang.System~out:Ljava.io.PrintStream;
/*  #10 */
                ConstantTags.UTF8.toByte(), 0x00, 0x13, 'j'.toByte(), 'a'.toByte(), 'v'.toByte(),
                'a'.toByte(), '/'.toByte(), 'i'.toByte(), 'o'.toByte(), '/'.toByte(), 'P'.toByte(),
                'r'.toByte(), 'i'.toByte(),
                'n'.toByte(), 't'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(), 'e'.toByte(),
                'a'.toByte(), 'm'.toByte(),  // u"kotlin/io/PrintStream"
/*  #11 */
                ConstantTags.CLASS.toByte(), 0x00, 0x0A,  // #10 -> "kotlin.io.PrintStream"
/*  #12 */
                ConstantTags.UTF8.toByte(), 0x00, 0x07, 'p'.toByte(), 'r'.toByte(), 'i'.toByte(),
                'n'.toByte(), 't'.toByte(), 'l'.toByte(), 'n'.toByte(),  // u"println"
/*  #13 */
                ConstantTags.UTF8.toByte(), 0x00, 0x15, '('.toByte(), 'L'.toByte(), 'j'.toByte(),
                'a'.toByte(), 'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                'n'.toByte(), 'g'.toByte(),
                '/'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(), 'i'.toByte(), 'n'.toByte(),
                'g'.toByte(), ';'.toByte(), ')'.toByte(), 'V'.toByte(),  // u"(Ljava/lang/String;)V"
/*  #14 */
                ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x0C, 0x00,
                0x0D,  // #12:#13 -> println:(Ljava.lang.String;)V
/*  #15 */
                ConstantTags.METHOD_REF.toByte(), 0x00, 0x0B, 0x00, 0x0E)
        // TestHelper.assertWritableEquals(expectedContext, context.getWritablePool());
        val expectedCode = byteArrayOf(0x00, 0x01,  // "Code"
                0x00, 0x00, 0x00, 0x15,  // 15 bytes
                0x00, 0x02,  // stack 2
                0x00, 0x00,  // locals 0
                0x00, 0x00, 0x00, 0x09,  // code len
                OpCodes.GETSTATIC.toByte(), 0x00,
                0x09,  // kotlin.lang.System.out (kotlin.lang.PrintStream)
                OpCodes.LDC.toByte(), 0x03,  // "Hello, World!"
                Opcodes.INVOKEVIRTUAL.toByte(), 0x00,
                0x0F,  // kotlin.io.PrintStream.println ((Ljava/lang/String;)V)
                OpCodes.RETURN.toByte(),  // end of code
                0x00, 0x00,  // exceptions len
                0x00, 0x00 // attributes len
        )
        // TestHelper.assertWritableEquals(expectedCode, eca);
    }
}