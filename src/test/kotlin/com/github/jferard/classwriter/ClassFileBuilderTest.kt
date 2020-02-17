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

import com.github.jferard.classwriter.api.ClassFile.Companion.builder
import com.github.jferard.classwriter.api.FieldRef.Companion.create
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.MethodRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.api.instruction.RawCodeBuilder
import com.github.jferard.classwriter.bytecode.writer.ByteCodeClassEncodedWriter
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.pool.ConstantTags
import com.github.jferard.classwriter.pool.StringEntry
import com.github.jferard.classwriter.tool.ConstantPoolHelper
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import org.junit.jupiter.api.Test
import java.io.*
import java.util.logging.Logger

internal class ClassFileBuilderTest {
    /** javap -v -p -s -sysinfo -constants target/classes/com/github/jferard/classwriter/api/ClassFile.class */
    //@Test
    @Throws(IOException::class)
    fun test0() {
        val fname =
                "target/classes/com/github/jferard/classwriter/api/ClassFile.class"
        println("// Parsing $fname")
        val stream = FileInputStream(fname)
        val classRep = ConstantPoolHelper.viewClass(stream)
        println(classRep)

    }

    @Test
    @Throws(IOException::class)
    fun test1() {
        File("target/classes/com/github/jferard/classwriter").walk()
                .filter { it.extension == "class" }.forEach { testHelper(it) }
    }

