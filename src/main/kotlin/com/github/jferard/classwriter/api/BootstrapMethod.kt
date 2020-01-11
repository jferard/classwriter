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

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ConstantPoolEntryFactory
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.pool.MethodHandleEntry
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter

/**
 * Represented by the BootstrapMethods Attribute
 * 4.7.23. The BootstrapMethods Attribute (ClassFile)
 * ```
 * bootstrap_method {
 *     u2 bootstrap_method_ref;
 *     u2 num_bootstrap_arguments;
 *     u2 bootstrap_arguments[num_bootstrap_arguments];
 * }
 * ```
 *
 * @param methodHandle a methodRef + a kind of handle
 * @param arguments    the arguments
 */
class BootstrapMethod(private val methodHandle: MethodHandle,
                      private val arguments: List<Any>) :
        Encodable<BootstrapMethod, EncodedBootstrapMethod, ClassEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedBootstrapMethod {
        val methodRefIndex = context.addToPool(
                MethodHandleEntry(methodHandle))
        val argumentIndexes = arguments
                .map { arg -> ConstantPoolEntryFactory.create(arg).addToPool(context) }
        return EncodedBootstrapMethod(methodRefIndex, argumentIndexes)
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        encodableWriter.bootstrapMethod(methodHandle, arguments)
    }
}