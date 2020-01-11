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
package com.github.jferard.classwriter.internal.attribute

import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.instruction.Instruction
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter

/**
 * 4.7.3. The Code Attribute
 */
class CodeAttributeBuilder(
        private val code: Instruction) {
    private val runtimeVisibleTypeAnnotations: AnnotationsAttributeBuilder =
            AnnotationsAttributeBuilder(
                    AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS)
    private val runtimeInvisibleTypeAnnotations: AnnotationsAttributeBuilder =
            AnnotationsAttributeBuilder(
                    AnnotationsAttribute.RUNTIME_INVISIBLE_TYPE_ANNOTATIONS)
    private var lineNumberTable: LineNumberTableAttribute? = null
    private var localVariableTable: LocalVariableTableAttribute? = null
    private var localVariableTypeTable: LocalVariableTypeTableAttribute? = null
    private var stackMapTable: StackMapTableAttribute? = null
    private var argVerificationTypes: MutableList<VerificationType> = ArrayList()
    /**
     * 4.7.12. The LineNumberTable Attribute
     */
    fun lineNumberTable(lineNumberTable: LineNumberTableAttribute): CodeAttributeBuilder {
        this.lineNumberTable = lineNumberTable
        return this
    }

    /**
     * 4.7.13. The LocalVariableTable Attribute
     */
    fun localVariableTable(
            localVariableTable: LocalVariableTableAttribute): CodeAttributeBuilder {
        this.localVariableTable = localVariableTable
        return this
    }

    /**
     * 4.7.14. The LocalVariableTypeTable Attribute
     */
    fun localVariableTypeTable(
            localVariableTypeTable: LocalVariableTypeTableAttribute): CodeAttributeBuilder {
        this.localVariableTypeTable = localVariableTypeTable
        return this
    }

    /**
     * 4.7.4. The StackMapTable Attribute
     */
    fun stackMapTable(stackMapTable: StackMapTableAttribute): CodeAttributeBuilder {
        this.stackMapTable = stackMapTable
        return this
    }

    /**
     * 4.7.16. The RuntimeVisibleAnnotations Attribute
     */
    fun addRuntimeVisibleTypeAnnotation(
            annotation: Annotation): CodeAttributeBuilder {
        runtimeVisibleTypeAnnotations.add(annotation)
        return this
    }

    /**
     * 4.7.17. The RuntimeInvisibleAnnotations Attribute
     */
    fun addRuntimeInvisibleTypeAnnotation(
            annotation: Annotation): CodeAttributeBuilder {
        runtimeInvisibleTypeAnnotations.add(annotation)
        return this
    }

    fun build(): CodeAttribute {
        return CodeAttribute(code, argVerificationTypes,
                listOf(lineNumberTable,
                        localVariableTable,
                        localVariableTypeTable, stackMapTable,
                        runtimeVisibleTypeAnnotations.build(),
                        runtimeInvisibleTypeAnnotations.build()).filterNotNull() as List<CodeAttributeAttribute<*, EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>, *>>)
    }

    fun argVerificationTypes(
            argVerificationTypes: MutableList<VerificationType>) {
        this.argVerificationTypes = argVerificationTypes
    }

    companion object {
        const val CODE_NAME: String = "Code"
    }

}