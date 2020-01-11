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
package com.github.jferard.classwriter.internal.context


class PaddingItem(override var offset: Int, private val padding: Int) : PositionedItem {
    override fun shift(curShift: Int) {
        offset += curShift
    }

    override fun updateShift(curShift: Int, targetItem: TargetItem): Int {
        val newShift: Int
        val mod = curShift % PADDING_SIZE
        newShift = if (mod == 0) {
            curShift
        } else if (mod < padding) { // padding += mod;
            curShift - mod
        } else { // mod >= padding
// padding += PADDING_SIZE - mod;
            curShift + PADDING_SIZE - mod
        }
        return newShift
    }

    override fun extractOffsets(builder: OffsetsBuilder) { // do nothing
    }

    companion object {
        const val PADDING_SIZE = 4
    }

}