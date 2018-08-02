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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotationAttribute;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 * <p>
 * RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations,
 * RuntimeInvisibleParameterAnnotations, RuntimeVisibleTypeAnnotations and
 * RuntimeInvisibleTypeAnnotations.
 */
public class AnnotationsAttribute
        implements ClassFileAttribute, FieldAttribute, MethodInfoAttribute, CodeAttributeAttribute {

    public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    public static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    public static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS =
            "RuntimeInvisibleTypeAnnotations";

    public static AnnotationsAttribute runtimeVisible(List<Annotation> annotations) {
        return new AnnotationsAttribute(RUNTIME_VISIBLE_ANNOTATIONS, annotations);
    }

    public static AnnotationsAttribute runtimeInvisible(List<Annotation> annotations) {
        return new AnnotationsAttribute(RUNTIME_INVISIBLE_ANNOTATIONS, annotations);
    }

    public static AnnotationsAttribute runtimeVisibleType(List<Annotation> annotations) {
        return new AnnotationsAttribute(RUNTIME_VISIBLE_TYPE_ANNOTATIONS, annotations);
    }

    public static AnnotationsAttribute runtimeInvisibleType(List<Annotation> annotations) {
        return new AnnotationsAttribute(RUNTIME_INVISIBLE_TYPE_ANNOTATIONS, annotations);
    }

    public static AnnotationsAttributeBuilder runtimeVisibleBuilder(List<Annotation> annotations) {
        return new AnnotationsAttributeBuilder(RUNTIME_VISIBLE_ANNOTATIONS);
    }

    public static AnnotationsAttributeBuilder runtimeInvisibleBuilder(
            List<Annotation> annotations) {
        return new AnnotationsAttributeBuilder(RUNTIME_INVISIBLE_ANNOTATIONS);
    }

    public static AnnotationsAttributeBuilder runtimeVisibleTypeBuilder(
            List<Annotation> annotations) {
        return new AnnotationsAttributeBuilder(RUNTIME_VISIBLE_TYPE_ANNOTATIONS);
    }

    public static AnnotationsAttributeBuilder runtimeInvisibleTypeBuilder(
            List<Annotation> annotations) {
        return new AnnotationsAttributeBuilder(RUNTIME_INVISIBLE_TYPE_ANNOTATIONS);
    }


    private final String annotationsName;
    private final List<Annotation> annotations;

    public AnnotationsAttribute(String annotationsName, List<Annotation> annotations) {
        this.annotationsName = annotationsName;
        this.annotations = annotations;
    }

    @Override
    public EncodedAnnotationAttribute encode(GlobalContext context, MethodContext codeContext) {
        int annotationsNameIndex = context.addUtf8ToPool(this.annotationsName);
        int length = this.annotations.stream().mapToInt(Annotation::size).sum();
        return new EncodedAnnotationAttribute(annotationsNameIndex, length,
                this.annotations.stream().map(a -> a.encode(context, codeContext))
                        .collect(Collectors.toList()));
    }
}
