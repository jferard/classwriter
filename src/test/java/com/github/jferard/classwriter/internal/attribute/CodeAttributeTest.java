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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.instruction.Instruction;
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder;
import com.github.jferard.classwriter.pool.ConstantTags;
import com.github.jferard.classwriter.pool.StringEntry;
import com.github.jferard.classwriter.tool.FieldTypeHelper;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;

public class CodeAttributeTest {
    @Test
    public void testMain() throws IOException {
        Instruction mainCode = RawCodeBuilder.instance()
                .getstatic(FieldRef.create("java.lang.System", "out", PrintStream.class))
                .ldc(new StringEntry("Hello, World!")).invokevirtual(
                        new MethodRef(new PlainClassRef("java.io.PrintStream"), "println",
                                MethodDescriptor.builder().params(FieldTypeHelper.get(String.class))
                                        .build())).return_().build();

        GlobalContext context = GlobalContext.create();
        CodeAttribute ca = new CodeAttribute(mainCode, Collections.emptyList(), Collections.emptyList());

        // Writable eca = ca.encode(context, MethodContext.create(0));

        byte[] expectedContext = {
/* CONSTANT POOL */
                0x00, 0x10, // number of entries: 15
/*   #1 */ ConstantTags.UTF8, 0x00, 0x04, 'C', 'o', 'd', 'e', // u"Code"
/*   #2 */ ConstantTags.UTF8, 0x00, 0x0D, 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l',
                'd', '!', // u"Hello, World!"
/*   #3 */ ConstantTags.STRING, 0x00, 0x02, // #2 -> "Hello, World!"
/*   #4 */ ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/', 'S',
                'y', 's', 't', 'e', 'm', // u"java/lang/System"
/*   #5 */ ConstantTags.CLASS, 0x00, 0x04, // #4 -> "java.lang.System"
/*   #6 */ ConstantTags.UTF8, 0x00, 0x03, 'o', 'u', 't', // u"out"
/*   #7 */ ConstantTags.UTF8, 0x00, 0x15, 'L', 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r',
                'i', 'n', 't', 'S', 't', 'r', 'e', 'a', 'm', ';', // u"Ljava/io/PrintStream;"
/*   #8 */ ConstantTags.NAMEANDTYPE, 0x00, 0x06, 0x00, 0x07, // #6:#7 -> out:Ljava.io.PrintStream;
/*   #9 */ ConstantTags.FIELDREF, 0x00, 0x05, 0x00, 0x08,
                // #5~#8 -> java.lang.System~out:Ljava.io.PrintStream;
/*  #10 */ ConstantTags.UTF8, 0x00, 0x13, 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r', 'i',
                'n', 't', 'S', 't', 'r', 'e', 'a', 'm', // u"java/io/PrintStream"
/*  #11 */ ConstantTags.CLASS, 0x00, 0x0A, // #10 -> "java.io.PrintStream"
/*  #12 */ ConstantTags.UTF8, 0x00, 0x07, 'p', 'r', 'i', 'n', 't', 'l', 'n', // u"println"
/*  #13 */ ConstantTags.UTF8, 0x00, 0x15, '(', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g',
                '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V', // u"(Ljava/lang/String;)V"
/*  #14 */ ConstantTags.NAMEANDTYPE, 0x00, 0x0C, 0x00, 0x0D,
                // #12:#13 -> println:(Ljava.lang.String;)V
/*  #15 */ ConstantTags.METHODREF, 0x00, 0x0B, 0x00, 0x0E,
                // #11~#14 -> java.io.PrintStream~println:(Ljava.lang.String;)V
        };

        // TestHelper.assertWritableEquals(expectedContext, context.getWritablePool());

        byte[] expectedCode = {0x00, 0x01, // "Code"
                0x00, 0x00, 0x00, 0x15, // 15 bytes
                0x00, 0x02, // stack 2
                0x00, 0x00, // locals 0
                0x00, 0x00, 0x00, 0x09, // code len
                (byte) OpCodes.GETSTATIC, 0x00, 0x09,
                // java.lang.System.out (java.lang.PrintStream)
                (byte) OpCodes.LDC, 0x03, // "Hello, World!"
                (byte) Opcodes.INVOKEVIRTUAL, 0x00, 0x0F,
                // java.io.PrintStream.println ((Ljava/lang/String;)V)
                (byte) OpCodes.RETURN, // end of code
                0x00, 0x00, // exceptions len
                0x00, 0x00 // attributes len
        };
        // TestHelper.assertWritableEquals(expectedCode, eca);
    }
}