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

import com.github.jferard.classwriter.api.BootstrapMethod
import com.github.jferard.classwriter.api.MethodHandle
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.writer.encoded.BootstrapMethodsAttributeEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedBootstrapMethod(private val methodRefIndex: Int,
                             private val argumentIndexes: List<Int>) :
        Encoded<BootstrapMethod, EncodedBootstrapMethod, BootstrapMethodsAttributeEncodedWriter> {
    override fun write(
            encodedWriter: BootstrapMethodsAttributeEncodedWriter) {
        encodedWriter.bootstrapMethod(methodRefIndex, argumentIndexes)
    }

    override val size: Int
        get() = 2 * BytecodeHelper.SHORT_SIZE +
                argumentIndexes.size * BytecodeHelper.SHORT_SIZE

    override fun decode(context: GlobalContext, codeContext: MethodContext): BootstrapMethod {
        val methodHandle = context.encodedPool.entries[methodRefIndex-1].toObject() as MethodHandle
        val arguments = argumentIndexes.map { i -> context.encodedPool.entries[i-1].toObject() }
        return BootstrapMethod(methodHandle, arguments)
    }
}