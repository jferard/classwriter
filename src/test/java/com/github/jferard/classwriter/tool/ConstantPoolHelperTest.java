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

package com.github.jferard.classwriter.tool;

import com.github.jferard.classwriter.api.FieldRef;
import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodRef;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.internal.instruction.Instruction;
import com.github.jferard.classwriter.internal.instruction.RawCodeBuilder;
import com.github.jferard.classwriter.pool.Utf8Entry;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

class ConstantPoolHelperTest {
    @Test
    public void test() throws IOException {
        Instruction mainCode = RawCodeBuilder.instance()
                .getstatic(FieldRef.create("java.lang.System", "out", PrintStream.class))
                .ldc(new Utf8Entry("Hello, World!")).invokevirtual(
                        new MethodRef(new PlainClassRef("java.io.PrintStream"), "println",
                                MethodDescriptor.builder().params(FieldTypeHelper.get(String.class))
                                        .build())).return_().build();

        GlobalContext context = GlobalContext.create();
        mainCode.encode(context, MethodContext.create(0));
        System.out.println(ConstantPoolHelper.viewPool(context));
    }

    @Test
    public void test2() throws IOException {
        final File file = new File(
                "./target/classes/com/github/jferard/classwriter/decoded/DecodedClassWritableFactory.class");
        System.out.println(file
                .getAbsolutePath());
        System.out.println(ConstantPoolHelper.viewClass(new FileInputStream(file)));

    }

}