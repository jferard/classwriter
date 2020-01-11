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
package com.github.jferard.classwriter.tool

import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.parsed.writer.*
import com.github.jferard.classwriter.tool.decoder.ClassFileParser
import com.github.jferard.classwriter.tool.decoder.ConstantPoolParser
import com.github.jferard.classwriter.tool.decoder.HeaderParser
import com.github.jferard.classwriter.tool.decoder.InterfacesParser
import java.io.*

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
        val input: DataInput = DataInputStream(inputStream)
        val w: Writer = StringWriter()
        val headerParser = HeaderParser()
        val constantPoolParser = ConstantPoolParser()
        val interfacesParser = InterfacesParser()
        val decoder = ClassFileParser(headerParser,
                constantPoolParser, interfacesParser)
        val encodedClassFile =
                decoder.parse(input)
        val entries = encodedClassFile.entries
        val writer = TextClassEncodedWriter(w, TextConstantPoolEncodedWriter(w,
                TextConstantPoolEntriesEncodedWriter(w, entries, arrayListOf(),
                        TextConstantPoolEntriesSummaryEncodedWriter(w, entries),
                        ParsedBootstrapMethodsAttributeEncodedWriter(w))))
        encodedClassFile.write(writer)
        return w.toString()
    }
}