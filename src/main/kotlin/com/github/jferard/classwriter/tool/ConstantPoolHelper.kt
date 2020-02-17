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
package com.github.jferard.classwriter.tool

import com.github.jferard.classwriter.encoded.EncodedClassFile
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.text.writer.*
import com.github.jferard.classwriter.tool.parser.ClassFileParser
import com.github.jferard.classwriter.tool.parser.ConstantPoolParser
import com.github.jferard.classwriter.tool.parser.HeaderParser
import com.github.jferard.classwriter.tool.parser.InterfacesParser
import java.io.*
import java.util.logging.Level
import java.util.logging.Logger

object ConstantPoolHelper {
    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun viewPool(context: GlobalContext): String { /*
        ConstantPoolWritableFactory<Writer> writableFactory =
                new DecodedConstantPoolWritableFactory();
        Writer sw = new StringWriter();
        final Writable<Writer> writablePool = (Writable<Writer>) context.getWritablePool(writableFactory);
        writablePool.write(sw);
        */
        return "sw.toString();"
    }

    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun viewClass(inputStream: InputStream): String {
        val encodedClassFile =
                parseClassByteCode(inputStream)
        val w: Writer = StringWriter()
        val entries = encodedClassFile.entries
        val writer = TextClassEncodedWriter.create(w, entries)
        encodedClassFile.write(writer)
        return w.toString()
    }

    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun parseClassByteCode(
            inputStream: InputStream): EncodedClassFile {
        val logger = Logger.getLogger("cw")
        logger.level = Level.FINEST
        logger.parent.handlers.forEach { it.level = Level.FINEST }
        logger.handlers.forEach { it.level = Level.FINEST }
        logger.fine("Log")
        val input: DataInput = DataInputStream(inputStream)
        val headerParser = HeaderParser(logger)
        val constantPoolParser = ConstantPoolParser(logger)
        val interfacesParser = InterfacesParser(logger)
        val decoder = ClassFileParser(logger, headerParser,
                constantPoolParser, interfacesParser)
        val encodedClassFile =
                decoder.parse(input)
        logger.parent.handlers.forEach { it.flush() }
        logger.handlers.forEach { it.flush() }
        return encodedClassFile
    }
}