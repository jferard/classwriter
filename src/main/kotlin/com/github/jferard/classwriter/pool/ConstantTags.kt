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

/**
 * Table 4.4-B. Constant pool tags (by tag)
 */
object ConstantTags {
    const val UTF8: Short = 1
    // no 2
    const val INTEGER: Short = 3
    const val FLOAT: Short = 4
    const val LONG: Short = 5
    const val DOUBLE: Short = 6
    const val CLASS: Short = 7
    const val STRING: Short = 8
    const val FIELD_REF: Short = 9
    const val METHOD_REF: Short = 10
    const val INTERFACE_METHOD_REF: Short = 11
    const val NAME_AND_TYPE: Short = 12
    // no 13-14
    const val METHOD_HANDLE: Short = 15
    const val METHOD_TYPE: Short = 16
    const val INVOKE_DYNAMIC: Short = 18
    const val MODULE: Short = 19
    const val PACKAGE: Short = 20
}