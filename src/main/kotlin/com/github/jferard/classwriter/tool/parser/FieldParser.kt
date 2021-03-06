/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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

import com.github.jferard.classwriter.encoded.EncodedField
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.FieldAttribute
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException
import java.util.*
import java.util.logging.Logger

/** ```field_info {
 * u2             access_flags;
 * u2             name_index;
 * u2             descriptor_index;
 * u2             attributes_count;
 * attribute_info attributes[attributes_count];
 * }
 * ``` *   */
class FieldParser(private val logger: Logger, private val attributesParser: FieldAttributesParser) :
        Parser<EncodedField> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedField {
        logger.finer("Parse field")
        val accessFlags = input.readShort().toInt()
        val nameIndex = input.readShort().toInt()
        val descriptorIndex = input.readShort().toInt()
        val attributesCount = input.readShort().toInt()
        val attributes: MutableList<EncodedFieldAttribute<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>, *, FieldAttributeEncodedWriter>> =
                ArrayList()
        for (i in 0 until attributesCount) {
            val encodedAttribute = attributesParser.parse(
                    input) as EncodedFieldAttribute<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>, *, FieldAttributeEncodedWriter>
            logger.finest("Parse attribute $i/$attributesCount: $encodedAttribute")
            attributes.add(encodedAttribute)
        }
        return EncodedField(accessFlags, nameIndex, descriptorIndex, attributes)
    }

    companion object {
        fun create(logger: Logger, entries: List<EncodedConstantPoolEntry>): FieldParser {
            return FieldParser(logger, FieldAttributesParser.create(logger, entries))
        }
    }

}