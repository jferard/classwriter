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
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.Map;
import java.util.Stack;

public class Offsets {
    private final Map<Label, Integer> offsetByLabel;
    private final Map<Instruction, Integer> offsetBySubroutine;
    private final Stack<StackMapFrame> stackMapFrames;

    public Offsets(Map<Label, Integer> offsetByLabel,
                   Map<Instruction, Integer> offsetBySubroutine,
                   Stack<StackMapFrame> stackMapFrames) {
        this.offsetByLabel = offsetByLabel;
        this.offsetBySubroutine = offsetBySubroutine;
        this.stackMapFrames = stackMapFrames;
    }

    public int getLabelOffset(Label label) {
        return this.offsetByLabel.get(label);
    }

    public long getSubroutineOffset(Instruction instruction) {
        return this.offsetBySubroutine.get(instruction);
    }

    public Stack<StackMapFrame> getStackMapFrames() {
        return stackMapFrames;
    }
}