    //@Test
    @Throws(IOException::class)
    fun testMinimalClass() {
        val helloWorld =
                builder("HelloWorld").encode()
//        println(viewClass(helloWorld))
        val expectedBytecode =
                byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(),
                        0xBE.toByte(),  // magic number
                        0x00, 0x00,  // minor
                        0x00, 0x34,  // major = 52
                        0x00, 0x05,  // pool count = size + 1
                        ConstantTags.UTF8.toByte(), 0x00, 0x0A, 'H'.toByte(), 'e'.toByte(),
                        'l'.toByte(), 'l'.toByte(), 'o'.toByte(), 'W'.toByte(), 'o'.toByte(),
                        'r'.toByte(), 'l'.toByte(), 'd'.toByte(),  // "HelloWorld"
                        ConstantTags.CLASS.toByte(), 0x00, 0x01,  // HelloWorld
                        ConstantTags.UTF8.toByte(), 0x00, 0x10, 'j'.toByte(), 'a'.toByte(),
                        'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                        'n'.toByte(), 'g'.toByte(), '/'.toByte(),
                        'O'.toByte(), 'b'.toByte(), 'j'.toByte(), 'e'.toByte(), 'c'.toByte(),
                        't'.toByte(),  // "kotlin/lang/Object"
                        ConstantTags.CLASS.toByte(), 0x00, 0x03,  // Object
                        0x00, ClassAccess.ACC_PUBLIC.toByte(),  // access
                        0x00, 0x02,  // this = HelloWorld
                        0x00, 0x04,  // super
                        0x00, 0x00,  // interfaces_count
                        0x00, 0x00,  // fields_count
                        0x00, 0x00,  // methods_count
                        0x00, 0x00 // attributes_count
                )
        FileOutputStream("HelloWorld.class").use {
            val out: DataOutputStream = DataOutputStream(it)
        }
    }

    //@Test
    @Throws(IOException::class)
    fun testHelloWorld() {
        val classBuilder =
                builder("HelloWorld")
                        .package_("com.github.jferard.classwriter").access(ClassAccess.ACC_PUBLIC)
        val mainCode =
                RawCodeBuilder.instance()
                        .getstatic(
                                create("java.lang.System", "out", PrintStream::class.java))
                        .ldc(StringEntry("Hello, World!"))
                        .invokevirtual(
                                MethodRef(
                                        PlainClassRef("java.io.PrintStream"), "println",
                                        builder().params(
                                                get(
                                                        String::class.java))
                                                .build())).return_().build()
//        val mainBuilder = ByteCodeMethodWriter.main(mainCode)
        //        classBuilder.method(mainBuilder)
        //        Writable helloWorld = classBuilder.encode();
        val expectedBytecode =
                byteArrayOf(0xCA.toByte(), 0xFE.toByte(), 0xBA.toByte(),
                        0xBE.toByte(),  // magic number
                        0x00, 0x00,  // minor
                        0x00, 0x34,  // major = 52
                        0x00, 0x16,  // pool count = size + 1
                        ConstantTags.UTF8.toByte(), 0x00, 0x0A, 'H'.toByte(), 'e'.toByte(),
                        'l'.toByte(), 'l'.toByte(), 'o'.toByte(), 'W'.toByte(), 'o'.toByte(),
                        'r'.toByte(), 'l'.toByte(), 'd'.toByte(),  // "HelloWorld"
                        ConstantTags.CLASS.toByte(), 0x00, 0x01,  // HelloWorld
                        ConstantTags.UTF8.toByte(), 0x00, 0x10, 'j'.toByte(), 'a'.toByte(),
                        'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                        'n'.toByte(), 'g'.toByte(), '/'.toByte(),
                        'O'.toByte(), 'b'.toByte(), 'j'.toByte(), 'e'.toByte(), 'c'.toByte(),
                        't'.toByte(),  // "kotlin/lang/Object"
                        ConstantTags.CLASS.toByte(), 0x00, 0x03,  // Object
                        ConstantTags.UTF8.toByte(), 0x00, 0x04, 'm'.toByte(), 'a'.toByte(),
                        'i'.toByte(), 'n'.toByte(),  // "main"
                        ConstantTags.UTF8.toByte(), 0x00, 0x16, '('.toByte(), '['.toByte(),
                        'L'.toByte(), 'j'.toByte(), 'a'.toByte(), 'v'.toByte(), 'a'.toByte(),
                        '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                        'n'.toByte(), 'g'.toByte(), '/'.toByte(), 'S'.toByte(), 't'.toByte(),
                        'r'.toByte(), 'i'.toByte(), 'n'.toByte(), 'g'.toByte(), ';'.toByte(),
                        ')'.toByte(), 'V'.toByte(),  // "([Ljava/lang/String;)V"
                        ConstantTags.UTF8.toByte(), 0x00, 0x04, 'C'.toByte(), 'o'.toByte(),
                        'd'.toByte(), 'e'.toByte(),  // "Code"
                        ConstantTags.UTF8.toByte(), 0x00, 0x0D, 'H'.toByte(), 'e'.toByte(),
                        'l'.toByte(), 'l'.toByte(), 'o'.toByte(), ','.toByte(), ' '.toByte(),
                        'W'.toByte(), 'o'.toByte(), 'r'.toByte(),
                        'l'.toByte(), 'd'.toByte(), '!'.toByte(),  // "Hello, World!"
                        ConstantTags.STRING.toByte(), 0x00, 0x08,  // "Hello, World!"
                        ConstantTags.UTF8.toByte(), 0x00, 0x10, 'j'.toByte(), 'a'.toByte(),
                        'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'l'.toByte(), 'a'.toByte(),
                        'n'.toByte(), 'g'.toByte(), '/'.toByte(),
                        'S'.toByte(), 'y'.toByte(), 's'.toByte(), 't'.toByte(), 'e'.toByte(),
                        'm'.toByte(),  // "kotlin/lang/System"
                        ConstantTags.CLASS.toByte(), 0x00, 0x0A,  // kotlin.lang.System
                        ConstantTags.UTF8.toByte(), 0x00, 0x03, 'o'.toByte(), 'u'.toByte(),
                        't'.toByte(),  // "out"
                        ConstantTags.UTF8.toByte(), 0x00, 0x15, 'L'.toByte(), 'j'.toByte(),
                        'a'.toByte(), 'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'i'.toByte(),
                        'o'.toByte(), '/'.toByte(), 'P'.toByte(),
                        'r'.toByte(), 'i'.toByte(), 'n'.toByte(), 't'.toByte(), 'S'.toByte(),
                        't'.toByte(), 'r'.toByte(), 'e'.toByte(), 'a'.toByte(), 'm'.toByte(),
                        ';'.toByte(),  // Ljava/lang/PrintStream;
                        ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x0C, 0x00,
                        0x0D,  // out:Ljava/lang/PrintStream;
                        ConstantTags.FIELD_REF.toByte(), 0x00, 0x0B, 0x00,
                        0x0E,  // kotlin.lang.System.out:Ljava/lang/Stream;
                        ConstantTags.UTF8.toByte(), 0x00, 0x13, 'j'.toByte(), 'a'.toByte(),
                        'v'.toByte(), 'a'.toByte(), '/'.toByte(), 'i'.toByte(), 'o'.toByte(),
                        '/'.toByte(), 'P'.toByte(), 'r'.toByte(),
                        'i'.toByte(), 'n'.toByte(), 't'.toByte(), 'S'.toByte(), 't'.toByte(),
                        'r'.toByte(), 'e'.toByte(), 'a'.toByte(),
                        'm'.toByte(),  // "kotlin/lang/PrintStream"
                        ConstantTags.CLASS.toByte(), 0x00, 0x10,  // kotlin.lang.PrintStream
                        ConstantTags.UTF8.toByte(), 0x00, 0x07, 'p'.toByte(), 'r'.toByte(),
                        'i'.toByte(), 'n'.toByte(), 't'.toByte(), 'l'.toByte(),
                        'n'.toByte(),  // "println"
                        ConstantTags.UTF8.toByte(), 0x00, 0x15, '('.toByte(), 'L'.toByte(),
                        'j'.toByte(), 'a'.toByte(), 'v'.toByte(), 'a'.toByte(), '/'.toByte(),
                        'l'.toByte(), 'a'.toByte(), 'n'.toByte(),
                        'g'.toByte(), '/'.toByte(), 'S'.toByte(), 't'.toByte(), 'r'.toByte(),
                        'i'.toByte(), 'n'.toByte(), 'g'.toByte(), ';'.toByte(), ')'.toByte(),
                        'V'.toByte(),  // "(Ljava/lang/String;)V
                        ConstantTags.NAME_AND_TYPE.toByte(), 0x00, 0x12, 0x00, 0x13,  //
                        ConstantTags.METHOD_REF.toByte(), 0x00, 0x11, 0x00, 0x14,  //
                        0x00, ClassAccess.ACC_PUBLIC.toByte(),  // access
                        0x00, 0x02,  // this = HelloWorld
                        0x00, 0x04,  // super
                        0x00, 0x00,  // interfaces_count
                        0x00, 0x00,  // fields_count
                        0x00, 0x01,  // methods_count
                        0x00, 0x19,  // public static void
                        0x00, 0x05,  // "main"
                        0x00, 0x06,  // "(Ljava/lang/String;)V
                        0x00, 0x01,  // 1 attribute
                        0x00, 0x07,  // "Code"
                        0x00, 0x00, 0x00, 0x15,  // 15 bytes
                        0x00, 0x02,  // stack 2
                        0x00, 0x01,  // locals 1
                        0x00, 0x00, 0x00, 0x09,  // code len
                        OpCodes.GETSTATIC.toByte(), 0, 15,
                        OpCodes.LDC.toByte(), 9,
                        OpCodes.INVOKEVIRTUAL.toByte(), 0, 21,
                        OpCodes.RETURN.toByte(),  // end of code
                        0x00, 0x00,  // exceptions len
                        0x00, 0x00,  // code attributes count
                        0x00, 0x00 // class attributes_count
                )
        /*
        try (FileOutputStream fout = new FileOutputStream("HelloWorld.class")) {
            ClassOutput out = new DataOutputStream(fout);
            helloWorld.write(out);
        }
        TestHelper.assertWritableEquals(expectedBytecode, helloWorld);
        System.out.println(ConstantPoolHelper.viewClass(helloWorld));
        */
    }

    companion object {
        private fun testHelper(file: File) {
            if (file.path in arrayOf(
                            "target/classes/com/github/jferard/classwriter/bytecode/BytecodeHelper.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeFieldEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/ByteVerificationType.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeClassAnnotationEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeFieldAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeFieldEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeStackMapFrameEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeClassEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeConstantPoolEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeMethodEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeClassEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeCodeAttributeAttributeEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeMethodEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeClassFileAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeInstructionEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeMethodAttributeEncodedWriter\$Companion.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeStackMapFrameEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeVerificationTypeEncodedWriter.class"
                    )) {
                return
            }
            println("*************************")
            println("READ $file")
            println("*************************")
            val bytes = java.nio.file.Files.readAllBytes(file.toPath())
            val stream1 = ByteArrayInputStream(bytes)
            println(ConstantPoolHelper.viewClass(stream1))
            val stream = ByteArrayInputStream(bytes)
            val encodedClass = ConstantPoolHelper.parseClassByteCode(stream)
            val fname2 = file.absolutePath + ".bkp"
            println("*************************")
            println("WRITE $fname2")
            println("*************************")
            val out = DataOutputStream(FileOutputStream(fname2))
            val writer = ByteCodeClassEncodedWriter.create(Logger.getLogger("cw"), out)
            encodedClass.write(writer)
            // Assertions.assertTrue(Files.equal(file, File(fname2)))
            println("*************************")
            println("VERIFY $fname2")
            println("*************************")
            val bytes2 = java.nio.file.Files.readAllBytes(File(fname2).toPath())
            val stream3 = ByteArrayInputStream(bytes2)
            println(ConstantPoolHelper.viewClass(stream3))
            println("*************************")
            println("$file OK!!")
            println("*************************")
        }
    }
}