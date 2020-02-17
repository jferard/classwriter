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
package com.github.jferard.classwriter.encoded

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.internal.attribute.ClassFileAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter

class EncodedClassFileAttributes(
        private val encodedAttributes: List<EncodedClassFileAttribute<*, *, *>>) :
        Encoded<List<ClassFileAttribute<*, *, *>>, EncodedClassFileAttributes, ClassFileAttributeEncodedWriter> {
    override fun write(encodedWriter: ClassFileAttributeEncodedWriter) {
        return encodedWriter.classFileAttributes(
                encodedAttributes as List<EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>>)
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.SHORT_SIZE +
            Sized.listSize(0, encodedAttributes)

    override fun toString(): String {
        return String.format("Attributes %s", encodedAttributes)
    }

    val bootstrapMethods: List<EncodedBootstrapMethod>
        get() {
            for (e in encodedAttributes) {
                val encodedBootstrapMethods =
                        e.oGetBootstrapMethods()
                if (encodedBootstrapMethods != null) {
                    return encodedBootstrapMethods
                }
            }
            return emptyList()
        }

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): List<ClassFileAttribute<*, *, *>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}