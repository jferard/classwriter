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

package com.github.jferard.classwriter.internal.context;

import com.github.jferard.classwriter.api.Label;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber;
import com.github.jferard.classwriter.internal.attribute.stackmap.FrameContext;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.instruction.ExceptionInCode;
import com.github.jferard.classwriter.internal.instruction.ExceptionInCodeBuilder;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MethodContext {
    public static MethodContext create(int nargs) {
        return new MethodContext(0, FrameContext.create(), OffsetsContext.create(nargs),
                new ArrayList<>(), new Stack<>(), new ArrayList<>());
    }

    private final List<ExceptionInCode> exceptions;
    private OffsetsContext offsetsContext;
    private FrameContext frameContext;
    private int offset;
    private ExceptionInCodeBuilder curExceptionBuilder;
    private Stack<StackMapFrame> stackFrames;
    private Offsets finalOffsetsContext;

    public List<PositionAndLineNumber> getPositionsAndLineNumbers() {
        return this.positionsAndLineNumbers;
    }

    private List<PositionAndLineNumber> positionsAndLineNumbers;

    MethodContext(int offset, FrameContext frameContext, OffsetsContext offsetsContext,
                  final List<ExceptionInCode> exceptions, Stack<StackMapFrame> stackFrames,
                  List<PositionAndLineNumber> positionsAndLineNumbers) {
        this.offset = offset;
        this.frameContext = frameContext;
        this.offsetsContext = offsetsContext;
        this.exceptions = exceptions;
        this.stackFrames = stackFrames;
        this.positionsAndLineNumbers = positionsAndLineNumbers;
    }

    /**
     * Add a subroutine
     *
     * @param instruction the subroutine
     */
    public void addSubroutine(Instruction instruction) {
        this.offsetsContext.addSubroutine(instruction);
    }

    /**
     * should store the stackframe
     */
    public void storeGoto(Label label) {
        this.offsetsContext.storeGoto(label);
    }

    public void storeLabel(Label label) {
        this.offsetsContext.storeLabel(label, this.frameContext.toData());
    }

    public void storeJsr(Instruction instruction) {
        this.offsetsContext.storeJsr(instruction);
    }


    public void storeBranch(Label label) {
        this.offsetsContext.storeBranch(label);
    }

    public void storePadding(int padding) {
        this.offsetsContext.storePadding(padding);
    }

    /**
     * Fix wide index goto and jsr
     * What about padding (lookupswitch, tableswitch) ?
     */
    public void normalize(GlobalContext context) {
        this.finalOffsetsContext = this.offsetsContext.normalize(context, this);
    }

    public int getLabelOffset(Label label) {
        return this.finalOffsetsContext.getLabelOffset(label);
    }

    public long getSubroutineOffset(Instruction instruction) {
        return this.finalOffsetsContext.getSubroutineOffset(instruction);
    }


    public int getMaxStack() {
        return this.frameContext.getMaxStack();
    }

    public int getMaxLocals() {
        return this.frameContext.getMaxLocals();
    }

    public int getCurOffset() {
        return this.offset;
    }

    public void storeBeginException(PlainClassRef classRef, Label label, int curOffset) {
        this.curExceptionBuilder = ExceptionInCode.builder(classRef, label, curOffset);
    }

    public void storeEndException(int curOffset) {
        this.exceptions.add(this.curExceptionBuilder.endOffset(curOffset).build());
    }

    public List<ExceptionInCode> getExceptions() {
        return this.exceptions;
    }

    public MethodContext clone() {
        return new MethodContext(this.offset, FrameContext.create(), OffsetsContext.create(0),
                this.exceptions, new Stack<>(), this.positionsAndLineNumbers);
    }

    public void merge(MethodContext otherContext) {
        // merge frame context
        throw new IllegalStateException();
    }

    public void offsetDelta(int delta) {
        this.offset += delta;
    }

    public void stackEmpty() {
        this.frameContext.stackEmpty();
    }

    public void stackPush(VerificationType type) {
        this.frameContext.stackPush(type);
    }

    public void localsPop(int n) {
        this.frameContext.localsPop(n);
    }

    public void localsPush(VerificationType type) {
        this.frameContext.localsPush(type);
    }

    public VerificationType stackToTop() {
        return this.frameContext.stackToTop();
    }

    public VerificationType stackPop() {
        return this.frameContext.stackPop();
    }

    public VerificationType stackPopOneByte() {
        return this.frameContext.stackPopOneByte();
    }

    public VerificationType localsGet(int index) {
        return this.frameContext.localsGetVerificationType(index);
    }

    public StackMapFrameData getStackMapFrameData() {
        return this.frameContext.toData();
    }

    public void assertTypeEquals(VerificationType expectedType, VerificationType actualType) {
        if (expectedType.equals(actualType)) {
            return;
        }

        throw new IllegalStateException("" + expectedType + " vs " + actualType);
    }

    public void assertTypeAssignable(VerificationType expectedType, VerificationType actualType) {
        if (actualType.isAssignable(expectedType)) {
            return;
        }

        throw new IllegalStateException("" + expectedType + " vs " + actualType);
    }

    public ReferenceVerificationType assertIsArray(VerificationType verificationType) {
        if (verificationType instanceof ReferenceVerificationType) {
            final ReferenceVerificationType referenceVerificationType =
                    (ReferenceVerificationType) verificationType;
            if (referenceVerificationType.isArray()) {
                return referenceVerificationType;
            }
        }

        throw new IllegalStateException();
    }

    public VerificationType stackPeek() {
        return this.frameContext.stackPeek();
    }

    public void localsSet(int localIndex, VerificationType stackType) {
        this.frameContext.localsSet(localIndex, stackType);
    }

    public void stackClear() {
        this.frameContext.stackClear();
    }

    public void storeLineNumber(int lineNumber) {
        this.positionsAndLineNumbers.add(new PositionAndLineNumber(offset, lineNumber));
    }
}
