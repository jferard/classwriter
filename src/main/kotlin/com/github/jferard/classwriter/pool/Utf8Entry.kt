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
import java.nio.charset.StandardCharsets

class Utf8Entry(private val text: String) : ConstantPoolEntry, EncodedConstantPoolEntry {
    override fun addToPool(pool: GlobalContext): Int {
        return pool.addEncodedToPool(this)
    }

    override fun size(): Int {
        return BytecodeHelper.BYTE_SIZE
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Utf8Entry) return false
        return other.text == text
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }

    override fun toVerificationType(): VerificationType {
        throw IllegalArgumentException()
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedConstantPoolEntry {
        return this
    }

    override fun toString(): String {
        return "Utf8Entry['$text']"
    }

    override fun write(
            encodedWriter: ConstantPoolEntriesEncodedWriter) {
        return encodedWriter.utf8Entry(text)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ConstantPoolEntry {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override val size: Int
        get() = BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE +
                text.toByteArray(StandardCharsets.UTF_8).size

    override fun utf8Text(): String {
        return text
    }

    override fun toObject(): Any {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

}