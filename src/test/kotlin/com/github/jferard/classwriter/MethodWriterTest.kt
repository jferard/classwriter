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
package com.github.jferard.classwriter

import com.github.jferard.classwriter.api.FieldRef.Companion.create
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.MethodRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder
import com.github.jferard.classwriter.pool.StringEntry
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import jdk.internal.org.objectweb.asm.Opcodes
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.PrintStream

internal class MethodWriterTest {
    @Test
    @Throws(IOException::class)
    fun test() {
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
        val expectedMethod = byteArrayOf(0x00, 0x19,  // public static void
                0x00, 0x01,  // "main"
                0x00, 0x02,  // "(Ljava/lang/String;)V
                0x00, 0x01,  // 1 attribute
                0x00, 0x03,  // "Code"
                0x00, 0x00, 0x00, 0x15,  // 15 bytes
                0x00, 0x02,  // stack 2
                0x00, 0x01,  // locals 0
                0x00, 0x00, 0x00, 0x09,  // code len
                OpCodes.GETSTATIC.toByte(), 0x00,
                0x0B,  // kotlin.lang.System.out (kotlin.lang.PrintStream)
                OpCodes.LDC.toByte(), 0x05,  // "Hello, World!"
                Opcodes.INVOKEVIRTUAL.toByte(), 0x00,
                0x11,  // kotlin.io.PrintStream.println ((Ljava/lang/String;)V)
                OpCodes.RETURN.toByte(),  // end of code
                0x00, 0x00,  // exceptions len
                0x00, 0x00 // attributes len
        )
        //        Writable mainWriter = ByteCodeMethodWriter.main(mainCode).encode(context, MethodContext.create(0));
//        TestHelper.assertWritableEquals(expectedMethod, mainWriter);
    }
}