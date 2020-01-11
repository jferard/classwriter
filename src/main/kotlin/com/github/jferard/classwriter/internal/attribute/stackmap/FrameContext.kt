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
package com.github.jferard.classwriter.internal.attribute.stackmap

import java.util.*
import kotlin.collections.ArrayList

class FrameContext(private val stackItems: Stack<VerificationType>, maxStack: Int,
                   locals: Stack<VerificationType>, maxLocal: Int) {
    var maxStack: Int
        private set
    private val locals: Stack<VerificationType>
    var maxLocals: Int
        private set

    fun stackPush(type: VerificationType) {
        stackItems.push(type)
        if (type.isLong) {
            stackItems.push(VerificationType.TOP)
        }
        updateMaxStack()
    }

    fun stackPop(): VerificationType {
        val type: VerificationType = stackItems!!.pop()
        if (type.isLong) {
            val nextType: VerificationType = stackItems.pop()
            check(!(nextType !== VerificationType.TOP))
        }
        return type
    }

    fun stackPopOneByte(): VerificationType {
        return stackItems!!.pop()
    }

    fun stackPeek(): VerificationType {
        return stackItems!!.peek()
    }

    fun localsPush(type: VerificationType) {
        locals.push(type)
        if (type.isLong) locals.push(VerificationType.TOP)
        updateMaxLocals()
    }

    private fun updateMaxStack() {
        maxStack = computeNewMax(stackItems, maxStack)
    }

    private fun computeNewMax(types: Stack<VerificationType>, max: Int): Int {
        val size = types.map { t: VerificationType -> if (t != null && t.isLong) 2 else 1 }
                .sum()
        val newMax: Int
        newMax = if (size > max) {
            size
        } else {
            max
        }
        return newMax
    }

    private fun updateMaxLocals() {
        maxLocals = computeNewMax(locals, maxLocals)
    }

    fun stackEmpty() {
        stackItems!!.clear()
    }

    fun localsPop(n: Int) {
        for (i in 0 until n) {
            locals!!.pop()
        }
    }

    fun stackToTop(): VerificationType {
        val top: VerificationType = stackItems!!.pop()
        stackItems.clear()
        stackItems.push(top)
        return top
    }

    fun toFrame(previous: StackMapFrame, offset: Int): StackMapFrame {
        return StackMapFrame(previous, offset, ArrayList<VerificationType>(locals),
                ArrayList<VerificationType>(stackItems))
    }

    fun localsGetVerificationType(index: Int): VerificationType {
        return locals!![index]
    }

    fun toData(): StackMapFrameData {
        val locals: Stack<VerificationType> = Stack<VerificationType>()
        locals.addAll(this.locals!!)
        val stackItems: Stack<VerificationType> = Stack<VerificationType>()
        stackItems.addAll(this.stackItems!!)
        return StackMapFrameData(locals, stackItems)
    }

    fun localsSet(localIndex: Int, stackType: VerificationType) {
        while (localIndex >= locals!!.size) {
            locals.add(null)
        }
        locals[localIndex] = stackType
        updateMaxLocals()
    }

    fun stackClear() {
        stackItems!!.clear()
    }

    companion object {
        fun create(): FrameContext {
            return FrameContext(Stack<VerificationType>(), 0,
                    Stack<VerificationType>(), 0)
        }
    }

    init {
        this.maxStack = maxStack
        this.locals = locals
        maxLocals = maxLocal
    }
}