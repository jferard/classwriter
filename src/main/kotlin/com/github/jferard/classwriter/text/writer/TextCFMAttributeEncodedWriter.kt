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

import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.writer.encoded.AnnotationsEncodedWriter
import com.github.jferard.classwriter.writer.encoded.DeprecatedAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.SignatureAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.SyntheticAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextCFMAttributeEncodedWriter(private val output: Writer,
                                    private val entries: List<EncodedConstantPoolEntry>,
                                    private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter) :
        DeprecatedAttributeEncodedWriter, SyntheticAttributeEncodedWriter,
        SignatureAttributeEncodedWriter, AnnotationsEncodedWriter {
    override fun deprecatedAttribute(attributeNameIndex: Int) {
        output.write("deprecated\n")
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        output.write("synthetic\n")
    }

    override fun signatureAttribute(attributeNameIndex: Int,
                                    signatureIndex: Int) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "Attribute", attributeNameIndex, entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU2(output, "length", 2)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "Signature", signatureIndex, entries,
                summaryEncodedWriter)
    }

    override fun annotationAttribute(annotationsNameIndex: Int,
                                     encodedAnnotations: List<EncodedAnnotation>) {
        output.write("TODO ANNOTATIONS")
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter): TextCFMAttributeEncodedWriter {
            return TextCFMAttributeEncodedWriter(output, entries, summaryEncodedWriter)
        }

    }

}