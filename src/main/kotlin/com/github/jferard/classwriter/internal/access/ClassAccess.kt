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
 * Table 4.1-B. Class access and property modifiers
 */
object ClassAccess {
    const val ACC_PUBLIC = AccessFlags.ACC_PUBLIC
    const val ACC_FINAL = AccessFlags.ACC_FINAL
    const val ACC_SUPER = AccessFlags.ACC_SUPER
    const val ACC_INTERFACE =
            AccessFlags.ACC_INTERFACE
    const val ACC_ABSTRACT = AccessFlags.ACC_ABSTRACT
    const val ACC_SYNTHETIC =
            AccessFlags.ACC_SYNTHETIC
    const val ACC_ANNOTATION =
            AccessFlags.ACC_ANNOTATION
    const val ACC_ENUM = AccessFlags.ACC_ENUM
    const val ACC_MODULE = AccessFlags.ACC_MODULE
    fun builder(): ClassAccessFlagsBuilder {
        return ClassAccessFlagsBuilder()
    }

    val entries: List<Pair<Int, String>> = listOf(
            Pair(ACC_PUBLIC, "ClassAccess.ACC_PUBLIC"),
            Pair(ACC_FINAL, "ClassAccess.ACC_FINAL"),
            Pair(ACC_SUPER, "ClassAccess.ACC_SUPER"),
            Pair(ACC_INTERFACE, "ClassAccess.ACC_INTERFACE"),
            Pair(ACC_ABSTRACT, "ClassAccess.ACC_ABSTRACT"),
            Pair(ACC_SYNTHETIC, "ClassAccess.ACC_SYNTHETIC"),
            Pair(ACC_ANNOTATION, "ClassAccess.ACC_ANNOTATION"),
            Pair(ACC_ENUM, "ClassAccess.ACC_ENUM"),
            Pair(ACC_MODULE, "ClassAccess.ACC_MODULE")
    )
}