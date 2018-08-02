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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;

/**
 * 4.7.12:
 * <quote>
 * <p>Each entry in the line_number_table array indicates that the line number in the original
 * source file changes at a given point in the code array. Each line_number_table entry must
 * contain the following two items:</p>
 * <ul>
 * <li>startPc: The value of the startPc item must indicate the index into the code array at
 * which the code for a new line in the original source file begins. The value of startPc must
 * be less than the value of the code_length item of the Code attribute of which this
 * LineNumberTable is an attribute.</li>
 * <li>line_number: The value of the line_number item must give the corresponding line number in
 * the original source file.</li>
 * </ul>
 * </quote>
 */
public class PositionAndLineNumber implements Encoded<AttributeWritableFactory<?>> {
    private final int startPc;
    private final int lineNumber;

    public PositionAndLineNumber(int startPc, int lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.positionAndLineNumber(this.startPc, this.lineNumber);
    }

    @Override
    public int getSize() {
        return 2 * BytecodeHelper.SHORT_SIZE;
    }
}
