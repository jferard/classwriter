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

import com.github.jferard.classwriter.encoded.attribute.*
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.ConstantValueAttributeFactory
import java.io.DataInput
import java.io.IOException

class FieldAttributesParser(private val cfmParser: CFMAttributesParser,
                            private val entries: List<EncodedConstantPoolEntry>) :
        Parser<EncodedFieldAttribute<*, *, *>> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedFieldAttribute<*, *, *> {
        val index = input.readUnsignedShort()
        val constantPoolEntry = entries[index - 1]
        return when (constantPoolEntry.utf8Text()) {
            ConstantValueAttributeFactory.CONSTANT_VALUE_NAME -> {
                check(input.readInt() == 2)
                val constantIndex = input.readShort().toInt()
                EncodedConstantValueAttribute(index, constantIndex)
            }
            else -> cfmParser.parse(index, input)
        }
    }

    companion object {
        fun create(entries: List<EncodedConstantPoolEntry>) : FieldAttributesParser {
            return FieldAttributesParser(CFMAttributesParser.create(entries), entries)
        }
    }
}