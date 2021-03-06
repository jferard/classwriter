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

import com.github.jferard.classwriter.encoded.EncodedClassFile
import java.io.DataInput
import java.io.IOException
import java.util.logging.Logger

/**
 * 4.1. The ClassFile Structure
 * Parse a class file
 */
class ClassFileParser(private val logger: Logger, private val headerParser: HeaderParser,
                      private val constantPoolParser: ConstantPoolParser,
                      private val interfacesParser: InterfacesParser) :
        Parser<EncodedClassFile> {
    @Throws(IOException::class)
    override fun parse(
            input: DataInput): EncodedClassFile {
        val header = headerParser.parse(input)
        val constantPool =
                constantPoolParser.parse(input)
        val entries =
                constantPool.entries
        val fieldsParser: FieldsParser = FieldsParser.create(logger, entries)
        val methodsParser: MethodsParser = MethodsParser.create(logger, entries)
        val classAttributesParser: ClassAttributesParser =
                ClassAttributesParser.create(logger, entries)
        val accessFlags = input.readShort().toInt()
        val thisIndex = input.readShort().toInt()
        val superIndex = input.readShort().toInt()
        val encodedInterfaces = interfacesParser.parse(input)
        val encodedFields = fieldsParser.parse(input)
        val encodedMethods = methodsParser.parse(input)
        val encodedAttributes = classAttributesParser.parse(input)
        return EncodedClassFile(header, constantPool, accessFlags, thisIndex, superIndex,
                encodedInterfaces, encodedFields, encodedMethods, encodedAttributes)
    }

}