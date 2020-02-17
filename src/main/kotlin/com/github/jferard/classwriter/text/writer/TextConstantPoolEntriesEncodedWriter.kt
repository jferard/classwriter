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
package com.github.jferard.classwriter.text.writer

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper.hex
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import java.io.Writer
import java.nio.charset.StandardCharsets
import kotlin.experimental.and

class TextConstantPoolEntriesEncodedWriter(private val output: Writer,
                                           private val entries: List<EncodedConstantPoolEntry>,
                                           private val bootstrapMethods: List<EncodedBootstrapMethod>,
                                           private val textConstantPoolEntriesSummaryWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                                           private val textBootstrapMethodsAttributeWriter: TextBootstrapMethodsAttributeEncodedWriter) :
        ConstantPoolEntriesEncodedWriter {
    override fun classEntry(nameIndex: Int) {
        output.write(
                "ConstantTags.CLASS, %s, %s, // #%s -> ".format(
                        hex(nameIndex shr 8),
                        hex(nameIndex), nameIndex))
        textConstantPoolEntriesSummaryWriter.classEntry(nameIndex)
    }

    override fun utf8Entry(text: String) {
        if (text.isEmpty()) {
            output.write("ConstantTags.UTF8, 0x00, 0x00, // %s (len: 0)")
            return
        }
        val bytes = text.toByteArray(StandardCharsets.UTF_8)
        val str = bytes.indices.joinToString(", ") {
            val b: Int = (bytes[it] and 0xff.toByte()).toInt()
            when {
                b >= 32 -> TextEncodedWriterHelper.chr(b.toChar())
                else -> hex(b)
            }
        }
        val length = text.length
        output.write("ConstantTags.UTF8, %s, %s, %s, // %s (len: %s)".format(
                hex(length shr 8),
                hex(length), str,
                "u\"$text\"", text.length))
    }

    override fun invokeDynamicEntry(bootstrapMethodIndex: Int,
                                    descriptorIndex: Int) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s", "ConstantTags.INVOKEDYNAMIC",
                hex(bootstrapMethodIndex shr 8),
                hex(bootstrapMethodIndex),
                hex(descriptorIndex shr 8),
                hex(descriptorIndex),
                "#$bootstrapMethodIndex:#$descriptorIndex -> "))
        bootstrapMethods[bootstrapMethodIndex]
                .write(textBootstrapMethodsAttributeWriter)
        output.write(':'.toInt())
        entries[descriptorIndex - 1].write(
                textConstantPoolEntriesSummaryWriter)
    }

    override fun nameAndTypeEntry(nameIndex: Int, descriptorIndex: Int) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s", "ConstantTags.NAME_AND_TYPE",
                hex(nameIndex shr 8),
                hex(nameIndex),
                hex(descriptorIndex shr 8),
                hex(descriptorIndex),
                "#$nameIndex:#$descriptorIndex -> "))
        entries[nameIndex - 1].write(
                textConstantPoolEntriesSummaryWriter)
        output.write(':'.toInt())
        entries[descriptorIndex - 1].write(textConstantPoolEntriesSummaryWriter)
        // output.write(sw.toString().replace('/', '.'))
    }

    override fun methodRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        return createFieldOrMethodRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.METHOD_REF")
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
        output.write("%s, %s, %s, %s, %s, // %s".format("ConstantTags.INTEGER",
                hex(value shr 24),
                hex(value shr 16),
                hex(value shr 8), hex(value),
                value))
    }

    override fun methodHandleEntry(kind: Int, index: Int) {
        TODO("not implemented")
    }

    override fun methodTypeEntry(descriptorIndex: Int) {
        TODO("not implemented")
    }

    override fun fieldRefEntry(classIndex: Int, nameAndTypeIndex: Int) {
        return createFieldOrMethodRefDecoded(classIndex, nameAndTypeIndex, "ConstantTags.FIELD_REF")
    }

    private fun createFieldOrMethodRefDecoded(classIndex: Int, nameAndTypeIndex: Int,
                                              code: String) {
        output.write(String.format("%s, %s, %s, %s, %s, // %s",
                code, hex(classIndex shr 8),
                hex(classIndex),
                hex(nameAndTypeIndex shr 8),
                hex(nameAndTypeIndex),
                "#$classIndex~#$nameAndTypeIndex -> "))
        entries[classIndex - 1]
                .write(textConstantPoolEntriesSummaryWriter)
        output.append('~')
        entries[nameAndTypeIndex - 1]
                .write(textConstantPoolEntriesSummaryWriter)
    }

    override fun stringEntry(index: Int) {
        output.write("ConstantTags.STRING, %s, %s, // string: ".format(
                hex(index shr 8), hex(index)))
        entries[index - 1].write(textConstantPoolEntriesSummaryWriter)
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   bootstrapMethods: List<EncodedBootstrapMethod>): TextConstantPoolEntriesEncodedWriter {
            return TextConstantPoolEntriesEncodedWriter(output, entries, bootstrapMethods,
                    TextConstantPoolEntriesSummaryEncodedWriter(output, entries),
                    TextBootstrapMethodsAttributeEncodedWriter(output))
        }
    }

}