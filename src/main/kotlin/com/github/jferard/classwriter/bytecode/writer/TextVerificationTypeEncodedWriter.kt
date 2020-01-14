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

import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeConstants
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.parsed.writer.TextConstantPoolEntriesSummaryEncodedWriter
import com.github.jferard.classwriter.parsed.writer.TextEncodedWriterHelper
import java.io.Writer

class TextVerificationTypeEncodedWriter(private val output: Writer,
                                        private val entries: List<EncodedConstantPoolEntry>,
                                        private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter) :
        VerificationTypeEncodedWriter {
    override fun byteVerificationType(code: Int) {
        output.write("%s, // %s\n".format(when (code) {
            VerificationTypeConstants.TOP_CODE -> "VerificationTypeConstants.TOP_CODE"
            VerificationTypeConstants.INTEGER_CODE -> "VerificationTypeConstants.INTEGER_CODE"
            VerificationTypeConstants.FLOAT_CODE -> "VerificationTypeConstants.FLOAT_CODE"
            VerificationTypeConstants.DOUBLE_CODE -> "VerificationTypeConstants.DOUBLE_CODE"
            VerificationTypeConstants.LONG_CODE -> "VerificationTypeConstants.LONG_CODE"
//            VerificationTypeConstants.NULL_CODE -> "VerificationTypeConstants.NULL_CODE"
            else -> throw IllegalArgumentException("Unknown verification byte $code")
        }, code))
    }

    override fun objectVerificationType(classIndex: Int) {
        output.write("VerificationTypeConstants.OBJECT_CODE, ")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "class index", classIndex, entries,
                summaryEncodedWriter)
    }

    override fun nullVerificationType() {
        output.write("VerificationTypeConstants.NULL_CODE, // %s\n".format(
                VerificationTypeConstants.NULL_CODE))
    }

    override fun uninitializedOffsetVerificationType(offset: Int) {
        output.write("VerificationTypeConstants.UNINITIALIZED_OFFSET_CODE, ")
        TextEncodedWriterHelper.writeU2(output, "offset", offset)
    }
}