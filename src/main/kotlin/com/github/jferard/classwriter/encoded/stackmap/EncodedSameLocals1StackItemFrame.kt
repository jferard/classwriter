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

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrame
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * ```same_locals_1_stack_item_frame {
 * u1 frame_type = SAME_LOCALS_1_STACK_ITEM; * 64-127 *
 * verification_type_info stack[1];
 * }
 * ``` *
 */

class EncodedSameLocals1StackItemFrame(private val frameType: Int,
                                       private val encodedFirstStackItemVerificationType: EncodedVerificationType) :
        EncodedStackMapFrame {
    override fun write(
            encodedWriter: StackMapFrameEncodedWriter) {
        return encodedWriter.sameLocals1StackItemFrame(this.frameType,
                this.encodedFirstStackItemVerificationType)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): StackMapFrame {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSize(pos: Int): Int {
        return BytecodeHelper.BYTE_SIZE + this.encodedFirstStackItemVerificationType.getSize(0)
    }
}