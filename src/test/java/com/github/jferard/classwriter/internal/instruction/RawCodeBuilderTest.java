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

package com.github.jferard.classwriter.internal.instruction;

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.TestHelper;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.internal.instruction.base.PaddingHelper;
import com.github.jferard.classwriter.pool.ConstantTags;
import com.github.jferard.classwriter.pool.Utf8Entry;
import com.github.jferard.classwriter.tool.FieldTypeHelper;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.io.PrintStream;

class RawCodeBuilderTest {
    @Test
    public void testIinc() throws IOException {
        Instruction code = new RawCodeBuilder(new PaddingHelper()).iconst(2).istore(1).iinc(1, 1)
                .build();
        this.assertCodeEquals(code, OpCodes.ICONST_2, OpCodes.ISTORE_1, OpCodes.IINC, 1, 1);
        MethodContext codeContext = MethodContext.create(0);
        code.preprocess(GlobalContext.create(), codeContext);
        Assertions.assertEquals(1, codeContext.getMaxStack());
        Assertions.assertEquals(5, codeContext.getCurOffset());
        Assertions.assertEquals(2, codeContext.getMaxLocals());
    }

    @Test
    public void testWideIinc1() throws IOException {
        Instruction code = new RawCodeBuilder(new PaddingHelper()).iinc(1000, 1).build();
        this.assertCodeEquals(code, OpCodes.WIDE, OpCodes.IINC, 1000 >> 8, 1000 & 255, 0, 1);
        MethodContext codeContext = MethodContext.create(0);

        Executable preprocess = () -> code.preprocess(GlobalContext.create(), codeContext);

        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, preprocess, "");
    }

    @Test
    public void testWideIinc2() throws IOException {
        Instruction code = new RawCodeBuilder(new PaddingHelper()).iconst(2).istore(1).iinc(1, 1000).build();
        this.assertCodeEquals(code, OpCodes.ICONST_2, OpCodes.ISTORE_1, OpCodes.WIDE, OpCodes.IINC, 0, 1, 1000 >> 8, 1000 & 255);
        MethodContext codeContext = MethodContext.create(0);
        code.preprocess(GlobalContext.create(), codeContext);
        Assertions.assertEquals(1, codeContext.getMaxStack());
        Assertions.assertEquals(8, codeContext.getCurOffset());
        Assertions.assertEquals(2, codeContext.getMaxLocals());
    }

    @Test
    public void testMain() throws IOException {
        Instruction mainCode = RawCodeBuilder.instance()
                .getstatic(FieldRef.create("java.lang.System", "out", PrintStream.class))
                .ldc(new Utf8Entry("Hello, World!")).invokevirtual(
                        new MethodRef(new PlainClassRef("java.io.PrintStream"), "println",
                                MethodDescriptor.builder().params(FieldTypeHelper.get(String.class))
                                        .build())).return_().build();

        GlobalContext context = GlobalContext.create();
//        Writable code = mainCode.encode(context, MethodContext.create(0));

        byte[] expectedPool = {/* CONSTANT POOL */
                0x00, 0x0E, // number of entries: 13
/*   #1 */ ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/', 'S',
                'y', 's', 't', 'e', 'm', // u"java/lang/System"
/*   #2 */ ConstantTags.CLASS, 0x00, 0x01, // #1 -> "java.lang.System"
/*   #3 */ ConstantTags.UTF8, 0x00, 0x03, 'o', 'u', 't', // u"out"
/*   #4 */ ConstantTags.UTF8, 0x00, 0x15, 'L', 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r',
                'i', 'n', 't', 'S', 't', 'r', 'e', 'a', 'm', ';', // u"Ljava/io/PrintStream;"
/*   #5 */ ConstantTags.NAMEANDTYPE, 0x00, 0x03, 0x00, 0x04, // #3:#4 -> out:Ljava.io.PrintStream;
/*   #6 */ ConstantTags.FIELDREF, 0x00, 0x02, 0x00, 0x05,
                // #2~#5 -> java.lang.System~out:Ljava.io.PrintStream;
/*   #7 */ ConstantTags.UTF8, 0x00, 0x0D, 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l',
                'd', '!', // u"Hello, World!"
/*   #8 */ ConstantTags.UTF8, 0x00, 0x13, 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r', 'i',
                'n', 't', 'S', 't', 'r', 'e', 'a', 'm', // u"java/io/PrintStream"
/*   #9 */ ConstantTags.CLASS, 0x00, 0x08, // #8 -> "java.io.PrintStream"
/*  #10 */ ConstantTags.UTF8, 0x00, 0x07, 'p', 'r', 'i', 'n', 't', 'l', 'n', // u"println"
/*  #11 */ ConstantTags.UTF8, 0x00, 0x15, '(', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g',
                '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V', // u"(Ljava/lang/String;)V"
/*  #12 */ ConstantTags.NAMEANDTYPE, 0x00, 0x0A, 0x00, 0x0B,
                // #10:#11 -> println:(Ljava.lang.String;)V
/*  #13 */ ConstantTags.METHODREF, 0x00, 0x09, 0x00, 0x0C,
                // #9~#12 -> java.io.PrintStream~println:(Ljava.lang.String;)V
        };

//        TestHelper.assertWritableEquals(expectedPool, context.getWritablePool());

        byte[] expectedCode = {(byte) OpCodes.GETSTATIC, 0, 6, (byte) OpCodes.LDC, 7,
                (byte) Opcodes.INVOKEVIRTUAL, 0, 13, (byte) OpCodes.RETURN};
//        TestHelper.assertWritableEquals(expectedCode, code);
    }


    private void assertCodeEquals(Instruction code, int... opcodes) throws IOException {
        byte[] bytes = TestHelper.toBytes(opcodes);
        GlobalContext context = GlobalContext.create();
//        Writable writable = code.encode(context, MethodContext.create(0));
//        TestHelper.assertWritableEquals(bytes, writable);
    }
}