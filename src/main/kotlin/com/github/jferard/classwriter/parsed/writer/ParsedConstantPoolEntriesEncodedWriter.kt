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
package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.tool.byteviewer.ByteViewerFactory
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import java.io.Writer
import java.nio.charset.StandardCharsets
import kotlin.experimental.and

class ParsedConstantPoolEntriesEncodedWriter(private val output: Writer,
                                             private val entries: List<EncodedConstantPoolEntry>,
                                             private val bootstrapMethods: List<EncodedBootstrapMethod>,
                                             private val parsedConstantPoolEntriesSummaryWriter: ParsedConstantPoolEntriesSummaryEncodedWriter,
                                             private val parsedBootstrapMethodsAttributeWriter: ParsedBootstrapMethodsAttributeEncodedWriter) :
        ConstantPoolEntriesEncodedWriter {
    override fun classEntry(nameIndex: Int) {
        output.append("ConstantTags.CLASS").append(", ")
                .append(ByteViewerFactory.hex(nameIndex shr 8)).append(", ")
                .append(ByteViewerFactory.hex(nameIndex)).append(", // #")
                .append(nameIndex.toString()).append(" -> \"")
        //parsedConstantPoolEntriesSummaryWriter.classEntry(nameIndex).write(output)
        output.append("\"")
    }

    override fun utf8Entry(text: String) {
        val bytes = text.toByteArray(StandardCharsets.UTF_8)
        val str = bytes.indices.joinToString(", ") {
            val b: Int = (bytes[it] and 0xff.toByte()).toInt()
            when {
                b >= 32 -> ByteViewerFactory.chr(b.toChar())
                else -> ByteViewerFactory.hex(b)
            }
        }
        val length = text.length
        output.write(String.format("%s, %s, %s, %s, // %s", "ConstantTags.UTF8",
                ByteViewerFactory.hex(length shr 8),
                ByteViewerFactory.hex(length), str,
                "u\"$text\""))
    }

    override fun invokeDynamicEntry(bootstrapMethodIndex: Int,
                                    descriptorIndex: Int) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s", "ConstantTags.INVOKEDYNAMIC",
                ByteViewerFactory.hex(bootstrapMethodIndex shr 8),
                ByteViewerFactory.hex(bootstrapMethodIndex),
                ByteViewerFactory.hex(descriptorIndex shr 8),
                ByteViewerFactory.hex(descriptorIndex),
                "#$bootstrapMethodIndex:#$descriptorIndex -> "))
        bootstrapMethods[bootstrapMethodIndex]
                .write(parsedBootstrapMethodsAttributeWriter)
        output.write(':'.toInt())
        entries[descriptorIndex-1].write(
                parsedConstantPoolEntriesSummaryWriter)
    }

    override fun nameAndTypeEntry(nameIndex: Int, descriptorIndex: Int) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s", "ConstantTags.NAMEANDTYPE",
                ByteViewerFactory.hex(nameIndex shr 8),
                ByteViewerFactory.hex(nameIndex),
                ByteViewerFactory.hex(descriptorIndex shr 8),
                ByteViewerFactory.hex(descriptorIndex),
                "#$nameIndex:#$descriptorIndex -> "))
        entries[nameIndex-1].write(
                parsedConstantPoolEntriesSummaryWriter)
        output.write(':'.toInt())
        entries[descriptorIndex-1].write(parsedConstantPoolEntriesSummaryWriter)
        // output.write(sw.toString().replace('/', '.'))
    }

    override fun methodRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        return createFieldOrMethdRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.METHODREF")
    }

    override fun doubleEntry(value: Double) {
        TODO("not implemented")
    }

    override fun longEntry(value: Long) {
        TODO("not implemented")
    }

    override fun floatEntry(value: Float) {
        TODO("not implemented")
    }

    override fun integerEntry(value: Int) {
        TODO("not implemented")
    }

    override fun methodHandleEntry(kind: Int, index: Int) {
        TODO("not implemented")
    }

    override fun methodTypeEntry(descriptorIndex: Int) {
        TODO("not implemented")
    }

    override fun fieldRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        return createFieldOrMethdRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.FIELDREF")
    }

    private fun createFieldOrMethdRefDecoded(classIndex: Int, nameAndTypeIndex: Int,
                                             code: String) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s",
                code, ByteViewerFactory.hex(classIndex shr 8), ByteViewerFactory.hex(classIndex),
                ByteViewerFactory.hex(nameAndTypeIndex shr 8),
                ByteViewerFactory.hex(nameAndTypeIndex),
                "#$classIndex~#$nameAndTypeIndex -> "))
        entries[classIndex-1]
                .write(parsedConstantPoolEntriesSummaryWriter)
        output.append('~')
        entries[nameAndTypeIndex-1]
                .write(parsedConstantPoolEntriesSummaryWriter)
    }

    override fun stringEntry(index: Int) {
        TODO("not implemented")
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   bootstrapMethods: List<EncodedBootstrapMethod>): ParsedConstantPoolEntriesEncodedWriter {
//            return ParsedConstantPoolEntriesEncodedWriter(
//                    output, entries, bootstrapMethods,
//                    ParsedConstantPoolEntriesSummaryEncodedWriter(entries),
//                    ParsedBootstrapMethodsAttributeEncodedWriter()
//                    )
            TODO()
        }
    }

}