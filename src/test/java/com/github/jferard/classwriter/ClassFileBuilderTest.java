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

import com.github.jferard.classwriter.internal.access.ClassAccess;
import com.github.jferard.classwriter.api.ClassFile;
import com.github.jferard.classwriter.api.ClassFileBuilder;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.MethodBuilder;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.bytecode.ByteCodeMethodWriter;
import com.github.jferard.classwriter.encoded.EncodedClassFile;
import com.github.jferard.classwriter.internal.instruction.Instruction;
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder;
import com.github.jferard.classwriter.pool.ConstantTags;
import com.github.jferard.classwriter.pool.StringEntry;
import com.github.jferard.classwriter.tool.ConstantPoolHelper;
import com.github.jferard.classwriter.tool.FieldTypeHelper;
import org.junit.jupiter.api.Test;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class ClassFileBuilderTest {
    @Test
    public void testMinimalClass() throws IOException {
        EncodedClassFile helloWorld = ClassFile.builder("HelloWorld").encode();

        System.out.println(ConstantPoolHelper.viewClass(helloWorld));

        byte[] expectedBytecode = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                // magic number
                0x00, 0x00, // minor
                0x00, 0x34, // major = 52
                0x00, 0x05, // pool count = size + 1
                ConstantTags.UTF8, 0x00, 0x0A, 'H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd',
                // "HelloWorld"
                ConstantTags.CLASS, 0x00, 0x01, // HelloWorld
                ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/',
                'O', 'b', 'j', 'e', 'c', 't',
                // "java/lang/Object"
                ConstantTags.CLASS, 0x00, 0x03, // Object
                0x00, ClassAccess.ACC_PUBLIC, // access
                0x00, 0x02, // this = HelloWorld
                0x00, 0x04, // super
                0x00, 0x00, // interfaces_count
                0x00, 0x00, // fields_count
                0x00, 0x00, // methods_count
                0x00, 0x00  // attributes_count
        };

//        TestHelper.assertWritableEquals(expectedBytecode, helloWorld);

        try (FileOutputStream fout = new FileOutputStream("HelloWorld.class")) {
            DataOutput out = new DataOutputStream(fout);
 //           helloWorld.write(out);
        }
    }

    @Test
    public void testHelloWorld() throws IOException {
        ClassFileBuilder classBuilder = ClassFile.builder("HelloWorld")
                .package_("com.github.jferard.classwriter").access(ClassAccess.ACC_PUBLIC);

        Instruction mainCode = RawCodeBuilder.instance()
                .getstatic(FieldRef.create("java.lang.System", "out", PrintStream.class))
                .ldc(new StringEntry("Hello, World!")).invokevirtual(
                        new MethodRef(new PlainClassRef("java.io.PrintStream"), "println",
                                MethodDescriptor.builder().params(FieldTypeHelper.get(String.class))
                                        .build())).return_().build();

        MethodBuilder mainBuilder = ByteCodeMethodWriter.main(mainCode);
        classBuilder.method(mainBuilder);

//        Writable helloWorld = classBuilder.encode();

        byte[] expectedBytecode = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                // magic number
                0x00, 0x00, // minor
                0x00, 0x34, // major = 52
                0x00, 0x16, // pool count = size + 1
                ConstantTags.UTF8, 0x00, 0x0A, 'H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd',
                // "HelloWorld"
                ConstantTags.CLASS, 0x00, 0x01, // HelloWorld
                ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/',
                'O', 'b', 'j', 'e', 'c', 't',
                // "java/lang/Object"
                ConstantTags.CLASS, 0x00, 0x03, // Object
                ConstantTags.UTF8, 0x00, 0x04, 'm', 'a', 'i', 'n', // "main"
                ConstantTags.UTF8, 0x00, 0x16, '(', '[', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a',
                'n', 'g', '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V',
                // "([Ljava/lang/String;)V"
                ConstantTags.UTF8, 0x00, 0x04, 'C', 'o', 'd', 'e', // "Code"
                ConstantTags.UTF8, 0x00, 0x0D, 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r',
                'l', 'd', '!', // "Hello, World!"
                ConstantTags.STRING, 0x00, 0x08, // "Hello, World!"
                ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/',
                'S', 'y', 's', 't', 'e', 'm', // "java/lang/System"
                ConstantTags.CLASS, 0x00, 0x0A, // java.lang.System
                ConstantTags.UTF8, 0x00, 0x03, 'o', 'u', 't', // "out"
                ConstantTags.UTF8, 0x00, 0x15, 'L', 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P',
                'r', 'i', 'n', 't', 'S', 't', 'r', 'e', 'a', 'm', ';', // Ljava/lang/PrintStream;
                ConstantTags.NAMEANDTYPE, 0x00, 0x0C, 0x00, 0x0D, // out:Ljava/lang/PrintStream;
                ConstantTags.FIELDREF, 0x00, 0x0B, 0x00, 0x0E,
                // java.lang.System.out:Ljava/lang/Stream;
                ConstantTags.UTF8, 0x00, 0x13, 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r',
                'i', 'n', 't', 'S', 't', 'r', 'e', 'a', 'm', // "java/lang/PrintStream"
                ConstantTags.CLASS, 0x00, 0x10, // java.lang.PrintStream
                ConstantTags.UTF8, 0x00, 0x07, 'p', 'r', 'i', 'n', 't', 'l', 'n', // "println"
                ConstantTags.UTF8, 0x00, 0x15, '(', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n',
                'g', '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V', // "(Ljava/lang/String;)V
                ConstantTags.NAMEANDTYPE, 0x00, 0x12, 0x00, 0x13, //
                ConstantTags.METHODREF, 0x00, 0x11, 0x00, 0x14, //
                0x00, ClassAccess.ACC_PUBLIC, // access
                0x00, 0x02, // this = HelloWorld
                0x00, 0x04, // super
                0x00, 0x00, // interfaces_count
                0x00, 0x00, // fields_count
                0x00, 0x01, // methods_count
                0x00, 0x19, // public static void
                0x00, 0x05, // "main"
                0x00, 0x06, // "(Ljava/lang/String;)V
                0x00, 0x01, // 1 attribute
                0x00, 0x07, // "Code"
                0x00, 0x00, 0x00, 0x15, // 15 bytes
                0x00, 0x02, // stack 2
                0x00, 0x01, // locals 1
                0x00, 0x00, 0x00, 0x09, // code len
                (byte) OpCodes.GETSTATIC, 0, 15, (byte) OpCodes.LDC, 9,
                (byte) OpCodes.INVOKEVIRTUAL, 0, 21, (byte) OpCodes.RETURN, // end of code
                0x00, 0x00, // exceptions len
                0x00, 0x00, // code attributes count
                0x00, 0x00 // class attributes_count
        };

        /*
        try (FileOutputStream fout = new FileOutputStream("HelloWorld.class")) {
            DataOutput out = new DataOutputStream(fout);
            helloWorld.write(out);
        }
        TestHelper.assertWritableEquals(expectedBytecode, helloWorld);
        System.out.println(ConstantPoolHelper.viewClass(helloWorld));
        */
    }

}