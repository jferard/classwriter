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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.encoded.attribute.EncodedSourceFileAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassFileAttributeEncodableWriter

/**
 * 4.7.10. The SourceFile Attribute
 */
class SourceFileAttribute(private val sourceFileName: String) :
        ClassFileAttribute<SourceFileAttribute, EncodedSourceFileAttribute, ClassFileAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedSourceFileAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(SOURCE_FILE_NAME)
        val sourceFileNameIndex: Int = context.addUtf8ToPool(sourceFileName)
        return EncodedSourceFileAttribute(attributeNameIndex, sourceFileNameIndex)
    }

    companion object {
        const val SOURCE_FILE_NAME: String = "SourceFile"
    }

    override fun write(encodableWriter: ClassFileAttributeEncodableWriter) {
        TODO("not implemented")
    }
}