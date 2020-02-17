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
import com.github.jferard.classwriter.internal.attribute.BootstrapMethodsAttribute
import com.github.jferard.classwriter.internal.attribute.InnerClassesAttribute
import com.github.jferard.classwriter.internal.attribute.SignatureAttribute
import com.github.jferard.classwriter.internal.attribute.SourceFileAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException

interface ClassAttributeParser :
        Parser<EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>> {
    @Throws(IOException::class)
    fun decode(attributeNameIndex: Int,
               input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        TODO("Not implemented")
    }

    companion object {
        operator fun get(attributeName: String): ClassAttributeParser {
            return when (attributeName) {
                SignatureAttribute.SIGNATURE_NAME -> SignatureAttributeParser()
                SourceFileAttribute.SOURCE_FILE_NAME -> SourceFileAttributeParser()
                InnerClassesAttribute.INNER_CLASSES_NAME -> InnerClassesAttributeParser()
                BootstrapMethodsAttribute.BOOTSTRAP_METHODS_NAME -> BootstrapMethodsAttributeParser()
                else -> throw RuntimeException("Not implemented: $attributeName")
            }
        }
    }
}