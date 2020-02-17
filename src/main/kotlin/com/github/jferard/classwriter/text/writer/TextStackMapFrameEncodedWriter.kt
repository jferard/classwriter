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
package com.github.jferard.classwriter.text.writer

import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType
import com.github.jferard.classwriter.encoded.stackmap.StackMapFrameConstants
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.text.writer.TextEncodedWriterHelper.hex
import java.io.Writer

class TextStackMapFrameEncodedWriter(
        private val output: Writer,
        private val verificationTypeWriter: VerificationTypeEncodedWriter) :
        StackMapFrameEncodedWriter {

    override fun sameFrame(frameType: Int) {
        output.write("  /* same_frame */\n")
        output.write("%s, // frame_type: %s, offset_delta: %s\n".format(
                hex(frameType), frameType, frameType))
    }

    override fun sameLocals1StackItemFrame(frameType: Int,
                                           encodedFirstStackItemVerificationType: EncodedVerificationType) {
        output.write("  /* same_locals_1_stack_item_frame */\n")
        output.write("%s, // frame_type: %s, offset_delta: %s\n".format(
                hex(frameType), frameType, frameType - StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_BASE))
        encodedFirstStackItemVerificationType.write(verificationTypeWriter)
    }

    override fun chopFrame(frameType: Int, offsetDelta: Int) {
        output.write("  /* chop_frame */\n")
        output.write("%s, // frame_type: %s, remove %s locals\n".format(
                hex(frameType), frameType, 251 - frameType))
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta)
    }

    override fun fullFrame(offsetDelta: Int,
                           encodedLocals: List<EncodedVerificationType>,
                           encodedStackItems: List<EncodedVerificationType>) {
        output.write("  /* full_frame */\n")
        output.write("StackMapFrameConstants.FULL_FRAME, // frame_type: %s\n".format(
                StackMapFrameConstants.FULL_FRAME))
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta)
        TextEncodedWriterHelper.writeU2(output, "number of locals", encodedLocals.size)
        encodedLocals.forEach { it.write(verificationTypeWriter) }
        TextEncodedWriterHelper.writeU2(output, "number of stack items", encodedStackItems.size)
        encodedStackItems.forEach { it.write(verificationTypeWriter) }
    }

    override fun appendFrame(frameType: Int, offsetDelta: Int,
                             encodedNewTypes: List<EncodedVerificationType>) {
        output.write("  /* append_frame */\n")
        output.write("%s, // frame_type: %s, add %s locals\n".format(hex(frameType), frameType,
                frameType - StackMapFrameConstants.SAME_FRAME_EXTENDED))
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta)
        encodedNewTypes.forEach { it.write(verificationTypeWriter) }
    }

    override fun sameLocals1StackItemFrameExtended(offsetDelta: Int,
                                                   encodedFirstStackItemVerificationType: EncodedVerificationType) {
        output.write("  /* same_locals_1_stack_item_frame_extended */\n")
        output.write(
                "StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED, // frame_type: %s\n".format(
                        StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED))
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta)
        encodedFirstStackItemVerificationType.write(verificationTypeWriter)
    }

    override fun sameFrameExtended(offsetDelta: Int) {
        output.write("  /* same_frame_extended */\n")
        output.write("StackMapFrameConstants.SAME_FRAME_EXTENDED, // frame_type: %s".format(
                StackMapFrameConstants.SAME_FRAME_EXTENDED))
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta)
    }

}