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
 * append_frame {
 *     u1 frame_type = APPEND; // 252-254
 *     u2 offset_delta;
 *     verification_type_info locals[frame_type-251];
 * }
 * }</pre>
 */
public class EncodedAppendFrame implements EncodedStackMapFrame {
    private final int k;
    private final int offsetDelta;
    private final List<EncodedVerificationType> encodedNewTypes;

    public EncodedAppendFrame(int k, int offsetDelta,
                              List<EncodedVerificationType> encodedNewTypes) {
        this.k = k;
        this.offsetDelta = offsetDelta;
        this.encodedNewTypes = encodedNewTypes;
    }

    @Override
    public Writable<?> toWritable(StackMapFrameWritableFactory<?> writableFactory) {
        return writableFactory.appendFrame(this.k, this.offsetDelta, this.encodedNewTypes);
    }


    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE +
                this.encodedNewTypes.stream().mapToInt(Encoded::getSize).sum();
    }
}
