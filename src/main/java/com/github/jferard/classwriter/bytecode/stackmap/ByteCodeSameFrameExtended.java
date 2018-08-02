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

/**
 * <pre>{@code
 * same_frame_extended {
 *     u1 frame_type = SAME_FRAME_EXTENDED; // 251
 *     u2 offset_delta;
 *     }
 * }</pre>
 */

public class ByteCodeSameFrameExtended implements ByteCode {
    private int offsetDelta;

    public ByteCodeSameFrameExtended(int offsetDelta) {
        this.offsetDelta = offsetDelta;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(StackMapFrameConstants.SAME_FRAME_EXTENDED_BASE);
        output.writeShort(this.offsetDelta);

    }
}
