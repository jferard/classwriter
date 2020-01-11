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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.Attribute
import com.github.jferard.classwriter.FieldBuilder
import com.github.jferard.classwriter.encoded.EncodedClassFile
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.internal.access.ClassAccess
import com.github.jferard.classwriter.internal.attribute.*
import com.github.jferard.classwriter.internal.attribute.Annotation
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.util.*

/**
 * See 4.1 The ClassFile Structure
 */
class ClassFileBuilder(thisClassRef: PlainClassRef) {
    private val thisClassRef: PlainClassRef
    private val superClassRef: PlainClassRef
    private val minorVersion = 0
    private val majorVersion = 52
    private val fieldBuilders: MutableList<FieldBuilder>
    private val methodBuilders: MutableList<MethodBuilder>
    private val attributes: MutableList<ClassFileAttribute<*, *, *>>
    private var accessFlags: Int
    private var interfaces: MutableList<PlainClassRef>

    fun sourceFile(sourceName: String): ClassFileBuilder {
        attributes.add(SourceFileAttribute(sourceName))
        return this
    }

    fun synthetic(): ClassFileBuilder {
        attributes.add(SyntheticAttribute.INSTANCE)
        return this
    }

    fun deprecated(): ClassFileBuilder {
        attributes.add(DeprecatedAttribute.INSTANCE)
        return this
    }

    fun signature(
            signature: String): ClassFileBuilder { // this.attributes.add(this.signature = signature;
        return this
    }

    fun addInnerClass(
            innerClass: Any): ClassFileBuilder { // this.innerClasses.add(innerClass);
        return this
    }

    fun addRuntimeVisibleAnnotation(
            annotation: Annotation): ClassFileBuilder { // this.runtimeVisibleAnnotations.add(annotation);
        return this
    }

    fun addRuntimeInvisibleAnnotation(
            annotation: Annotation): ClassFileBuilder { // this.runtimeInvisibleAnnotations.add(annotation);
        return this
    }

    fun addRuntimeVisibleTypeAnnotation(
            annotation: Annotation): ClassFileBuilder { // this.runtimeVisibleTypeAnnotations.add(annotation);
        return this
    }

    fun addRuntimeInvisibleTypeAnnotation(
            annotation: Annotation): ClassFileBuilder { // this.runtimeInvisibleTypeAnnotations.add(annotation);
        return this
    }

    fun enclosingMethod(
            enclosingMethod: Any): ClassFileBuilder { // this.enclosingMethod = enclosingMethod;
        return this
    }

    fun sourceDebugExtension(
            sourceDebugExtension: Any): ClassFileBuilder { // this.sourceDebugExtension = sourceDebugExtension;
        return this
    }

    fun addBootstrapMethod(
            bootstrapMethod: Any): ClassFileBuilder { // this.bootstrapMethods.add(bootstrapMethod);
        return this
    }

    fun addMethod(method: MethodBuilder): ClassFileBuilder {
        methodBuilders.add(method)
        return this
    }

    fun addField(field: FieldRef): ClassFileBuilder {
        throw IllegalStateException()
    }

    fun addAttribute(
            attr: Attribute): ClassFileBuilder { // this.attrs.add(attr);
        return this
    }

    fun accessFlags(accessFlags: Short): ClassFileBuilder {
        this.accessFlags = accessFlags.toInt()
        return this
    }

    fun superClass(c: ClassFileBuilder): ClassFileBuilder { // this.superClass = c;
        return this
    }

    fun interfaces(l: MutableList<PlainClassRef>): ClassFileBuilder {
        interfaces = l
        return this
    }

    fun build(): ClassFile {
        val fields =
                fieldBuilders.map(FieldBuilder::build)
        val methods =
                methodBuilders.map(MethodBuilder::build)
        return ClassFile(
                Header(minorVersion, majorVersion),
                accessFlags,
                thisClassRef, superClassRef, interfaces, fields, methods,
                attributes.toList() as List<ClassFileAttribute<*,
                        EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter>,
                        *>>)
    }

    val constantPoolCount: Int
        get() = 0

    fun package_(packageName: String): ClassFileBuilder { // this.packageName = packageName;
        return this
    }

    fun access(accessFlags: Int): ClassFileBuilder {
        this.accessFlags = accessFlags
        return this
    }

    fun method(builder: MethodBuilder) {
        methodBuilders.add(builder)
    }

    fun encode(): EncodedClassFile {
        val context: GlobalContext = GlobalContext.create()
        return build().encode(context, MethodContext.create(0))
    }

    init {
        accessFlags = ClassAccess.ACC_PUBLIC
        this.thisClassRef = thisClassRef
        superClassRef = ClassRef.OBJECT_CLASS_REF
        interfaces = ArrayList()
        fieldBuilders = ArrayList()
        methodBuilders = ArrayList()
        attributes = ArrayList()
    }
}