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
import com.github.jferard.classwriter.writer.encoded.AnnotationsAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.FieldAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextFieldAttributeEncodedWriter(private val output: Writer,
                                      private val entries: List<EncodedConstantPoolEntry>,
                                      private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                                      private val cfmAttributeEncodedWriter: TextCFMAttributeEncodedWriter) :
        FieldAttributeEncodedWriter {
    override fun constantValueAttribute(attributeNameIndex: Int,
                                        valueIndex: Int) {
        output.write("constant\n")
    }

    override fun deprecatedAttribute(attributeNameIndex: Int) {
        cfmAttributeEncodedWriter.deprecatedAttribute(attributeNameIndex)
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        cfmAttributeEncodedWriter.syntheticAttribute(attributeNameIndex)
    }

    override fun signatureAttribute(attributeNameIndex: Int,
                                    signatureIndex: Int) {
        cfmAttributeEncodedWriter.signatureAttribute(attributeNameIndex, signatureIndex)
    }

    override fun annotationAttribute(annotationsNameIndex: Int,
                                     encodedAnnotations: List<EncodedAnnotation>) {
        cfmAttributeEncodedWriter.annotationAttribute(annotationsNameIndex, encodedAnnotations)
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter): TextFieldAttributeEncodedWriter {
            return TextFieldAttributeEncodedWriter(output, entries, summaryEncodedWriter,
                    TextCFMAttributeEncodedWriter.create(output, entries, summaryEncodedWriter))
        }
    }

}