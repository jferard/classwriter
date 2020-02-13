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

import com.github.jferard.classwriter.encoded.EncodedMethods
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import java.io.DataInput
import java.io.IOException
import java.util.logging.Logger

/**
 * 4.1. The ClassFile Structure
 * u2             methods_count;
 * method_info    methods[methods_count];
 */
class MethodsParser(private val logger: Logger, private val methodParser: MethodParser) :
        Parser<EncodedMethods> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedMethods {
        logger.finer("Parse methods")
        val methodsCount = input.readShort().toInt()
        val encodedMethods = (0 until methodsCount).map {
            methodParser.parse(input)
        }
        return EncodedMethods(encodedMethods)
    }

    companion object {
        fun create(logger: Logger,
                entries: List<EncodedConstantPoolEntry>): MethodsParser {
            return MethodsParser(logger, MethodParser.create(logger, entries))
        }
    }

}