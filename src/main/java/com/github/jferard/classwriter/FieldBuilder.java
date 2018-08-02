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

package com.github.jferard.classwriter;

import com.github.jferard.classwriter.internal.access.FieldAccess;
import com.github.jferard.classwriter.api.FieldDescriptor;
import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.internal.attribute.Annotation;
import com.github.jferard.classwriter.internal.attribute.AnnotationsAttribute;
import com.github.jferard.classwriter.internal.attribute.DeprecatedAttribute;
import com.github.jferard.classwriter.internal.attribute.FieldAttribute;
import com.github.jferard.classwriter.internal.attribute.Signature;
import com.github.jferard.classwriter.internal.attribute.SignatureAttribute;
import com.github.jferard.classwriter.internal.attribute.SyntheticAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 4.5. Fields
 * <pre>{@code
 * field_info {
 *     u2             access_flags;
 *     u2             name_index;
 *     u2             descriptor_index;
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 * }</pre>
 */
public class FieldBuilder {
    public static FieldBuilder create(final String name, ValueType type) {
        return new FieldBuilder(name, new FieldDescriptor(type));
    }

    private final String name;
    private final FieldDescriptor descriptor;
    private short accessFlags;
    private FieldAttribute constantValueAttribute;
    private FieldAttribute syntheticAttribute;
    private FieldAttribute deprecatedAttribute;
    private FieldAttribute signatureAttribute;
    private Map<String, List<Annotation>> annotationsByCategoryName;

    FieldBuilder(final String name, FieldDescriptor descriptor) {
        this.accessFlags = FieldAccess.ACC_PRIVATE;
        this.name = name;
        this.descriptor = descriptor;
        this.constantValueAttribute = null;
        this.syntheticAttribute = null;
        this.deprecatedAttribute = null;
        this.signatureAttribute = null;
    }

    public FieldBuilder accessFlags(final short accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }

    public FieldBuilder constantValue(Object o) {
        this.constantValueAttribute = this.descriptor.toConstantValueAttribute(o);
        return this;
    }

    public FieldBuilder synthetic() {
        this.accessFlags |= FieldAccess.ACC_SYNTHETIC;
        this.syntheticAttribute = SyntheticAttribute.INSTANCE;
        return this;
    }

    public FieldBuilder deprecated() {
        this.deprecatedAttribute = DeprecatedAttribute.INSTANCE;
        return this;
    }

    public FieldBuilder signature(Signature signature) {
        this.signatureAttribute = new SignatureAttribute(signature);
        return this;
    }


    public FieldBuilder addRuntimeVisibleAnnotation(final Annotation annotation) {
        return this.addAnnotation(AnnotationsAttribute.RUNTIME_VISIBLE_ANNOTATIONS, annotation);
    }

    private FieldBuilder addAnnotation(String categoryName, Annotation annotation) {
        List<Annotation> annotations = this.annotationsByCategoryName
                .computeIfAbsent(categoryName, k -> new ArrayList<>());
        annotations.add(annotation);
        return this;
    }

    public FieldBuilder addRuntimeInvisibleAnnotation(final Annotation annotation) {
        return this.addAnnotation(AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS, annotation);
    }

    public FieldBuilder addRuntimeVisibleTypeAnnotation(final Annotation annotation) {
        return this.addAnnotation(AnnotationsAttribute.RUNTIME_VISIBLE_TYPE_ANNOTATIONS, annotation);
    }

    public FieldBuilder addRuntimeInvisibleTypeAnnotation(final Annotation annotation) {
        return this
                .addAnnotation(AnnotationsAttribute.RUNTIME_INVISIBLE_TYPE_ANNOTATIONS, annotation);
    }

    public Field build() {
        Stream<FieldAttribute> annotationsAttributesStream =
                this.annotationsByCategoryName
                .entrySet().stream().map(e -> new AnnotationsAttribute(e.getKey(), e.getValue()));
        Stream<FieldAttribute> otherAttributesStream = Stream
                .of(this.constantValueAttribute, this.syntheticAttribute, this.deprecatedAttribute,
                        this.signatureAttribute).filter(Objects::nonNull);

        List<FieldAttribute> attributes = Stream
                .concat(otherAttributesStream, annotationsAttributesStream)
                .collect(Collectors.toList());

        return new Field(this.accessFlags, this.name, this.descriptor, attributes);
    }
}
