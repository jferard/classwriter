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
package com.github.jferard.classwriter.tool.parser

import com.github.jferard.classwriter.encoded.pool.*
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.pool.ConstantTags
import java.io.DataInput
import java.io.IOException
import java.lang.Float

/**
 * 4.1. The ClassFile Structure
 * u2             constant_pool_count;
 * cp_info        constant_pool[constant_pool_count-1];
 *
 *
 * 4.4. The Constant Pool: Table 4.4-B. Constant pool tags (by tag)
 *
 *
 * Decode the constant pool
 */
class ConstantPoolParser :
        Parser<ConstantPool> {
    @Throws(IOException::class)
    override fun parse(
            input: DataInput): ConstantPool {
        val entryCount = input.readShort() - 1
        val constantPool =
                ConstantPool()
        repeat((0 until entryCount).count()) {
            constantPool.addEncoded(this.readNextEntry(input))
        }
        return constantPool
    }

    /**
     * Table 4.4-B. Constant pool tags (by tag)
     *
     * @param input the input
     * @return an encoded constant pool entry
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readNextEntry(
            input: DataInput): EncodedConstantPoolEntry {
        val tag = input.readByte().toInt()
        return this.readNextEntryFromTag(tag, input)
    }

    /**
     * Table 4.4-B. Constant pool tags (by tag)
     *
     * @param tag   the tag
     * @param input the input
     * @return an encoded constant pool entry
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readNextEntryFromTag(tag: Int,
                                     input: DataInput): EncodedConstantPoolEntry {
        when (tag.toShort()) {
            ConstantTags.CLASS -> return EncodedClassEntry(input.readShort().toInt())
            ConstantTags.FIELD_REF -> return EncodedFieldRefEntry(input.readShort().toInt(),
                    input.readShort().toInt())
            ConstantTags.METHOD_REF,
            ConstantTags.INTERFACE_METHOD_REF -> return EncodedMethodRefEntry(
                    input.readShort().toInt(), input.readShort().toInt())
            ConstantTags.STRING -> return EncodedStringEntry(input.readShort().toInt())
            ConstantTags.INTEGER -> return EncodedIntegerEntry(input.readInt())
            ConstantTags.FLOAT -> return EncodedFloatEntry(Float.intBitsToFloat(input.readInt()))
            ConstantTags.LONG -> return EncodedLongEntry(input.readLong())
            ConstantTags.DOUBLE -> return EncodedDoubleEntry(input.readDouble())
            ConstantTags.NAME_AND_TYPE -> return EncodedNameAndTypeEntry(input.readShort().toInt(),
                    input.readShort().toInt())
            ConstantTags.UTF8 -> return EncodedUtf8Entry(input.readUTF())
            ConstantTags.METHOD_HANDLE -> return EncodedMethodHandleEntry(input.readByte().toInt(),
                    input.readShort().toInt())
            ConstantTags.METHOD_TYPE -> return EncodedMethodTypeEntry(input.readShort().toInt())
            ConstantTags.INVOKE_DYNAMIC -> return EncodedInvokeDynamicEntry(
                    input.readShort().toInt(),
                    input.readShort().toInt())
            ConstantTags.MODULE, ConstantTags.PACKAGE -> throw RuntimeException("Not implemented")
            else -> throw java.lang.IllegalArgumentException(tag.toString())
        }
    }
}