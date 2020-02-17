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
package com.github.jferard.classwriter.encoded.pool

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter

/**
 * 4.4.2. The CONSTANT_Fieldref_info, CONSTANT_Methodref_info, and
 * CONSTANT_InterfaceMethodref_info Structures
 * ```CONSTANT_Methodref_info {
 * u1 tag;
 * u2 class_index;
 * u2 name_and_type_index;
 * }
* ``` *
 */
class EncodedMethodRefEntry(private val classIndex: Int, private val nameAndTypeIndex: Int) :
        EncodedConstantPoolEntry {
    override fun write(
            encodedWriter: ConstantPoolEntriesEncodedWriter) {
        return encodedWriter.methodRefEntry(classIndex, nameAndTypeIndex)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ConstantPoolEntry {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.BYTE_SIZE + 2 * BytecodeHelper.SHORT_SIZE

    override fun utf8Text(): String {
        throw IllegalArgumentException()
    }

    override fun toObject(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "EncodedMethodRefEntry[$classIndex, $nameAndTypeIndex]"
    }
}