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

import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.instruction.Instruction;
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder;
import com.github.jferard.classwriter.pool.StringEntry;
import com.github.jferard.classwriter.tool.FieldTypeHelper;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;

class MethodWriterTest {
    @Test
    public void test() throws IOException {
        Instruction mainCode = RawCodeBuilder.instance()
                .getstatic(FieldRef.create("java.lang.System", "out", PrintStream.class))
                .ldc(new StringEntry("Hello, World!")).invokevirtual(
                        new MethodRef(new PlainClassRef("java.io.PrintStream"), "println",
                                MethodDescriptor.builder().params(FieldTypeHelper.get(String.class))
                                        .build())).return_().build();
        GlobalContext context = GlobalContext.create();

        byte[] expectedMethod = {0x00, 0x19, // public static void
                0x00, 0x01, // "main"
                0x00, 0x02, // "(Ljava/lang/String;)V
                0x00, 0x01, // 1 attribute
                0x00, 0x03, // "Code"
                0x00, 0x00, 0x00, 0x15, // 15 bytes
                0x00, 0x02, // stack 2
                0x00, 0x01, // locals 0
                0x00, 0x00, 0x00, 0x09, // code len
                (byte) OpCodes.GETSTATIC, 0x00, 0x0B,
                // java.lang.System.out (java.lang.PrintStream)
                (byte) OpCodes.LDC, 0x05, // "Hello, World!"
                (byte) Opcodes.INVOKEVIRTUAL, 0x00, 0x11,
                // java.io.PrintStream.println ((Ljava/lang/String;)V)
                (byte) OpCodes.RETURN, // end of code
                0x00, 0x00, // exceptions len
                0x00, 0x00 // attributes len
        };
//        Writable mainWriter = ByteCodeMethodWriter.main(mainCode).encode(context, MethodContext.create(0));
//        TestHelper.assertWritableEquals(expectedMethod, mainWriter);
    }

}