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
package com.github.jferard.classwriter.encoded.stackmap

/** 4.7.4. The StackMapTable Attribute  */
object StackMapFrameConstants {
    const val CHOP_FRAME_BASE = 251
    const val SAME_FRAME_BASE = 0
    const val FULL_FRAME = 255
    const val APPEND_FRAME_BASE = 251
    const val SAME_LOCALS_1_STACK_ITEM_BASE = 64
    const val SAME_LOCALS_1_STACK_ITEM_EXTENDED = 247
    const val SAME_FRAME_EXTENDED = 251
}