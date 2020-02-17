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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.TypedIdentifier
import com.github.jferard.classwriter.internal.attribute.Annotation
import com.github.jferard.classwriter.internal.attribute.CodeAttribute
import com.github.jferard.classwriter.api.instruction.Instruction
import java.util.*

/**
 * 4.6. Methods
 */
class MethodBuilder(private val name: String,
                    private val descriptor: MethodDescriptor,
                    code: Instruction) {
    private val codeAttribute: CodeAttribute
    //    private List<Writable> attributes;
    private var accessFlags = 0
    private var params: MutableList<TypedIdentifier>? = null
    private var ret: TypedIdentifier? = null
    private var exceptions: MutableList<Exception>? = null
    private var synthetic = false
    private var deprecated = false
    private var signature: String? = null
    private val runtimeVisibleParameterAnnotations: MutableList<Annotation> = ArrayList()
    private val runtimeInvisibleParameterAnnotations: MutableList<Annotation>? = null
    private val runtimeVisibleAnnotations: MutableList<Annotation>? = null
    private val runtimeInvisibleAnnotations: MutableList<Annotation>? = null
    private val runtimeVisibleTypeAnnotations: MutableList<Annotation>? = null
    private val runtimeInvisibleTypeAnnotations: MutableList<Annotation>? = null
    private var annotationDefault: Any? = null
    private var methodParameters: Any? = null

    fun accessFlags(accessFlags: Int): MethodBuilder {
        this.accessFlags = accessFlags
        return this
    }

    fun params(params: MutableList<TypedIdentifier>): MethodBuilder {
        this.params = params
        return this
    }

    fun ret(ret: TypedIdentifier): MethodBuilder {
        this.ret = ret
        return this
    }

    fun exceptions(exceptions: MutableList<Exception>): MethodBuilder {
        this.exceptions = exceptions
        return this
    }

    fun synthetic(): MethodBuilder {
        synthetic = true
        return this
    }

    fun deprecated(): MethodBuilder {
        deprecated = true
        return this
    }

    fun signature(signature: String): MethodBuilder {
        this.signature = signature
        return this
    }

    fun addRuntimeVisibleParameterAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeVisibleParameterAnnotations!!.add(annotation)
        return this
    }

    fun addRuntimeInvisibleParameterAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeInvisibleParameterAnnotations!!.add(annotation)
        return this
    }

    fun addRuntimeVisibleAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeVisibleAnnotations!!.add(annotation)
        return this
    }

    fun addRuntimeInvisibleAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeInvisibleAnnotations!!.add(annotation)
        return this
    }

    fun addRuntimeVisibleTypeAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeVisibleTypeAnnotations!!.add(annotation)
        return this
    }

    fun addRuntimeInvisibleTypeAnnotation(
            annotation: Annotation): MethodBuilder {
        runtimeInvisibleTypeAnnotations!!.add(annotation)
        return this
    }

    fun annotationDefault(
            AnnotationDefault: Annotation): MethodBuilder {
        annotationDefault = AnnotationDefault
        return this
    }

    fun methodParameters(methodParameters: Any): MethodBuilder {
        this.methodParameters = methodParameters
        return this
    }

    fun access(accessFlags: Int): MethodBuilder {
        this.accessFlags = accessFlags
        return this
    }

    fun build(): Method {
        return Method(accessFlags, name,
                descriptor, codeAttribute)
    }

    init {
        //        this.attributes = new ArrayList<>();
        val argVerificationTypes =
                descriptor.argTypes
                        .map { at: ValueType -> at.verificationType }
        codeAttribute = CodeAttribute(code, argVerificationTypes,
                ArrayList())
    }
}