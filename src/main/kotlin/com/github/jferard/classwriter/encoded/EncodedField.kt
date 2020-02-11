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
package com.github.jferard.classwriter.encoded

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.Field
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import com.github.jferard.classwriter.api.FieldDescriptor
import com.github.jferard.classwriter.writer.encoded.FieldEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute
import com.github.jferard.classwriter.internal.attribute.FieldAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

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
class EncodedField(private val accessFlags: Int, private val nameIndex: Int,
                   private val descriptorIndex: Int,
                   private val encodedAttributes: List<EncodedFieldAttribute<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>, *, FieldAttributeEncodedWriter>>) :
        Encoded<Field, EncodedField, FieldEncodedWriter> {
    override fun write(encodedWriter: FieldEncodedWriter) {
        encodedWriter
                .field(accessFlags, nameIndex, descriptorIndex, encodedAttributes)
    }

    override val size: Int = 4 * BytecodeHelper.SHORT_SIZE +
                Sized.listSize(encodedAttributes)

    override fun toString(): String {
        return "Field[access=$accessFlags, name index=$nameIndex, descriptor index=$descriptorIndex, attributes=$encodedAttributes]"
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Field {
        val entries = context.encodedPool.entries
        return Field(accessFlags, entries[nameIndex-1].utf8Text(),
                entries[descriptorIndex-1].toObject() as FieldDescriptor,
                encodedAttributes.map { ea -> ea.decode(context, codeContext) })
    }
}