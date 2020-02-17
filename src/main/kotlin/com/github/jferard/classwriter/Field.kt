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
package com.github.jferard.classwriter

import com.github.jferard.classwriter.api.FieldDescriptor
import com.github.jferard.classwriter.encoded.EncodedField
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute
import com.github.jferard.classwriter.internal.attribute.FieldAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.FieldAttributeEncodableWriter
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter

/**
 * 4.5. Fields
 *
 * ```
 * field_info {
 *     u2             access_flags;
 *     u2             name_index;
 *     u2             descriptor_index;
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 * ```
 */
class Field(private val accessFlags: Int, private val name: String,
            private val descriptor: FieldDescriptor,
            private val attributes: List<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>>) :
        Encodable<Field, EncodedField, FieldAttributeEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedField {
        val nameIndex: Int = context.addUtf8ToPool(name)
        val descriptorIndex: Int = context.addToPool(descriptor.toPoolEntry())
        val encodedFieldAttributes =
                attributes.map {
                    it.encode(context, codeContext)
                } as List<EncodedFieldAttribute<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>, *, FieldAttributeEncodedWriter>>
        return EncodedField(accessFlags, nameIndex, descriptorIndex,
                encodedFieldAttributes)
    }

    override fun write(attributeEncodableWriter: FieldAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}