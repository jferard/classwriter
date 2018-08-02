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

/** <pre>{@code
 * same_locals_1_stack_item_frame_extended {
 *         u1 frame_type = SAME_LOCALS_1_STACK_ITEM_EXTENDED; // 247
 *         u2 offset_delta;
 *         verification_type_info stack[1];
 *         }
 * }</pre> */
public class ByteCodeLocalsSame1StackItemFrameExtended implements ByteCode {
    private final int offsetDelta;
    private final ByteCode writableFirstStackItemVerificationType;

    public ByteCodeLocalsSame1StackItemFrameExtended(int offsetDelta,
                                                     ByteCode writableFirstStackItemVerificationType) {
        this.offsetDelta = offsetDelta;
        this.writableFirstStackItemVerificationType = writableFirstStackItemVerificationType;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED_BASE);
        output.writeShort(this.offsetDelta);
        this.writableFirstStackItemVerificationType.write(output);
    }
}
