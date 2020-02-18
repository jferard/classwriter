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
import com.github.jferard.classwriter.api.ClassFile.Companion.builder
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.instruction.RawCodeBuilder
import com.github.jferard.classwriter.bytecode.writer.ByteCodeClassEncodedWriter
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.internal.access.MethodAccess
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.StringEntry
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import org.junit.jupiter.api.Test
import java.io.DataOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream
import java.util.logging.Logger

internal class CompilerIT {
    @Test
    @Throws(IOException::class)
    fun testHelloWorld() {
        val classBuilder =
                builder("HelloWorld")
                        .access(ClassAccess.ACC_PUBLIC)
        val mainCode =
                RawCodeBuilder.instance()
                        .getstatic(FieldRef.create("java.lang.System", "out", PrintStream::class.java))
                        .ldc(StringEntry("Hello, World!"))
                        .invokevirtual(MethodRef(
                                PlainClassRef("java.io.PrintStream"), "println",
                                builder().params(get(String::class.java))
                                        .build()))
                        .return_().build()
        val descriptor =
                MethodDescriptor.builder().params(ValueType.array(ValueType.STRING)).build()
        val method = MethodBuilder("main",
                descriptor, mainCode)
                .access(MethodAccess.builder().publicFlag().staticFlag().finalFlag()
                        .build())
        classBuilder.method(method)
        classBuilder.build().encode(GlobalContext.create(), MethodContext.create(0)).write(ByteCodeClassEncodedWriter.create(
                Logger.getAnonymousLogger(), DataOutputStream(FileOutputStream("HelloWorld.class"))))
    }
}