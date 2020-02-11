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

import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData
import com.github.jferard.classwriter.api.instruction.Instruction
import java.util.*
import kotlin.collections.HashMap

class OffsetsBuilder {
    var offsetByLabel: MutableMap<Label, Int> = HashMap()
    var offsetBySubroutine: MutableMap<Instruction, Int> = HashMap()
    var stackMapFrames: Stack<StackMapFrame> = Stack<StackMapFrame>()

    fun build(): Offsets {
        return Offsets(offsetByLabel, offsetBySubroutine, stackMapFrames)
    }

    fun putLabel(label: Label, offset: Int) {
        offsetByLabel[label] = offset
    }

    fun putStackMapFrame(stackMapFrameData: StackMapFrameData, offset: Int) {
        stackMapFrames.push(null)
    }

    fun putSubroutine(instruction: Instruction,
                      offset: Int) {
        offsetBySubroutine[instruction] = offset
    }

}