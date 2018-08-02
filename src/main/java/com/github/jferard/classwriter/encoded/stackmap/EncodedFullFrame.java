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
import com.github.jferard.classwriter.encoded.Encoded;

import java.util.List;

/**
 * <pre>{@code
 * full_frame {
 *     u1 frame_type = FULL_FRAME; // 255
 *     u2 offset_delta;
 *     u2 number_of_locals;
 *     verification_type_info locals[number_of_locals];
 *     u2 number_of_stack_items;
 *     verification_type_info stack[number_of_stack_items];
 * }
 * }</pre>
 */
public class EncodedFullFrame implements EncodedStackMapFrame {
    private final int offsetDelta;
    private final List<EncodedVerificationType> encodedLocals;
    private final List<EncodedVerificationType> encodedStackItems;

    public EncodedFullFrame(int offsetDelta, List<EncodedVerificationType> encodedLocals,
                            List<EncodedVerificationType> encodedStackItems) {
        this.offsetDelta = offsetDelta;
        this.encodedLocals = encodedLocals;
        this.encodedStackItems = encodedStackItems;
    }

    @Override
    public Writable<?> toWritable(StackMapFrameWritableFactory<?> writableFactory) {
        return writableFactory.fullFrame(offsetDelta, encodedLocals, encodedStackItems);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + 2 * BytecodeHelper.SHORT_SIZE +
                encodedLocals.stream().mapToInt(Encoded::getSize).sum() +
                BytecodeHelper.SHORT_SIZE +
                encodedStackItems.stream().mapToInt(Encoded::getSize).sum();
    }
}
