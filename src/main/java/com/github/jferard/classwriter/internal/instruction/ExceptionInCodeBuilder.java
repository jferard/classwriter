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

package com.github.jferard.classwriter.internal.instruction;

import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.Label;

public class ExceptionInCodeBuilder {
    private final PlainClassRef classRef;
    private final Label label;
    private final int startOffset;
    private int endOffset;

    public ExceptionInCodeBuilder(PlainClassRef classRef, Label label, int startOffset) {
        this.classRef = classRef;
        this.label = label;
        this.startOffset = startOffset;
    }

    public ExceptionInCodeBuilder endOffset(int endOffset) {
        this.endOffset = endOffset;
        return this;
    }

    public ExceptionInCode build() {
        return new ExceptionInCode(this.classRef, this.label, this.startOffset, this.endOffset);
    }
}
