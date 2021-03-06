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

import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedSourceFileAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException

/**
 * 4.7.10. The SourceFile Attribute
 * ```
 * SourceFile_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 sourcefile_index;
 * }
 * ```
 */
class SourceFileAttributeParser : ClassAttributeParser {
    @Throws(IOException::class)
    override fun decode(attributeNameIndex: Int,
                        input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        check(input!!.readInt() == 2)
        val sourceFileIndex = input.readShort().toInt()
        return EncodedSourceFileAttribute(attributeNameIndex, sourceFileIndex)
    }

    override fun parse(
            input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }
}