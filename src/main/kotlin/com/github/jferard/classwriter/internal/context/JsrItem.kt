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
package com.github.jferard.classwriter.internal.context

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.api.instruction.Instruction

class JsrItem(override var offset: Int,
              private val instruction: Instruction) :
        PositionedItem {
    override fun shift(curShift: Int) {
        offset += curShift
    }

    override fun updateShift(curShift: Int, targetItem: TargetItem): Int {
        assert(targetItem.offset > BytecodeHelper.SHORT_MAX)
        return if (targetItem!!.isItemForTarget(instruction)) {
            curShift + 2
        } else {
            curShift
        }
    }

    override fun extractOffsets(builder: OffsetsBuilder) { // do nothing
    }

}