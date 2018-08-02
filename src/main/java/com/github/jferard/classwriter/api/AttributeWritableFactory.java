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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable;
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber;
import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

import java.util.List;

public interface AttributeWritableFactory<O> extends WritableFactory<O> {
    Writable<O> codeAttribute(int attributeNameIndex, int maxStack, int maxLocals, int codeLength,
                              EncodedInstruction encodedencodedInstruction,
                              List<EncodedExceptionInCode> exceptions, int attributesLength,
                              List<EncodedAttribute> attributes);

    Writable<O> stackMapTableAttribute(int attributeNameIndex, List<EncodedStackMapFrame> encodedStackMapFrames);

    Writable<O> constantValueAttribute(int attributeNameIndex, int valueIndex);

    Writable<O> annotationAttribute(int annotationsNameIndex, List<EncodedAnnotation> encodedAnnotations);

    Writable<O> innerClassesAttribute(int attributeNameIndex,
                                      List<EncodedInnerClass> encodedInnerClasses);

    Writable<O> innerClass(int innerClassIndex, int outerClassNameIndex, int innerClassNameIndex, int innerAccessFlags);

    Writable<O> lineNumberTableAttribute(int nameIndex,
                                         List<PositionAndLineNumber> positionAndLineNumbers);

    Writable<O> positionAndLineNumber(int startPc, int lineNumber);

    Writable<O> variableTableAttribute(int attributeNameIndex,
                                       List<EncodedLocalVariableTable> encodedLocalVariables);

    Writable<O> localVariableTable(int startPc, int length, int nameIndex, int descriptorIndex, int index);

    Writable<O> localVariableTypeTable(int startPc, int length, int nameIndex, int signatureIndex, int index);

    Writable<O> variableTypeTableAttribute(int attributeNameIndex,
                                           List<EncodedLocalVariableTypeTable> encodedLocalVariableTypes);

    Writable<O> deprecatedAttribute(int attributeNameIndex);

    Writable<O> sourceFileAttribute(int attributeNameIndex, int sourceFileNameIndex);

    Writable<O> syntheticAttribute(int attributeNameIndex);

    Writable<O> attributes(List<? extends EncodedAttribute> encodedAttributes);

    Writable<O> bootstrapMethodsAttribute(int nameIndex,
                                          List<EncodedBootstrapMethod> encodedBootstrapMethods);

    Writable<O> signatureAttribute(int attributeNameIndex, int signatureIndex);
}
