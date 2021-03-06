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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.encoded.EncodedMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.internal.attribute.CodeAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter

class Method(private val accessFlags: Int, private val name: String,
             private val descriptor: MethodDescriptor,
             private val codeAttribute: CodeAttribute) :
        Encodable<Method, EncodedMethod, ClassEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedMethod {
        val nameIndex = context.addUtf8ToPool(name)
        val descriptorIndex = context.addToPool(descriptor.toPoolEntry())
        val attributes: List<EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>> =
                listOf(codeAttribute.encode(context, MethodContext.create(0)))
        return EncodedMethod(accessFlags, nameIndex, descriptorIndex, attributes)
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}