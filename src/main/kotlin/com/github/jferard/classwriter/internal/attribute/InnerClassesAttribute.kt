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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClassesAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.ClassFileAttributeEncodableWriter

/**
 * 4.7.6. The InnerClasses Attribute
 */
class InnerClassesAttribute internal constructor(
        private val innerClasses: List<InnerClass>) :
        ClassFileAttribute<InnerClassesAttribute, EncodedInnerClassesAttribute, ClassFileAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInnerClassesAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(INNER_CLASSES_NAME)
        val encodedInnerClasses: List<EncodedInnerClass> =
                innerClasses.map {
                    it.encode(context, MethodContext.create(0))
                }
        return EncodedInnerClassesAttribute(attributeNameIndex,
                encodedInnerClasses)
    }

    companion object {
        const val INNER_CLASSES_NAME: String = "InnerClasses"
    }

    override fun write(encodableWriter: ClassFileAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}