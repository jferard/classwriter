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
package com.github.jferard.classwriter.internal.attribute.stackmap

import com.github.jferard.classwriter.api.MethodDescriptor
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.api.ValueType
import com.github.jferard.classwriter.internal.access.MethodAccess

class FirstStackMapFrameFactory {
    fun create(classRef: PlainClassRef, methodName: String,
               descriptor: MethodDescriptor,
               accessFlags: Int): StackMapFrame {
        val args = expandArgs(descriptor.argTypes).toMutableList()
        if (accessFlags and MethodAccess.ACC_STATIC == 0) {
            args.add(if (methodName
                    == "<init>") VerificationType.UNITIALIZED_THIS else VerificationType.fromClassRef(
                    classRef))
        }
        return StackMapFrame(null, 0, args, emptyList())
    }

    /**
     * expandTypeList(RawArgs, Args)
     */
    private fun expandArgs(
            args: List<ValueType>): List<VerificationType> {
        return args.flatMap { a: ValueType ->
            if (a.size == 1) listOf(VerificationType.fromValueType(a))
            else listOf(VerificationType.fromValueType(a), VerificationType.TOP)
        }
    }
}