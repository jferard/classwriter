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
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameData;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class OffsetsBuilder {
    Map<Label, Integer> offsetByLabel;
    Map<Instruction, Integer> offsetBySubroutine;
    Stack<StackMapFrame> stackMapFrames;

    public OffsetsBuilder() {
        offsetByLabel = new HashMap<>();
        offsetBySubroutine = new HashMap<>();
        stackMapFrames = new Stack<>();
    }

    public Offsets build() {
        return new Offsets(this.offsetByLabel, this.offsetBySubroutine, this.stackMapFrames);
    }

    public void putLabel(Label label, int offset) {
        this.offsetByLabel.put(label, offset);
    }

    public void putStackMapFrame(StackMapFrameData stackMapFrameData, int offset) {
        this.stackMapFrames.push(null);
    }

    public void putSubroutine(Instruction instruction, int offset) {
        this.offsetBySubroutine.put(instruction, offset);
    }
}
