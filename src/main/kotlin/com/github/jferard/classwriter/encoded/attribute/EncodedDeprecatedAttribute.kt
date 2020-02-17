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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.attribute.DeprecatedAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.DeprecatedAttributeEncodedWriter

/**
 * 4.7.15. The Deprecated Attribute
 * ```
 * Deprecated_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 * ```
 */
class EncodedDeprecatedAttribute(private val attributeNameIndex: Int) :
        EncodedCFMAttribute<DeprecatedAttribute, EncodedDeprecatedAttribute, DeprecatedAttributeEncodedWriter> {
    override fun write(encodedWriter: DeprecatedAttributeEncodedWriter) {
        return encodedWriter.deprecatedAttribute(attributeNameIndex)
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        return null
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): DeprecatedAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}