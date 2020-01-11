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

import com.github.jferard.classwriter.internal.descriptor.MethodDescriptorBuilder
import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.pool.Utf8Entry
import java.util.*
import java.util.function.Consumer

/**
 * 4.3.3. Method Descriptors
 */
class MethodDescriptor(val retType: ValueType?,
                       val argTypes: List<ValueType>) :
        Descriptor {
    val argsStackDepth: Int
        get() {
            var ret = if (hasNonVoidRet()) 1 else 0
            ret += argsSize
            return ret
        }

    fun hasNonVoidRet(): Boolean {
        return retType != null
    }

    /**
     * 6.5. Instructions:
     * The count operand of the invokeinterface instruction records a measure of the number of
     * argument values, where an argument value of type long or type double contributes two units
     * to the count value and an argument of any other type contributes one unit. This
     * information can also be derived from the descriptor of the selected method. The redundancy
     * is historical.
     */
    val argsSize: Int
        get() = argTypes.map { obj: ValueType -> obj.size }.sum()

    val argsCount: Int
        get() = argTypes.size

    override fun toString(): String {
        val sb = StringBuilder()
        argTypes.forEach(
                Consumer { obj: ValueType -> sb.append(obj) })
        return "(" + sb.toString() + ")" + Objects.toString(retType, "V")
    }

    override fun toPoolEntry(): ConstantPoolEntry {
        return Utf8Entry(this.toString())
    }

    companion object {
        val EMPTY: MethodDescriptor =
                MethodDescriptor(null, emptyList())

        @kotlin.jvm.JvmStatic
        fun builder(): MethodDescriptorBuilder {
            return MethodDescriptorBuilder()
        }
    }

}