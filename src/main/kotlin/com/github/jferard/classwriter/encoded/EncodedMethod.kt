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
import com.github.jferard.classwriter.api.Method
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodEncodedWriter

/**
 * 4.6. Methods
 * ```method_info {
 * u2             access_flags;
 * u2             name_index;
 * u2             descriptor_index;
 * u2             attributes_count;
 * attribute_info attributes[attributes_count];
 * }
 * ``` *
 */
class EncodedMethod(private val accessFlags: Int, private val nameIndex: Int,
                    private val descriptorIndex: Int,
                    private val attributes: List<EncodedMethodAttribute<*, *, MethodAttributeEncodedWriter>>) :
        Encoded<Method, EncodedMethod, MethodEncodedWriter> {
    override fun write(encodedWriter: MethodEncodedWriter) {
        return encodedWriter.method(accessFlags, nameIndex, descriptorIndex, attributes)
    }

    override val size: Int = 4 * BytecodeHelper.SHORT_SIZE + Sized.listSize(attributes)

    override fun toString(): String {
        return String.format("Method [access=%s, name=%s, descriptor=%s, attributes=%s]",
                accessFlags, nameIndex, descriptorIndex, attributes)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Method {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}