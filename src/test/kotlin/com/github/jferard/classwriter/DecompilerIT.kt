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

internal class DecompilerIT {
    /** javap -v -p -s -sysinfo -constants target/classes/com/github/jferard/classwriter/api/ClassFile.class */
    @Test
    @Throws(IOException::class)
    fun testDecompileAll() {
        File("target/classes/com/github/jferard/classwriter").walk()
                .filter { it.extension == "class" }.forEach { testHelper(it) }
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
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeVerificationTypeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeCodeAttributeAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeMethodAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeConstantPoolEntriesEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeConstantPoolEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/bytecode/writer/ByteCodeBootstrapMethodsAttributeEncodedWriter.class",
                            "target/classes/com/github/jferard/classwriter/Attribute.class",
                            "target/classes/com/github/jferard/classwriter/encoded/EncodedMethod.class"
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