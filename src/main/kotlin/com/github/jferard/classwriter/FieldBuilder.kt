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
package com.github.jferard.classwriter

import com.github.jferard.classwriter.api.FieldDescriptor
import com.github.jferard.classwriter.api.ValueType
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute
import com.github.jferard.classwriter.internal.access.FieldAccess
import com.github.jferard.classwriter.internal.attribute.*
import com.github.jferard.classwriter.internal.attribute.Annotation
import kotlin.experimental.or

/**
 * 4.5. Fields
 * ```field_info {
 * u2             access_flags;
 * u2             name_index;
 * u2             descriptor_index;
 * u2             attributes_count;
 * attribute_info attributes[attributes_count];
 * }
 * ``` *
 */
class FieldBuilder internal constructor(name: String, descriptor: FieldDescriptor) {
    private val name: String
    private val descriptor: FieldDescriptor
    private var accessFlags: Short
    private var constantValueAttribute: FieldAttribute<*, *, *>?
    private var syntheticAttribute: FieldAttribute<*, *, *>?
    private var deprecatedAttribute: FieldAttribute<*, *, *>?
    private var signatureAttribute: FieldAttribute<*, *, *>?
    private val annotationsByCategoryName: MutableMap<String, MutableList<Annotation>> = HashMap()

    fun accessFlags(accessFlags: Short): FieldBuilder {
        this.accessFlags = accessFlags
        return this
    }

    fun constantValue(o: Any): FieldBuilder {
        constantValueAttribute = descriptor.toConstantValueAttribute(o)
        return this
    }

    fun synthetic(): FieldBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_SYNTHETIC.toShort()
        syntheticAttribute = SyntheticAttribute.INSTANCE
        return this
    }

    fun deprecated(): FieldBuilder {
        deprecatedAttribute = DeprecatedAttribute.INSTANCE
        return this
    }

    fun signature(signature: Signature): FieldBuilder {
        signatureAttribute = SignatureAttribute(signature)
        return this
    }

    fun addRuntimeVisibleAnnotation(
            annotation: Annotation): FieldBuilder {
        return addAnnotation(AnnotationsAttribute.RUNTIME_VISIBLE_ANNOTATIONS,
                annotation)
    }

    private fun addAnnotation(categoryName: String,
                              annotation: Annotation): FieldBuilder {
        annotationsByCategoryName.computeIfAbsent(categoryName
        ) { ArrayList() }.add(annotation)
        return this
    }

    fun addRuntimeInvisibleAnnotation(
            annotation: Annotation): FieldBuilder {
        return addAnnotation(AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS,
                annotation)
    }

    fun addRuntimeVisibleTypeAnnotation(
            annotation: Annotation): FieldBuilder {
        return addAnnotation(AnnotationsAttribute.RUNTIME_VISIBLE_TYPE_ANNOTATIONS,
                annotation)
    }

    fun addRuntimeInvisibleTypeAnnotation(
            annotation: Annotation): FieldBuilder {
        return addAnnotation(AnnotationsAttribute.RUNTIME_INVISIBLE_TYPE_ANNOTATIONS,
                annotation)
    }

    fun build(): Field {
        val annotationsAttributes: List<FieldAttribute<*, *, *>> =
                annotationsByCategoryName.entries.map { e ->
                    AnnotationsAttribute(e.key, e.value)
                }
        val otherAttributes: List<FieldAttribute<*, *, *>> =
                listOf(constantValueAttribute, syntheticAttribute,
                        deprecatedAttribute,
                        signatureAttribute).filterNotNull()
        val attributes =
                otherAttributes.plus(
                        annotationsAttributes) as List<FieldAttribute<*, EncodedFieldAttribute<*, *, *>, *>>
        return Field(accessFlags.toInt(), name, descriptor, attributes)
    }

    companion object {
        fun create(name: String, type: ValueType): FieldBuilder {
            return FieldBuilder(name, FieldDescriptor(type))
        }
    }

    init {
        accessFlags =
                FieldAccess.ACC_PRIVATE.toShort()
        this.name = name
        this.descriptor = descriptor
        constantValueAttribute = null
        syntheticAttribute = null
        deprecatedAttribute = null
        signatureAttribute = null
    }
}