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

import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ConstantPoolEncodableWriter
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEncodedWriter
import java.util.*

/**
 * 4.4 The Constant Pool
 */
class ConstantPool : Encodable<ConstantPool, ConstantPool, ConstantPoolEncodableWriter>,
        Encoded<ConstantPool, ConstantPool, ConstantPoolEncodedWriter> {
    private val indexByEncoded: MutableMap<EncodedConstantPoolEntry, Int> = HashMap()
    private val indexByEntry: MutableMap<ConstantPoolEntry, Int> = HashMap()
    private var index = 1

    /**
     * Add an entry if not already present, and return the index
     */
    fun add(entry: ConstantPoolEntry, context: GlobalContext): Int {
        val previousIndex = indexByEntry[entry]
        return if (previousIndex == null) {
            val index = entry.addToPool(context) // calls addEncoded
            indexByEntry[entry] = index
            index
        } else {
            previousIndex
        }
    }

    /**
     * Add an already encoded entry
     */
    fun addEncoded(encodedEntry: EncodedConstantPoolEntry): Int {
        indexByEncoded[encodedEntry] = index
        return index++
    }

    /**
     * @return the size of the pool
     */
    override val size: Int = indexByEntry.keys.map(ConstantPoolEntry::size).sum()

    fun count(): Int {
        return indexByEncoded.size
    }

    override fun write(encodedWriter: ConstantPoolEncodedWriter) {
        return encodedWriter.constantPool(entries, null)
    }

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): ConstantPool {
        return this
    }

    /**
     * Entries of the pool.
     */
    val entries: List<EncodedConstantPoolEntry>
        get() {
            val entries: MutableList<EncodedConstantPoolEntry?> =
                    ArrayList()
            for (e in indexByEncoded.entries) {
                val index = e.value
                while (index > entries.size - 1) {
                    entries.add(null)
                }
                entries[index] = e.key
            }
            return entries.filterNotNull()
        }

    /** 4.1: The constant_pool table is indexed from 1 to constant_pool_count - 1. */
    override fun toString(): String {
        return "Constant pool[\n${entries.mapIndexed { i, entry -> "${i+1}: $entry" }.joinToString(
                "\n")}\n]"
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ConstantPool {
        return this
    }

    override fun write(encodableWriter: ConstantPoolEncodableWriter) {
        TODO()
    }
}