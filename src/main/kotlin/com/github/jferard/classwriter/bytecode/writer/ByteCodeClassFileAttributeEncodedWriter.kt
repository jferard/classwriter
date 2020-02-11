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

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.DataOutput

class ByteCodeClassFileAttributeEncodedWriter(private val output: DataOutput,
                                              private val annotationWriter: AnnotationEncodedWriter) :
        ClassFileAttributeEncodedWriter {


    override fun innerClassesAttribute(attributeNameIndex: Int,
                                       encodedInnerClasses: List<EncodedInnerClass>) {
        val length = Sized.listSize(encodedInnerClasses)
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        encodedInnerClasses.forEach { writableInnerClass ->
            writableInnerClass.write(this)
        }
    }

    override fun sourceFileAttribute(attributeNameIndex: Int,
                                     sourceFileNameIndex: Int) {
        output.writeShort(attributeNameIndex)
        output.writeInt(BytecodeHelper.SHORT_SIZE)
        output.writeShort(sourceFileNameIndex)
    }

    override fun bootstrapMethodsAttribute(nameIndex: Int,
                                           encodedBootstrapMethods: List<EncodedBootstrapMethod>) {
        TODO("not implemented")
    }

    override fun classFileAttributes(
            encodedAttributes: List<EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>>) {
        output.writeShort(encodedAttributes.size)
        for (byteCode in encodedAttributes) {
            byteCode.write(this)
        }
    }

    override fun signatureAttribute(attributeNameIndex: Int, signatureIndex: Int) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun annotationsAttribute(annotationsNameIndex: Int,
                                      encodedAnnotations: List<EncodedAnnotation>) {
        println("write class annot: $annotationsNameIndex")
        output.writeShort(annotationsNameIndex)
        output.writeInt(BytecodeHelper.SHORT_SIZE + Sized.listSize(encodedAnnotations))
        output.writeShort(encodedAnnotations.size)
        encodedAnnotations.forEach { it.write(this.annotationWriter) }
    }

    /**
     * ```
     * RuntimeVisibleParameterAnnotations_attribute {
     *    u2 attribute_name_index;
     *    u4 attribute_length;
     *    u1 num_parameters;
     *    {   u2         num_annotations;
     *        annotation annotations[num_annotations];
     *    } parameter_annotations[num_parameters];
     * }
     * ```
     */
    override fun parameterAnnotationsAttribute(attributeNameIndex: Int,
                                               parameterAnnotations: List<List<EncodedAnnotation>>) {
        val length = BytecodeHelper.BYTE_SIZE + parameterAnnotations.map {
            BytecodeHelper.SHORT_SIZE + Sized.listSize(it)
        }.sum()
        output.writeShort(attributeNameIndex)
        output.writeInt(length)
        output.writeByte(parameterAnnotations.size)
        parameterAnnotations.forEach {
            output.writeShort(it.size)
            it.forEach {
                it.write(this.annotationWriter)
            }
        }
    }

    /**
     * ```
     * SourceDebugExtension_attribute {
     *   u2 attribute_name_index;
     *   u4 attribute_length;
     *   u1 debug_extension[attribute_length];
     * }
     * ```
     */
    override fun sourceDebugExtension(attributeNameIndex: Int, debugExtension: ByteArray) {
        output.writeShort(attributeNameIndex)
        output.writeInt(debugExtension.size)
        output.write(debugExtension)
    }

    override fun innerClass(innerClassIndex: Int, outerClassNameIndex: Int,
                            innerClassNameIndex: Int,
                            innerAccessFlags: Int) {
        output.writeShort(innerClassIndex)
        output.writeShort(outerClassNameIndex)
        output.writeShort(innerClassNameIndex)
        output.writeShort(innerAccessFlags)
    }
}