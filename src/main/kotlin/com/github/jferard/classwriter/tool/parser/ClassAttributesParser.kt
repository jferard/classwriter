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

import com.github.jferard.classwriter.encoded.EncodedClassFileAttributes
import com.github.jferard.classwriter.encoded.attribute.*
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.InnerClassesAttribute
import com.github.jferard.classwriter.internal.attribute.SourceFileAttribute
import java.io.DataInput
import java.io.IOException

/**
 * 4.1. The ClassFile Structure
 * ```
 *      u2             attributes_count;
 *      attribute_info attributes[attributes_count];
 * ```
 * Table 4.7-C. Predefined class file attributes (by location)
 */
class ClassAttributesParser(
        private val cfmParser: CFMAttributesParser,
        private val entries: List<EncodedConstantPoolEntry>) :
        Parser<EncodedClassFileAttributes> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedClassFileAttributes {
        val classAttributesCount = input.readShort().toInt()
        val encodedClassFileAttributes = (1..classAttributesCount).map {
            parseClassAttribute(input)
        }
        return EncodedClassFileAttributes(encodedClassFileAttributes)
    }

    @Throws(IOException::class)
    private fun parseClassAttribute(
            input: DataInput): EncodedClassFileAttribute<*, *, *> {
        val attributeNameIndex = input.readUnsignedShort()
        val attributeName = entries[attributeNameIndex - 1].utf8Text()
        return when (attributeName) {
            InnerClassesAttribute.INNER_CLASSES_NAME -> parseInnerClassesAttribute(
                    attributeNameIndex, input)
            SourceFileAttribute.SOURCE_FILE_NAME -> parseSourceFileAttribute(
                    attributeNameIndex, input)
            SourceDebugExtensionAttribute.SOURCE_DEBUG_EXTENSION_NAME -> parseSourceDebugExtensionAttribute(
                    attributeNameIndex, input)
            else -> cfmParser.parse(attributeNameIndex, input)
        }
    }

    private fun parseSourceDebugExtensionAttribute(attributeNameIndex: Int,
                                                   input: DataInput): EncodedClassFileAttribute<*, *, *> {
        val length = input.readInt()
        val debugExtension = ByteArray(length)
        input.readFully(debugExtension)
        return EncodedSourceDebugExtensionAttribute(attributeNameIndex, debugExtension)
    }

    private fun parseSourceFileAttribute(attributeNameIndex: Int,
                                         input: DataInput): EncodedClassFileAttribute<*, *, *> {
        val length = input.readInt()
        val sourceFileIndex = input.readUnsignedShort()
        return EncodedSourceFileAttribute(attributeNameIndex, sourceFileIndex)
    }

    private fun parseInnerClassesAttribute(attributeNameIndex: Int,
                                           input: DataInput): EncodedClassFileAttribute<*, *, *> {
        val length = input.readInt()
        val numberOfClasses = input.readUnsignedShort()
        val encodedInnerClasses = (1..numberOfClasses).map {
            val innerClassIndex = input.readUnsignedShort()
            val outerClassIndex = input.readUnsignedShort()
            val innerNameIndex = input.readUnsignedShort()
            val outerNameIndex = input.readUnsignedShort()
            EncodedInnerClass(innerClassIndex, outerClassIndex, innerNameIndex, outerNameIndex)
        }
        return EncodedInnerClassesAttribute(attributeNameIndex, encodedInnerClasses)
    }

    companion object {
        fun create(
                entries: List<EncodedConstantPoolEntry>): ClassAttributesParser {
            return ClassAttributesParser(CFMAttributesParser.create(entries), entries)
        }
    }

}