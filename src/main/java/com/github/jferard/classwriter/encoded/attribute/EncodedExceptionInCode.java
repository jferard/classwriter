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

package com.github.jferard.classwriter.encoded.attribute;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;

/**
 * 4.7.3. The Code Attribute > exception_table[]
 */
public class EncodedExceptionInCode implements Encoded<MethodWritableFactory<?>> {
    public static final int SIZE = 4 * BytecodeHelper.SHORT_SIZE;
    private final int startPc;
    private final int endPc;
    private final int handlerPc;
    private final int catchTypeIndex;

    public EncodedExceptionInCode(int startPc, int endPc, int handlerPc, int catchTypeIndex) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchTypeIndex = catchTypeIndex;
    }

    @Override
    public Writable<?> toWritable(MethodWritableFactory<?> writableFactory) {
        return writableFactory.exceptionInCode(startPc, endPc, handlerPc, catchTypeIndex);
    }

    @Override
    public int getSize() {
        return 4 * BytecodeHelper.SHORT_SIZE;
    }
}
