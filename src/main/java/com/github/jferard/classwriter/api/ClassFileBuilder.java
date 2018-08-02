/*
 * ClassWriter - A minimal Java bytecode writer. Creates classes, methodBuilders, interfaces...
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

import com.github.jferard.classwriter.Attribute;
import com.github.jferard.classwriter.Field;
import com.github.jferard.classwriter.FieldBuilder;
import com.github.jferard.classwriter.internal.access.ClassAccess;
import com.github.jferard.classwriter.internal.attribute.Annotation;
import com.github.jferard.classwriter.internal.attribute.ClassFileAttribute;
import com.github.jferard.classwriter.internal.attribute.DeprecatedAttribute;
import com.github.jferard.classwriter.internal.attribute.SourceFileAttribute;
import com.github.jferard.classwriter.internal.attribute.SyntheticAttribute;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.EncodedClassFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * See 4.1 The ClassFile Structure
 */
public class ClassFileBuilder {
    private final PlainClassRef thisClassRef;
    private final PlainClassRef superClassRef;
    private final int minorVersion;
    private final int majorVersion;
    private final List<FieldBuilder> fieldBuilders;
    private final List<MethodBuilder> methodBuilders;
    private final List<ClassFileAttribute> attributes;
    private int accessFlags;
    private List<PlainClassRef> interfaces;

    public ClassFileBuilder(PlainClassRef thisClassRef) {
        this.minorVersion = 0;
        this.majorVersion = 52;
        this.accessFlags = ClassAccess.ACC_PUBLIC;
        this.thisClassRef = thisClassRef;
        this.superClassRef = ClassRef.OBJECT_CLASS_REF;
        this.interfaces = new ArrayList<>();
        this.fieldBuilders = new ArrayList<>();
        this.methodBuilders = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public ClassFileBuilder sourceFile(final String sourceName) {
        this.attributes.add(new SourceFileAttribute(sourceName));
        return this;
    }

    public ClassFileBuilder synthetic() {
        this.attributes.add(SyntheticAttribute.INSTANCE);
        return this;
    }

    public ClassFileBuilder deprecated() {
        this.attributes.add(DeprecatedAttribute.INSTANCE);
        return this;
    }

    public ClassFileBuilder signature(final String signature) {
        // this.attributes.add(this.signature = signature;
        return this;
    }

    public ClassFileBuilder addInnerClass(final Object innerClass) {
        // this.innerClasses.add(innerClass);
        return this;
    }

    public ClassFileBuilder addRuntimeVisibleAnnotation(final Annotation annotation) {
        // this.runtimeVisibleAnnotations.add(annotation);
        return this;
    }

    public ClassFileBuilder addRuntimeInvisibleAnnotation(final Annotation annotation) {
        // this.runtimeInvisibleAnnotations.add(annotation);
        return this;
    }

    public ClassFileBuilder addRuntimeVisibleTypeAnnotation(final Annotation annotation) {
        // this.runtimeVisibleTypeAnnotations.add(annotation);
        return this;
    }

    public ClassFileBuilder addRuntimeInvisibleTypeAnnotation(final Annotation annotation) {
        // this.runtimeInvisibleTypeAnnotations.add(annotation);
        return this;
    }

    public ClassFileBuilder enclosingMethod(final Object enclosingMethod) {
        // this.enclosingMethod = enclosingMethod;
        return this;
    }

    public ClassFileBuilder sourceDebugExtension(final Object sourceDebugExtension) {
        // this.sourceDebugExtension = sourceDebugExtension;
        return this;
    }

    public ClassFileBuilder addBootstrapMethod(final Object bootstrapMethod) {
        // this.bootstrapMethods.add(bootstrapMethod);
        return this;
    }


    public ClassFileBuilder addMethod(final MethodBuilder method) {
        this.methodBuilders.add(method);
        return this;
    }

    public ClassFileBuilder addField(final FieldRef field) {
        throw new IllegalStateException();
    }

    public ClassFileBuilder addAttribute(final Attribute attr) {
        // this.attrs.add(attr);
        return this;
    }

    public ClassFileBuilder accessFlags(final short accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }


    public ClassFileBuilder superClass(final ClassFileBuilder c) {
        // this.superClass = c;
        return this;
    }

    public ClassFileBuilder interfaces(final List<PlainClassRef> l) {
        this.interfaces = l;
        return this;
    }

    public ClassFile build() {
        final List<Field> fields = this.fieldBuilders.stream().map(fb -> fb.build())
                .collect(Collectors.toList());
        final List<Method> methods = this.methodBuilders.stream().map(mb -> mb.build())
                .collect(Collectors.toList());
        return new ClassFile(new Header(this.minorVersion, this.majorVersion), this.accessFlags,
                this.thisClassRef, this.superClassRef, this.interfaces, fields, methods,
                this.attributes);
    }

    public int getConstantPoolCount() {
        return 0;
    }

    public ClassFileBuilder package_(String packageName) {
        // this.packageName = packageName;
        return this;
    }

    public ClassFileBuilder access(int accessFlags) {
        this.accessFlags = accessFlags;
        return this;
    }

    public void method(MethodBuilder builder) {
        this.methodBuilders.add(builder);
    }

    public EncodedClassFile encode() {
        GlobalContext context = GlobalContext.create();
        return this.build().encode(context, MethodContext.create(0));
    }
}
