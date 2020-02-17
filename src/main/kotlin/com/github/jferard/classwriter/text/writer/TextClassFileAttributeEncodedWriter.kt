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
package com.github.jferard.classwriter.text.writer

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.AnnotationsAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.CommonAttributeEncodedWriter
import java.io.Writer

/**
 * Decoder for EncodedAttribute (EncodedCodeAttribute, EncodedStackMapTableAttribute, ...)
 */
class TextClassFileAttributeEncodedWriter(private val output: Writer,
                                          private val entries: List<EncodedConstantPoolEntry>,
                                          private val summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter,
                                          private val cfmAttributeEncodedWriter: TextCFMAttributeEncodedWriter,
                                          private val annotationEncodedWriter: AnnotationEncodedWriter,
                                          private val textBootstrapMethodsAttributeWritableFactory: TextBootstrapMethodsAttributeEncodedWriter) :
        ClassFileAttributeEncodedWriter, AnnotationsAttributeEncodedWriter,
        CommonAttributeEncodedWriter {
    override fun innerClass(innerClassIndex: Int, outerClassNameIndex: Int,
                            innerNameIndex: Int,
                            innerAccessFlags: Int) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "inner class", innerClassIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "outer class", outerClassNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "inner name", innerNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeAccessFlags(output, ClassAccess.entries,
                innerAccessFlags)
    }

    override fun innerClassesAttribute(attributeNameIndex: Int,
                                       encodedInnerClasses: List<EncodedInnerClass>) {
        output.write("/** Inner classes */\n")
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "attribute len",
                Sized.listSize(0, encodedInnerClasses))
        encodedInnerClasses.forEach { it.write(this) }
    }

    override fun sourceFileAttribute(attributeNameIndex: Int,
                                     sourceFileNameIndex: Int) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "attribute len", 2)
        TextEncodedWriterHelper.writeShortEntryIndex(output, "source file name",
                sourceFileNameIndex, entries, summaryEncodedWriter)
    }

    override fun bootstrapMethodsAttribute(nameIndex: Int,
                                           encodedBootstrapMethods: List<EncodedBootstrapMethod>) {
        return textBootstrapMethodsAttributeWritableFactory
                .bootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods)
    }

    override fun classFileAttributes(
            encodedAttributes: List<EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>>) {
        output.write("/* ATTRIBUTES */")
        encodedAttributes.forEach { it.write(this) }
    }

    override fun sourceDebugExtension(attributeNameIndex: Int, debugExtension: ByteArray) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", attributeNameIndex,
                entries, summaryEncodedWriter)
        TextEncodedWriterHelper.writeU4(output, "attribute len", debugExtension.size)
        output.write(String(debugExtension, Charsets.UTF_8))
    }

    override fun signatureAttribute(attributeNameIndex: Int, signatureIndex: Int) {
        cfmAttributeEncodedWriter.signatureAttribute(attributeNameIndex, signatureIndex)
    }

    companion object {
        fun create(output: Writer,
                   entries: List<EncodedConstantPoolEntry>,
                   summaryEncodedWriter: TextConstantPoolEntriesSummaryEncodedWriter): TextClassFileAttributeEncodedWriter {
            return TextClassFileAttributeEncodedWriter(
                    output, entries, summaryEncodedWriter,
                    TextCFMAttributeEncodedWriter(output, entries, summaryEncodedWriter),
                    TextAnnotationEncodedWriter(
                            output, entries, summaryEncodedWriter),
                    TextBootstrapMethodsAttributeEncodedWriter(output))
        }
    }

    override fun syntheticAttribute(attributeNameIndex: Int) {
        cfmAttributeEncodedWriter.syntheticAttribute(attributeNameIndex)
    }

    override fun annotationsAttribute(annotationsNameIndex: Int,
                                      encodedAnnotations: List<EncodedAnnotation>) {
        TextEncodedWriterHelper.writeShortEntryIndex(output, "attribute name", annotationsNameIndex,
                entries,
                summaryEncodedWriter)
        TextEncodedWriterHelper.writeU2(output, "attribute length",
                Sized.listSize(0, encodedAnnotations))
        encodedAnnotations.forEach { it.write(annotationEncodedWriter) }
    }

    override fun parameterAnnotationsAttribute(attributeNameIndex: Int,
                                               parameterAnnotations: List<List<EncodedAnnotation>>) {
        TODO("Not yet implemented")
    }

}