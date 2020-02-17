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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.api.MethodDescriptor
import com.github.jferard.classwriter.api.MethodHandle
import com.github.jferard.classwriter.api.PlainClassRef

object ConstantPoolEntryFactory {
    fun create(o: Any): ConstantPoolEntry {
        if (o is String) {
            return StringEntry(o as String)
        } else if (o is PlainClassRef) {
            return ClassEntry(o as PlainClassRef)
        } else if (o is Int) {
            return IntegerEntry((o as Int)!!)
        } else if (o is Long) {
            return LongEntry((o as Long)!!)
        } else if (o is Float) {
            return FloatEntry((o as Float)!!)
        } else if (o is Double) {
            return DoubleEntry((o as Double)!!)
        } else if (o is MethodHandle) {
            return MethodHandleEntry(
                    o as MethodHandle)
        } else if (o is MethodDescriptor) {
            return MethodTypeEntry(
                    o as MethodDescriptor)
        }
        throw IllegalStateException()
    }
}