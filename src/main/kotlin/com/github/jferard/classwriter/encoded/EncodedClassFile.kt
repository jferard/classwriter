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

import com.github.jferard.classwriter.api.ClassFile
import com.github.jferard.classwriter.api.Header
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ConstantPool
import com.github.jferard.classwriter.writer.encoded.ClassEncodedWriter

class EncodedClassFile(private val header: Header,
                       private val pool: ConstantPool,
                       private val accessFlags: Int,
                       private val thisIndex: Int, private val superIndex: Int,
                       private val encodedInterfaces: EncodedInterfaces,
                       private val encodedFields: EncodedFields,
                       private val encodedMethods: EncodedMethods,
                       private val encodedAttributes: EncodedClassFileAttributes) :
        Encoded<ClassFile, EncodedClassFile, ClassEncodedWriter> {
    override fun write(encodedWriter: ClassEncodedWriter) {
        encodedWriter
                .classFile(header, pool, accessFlags, thisIndex,
                        superIndex, encodedInterfaces, encodedFields,
                        encodedMethods, encodedAttributes)
    }

    val entries: List<EncodedConstantPoolEntry> = pool.entries

    override fun getSize(pos: Int): Int = 0

    companion object {
        const val MAGIC_NUMBER = -0x35014542
        private const val THIS_CLASS_COUNT = 1
        private const val SUPER_CLASS_COUNT = 1
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): ClassFile {
        val thisClassRef = context.encodedPool.entries[thisIndex-1].toObject() as PlainClassRef
        TODO("not implemented")
    }

}