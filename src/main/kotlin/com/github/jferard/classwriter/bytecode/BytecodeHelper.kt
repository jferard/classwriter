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
package com.github.jferard.classwriter.bytecode


object BytecodeHelper {
    const val BYTE_SIZE = 1
    const val SHORT_SIZE = 2
    const val INT_SIZE = 4
    const val FLOAT_SIZE = 4
    const val DOUBLE_SIZE = 8
    const val LONG_SIZE = 8
    private var SHIFT_ONE = 0
    var BYTE_MAX = 0
    private var SHIFT_TWO = 0
    private var SHIFT_THREE = 0
    private var SHIFT_FOUR = 0
    private var SHIFT_FIVE = 0
    private var SHIFT_SIX = 0
    private var SHIFT_SEVEN = 0
    var SHORT_MAX = 0
    var INT_MAX = 0

    init {
        SHIFT_ONE = 8
        BYTE_MAX = (1 shl SHIFT_ONE) - 1
        SHIFT_TWO = 2 * SHIFT_ONE
        SHORT_MAX = (1 shl SHIFT_TWO) - 1
        SHIFT_THREE = 3 * SHIFT_ONE
        INT_MAX = (1 shl SHIFT_THREE) - 1
        SHIFT_FOUR = 4 * SHIFT_ONE
        SHIFT_FIVE = 5 * SHIFT_ONE
        SHIFT_SIX = 6 * SHIFT_ONE
        SHIFT_SEVEN = 7 * SHIFT_ONE
    }
}