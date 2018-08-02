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

package com.github.jferard.classwriter.bytecode.stackmap;

import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.encoded.stackmap.StackMapFrameConstants;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * <pre>{@code
 * full_frame {
 *         u1 frame_type = FULL_FRAME; // 255
 *         u2 offset_delta;
 *         u2 number_of_locals;
 *         verification_type_info locals[number_of_locals];
 *         u2 number_of_stack_items;
 *         verification_type_info stack[number_of_stack_items];
 *         }
 * }</pre>
 */
public class ByteCodeFullFrame implements ByteCode {
    private final int offsetDelta;
    private final List<ByteCode> writableLocals;
    private final List<ByteCode> writableStackItems;

    public ByteCodeFullFrame(int offsetDelta, List<ByteCode> writableLocals,
                             List<ByteCode> writableStackItems) {
        this.offsetDelta = offsetDelta;
        this.writableLocals = writableLocals;
        this.writableStackItems = writableStackItems;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(StackMapFrameConstants.FULL_FRAME);
        output.writeShort(this.offsetDelta);
        output.writeShort(this.writableLocals.size());
        for (ByteCode writableLocal : this.writableLocals) {
            writableLocal.write(output);
        }
        output.writeShort(this.writableStackItems.size());
        for (ByteCode writableStackItem : this.writableStackItems) {
            writableStackItem.write(output);
        }
    }
}
