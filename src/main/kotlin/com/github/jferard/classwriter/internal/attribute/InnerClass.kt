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

import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.pool.Utf8Entry
import com.github.jferard.classwriter.writer.encodable.ClassFileAttributeEncodableWriter

class InnerClass(private val innerClassRef: PlainClassRef, private val outerClassRef: PlainClassRef,
                 private val innerAccessFlags: Int) :
        Encodable<InnerClass, EncodedInnerClass, ClassFileAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedInnerClass {
        val innerClassIndex: Int = context.addToPool(innerClassRef.toEntry())
        val innerClassNameIndex: Int = if (innerClassRef.isAnonymous) {
            0
        } else {
            context.addToPool(Utf8Entry(
                    innerClassRef.internalName))
        }
        val outerClassIndex: Int = if (outerClassRef == null) {
            0
        } else {
            context.addToPool(outerClassRef.toEntry())
        }
        assert(innerClassNameIndex != 0 || outerClassIndex == 0)
        return EncodedInnerClass(innerClassIndex, outerClassIndex, innerClassNameIndex,
                innerAccessFlags)
    }

    companion object {
        val SIZE: Int = 4 * BytecodeHelper.SHORT_SIZE
    }

    override fun write(encodableWriter: ClassFileAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}