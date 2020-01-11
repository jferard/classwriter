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

import com.github.jferard.classwriter.Writable
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.ConstantPoolEntriesEncodedWriter
import java.io.StringWriter
import java.io.Writer

class ParsedConstantPoolEntriesSummaryEncodedWriter(private val output: Writer,
                                                    private val entries: List<EncodedConstantPoolEntry>) :
        ConstantPoolEntriesEncodedWriter {
    override fun classEntry(nameIndex: Int) {
        entries[nameIndex-1].write(this)
        // output.write(sw.toString().replace('/', '.'))
    }

    override fun utf8Entry(text: String) {
        output.append(text)
    }

    override fun invokeDynamicEntry(bootstrapMethodIndex: Int,
                                    descriptorIndex: Int) {
        TODO("not implemented")
    }

    override fun nameAndTypeEntry(nameIndex: Int,
                                  descriptorIndex: Int) {
        val decodedName = entries[nameIndex-1]
                .write(this) as Writable<Writer>
        val decodedDescriptor = entries[descriptorIndex-1]
                .write(this) as Writable<Writer>
        decodedName.write(output)
        output.append(':')
        val sw = StringWriter()
        decodedDescriptor.write(sw)
        output.write(sw.toString().replace('/', '.'))
    }

    override fun methodRefEntry(classIndex: Int,
                                nameAndTypeIndex: Int) {
        getFieldOrMethodRefDecodedSummary(classIndex, nameAndTypeIndex)
    }

    private fun getFieldOrMethodRefDecodedSummary(classIndex: Int,
                                                  nameAndTypeIndex: Int) {
        val decodedClassName = entries[classIndex-1]
                .write(this) as Writable<Writer>
        val decodedNameAndType = entries[nameAndTypeIndex-1]
                .write(this) as Writable<Writer>
        decodedClassName.write(output)
        output.write('~'.toInt())
        decodedNameAndType.write(output)
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

    override fun fieldRefEntry(classIndex: Int,
                               nameAndTypeIndex: Int) {
        return getFieldOrMethodRefDecodedSummary(classIndex, nameAndTypeIndex)
    }

    override fun stringEntry(index: Int) {
        TODO("not implemented")
    }

}