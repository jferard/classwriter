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

package com.github.jferard.classwriter.api;

import com.github.jferard.classwriter.TypedIdentifier;
import com.github.jferard.classwriter.internal.attribute.Annotation;
import com.github.jferard.classwriter.internal.attribute.CodeAttribute;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.6. Methods
 */
public class MethodBuilder {
    private final CodeAttribute codeAttribute;
    private final MethodDescriptor descriptor;
    private final String name;
//    private List<Writable> attributes;
    private int accessFlags;
    private List<TypedIdentifier> params;
    private TypedIdentifier ret;
    private List<Exception> exceptions;
    private boolean synthetic;
    private boolean deprecated;
    private String signature;
    private List<Annotation> runtimeVisibleParameterAnnotations;
    private List<Annotation> runtimeInvisibleParameterAnnotations;
    private List<Annotation> runtimeVisibleAnnotations;
    private List<Annotation> runtimeInvisibleAnnotations;
    private List<Annotation> runtimeVisibleTypeAnnotations;
    private List<Annotation> runtimeInvisibleTypeAnnotations;
    private Object annotationDefault;
    private Object methodParameters;

    public MethodBuilder(final String name, final MethodDescriptor descriptor, Instruction code) {
        this.name = name;
        this.descriptor = descriptor;
//        this.attributes = new ArrayList<>();
        final List<VerificationType> argVerificationTypes = descriptor.getArgTypes().stream()
                .map(at -> at.getVerificationType()).collect(Collectors.toList());
        this.codeAttribute = new CodeAttribute(code, argVerificationTypes, new ArrayList<>());
    }

    public MethodBuilder accessFlags(final int accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }

    public MethodBuilder params(final List<TypedIdentifier> params) {
        this.params = params;
        return this;
    }

    public MethodBuilder ret(final TypedIdentifier ret) {
        this.ret = ret;
        return this;
    }

    public MethodBuilder exceptions(final List<Exception> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public MethodBuilder synthetic() {
        this.synthetic = true;
        return this;
    }

    public MethodBuilder deprecated() {
        this.deprecated = true;
        return this;
    }

    public MethodBuilder signature(final String signature) {
        this.signature = signature;
        return this;
    }

    public MethodBuilder addRuntimeVisibleParameterAnnotation(final Annotation annotation) {
        this.runtimeVisibleParameterAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder addRuntimeInvisibleParameterAnnotation(final Annotation annotation) {
        this.runtimeInvisibleParameterAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder addRuntimeVisibleAnnotation(final Annotation annotation) {
        this.runtimeVisibleAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder addRuntimeInvisibleAnnotation(final Annotation annotation) {
        this.runtimeInvisibleAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder addRuntimeVisibleTypeAnnotation(final Annotation annotation) {
        this.runtimeVisibleTypeAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder addRuntimeInvisibleTypeAnnotation(final Annotation annotation) {
        this.runtimeInvisibleTypeAnnotations.add(annotation);
        return this;
    }

    public MethodBuilder annotationDefault(final Annotation AnnotationDefault) {
        this.annotationDefault = AnnotationDefault;
        return this;
    }

    public MethodBuilder methodParameters(Object methodParameters) {
        this.methodParameters = methodParameters;
        return this;
    }

    public MethodBuilder access(int accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }

    public Method build() {
        return new Method(this.accessFlags, this.name, this.descriptor, this.codeAttribute);
    }
}
