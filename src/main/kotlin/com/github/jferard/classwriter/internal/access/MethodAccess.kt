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
package com.github.jferard.classwriter.internal.access

/**
 * Table 4.6-A. Method access and property flags
 */
object MethodAccess {
    const val ACC_PUBLIC = AccessFlags.ACC_PUBLIC
    const val ACC_PRIVATE = AccessFlags.ACC_PRIVATE
    const val ACC_PROTECTED =
            AccessFlags.ACC_PROTECTED
    const val ACC_STATIC = AccessFlags.ACC_STATIC
    const val ACC_FINAL = AccessFlags.ACC_FINAL
    const val ACC_SYNCHRONIZED =
            AccessFlags.ACC_SYNCHRONIZED
    const val ACC_BRIDGE = AccessFlags.ACC_BRIDGE
    const val ACC_VARARGS = AccessFlags.ACC_VARARGS
    const val ACC_NATIVE = AccessFlags.ACC_NATIVE
    const val ACC_ABSTRACT = AccessFlags.ACC_ABSTRACT
    const val ACC_STRICT = AccessFlags.ACC_STRICT
    const val ACC_SYNTHETIC =
            AccessFlags.ACC_SYNTHETIC

    fun builder(): MethodAccessFlagsBuilder {
        return MethodAccessFlagsBuilder()
    }

    val entries: List<Pair<Int, String>> = listOf(
            Pair(ACC_PUBLIC, "MethodAccess.ACC_PUBLIC"),
            Pair(ACC_PRIVATE, "MethodAccess.ACC_PRIVATE"),
            Pair(ACC_PROTECTED, "MethodAccess.ACC_PROTECTED"),
            Pair(ACC_STATIC, "MethodAccess.ACC_STATIC"),
            Pair(ACC_FINAL, "MethodAccess.ACC_FINAL"),
            Pair(ACC_SYNCHRONIZED, "MethodAccess.ACC_SYNCHRONIZED"),
            Pair(ACC_BRIDGE, "MethodAccess.ACC_BRIDGE"),
            Pair(ACC_VARARGS, "MethodAccess.ACC_VARARGS"),
            Pair(ACC_NATIVE, "MethodAccess.ACC_NATIVE"),
            Pair(ACC_ABSTRACT, "MethodAccess.ACC_ABSTRACT"),
            Pair(ACC_STRICT, "MethodAccess.ACC_STRICT"),
            Pair(ACC_SYNTHETIC, "MethodAccess.ACC_SYNTHETIC")
    )
}