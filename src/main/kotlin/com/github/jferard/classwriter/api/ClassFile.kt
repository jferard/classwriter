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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.Field
import com.github.jferard.classwriter.encoded.*
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.internal.attribute.ClassFileAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter

/**
 * 4.1. The ClassFile Structure
 */
class ClassFile(private val header: Header, private val accessFlags: Int,
                private val thisClassRef: PlainClassRef,
                private val superClassRef: PlainClassRef?,
                private val interfaces: List<PlainClassRef>,
                private val fields: List<Field>,
                private val methods: List<Method>,
                private val attributes: List<ClassFileAttribute<*, EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>, *>>) :
        Encodable<ClassFile, EncodedClassFile, ClassEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedClassFile {
        val pool = context.encodedPool
        val thisIndex = context.addToPool(thisClassRef.toEntry())
        val superIndex = if (superClassRef == null) 0 else context.addToPool(
                superClassRef.toEntry())
        val encodedInterfaces =
                EncodedInterfaces(
                        interfaces.map { context.addToPool(it.toEntry()) })
        val encodedFields = EncodedFields(
                fields.map { it.encode(context, MethodContext.create(0)) })
        val encodedMethods = EncodedMethods(
                methods.map { it.encode(context, MethodContext.create(0)) })
        // add bootstrap methods to attributes here
        val encodedAttributes =
                EncodedClassFileAttributes(attributes.map {
                    it.encode(context, MethodContext.create(0))
                })
        return EncodedClassFile(header, pool, accessFlags, thisIndex, superIndex,
                encodedInterfaces, encodedFields, encodedMethods, encodedAttributes)
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun builder(className: String): ClassFileBuilder {
            return ClassFileBuilder(PlainClassRef(className))
        }
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        encodableWriter.classFile(header, accessFlags,
                thisClassRef, superClassRef, interfaces,
                fields, methods, attributes)
        /*
        header.write(encodableWriter);
        encodableWriter.accessFlags(accessFlags)
        encodableWriter.thisAndSuper(thisClassRef, superClassRef)

         */

    }
}