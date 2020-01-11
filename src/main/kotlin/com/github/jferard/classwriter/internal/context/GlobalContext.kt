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
package com.github.jferard.classwriter.internal.context

import com.github.jferard.classwriter.Writable
import com.github.jferard.classwriter.api.BootstrapMethod
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.BootstrapMethodsAttributeBuilder
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEncodedWriter
import com.github.jferard.classwriter.pool.Utf8Entry

/**
 * 4.4 The Constant Pool
 */
class GlobalContext(private val pool: ConstantPool,
                    private val bootstrapMethodsAttributeBuilder: BootstrapMethodsAttributeBuilder) {

    fun addToPool(entry: ConstantPoolEntry): Int {
        return pool.add(entry, this)
    }

    fun addEncodedToPool(encodedEntry: EncodedConstantPoolEntry): Int {
        return pool.addEncoded(encodedEntry)
    }

    fun addBootstrapMethod(bootstrapMethod: BootstrapMethod): Int {
        return bootstrapMethodsAttributeBuilder.add(bootstrapMethod)
    }

    fun addUtf8ToPool(text: String): Int {
        return addToPool(Utf8Entry(text))
    }

    fun getWritablePool(
            writableFactory: ConstantPoolEncodedWriter) {
        return encodedPool.write(writableFactory)
    }

    val encodedPool: ConstantPool
        get() = pool.encode(this, MethodContext.create(0))

    companion object {
        fun create(): GlobalContext {
            return GlobalContext(ConstantPool(),
                    BootstrapMethodsAttributeBuilder())
        }
    }

}