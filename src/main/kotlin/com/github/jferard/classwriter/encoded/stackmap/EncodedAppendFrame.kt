/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
package com.github.jferard.classwriter.encoded.stackmap

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * ```
 * append_frame {
 *     u1 frame_type = APPEND; // 252-254
 *     u2 offset_delta;
 *     verification_type_info locals[frame_type-251];
 * }
 * ```
 */
class EncodedAppendFrame(private val frameType: Int, private val offsetDelta: Int,
                         private val encodedNewTypes: List<EncodedVerificationType>) :
        EncodedStackMapFrame {
    override fun write(
            encodedWriter: StackMapFrameEncodedWriter) {
        encodedWriter.appendFrame(frameType, offsetDelta, encodedNewTypes)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): StackMapFrame {
        TODO("not implemented")
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.BYTE_SIZE + BytecodeHelper.SHORT_SIZE +
            Sized.listSize(0, encodedNewTypes)

}