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

import com.github.jferard.classwriter.encoded.attribute.EncodedSyntheticAttribute

import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.SyntheticAttributeWriter

/**
 * 4.7.8. The Synthetic Attribute (ClassFile, field_info, or method_info structure)
 * ```Synthetic_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * }
 * ``` *
 */
class SyntheticAttribute :
        FieldAttribute<SyntheticAttribute, EncodedSyntheticAttribute, SyntheticAttributeWriter>,
        ClassFileAttribute<SyntheticAttribute, EncodedSyntheticAttribute, SyntheticAttributeWriter>,
        MethodAttribute<SyntheticAttribute, EncodedSyntheticAttribute, SyntheticAttributeWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedSyntheticAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(SYNTHETIC_NAME)
        return EncodedSyntheticAttribute(attributeNameIndex)
    }

    companion object {
        val INSTANCE: SyntheticAttribute = SyntheticAttribute()
        const val SYNTHETIC_NAME: String = "Synthetic"
    }

    override fun write(encodableWriter: SyntheticAttributeWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}