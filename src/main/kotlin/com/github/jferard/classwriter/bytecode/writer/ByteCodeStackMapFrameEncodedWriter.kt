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
import java.io.DataOutputStream
import java.util.logging.Logger

class ByteCodeStackMapFrameEncodedWriter(private val logger: Logger,
        private val output: DataOutputStream,
        private val verificationTypeFactory: VerificationTypeEncodedWriter) :
        StackMapFrameEncodedWriter {

    override fun chopFrame(frameType: Int, offsetDelta: Int) {
        output.writeByte(StackMapFrameConstants.CHOP_FRAME_BASE - frameType)
        output.writeShort(offsetDelta)
    }

    override fun sameFrame(frameType: Int) {
        logger.finest("Write same frame: $frameType")
        assert(frameType < 64)
        output.writeByte(frameType)
    }

    override fun fullFrame(offsetDelta: Int,
                           encodedLocals: List<EncodedVerificationType>,
                           encodedStackItems: List<EncodedVerificationType>) {
        output.writeByte(StackMapFrameConstants.FULL_FRAME)
        output.writeShort(offsetDelta)
        output.writeShort(encodedLocals.size)
        encodedLocals.forEach { it.write(verificationTypeFactory) }
        output.writeShort(encodedStackItems.size)
        encodedStackItems.forEach { it.write(verificationTypeFactory) }
    }

    override fun appendFrame(frameType: Int, offsetDelta: Int,
                             encodedNewTypes: List<EncodedVerificationType>) {
        logger.finest("Write append frame: $frameType, $offsetDelta, $encodedNewTypes")
        output.writeByte(frameType)
        output.writeShort(offsetDelta)
        encodedNewTypes.forEach { it.write(verificationTypeFactory) }
    }

    override fun sameLocals1StackItemFrame(frameType: Int,
                                           encodedFirstStackItemVerificationType: EncodedVerificationType) {
        assert(frameType in 65..127)
        output.writeByte(frameType)
        encodedFirstStackItemVerificationType.write(verificationTypeFactory)
    }

    override fun sameLocals1StackItemFrameExtended(offsetDelta: Int,
                                                   encodedFirstStackItemVerificationType: EncodedVerificationType) {
        output.writeByte(StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED)
        output.writeShort(offsetDelta)
        encodedFirstStackItemVerificationType
                .write(verificationTypeFactory)
    }

    override fun sameFrameExtended(offsetDelta: Int) {
        output.writeByte(StackMapFrameConstants.SAME_FRAME_EXTENDED)
        output.writeShort(offsetDelta)
    }

    companion object {
        fun create(logger: Logger, output: DataOutputStream): ByteCodeStackMapFrameEncodedWriter {
            return ByteCodeStackMapFrameEncodedWriter(logger,
                    output,
                    ByteCodeVerificationTypeEncodedWriter(
                            output))
        }

    }
}