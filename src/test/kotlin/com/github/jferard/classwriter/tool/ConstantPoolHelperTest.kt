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
package com.github.jferard.classwriter.tool

import com.github.jferard.classwriter.api.FieldRef.Companion.create
import com.github.jferard.classwriter.api.MethodDescriptor.Companion.builder
import com.github.jferard.classwriter.api.MethodRef
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder
import com.github.jferard.classwriter.pool.Utf8Entry
import com.github.jferard.classwriter.tool.ConstantPoolHelper.viewClass
import com.github.jferard.classwriter.tool.ConstantPoolHelper.viewPool
import com.github.jferard.classwriter.tool.FieldTypeHelper.get
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.PrintStream

internal class ConstantPoolHelperTest {
    @Test
    @Throws(IOException::class)
    fun test() {
        val mainCode =
                RawCodeBuilder.instance()
                        .getstatic(
                                create("kotlin.lang.System", "out", PrintStream::class.java))
                        .ldc(Utf8Entry("Hello, World!"))
                        .invokevirtual(
                                MethodRef(
                                        PlainClassRef("kotlin.io.PrintStream"), "println",
                                        builder().params(
                                                get(
                                                        String::class.java))
                                                .build())).return_().build()
        val context = GlobalContext.create()
        mainCode.encode(context, MethodContext.create(0))
        println(viewPool(context))
    }

    @Test
    @Throws(IOException::class)
    fun test2() {
        val file = File(
                "./target/classes/com/github/jferard/classwriter/decoded/DecodedClassWritableFactory.class")
        println(file
                .absolutePath)
        println(viewClass(FileInputStream(file)))
    }
}