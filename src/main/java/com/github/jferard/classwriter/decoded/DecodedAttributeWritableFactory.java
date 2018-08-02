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

package com.github.jferard.classwriter.decoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame;
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class DecodedAttributeWritableFactory implements AttributeWritableFactory<Writer> {
    public static DecodedAttributeWritableFactory create() {
        return new DecodedAttributeWritableFactory(
                new DecodedBootstrapMethodsAttributeWritableFactory(),
                new DecodedInstructionWritableFactory(), DecodedMethodWritableFactory.create());
    }

    private final DecodedBootstrapMethodsAttributeWritableFactory decodedBootstrapMethodsAttributeWritableFactory;
    private DecodedInstructionWritableFactory decodedInstructionFactory;
    private DecodedMethodWritableFactory decodedMethodWritableFactory;

    public DecodedAttributeWritableFactory(
            DecodedBootstrapMethodsAttributeWritableFactory decodedBootstrapMethodsAttributeWritableFactory,
            DecodedInstructionWritableFactory decodedInstructionFactory,
            DecodedMethodWritableFactory decodedMethodWritableFactory) {
        this.decodedBootstrapMethodsAttributeWritableFactory =
                decodedBootstrapMethodsAttributeWritableFactory;
        this.decodedInstructionFactory = decodedInstructionFactory;
        this.decodedMethodWritableFactory = decodedMethodWritableFactory;
    }

    @Override
    public Writable<Writer> codeAttribute(int attributeNameIndex, int maxStack, int maxLocals,
                                          int codeLength, EncodedInstruction encodedInstruction,
                                          List<EncodedExceptionInCode> encodedExceptions,
                                          int attributesLength,
                                          List<EncodedAttribute> encodedAttributes) {
        final Decoded decodedInstruction = (Decoded) encodedInstruction
                .toWritable(this.decodedInstructionFactory);
        final List<Decoded> decodedExceptions = encodedExceptions.stream()
                .map(e -> (Decoded) e.toWritable(this.decodedMethodWritableFactory))
                .collect(Collectors.toList());

        return new DecodedCodeAttribute(attributeNameIndex, maxStack, maxLocals,
                decodedInstruction);
    }

    @Override
    public Writable<Writer> stackMapTableAttribute(int attributeNameIndex,
                                                   List<EncodedStackMapFrame> encodedStackMapFrames) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> constantValueAttribute(int attributeNameIndex, int valueIndex) {

        return new DecodedConstantValueAttribute(attributeNameIndex, valueIndex);
    }

    @Override
    public Writable<Writer> annotationAttribute(int annotationsNameIndex,
                                                List<EncodedAnnotation> encodedAnnotations) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> innerClassesAttribute(int attributeNameIndex,
                                                  List<EncodedInnerClass> encodedInnerClasses) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> innerClass(int innerClassIndex, int outerClassNameIndex,
                                       int innerClassNameIndex, int innerAccessFlags) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> lineNumberTableAttribute(int nameIndex,
                                                     List<PositionAndLineNumber> positionAndLineNumbers) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> positionAndLineNumber(int startPc, int lineNumber) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> variableTableAttribute(int attributeNameIndex,
                                                   List<EncodedLocalVariableTable> encodedLocalVariables) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> localVariableTable(int startPc, int length, int nameIndex,
                                               int descriptorIndex, int index) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> localVariableTypeTable(int startPc, int length, int nameIndex,
                                                   int signatureIndex, int index) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> variableTypeTableAttribute(int attributeNameIndex,
                                                       List<EncodedLocalVariableTypeTable> encodedLocalVariableTypes) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> deprecatedAttribute(int attributeNameIndex) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> sourceFileAttribute(int attributeNameIndex, int sourceFileNameIndex) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> syntheticAttribute(int attributeNameIndex) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<Writer> attributes(List<? extends EncodedAttribute> encodedAttributes) {
        List<Decoded> decodedAttributes = encodedAttributes.stream()
                .map(e -> (Decoded) e.toWritable(this)).collect(Collectors.toList());
        return new DecodedAttributes(decodedAttributes);
    }

    @Override
    public Writable<Writer> bootstrapMethodsAttribute(int nameIndex,
                                                      List<EncodedBootstrapMethod> encodedBootstrapMethods) {
        return this.decodedBootstrapMethodsAttributeWritableFactory
                .bootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods);
    }

    @Override
    public Writable<Writer> signatureAttribute(int attributeNameIndex, int signatureIndex) {
        return new DecodedSignatureAttribute(attributeNameIndex, signatureIndex);
    }
}
