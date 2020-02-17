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
package com.github.jferard.classwriter.internal.descriptor

import com.github.jferard.classwriter.tool.FieldTypeHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FieldTypeTest {
    @Test
    fun test() {
        val ftp = FieldTypeHelper
        Assertions.assertEquals("I", ftp.get(Int::class.javaPrimitiveType!!).toString())
        Assertions.assertEquals("[[I",
                ftp.get(Array<IntArray>::class.java).toString())
        Assertions.assertEquals("Lcom/github/jferard/classwriter/tool/FieldTypeHelper;",
                ftp.get(FieldTypeHelper::class.java).toString())
        Assertions.assertEquals("[Lcom/github/jferard/classwriter/tool/FieldTypeHelper;",
                ftp.get(Array<FieldTypeHelper>::class.java).toString())
    }
}