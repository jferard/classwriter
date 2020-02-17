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

import com.github.jferard.classwriter.api.BootstrapMethod
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedBootstrapMethodsAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.BootstrapMethodsAttributeEncodableWriter

class BootstrapMethodsAttribute(
        private val bootstrapMethods: List<BootstrapMethod>) :
        Encodable<BootstrapMethodsAttribute, EncodedBootstrapMethodsAttribute, BootstrapMethodsAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedBootstrapMethodsAttribute {
        val nameIndex: Int =
                context.addUtf8ToPool(BOOTSTRAP_METHODS_NAME)
        val encodedBootstrapMethods: List<EncodedBootstrapMethod> =
                bootstrapMethods.map { it.encode(context, MethodContext.create(0)) }
        return EncodedBootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods)
    }

    companion object {
        const val BOOTSTRAP_METHODS_NAME: String = "BootstrapMethods"
    }

    override fun write(encodableWriter: BootstrapMethodsAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}