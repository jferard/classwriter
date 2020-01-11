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

import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.context.GlobalContext

/**
 * Entries of the constant pool
 * 4.4.1 -> 4.4.10
 */
interface ConstantPoolEntry : Encodable<ConstantPoolEntry, EncodedConstantPoolEntry, ClassEncodableWriter> {
    /**
     * Add this entry to the pool
     * @param constantPool the pool
     * @return the index
     */
    fun addToPool(constantPool: GlobalContext): Int

    /**
     * 2.6.2. Operand Stacks
     * At any point in time, an operand stack has an associated depth, where a value of type long
     * or double contributes two units to the depth and a value of any other type contributes one
     * unit.
     */
    fun size(): Int

    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
    fun toVerificationType(): VerificationType
}