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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotationsAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encodable.AnnotationEncodableWriter


/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute (ClassFile, field_info, or method_info)
 *
 *
 * RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations,
 * RuntimeInvisibleParameterAnnotations, RuntimeVisibleTypeAnnotations and
 * RuntimeInvisibleTypeAnnotations.
 */
class AnnotationsAttribute(private val annotationsName: String,
                           private val annotations: List<Annotation>) :
        ClassFileAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationEncodableWriter>,
        FieldAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationEncodableWriter>,
        CodeAttributeAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationEncodableWriter>,
        MethodAttribute<AnnotationsAttribute, EncodedAnnotationsAttribute, AnnotationEncodableWriter> {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedAnnotationsAttribute {
        val annotationsNameIndex: Int = context.addUtf8ToPool(annotationsName)
        val length = Sized.listSize(0, annotations)
        return EncodedAnnotationsAttribute(annotationsNameIndex,
                annotations.map { a: Annotation -> a.encode(context, codeContext) })
    }

    companion object {
        const val RUNTIME_VISIBLE_ANNOTATIONS: String = "RuntimeVisibleAnnotations"
        const val RUNTIME_INVISIBLE_ANNOTATIONS: String = "RuntimeInvisibleAnnotations"
        const val RUNTIME_VISIBLE_TYPE_ANNOTATIONS: String = "RuntimeVisibleTypeAnnotations"
        const val RUNTIME_INVISIBLE_TYPE_ANNOTATIONS: String = "RuntimeInvisibleTypeAnnotations"
        const val RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS: String =
                "RuntimeInvisibleParameterAnnotations"

        fun runtimeVisible(
                annotations: MutableList<Annotation>): AnnotationsAttribute {
            return AnnotationsAttribute(RUNTIME_VISIBLE_ANNOTATIONS,
                    annotations)
        }

        fun runtimeInvisible(
                annotations: MutableList<Annotation>): AnnotationsAttribute {
            return AnnotationsAttribute(
                    RUNTIME_INVISIBLE_ANNOTATIONS, annotations)
        }

        fun runtimeVisibleType(
                annotations: MutableList<Annotation>): AnnotationsAttribute {
            return AnnotationsAttribute(
                    RUNTIME_VISIBLE_TYPE_ANNOTATIONS, annotations)
        }

        fun runtimeInvisibleType(
                annotations: MutableList<Annotation>): AnnotationsAttribute {
            return AnnotationsAttribute(
                    RUNTIME_INVISIBLE_TYPE_ANNOTATIONS, annotations)
        }

        fun runtimeVisibleBuilder(
                annotations: MutableList<Annotation>): AnnotationsAttributeBuilder {
            return AnnotationsAttributeBuilder(
                    RUNTIME_VISIBLE_ANNOTATIONS)
        }

        fun runtimeInvisibleBuilder(
                annotations: MutableList<Annotation>): AnnotationsAttributeBuilder {
            return AnnotationsAttributeBuilder(
                    RUNTIME_INVISIBLE_ANNOTATIONS)
        }

        fun runtimeVisibleTypeBuilder(
                annotations: MutableList<Annotation>): AnnotationsAttributeBuilder {
            return AnnotationsAttributeBuilder(
                    RUNTIME_VISIBLE_TYPE_ANNOTATIONS)
        }

        fun runtimeInvisibleTypeBuilder(
                annotations: MutableList<Annotation>): AnnotationsAttributeBuilder {
            return AnnotationsAttributeBuilder(
                    RUNTIME_INVISIBLE_TYPE_ANNOTATIONS)
        }
    }

    override fun write(encodableWriter: AnnotationEncodableWriter) {


    }

}