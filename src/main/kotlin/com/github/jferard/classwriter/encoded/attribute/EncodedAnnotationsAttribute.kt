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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.attribute.AnnotationsAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.writer.encoded.AnnotationsAttributeEncodedWriter


/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute (ClassFile, field_info, or method_info structure)
 * ```
 * Annotations_attribute {
 *     u2         attribute_name_index;
 *     u4         attribute_length;
 *     u2         num_annotations;
 *     annotation annotations[num_annotations];
 * }
 * ```
 */
class EncodedAnnotationsAttribute(
        private val annotationsNameIndex: Int,
        private val encodedAnnotations: List<EncodedAnnotation>) :
        EncodedCFMAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationsAttributeEncodedWriter>,
        EncodedCodeAttributeAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationsAttributeEncodedWriter> {

    override fun write(
            encodedWriter: AnnotationsAttributeEncodedWriter) {
        return encodedWriter.annotationsAttribute(annotationsNameIndex, encodedAnnotations)
    }

    override val size: Int
        get() = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + BytecodeHelper.SHORT_SIZE +
                encodedAnnotations.map(EncodedAnnotation::size).sum()

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        TODO("implement")
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): AnnotationsAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}