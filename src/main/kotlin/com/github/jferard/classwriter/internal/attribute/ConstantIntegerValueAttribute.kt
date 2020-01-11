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

import com.github.jferard.classwriter.encoded.attribute.EncodedConstantValueAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.IntegerEntry
import com.github.jferard.classwriter.writer.encodable.FieldAttributeEncodableWriter

/**
 * 4.7.2. The ConstantValue Attribute
 * ```
 * ConstantValue_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 constantvalue_index;
 * }
 * ```
 *
 * CONSTANT_Integer
 */
class ConstantIntegerValueAttribute internal constructor(private val value: Int) :
        ConstantFieldAttribute<ConstantIntegerValueAttribute> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedConstantValueAttribute {
        val attributeNameIndex: Int =
                context.addUtf8ToPool(ConstantValueAttributeFactory.CONSTANT_VALUE_NAME)
        val valueIndex: Int = context.addToPool(IntegerEntry(value))
        return EncodedConstantValueAttribute(attributeNameIndex, valueIndex)
    }

    override fun write(encodableWriter: FieldAttributeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}