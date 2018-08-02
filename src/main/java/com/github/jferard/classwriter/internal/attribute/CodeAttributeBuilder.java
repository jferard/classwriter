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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.Arrays;
import java.util.List;

/**
 * 4.7.3. The Code Attribute
 */
public class CodeAttributeBuilder {
    public static final String CODE_NAME = "Code";
    private final Instruction code;
    private LineNumberTableAttribute lineNumberTable;
    private AnnotationsAttributeBuilder runtimeVisibleTypeAnnotations;
    private AnnotationsAttributeBuilder runtimeInvisibleTypeAnnotations;
    private LocalVariableTableAttribute localVariableTable;
    private LocalVariableTypeTableAttribute localVariableTypeTable;
    private StackMapTableAttribute stackMapTable;
    private List<VerificationType> argVerificationTypes;

    public CodeAttributeBuilder(final Instruction code) {
        this.code = code;
        this.runtimeVisibleTypeAnnotations = new AnnotationsAttributeBuilder(
                AnnotationsAttribute.RUNTIME_INVISIBLE_ANNOTATIONS);
        this.runtimeInvisibleTypeAnnotations = new AnnotationsAttributeBuilder(
                AnnotationsAttribute.RUNTIME_INVISIBLE_TYPE_ANNOTATIONS);
    }

    /**
     * 4.7.12. The LineNumberTable Attribute
     */
    public CodeAttributeBuilder lineNumberTable(LineNumberTableAttribute lineNumberTable) {
        this.lineNumberTable = lineNumberTable;
        return this;
    }

    /**
     * 4.7.13. The LocalVariableTable Attribute
     */
    public CodeAttributeBuilder localVariableTable(LocalVariableTableAttribute localVariableTable) {
        this.localVariableTable = localVariableTable;
        return this;
    }

    /**
     * 4.7.14. The LocalVariableTypeTable Attribute
     */
    public CodeAttributeBuilder localVariableTypeTable(
            LocalVariableTypeTableAttribute localVariableTypeTable) {
        this.localVariableTypeTable = localVariableTypeTable;
        return this;
    }

    /**
     * 4.7.4. The StackMapTable Attribute
     */
    public CodeAttributeBuilder stackMapTable(StackMapTableAttribute stackMapTable) {
        this.stackMapTable = stackMapTable;
        return this;
    }

    /**
     * 4.7.16. The RuntimeVisibleAnnotations Attribute
     */
    public CodeAttributeBuilder addRuntimeVisibleTypeAnnotation(final Annotation annotation) {
        this.runtimeVisibleTypeAnnotations.add(annotation);
        return this;
    }

    /**
     * 4.7.17. The RuntimeInvisibleAnnotations Attribute
     */
    public CodeAttributeBuilder addRuntimeInvisibleTypeAnnotation(final Annotation annotation) {
        this.runtimeInvisibleTypeAnnotations.add(annotation);
        return this;
    }

    public CodeAttribute build() {
        return new CodeAttribute(this.code, this.argVerificationTypes,
                Arrays.<CodeAttributeAttribute>asList(this.lineNumberTable, this.localVariableTable,
                        this.localVariableTypeTable, this.stackMapTable,
                        this.runtimeVisibleTypeAnnotations.build(),
                        this.runtimeInvisibleTypeAnnotations.build()));
    }

    public void argVerificationTypes(List<VerificationType> argVerificationTypes) {
        this.argVerificationTypes = argVerificationTypes;
    }
}
