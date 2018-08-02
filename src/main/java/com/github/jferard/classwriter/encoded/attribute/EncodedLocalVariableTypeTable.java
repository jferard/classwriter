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
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;

public class EncodedLocalVariableTypeTable implements Encoded<AttributeWritableFactory<?>> {
    private final int startPc;
    private final int length;
    private final int nameIndex;
    private final int signatureIndex;
    private final int index;

    public EncodedLocalVariableTypeTable(int startPc, int length, int nameIndex, int signatureIndex, int index) {
        this.startPc = startPc;
        this.length = length;
        this.nameIndex = nameIndex;
        this.signatureIndex = signatureIndex;
        this.index = index;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory
                .localVariableTypeTable(this.startPc, this.length, nameIndex, signatureIndex,
                        this.index);
    }

    @Override
    public int getSize() {
        return 5 * BytecodeHelper.SHORT_SIZE;
    }
}
