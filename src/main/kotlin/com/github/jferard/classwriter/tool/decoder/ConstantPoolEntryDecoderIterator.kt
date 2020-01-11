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
package com.github.jferard.classwriter.tool.decoder

import com.github.jferard.classwriter.pool.ConstantTags
import java.io.DataInput
import java.io.IOException
import java.util.*

class ConstantPoolEntryParserIterator(private val input: DataInput, private var entryCount: Int) :
        MutableIterator<ConstantPoolEntryParser> {
    override fun next(): ConstantPoolEntryParser {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        return try {
            val tag = input.readByte().toInt()
            val ret = readNext(tag)
            entryCount--
            ret
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    override fun hasNext(): Boolean {
        return entryCount > 0
    }

    @Throws(IOException::class)
    private fun readNext(tag: Int): ConstantPoolEntryParser {
        return when (tag.toShort()) {
            ConstantTags.CLASS -> ClassEntryParser(input.readShort())
            ConstantTags.FIELD_REF -> FieldRefEntryParser(input.readShort(),
                    input.readShort())
            ConstantTags.METHOD_REF -> MethodRefEntryParser(input.readShort(),
                    input.readShort())
            ConstantTags.INTERFACE_METHOD_REF -> InterfaceMethodRefEntryParser(
                    input.readShort(),
                    input.readShort())
            ConstantTags.STRING -> StringEntryParser(input.readShort().toInt())
            ConstantTags.INTEGER -> IntegerEntryParser(input.readInt())
            ConstantTags.FLOAT -> FloatEntryParser(
                    java.lang.Float.intBitsToFloat(input.readInt()))
            ConstantTags.LONG -> LongEntryParser(input.readLong())
            ConstantTags.DOUBLE -> DoubleEntryParser(input.readDouble())
            ConstantTags.NAME_AND_TYPE -> NameAndTypeEntryParser(input.readShort(),
                    input.readShort())
            ConstantTags.UTF8 -> Utf8EntryParser(input.readUTF())
            ConstantTags.METHOD_HANDLE -> MethodHandleEntryParser(input.readByte(),
                    input.readShort())
            ConstantTags.METHOD_TYPE -> MethodTypeEntryParser(input.readShort())
            ConstantTags.INVOKE_DYNAMIC -> InvokeDynamicEntryParser(input.readShort(),
                    input.readShort())
            else -> UnknownEntryParser(tag)
        }
    }

    override fun remove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}