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

import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.internal.attribute.stackmap.FrameContext
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.api.instruction.ExceptionInCode
import com.github.jferard.classwriter.api.instruction.ExceptionInCodeBuilder
import com.github.jferard.classwriter.api.instruction.Instruction
import java.util.*
import kotlin.collections.ArrayList


class MethodContext(var curOffset: Int, private val frameContext: FrameContext,
                                         private val offsetsContext: OffsetsContext,
                                         private val exceptions: MutableList<ExceptionInCode>,
                                         private val stackFrames: Stack<StackMapFrame>,
                                         private val positionsAndLineNumbers: MutableList<PositionAndLineNumber>) {
    private var curExceptionBuilder: ExceptionInCodeBuilder? = null
    private var finalOffsetsContext: Offsets? = null
    
    fun getPositionsAndLineNumbers(): List<PositionAndLineNumber> {
        return positionsAndLineNumbers
    }

    /**
     * Add a subroutine
     *
     * @param instruction the subroutine
     */
    fun addSubroutine(
            instruction: Instruction) {
        offsetsContext.addSubroutine(instruction)
    }

    /**
     * should store the stackframe
     */
    fun storeGoto(label: Label) {
        offsetsContext.storeGoto(label)
    }

    fun storeLabel(label: Label) {
        offsetsContext.storeLabel(label, frameContext.toData())
    }

    fun storeJsr(
            instruction: Instruction) {
        offsetsContext.storeJsr(instruction)
    }

    fun storeBranch(label: Label) {
        offsetsContext.storeBranch(label)
    }

    fun storePadding(padding: Int) {
        offsetsContext.storePadding(padding)
    }

    /**
     * Fix wide index goto and jsr
     * What about padding (lookupswitch, tableswitch) 
     */
    fun normalize(context: GlobalContext) {
        finalOffsetsContext = offsetsContext.normalize(context, this)
    }

    fun getLabelOffset(label: Label): Int {
        return finalOffsetsContext!!.getLabelOffset(label)
    }

    fun getSubroutineOffset(
            instruction: Instruction): Long {
        return finalOffsetsContext!!.getSubroutineOffset(instruction)
    }

    val maxStack: Int
        get() = frameContext.maxStack

    val maxLocals: Int
        get() = frameContext.maxLocals

    fun storeBeginException(classRef: PlainClassRef,
                            label: Label,
                            curOffset: Int) {
        curExceptionBuilder = ExceptionInCode.builder(classRef, label, curOffset)
    }

    fun storeEndException(curOffset: Int) {
        exceptions.add(curExceptionBuilder!!.endOffset(curOffset).build())
    }

    fun getExceptions(): List<ExceptionInCode> {
        return exceptions
    }

    fun clone(): MethodContext {
        return MethodContext(curOffset, FrameContext.create(),
                OffsetsContext.create(0),
                exceptions, Stack(), positionsAndLineNumbers)
    }

    fun merge(otherContext: MethodContext) { // merge frame context
        throw IllegalStateException()
    }

    fun offsetDelta(delta: Int) {
        curOffset += delta
    }

    fun stackEmpty() {
        frameContext.stackEmpty()
    }

    fun stackPush(type: VerificationType) {
        frameContext.stackPush(type)
    }

    fun localsPop(n: Int) {
        frameContext.localsPop(n)
    }

    fun localsPush(type: VerificationType) {
        frameContext.localsPush(type)
    }

    fun stackToTop(): VerificationType {
        return frameContext.stackToTop()
    }

    fun stackPop(): VerificationType {
        return frameContext.stackPop()
    }

    fun stackPopOneByte(): VerificationType {
        return frameContext.stackPopOneByte()
    }

    fun localsGet(index: Int): VerificationType {
        return frameContext.localsGetVerificationType(index)
    }

    val stackMapFrameData: StackMapFrameData
        get() = frameContext.toData()

    fun assertTypeEquals(expectedType: VerificationType,
                         actualType: VerificationType) {
        if (expectedType == actualType) {
            return
        }
        throw IllegalStateException("$expectedType vs $actualType")
    }

    fun assertTypeAssignable(expectedType: VerificationType,
                             actualType: VerificationType) {
        if (actualType.isAssignable(expectedType)) {
            return
        }
        throw IllegalStateException("$expectedType vs $actualType")
    }

    fun assertIsArray(verificationType: VerificationType): ReferenceVerificationType {
        if (verificationType is ReferenceVerificationType) {
            val referenceVerificationType: ReferenceVerificationType =
                    verificationType
            if (referenceVerificationType.isArray) {
                return referenceVerificationType
            }
        }
        throw IllegalStateException()
    }

    fun stackPeek(): VerificationType {
        return frameContext.stackPeek()
    }

    fun localsSet(localIndex: Int, stackType: VerificationType) {
        frameContext.localsSet(localIndex, stackType)
    }

    fun stackClear() {
        frameContext.stackClear()
    }

    fun storeLineNumber(lineNumber: Int) {
        positionsAndLineNumbers.add(PositionAndLineNumber(curOffset, lineNumber))
    }

    companion object {
        fun create(nargs: Int): MethodContext {
            return MethodContext(0, FrameContext.create(),
                    OffsetsContext.create(nargs),
                    ArrayList(), Stack(),
                    ArrayList())
        }
    }
}