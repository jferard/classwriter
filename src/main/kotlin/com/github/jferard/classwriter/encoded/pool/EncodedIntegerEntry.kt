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
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter

class EncodedIntegerEntry(private val value: Int) : EncodedConstantPoolEntry {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is EncodedIntegerEntry) return false
        return other.value == value
    }

    override fun hashCode(): Int {
        return Integer.hashCode(value)
    }

    override fun write(
            encodedWriter: ConstantPoolEntriesEncodedWriter) {
        return encodedWriter.integerEntry(value)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ConstantPoolEntry {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int = BytecodeHelper.BYTE_SIZE + BytecodeHelper.INT_SIZE

    override fun utf8Text(): String {
        throw IllegalArgumentException()
    }

    override fun toObject(): Any {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "IntegerEntry[$value]"
    }
}