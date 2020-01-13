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
package com.github.jferard.classwriter.bytecode.writer

import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType
import com.github.jferard.classwriter.encoded.stackmap.StackMapFrameConstants
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameEncodedWriter
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import java.io.DataOutput
import java.io.Writer

class TextStackMapFrameEncodedWriter(
        private val output: Writer) :
        StackMapFrameEncodedWriter {

    override fun sameFrame(offsetDelta: Int) {
        TextEncodedWriterHelper.writeU1(output, "same frame, offset_delta", offsetDelta)
    }

    override fun sameLocals1StackItemFrame(offsetDelta: Int,
                                           encodedFirstStackItemVerificationType: EncodedVerificationType) {
//        output.writeByte(StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_BASE + offsetDelta)
//        encodedFirstStackItemVerificationType.write(verificationTypeFactory)
    }

    override fun chopFrame(k: Int, offsetDelta: Int) {
        output.write("StackMapFrameConstants.CHOP_FRAME_BASE, ")
        TextEncodedWriterHelper.writeU2(output, "offset_delta", offsetDelta + k)
    }

    override fun fullFrame(offsetDelta: Int,
                           encodedLocals: List<EncodedVerificationType>,
                           encodedStackItems: List<EncodedVerificationType>) {
 //       output.writeByte(StackMapFrameConstants.FULL_FRAME)
 //       output.writeShort(offsetDelta)
 //       output.writeShort(encodedLocals.size)
//        encodedLocals.forEach { it.write(verificationTypeFactory) }
  //      output.writeShort(encodedStackItems.size)
//        encodedStackItems.forEach { it.write(verificationTypeFactory) }
    }

    override fun appendFrame(k: Int, offsetDelta: Int,
                             encodedNewTypes: List<EncodedVerificationType>) {
//        output.writeByte(StackMapFrameConstants.APPEND_FRAME_BASE + k)
//        output.writeShort(offsetDelta)
//        encodedNewTypes.forEach { it.write(verificationTypeFactory) }
    }

    override fun sameLocals1StackItemFrameExtended(offsetDelta: Int,
                                                   encodedFirstStackItemVerificationType: EncodedVerificationType) {
//        output.writeByte(StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED_BASE)
//        output.writeShort(offsetDelta)
//        encodedFirstStackItemVerificationType
//                .write(verificationTypeFactory)
    }

    override fun sameFrameExtended(offsetDelta: Int) {
 //       output.writeByte(StackMapFrameConstants.SAME_FRAME_EXTENDED_BASE)
 //       output.writeShort(offsetDelta)
    }

}