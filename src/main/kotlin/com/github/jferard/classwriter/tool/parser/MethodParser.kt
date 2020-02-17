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

import com.github.jferard.classwriter.encoded.EncodedMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException
import java.util.logging.Logger

class MethodParser(private val logger: Logger, private val methodAttributeParser: MethodAttributeParser) :
        Parser<EncodedMethod> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedMethod {
        val accessFlags = input.readUnsignedShort()
        val nameIndex = input.readUnsignedShort()
        val descriptorIndex = input.readUnsignedShort()
        val attributesCount = input.readUnsignedShort()
        logger.finer("Parse method, name index=$nameIndex, attribute count=$attributesCount")
        val encodedAttributes =
                (1..attributesCount).map {
                    methodAttributeParser.parse(
                            input) as EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>
                }
        return EncodedMethod(accessFlags, nameIndex, descriptorIndex, encodedAttributes)
    }

    companion object {
        fun create(logger: Logger,
                entries: List<EncodedConstantPoolEntry>): MethodParser {
            return MethodParser(logger,
                    MethodAttributeParser(logger, CodeAttributeAttributeParser(logger, entries),
                            InstructionsParser(logger), AnnotationParser(logger), entries))
        }
    }

}