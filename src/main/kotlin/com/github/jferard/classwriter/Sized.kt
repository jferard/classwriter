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

package com.github.jferard.classwriter

interface Sized {
    /**
     * The size of the object. Usually, but not always, the size is independent of the position.
     * @param pos the position.
     * @return the size of the object.
     */
    fun getSize(pos: Int): Int

    companion object {
        /**
         * @param pos the start position
         * @param list the list
         * @return the size of the list
         */
        fun listSize(pos: Int,
                     list: Iterable<Sized>): Int = list.fold(pos, { p: Int, sized: Sized -> p + sized.getSize(
                p)
        })
    }
}