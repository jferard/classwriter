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

package com.github.jferard.classwriter.internal.attribute.stackmap;

import java.util.ArrayList;
import java.util.Stack;

public class FrameContext {
    public static FrameContext create() {
        return new FrameContext(new Stack<>(), 0, new Stack<>(), 0);
    }

    private Stack<VerificationType> stackItems;
    private int maxStack;
    private Stack<VerificationType> locals;
    private int maxLocal;

    public FrameContext(Stack<VerificationType> stackItems, int maxStack,
                        Stack<VerificationType> locals, int maxLocal) {
        this.stackItems = stackItems;
        this.maxStack = maxStack;
        this.locals = locals;
        this.maxLocal = maxLocal;
    }

    public void stackPush(VerificationType type) {
        this.stackItems.push(type);
        if (type.isLong()) {
            this.stackItems.push(VerificationType.TOP);
        }
        this.updateMaxStack();
    }

    public VerificationType stackPop() {
        VerificationType type = this.stackItems.pop();
        if (type.isLong()) {
            final VerificationType nextType = this.stackItems.pop();
            if (nextType != VerificationType.TOP) {
                throw new IllegalStateException();
            }
        }
        return type;
    }

    public VerificationType stackPopOneByte() {
        return this.stackItems.pop();
    }

    public VerificationType stackPeek() {
        return this.stackItems.peek();
    }

    public void localsPush(VerificationType type) {
        this.locals.push(type);
        if (type.isLong()) this.locals.push(VerificationType.TOP);
        this.updateMaxLocals();
    }


    private void updateMaxStack() {
        this.maxStack = computeNewMax(this.stackItems, this.maxStack);
    }

    private int computeNewMax(Stack<VerificationType> types, int max) {
        final int size = types.stream().mapToInt(t -> t != null && t.isLong() ? 2 : 1).sum();
        int newMax;
        if (size > max) {
            newMax = size;
        } else {
            newMax = max;
        }
        return newMax;
    }

    private void updateMaxLocals() {
        this.maxLocal = computeNewMax(this.locals, this.maxLocal);
    }

    public void stackEmpty() {
        this.stackItems.clear();
    }

    public void localsPop(int n) {
        for (int i = 0; i < n; i++) {
            this.locals.pop();
        }
    }

    public VerificationType stackToTop() {
        VerificationType top = this.stackItems.pop();
        this.stackItems.clear();
        this.stackItems.push(top);
        return top;
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public int getMaxLocals() {
        return this.maxLocal;
    }

    public StackMapFrame toFrame(StackMapFrame previous, int offset) {
        return new StackMapFrame(previous, offset, new ArrayList<>(this.locals),
                new ArrayList<>(this.stackItems));
    }

    public VerificationType localsGetVerificationType(int index) {
        return this.locals.get(index);
    }

    public StackMapFrameData toData() {
        final Stack<VerificationType> locals = new Stack<>();
        locals.addAll(this.locals);
        final Stack<VerificationType> stackItems = new Stack<>();
        stackItems.addAll(this.stackItems);
        return new StackMapFrameData(locals, stackItems);
    }

    public void localsSet(int localIndex, VerificationType stackType) {
        while (localIndex >= this.locals.size()) {
            this.locals.add(null);
        }
        this.locals.set(localIndex, stackType);
        this.updateMaxLocals();
    }

    public void stackClear() {
        this.stackItems.clear();
    }
}
