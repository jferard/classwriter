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

import com.github.jferard.classwriter.api.*
import com.github.jferard.classwriter.api.instruction.RawCodeBuilder
import com.github.jferard.classwriter.bytecode.writer.ByteCodeClassEncodedWriter
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.internal.access.MethodAccess
import com.github.jferard.classwriter.pool.StringEntry
import org.junit.jupiter.api.Test
import java.io.DataOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.logging.Logger

internal class CompilerIT {
    @Test
    @Throws(IOException::class)
    fun testHelloWorld() {
        val sysOut = FieldRef.create(java.lang.System::class.java, "out")
        val psPrintln = MethodRef.create(java.io.PrintStream::class.java,
                "println", String::class.java)
        val mainCode = RawCodeBuilder.instance()
                .getstatic(sysOut)
                .ldc(StringEntry("Hello, World!"))
                .invokevirtual(psPrintln)
                .return_().build()
        val mainDescriptor =
                MethodDescriptor.builder().params(ValueType.array(ValueType.STRING)).build()
        val mainAccFlags = MethodAccess.builder().publicFlag().staticFlag().finalFlag()
                .build()
        val mainMethod = MethodBuilder("main", mainDescriptor, mainCode).access(mainAccFlags)
        val encodedClassFile = ClassFile.builder("HelloWorld")
                .access(ClassAccess.ACC_PUBLIC)
                .method(mainMethod)
                .encode()
        encodedClassFile.write(ByteCodeClassEncodedWriter.create(
                Logger.getAnonymousLogger(),
                DataOutputStream(FileOutputStream("HelloWorld.class"))))
    }
}