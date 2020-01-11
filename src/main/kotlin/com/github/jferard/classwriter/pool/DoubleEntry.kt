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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

/**
 * 4.4.5. The CONSTANT_Long_info and CONSTANT_Double_info Structures
 *
 * ```
 * CONSTANT_Double_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 * ```
 */
class DoubleEntry(private val value: Double) : ConstantPoolEntry, EncodedConstantPoolEntry {
    override fun addToPool(constantPool: GlobalContext): Int {
        return constantPool.addEncodedToPool(this)
    }

    override fun size(): Int {
        return 2
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DoubleEntry) return false
        return other.value.compareTo(value) == 0
    }

    override fun hashCode(): Int {
        return java.lang.Double.hashCode(value)
    }

    override fun toVerificationType(): VerificationType {
        return VerificationType.DOUBLE
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedConstantPoolEntry {
        return this
    }

    override fun write(
            writableFactory: ConstantPoolEntriesEncodedWriter) {
        return writableFactory.doubleEntry(value)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ConstantPoolEntry {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = BytecodeHelper.BYTE_SIZE + BytecodeHelper.DOUBLE_SIZE

    override fun utf8Text(): String {
        throw IllegalArgumentException()
    }

    override fun toObject(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}