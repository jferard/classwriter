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

package com.github.jferard.classwriter.encoded.stackmap;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

/**
 * <pre>{@code
 * same_locals_1_stack_item_frame {
 *     u1 frame_type = SAME_LOCALS_1_STACK_ITEM; /* 64-127 * /
 *     verification_type_info stack[1];
 * }
 * }</pre>
 */
public class EncodedLocalsSame1StackItemFrame implements EncodedStackMapFrame {
    private final int offsetDelta;
    private final EncodedVerificationType encodedFirstStackItemVerificationType;

    public EncodedLocalsSame1StackItemFrame(int offsetDelta,
                                            EncodedVerificationType encodedFirstStackItemVerificationType) {
        this.offsetDelta = offsetDelta;
        this.encodedFirstStackItemVerificationType = encodedFirstStackItemVerificationType;
    }

    @Override
    public Writable<?> toWritable(StackMapFrameWritableFactory<?> writableFactory) {
        return writableFactory.localsSame1StackItemFrame(this.offsetDelta,
                this.encodedFirstStackItemVerificationType);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + this.encodedFirstStackItemVerificationType.getSize();
    }
}
