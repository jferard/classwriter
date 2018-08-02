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

package com.github.jferard.classwriter.bytecode;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.internal.attribute.AnnotationWritableFactory;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeInnerClassesAttribute;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeLineNumberTableAttribute;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeStackMapTableAttribute;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeVariableTableAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable;
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameWritableFactory;
import com.github.jferard.classwriter.bytecode.attribute.ByteCodeIndexedAttribute;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame;
import com.github.jferard.classwriter.internal.instruction.base.InstructionWritableFactory;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeAttributeWritableFactory implements AttributeWritableFactory<DataOutput> {
    private InstructionWritableFactory<DataOutput> instructionWritableFactory;
    private MethodWritableFactory<DataOutput> methodWritableFactory;
    private StackMapFrameWritableFactory<DataOutput> stackMapTabelWritableFactory;
    private AnnotationWritableFactory<DataOutput> annotationWritableFactory;

    @Override
    public ByteCode codeAttribute(int attributeNameIndex, int maxStack, int maxLocals,
                                  int codeLength, EncodedInstruction encodedInstruction,
                                  List<EncodedExceptionInCode> encodedExceptions,
                                  int attributesLength, List<EncodedAttribute> encodedAttributes) {
        final ByteCode writableInstruction = (ByteCode) encodedInstruction
                .toWritable(this.instructionWritableFactory);
        List<ByteCode> writableExceptions = encodedExceptions.stream()
                .map(e -> (ByteCode) e.toWritable(this.methodWritableFactory))
                .collect(Collectors.toList());
        final List<ByteCode> writableAttributes = encodedAttributes.stream()
                .map(a -> (ByteCode) a.toWritable(this)).collect(Collectors.toList());

        int length = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + codeLength +
                BytecodeHelper.SHORT_SIZE +
                encodedExceptions.stream().mapToInt(EncodedExceptionInCode::getSize).sum() +
                BytecodeHelper.SHORT_SIZE +
                encodedAttributes.stream().mapToInt(Encoded::getSize).sum();

        return new ByteCodeCodeAttribute(attributeNameIndex, length, maxStack, maxLocals,
                codeLength, writableInstruction, writableExceptions, attributesLength,
                writableAttributes);
    }

    @Override
    public ByteCode stackMapTableAttribute(int attributeNameIndex,
                                           List<EncodedStackMapFrame> encodedStackMapFrames) {
        int length = encodedStackMapFrames.stream().mapToInt(Encoded::getSize).sum();
        final List<ByteCode> writableStackMapFrames = encodedStackMapFrames.stream()
                .map(esmf -> (ByteCode) esmf.toWritable(this.stackMapTabelWritableFactory))
                .collect(Collectors.toList());
        return new ByteCodeStackMapTableAttribute(attributeNameIndex, length, writableStackMapFrames);
    }

    @Override
    public ByteCode constantValueAttribute(int attributeNameIndex, int valueIndex) {
        return new ByteCodeIndexedAttribute(attributeNameIndex, valueIndex);
    }

    @Override
    public ByteCode annotationAttribute(int annotationsNameIndex,
                                        List<EncodedAnnotation> encodedAnnotations) {
        final List<ByteCode> writableAnnotations = encodedAnnotations.stream()
                .map(a -> (ByteCode) a.toWritable(this.annotationWritableFactory))
                .collect(Collectors.toList());

        final int length = encodedAnnotations.stream().mapToInt(Encoded::getSize).sum();
        return new ByteCodeAnnotationAttribute(annotationsNameIndex, length, writableAnnotations);
    }

    @Override
    public ByteCode innerClassesAttribute(int attributeNameIndex,
                                          List<EncodedInnerClass> encodedInnerClasses) {
        int length = encodedInnerClasses.stream().mapToInt(Encoded::getSize).sum();
        List<ByteCode> writableInnerClasses = encodedInnerClasses.stream()
                .map(eic -> (ByteCode) eic.toWritable(this)).collect(Collectors.toList());
        return new ByteCodeInnerClassesAttribute(attributeNameIndex, length, writableInnerClasses);
    }

    @Override
    public ByteCode innerClass(int innerClassIndex, int outerClassIndex, int innerClassNameIndex,
                               int innerAccessFlags) {
        return new ByteCodeInnerClass(innerClassIndex, outerClassIndex, innerClassNameIndex,
                innerAccessFlags);
    }

    @Override
    public ByteCode lineNumberTableAttribute(int nameIndex,
                                             List<PositionAndLineNumber> positionAndLineNumbers) {
        final List<ByteCode> writablePositionAndLineNumbers = positionAndLineNumbers.stream()
                .map(paln -> (ByteCode) paln.toWritable(this)).collect(Collectors.toList());
        final int attributeLength = BytecodeHelper.SHORT_SIZE +
                positionAndLineNumbers.stream().mapToInt(Encoded::getSize).sum();
        return new ByteCodeLineNumberTableAttribute(nameIndex, attributeLength, writablePositionAndLineNumbers);
    }

    @Override
    public ByteCode positionAndLineNumber(int startPc, int lineNumber) {
        return new ByteCodePositionAndLineNumber(startPc, lineNumber);
    }

    @Override
    public ByteCode variableTableAttribute(int attributeNameIndex,
                                           List<EncodedLocalVariableTable> encodedLocalVariables) {
        final List<ByteCode> writableLocalVariables = encodedLocalVariables.stream()
                .map(elv -> (ByteCode) elv.toWritable(this)).collect(Collectors.toList());
        final int length = encodedLocalVariables.stream().mapToInt(Encoded::getSize).sum();
        return new ByteCodeVariableTableAttribute(attributeNameIndex, length,
                writableLocalVariables);
    }

    @Override
    public ByteCode localVariableTable(int startPc, int length, int nameIndex, int descriptorIndex,
                                       int index) {
        return new ByteCodeLocalVariableOrVariableTypeTable(startPc, length, nameIndex,
                descriptorIndex, index);
    }

    @Override
    public ByteCode localVariableTypeTable(int startPc, int length, int nameIndex,
                                           int signatureIndex, int index) {
        return new ByteCodeLocalVariableOrVariableTypeTable(startPc, length, nameIndex,
                signatureIndex, index);
    }

    @Override
    public ByteCode variableTypeTableAttribute(int attributeNameIndex,
                                               List<EncodedLocalVariableTypeTable> encodedLocalVariableTypes) {
        final List<ByteCode> writableLocalVariableTypes = encodedLocalVariableTypes.stream()
                .map(elv -> (ByteCode) elv.toWritable(this)).collect(Collectors.toList());
        final int length = encodedLocalVariableTypes.stream().mapToInt(Encoded::getSize).sum();
        return new ByteCodeVariableTableAttribute(attributeNameIndex, length,
                writableLocalVariableTypes);
    }

    @Override
    public Writable<DataOutput> deprecatedAttribute(int attributeNameIndex) {
        return new ByteCodeVoidAttribute(attributeNameIndex);
    }

    @Override
    public Writable<DataOutput> sourceFileAttribute(int attributeNameIndex,
                                                    int sourceFileNameIndex) {
        return new ByteCodeIndexedAttribute(attributeNameIndex, sourceFileNameIndex);
    }

    @Override
    public Writable<DataOutput> syntheticAttribute(int attributeNameIndex) {
        return new ByteCodeVoidAttribute(attributeNameIndex);
    }

    @Override
    public Writable<DataOutput> attributes(List<? extends EncodedAttribute> encodedAttributes) {
        return new ByteCodeList(encodedAttributes.stream()
                .map(ef -> (ByteCode) ef.toWritable(this))
                .collect(Collectors.toList()));
    }

    @Override
    public Writable<DataOutput> bootstrapMethodsAttribute(int nameIndex,
                                                          List<EncodedBootstrapMethod> encodedBootstrapMethods) {
        throw new IllegalStateException();
    }

    @Override
    public Writable<DataOutput> signatureAttribute(int attributeNameIndex, int signatureIndex) {
        return new ByteCodeIndexedAttribute(attributeNameIndex, signatureIndex);
    }

}
