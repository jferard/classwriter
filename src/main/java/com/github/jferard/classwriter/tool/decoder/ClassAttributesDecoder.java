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

package com.github.jferard.classwriter.tool.decoder;

import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedBootstrapMethodsAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClassesAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedSignatureAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedSourceFileAttribute;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassAttributesDecoder implements Decoder<AttributeWritableFactory<?>> {
    public static ClassAttributesDecoder create(final List<EncodedConstantPoolEntry> entries) {
        return new ClassAttributesDecoder(entries);
    }

    private List<EncodedConstantPoolEntry> entries;
    private List<EncodedBootstrapMethod> encodedBootstrapMethods;

    public ClassAttributesDecoder(List<EncodedConstantPoolEntry> entries) {
        this.entries = entries;
    }

    @Override
    public EncodedAttributes decode(DataInput input) throws IOException {
        int classAttributesCount = input.readShort();
        List<EncodedClassFileAttribute> encodedClassFileAttributes = new ArrayList<>(
                classAttributesCount);
        for (int i = 0; i < classAttributesCount; i++) {
            encodedClassFileAttributes.add(this.decodeClassAttribute(input));
        }
        return new EncodedAttributes(encodedClassFileAttributes);
    }

    private EncodedClassFileAttribute decodeClassAttribute(DataInput input) throws IOException {
        int attributeNameIndex = input.readShort();

        String attributeName = this.entries.get(attributeNameIndex).utf8Text();
        switch (attributeName) {
            case "Signature":
                return this.readSignatureAttribute(attributeNameIndex, input);
            case "SourceFile":
                return this.readSourceFileAttribute(attributeNameIndex, input);
            case "InnerClasses":
                return this.readInnerClassesAttribute(attributeNameIndex, input);
            case "BootstrapMethods":
                return this.readBootstrapMethodsAttribute(attributeNameIndex, input);
            default:
                System.out.println(attributeName);
        }
        throw new IllegalStateException();
    }

    /**
     * BootstrapMethods_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 num_bootstrap_methods;
     * {
     * u2 bootstrap_method_ref;
     * u2 num_bootstrap_arguments;
     * u2 bootstrap_arguments[num_bootstrap_arguments];
     * } bootstrap_methods[num_bootstrap_methods];
     * }
     */
    private EncodedClassFileAttribute readBootstrapMethodsAttribute(int attributeNameIndex,
                                                                    DataInput input)
            throws IOException {
        int length = input.readInt();
        int numBoostrapMethods = input.readShort();
        encodedBootstrapMethods = new ArrayList<>(numBoostrapMethods);
        for (int bsm = 0; bsm < numBoostrapMethods; bsm++) {
            int boostrapMethodRef = input.readShort();
            int numBoostrapArguments = input.readShort();
            List<Integer> boostrapArguments = new ArrayList<>(numBoostrapArguments);
            for (int bsa = 0; bsa < numBoostrapArguments; bsa++) {
                int boostrapArgument = input.readShort();
                boostrapArguments.add(boostrapArgument);
            }
            encodedBootstrapMethods
                    .add(new EncodedBootstrapMethod(boostrapMethodRef, boostrapArguments));
        }
        return new EncodedBootstrapMethodsAttribute(attributeNameIndex, encodedBootstrapMethods);

    }

    /**
     * InnerClasses_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 number_of_classes;
     * {   u2 inner_class_info_index;
     * u2 outer_class_info_index;
     * u2 inner_name_index;
     * u2 inner_class_access_flags;
     * } classes[number_of_classes];
     * }
     */
    private EncodedClassFileAttribute readInnerClassesAttribute(int attributeNameIndex,
                                                                DataInput input)
            throws IOException {
        int length = input.readInt();
        int numberOfClasses = input.readShort();
        List<EncodedInnerClass> encodedInnerClasses = new ArrayList<>(numberOfClasses);
        for (int c = 0; c < numberOfClasses; c++) {
            int innerClassIndex = input.readShort();
            int outerClassIndex = input.readShort();
            int innerNameIndex = input.readShort();
            int innerClassAccess = input.readShort();
            encodedInnerClasses
                    .add(new EncodedInnerClass(innerClassIndex, outerClassIndex, innerNameIndex,
                            innerClassAccess));
        }
        return new EncodedInnerClassesAttribute(attributeNameIndex, encodedInnerClasses);
    }

    private EncodedClassFileAttribute readSourceFileAttribute(int attributeNameIndex,
                                                              DataInput input) throws IOException {
        int length = input.readInt();
        int sourceFileIndex = input.readShort();
        return new EncodedSourceFileAttribute(attributeNameIndex, sourceFileIndex);
    }

    private EncodedClassFileAttribute readSignatureAttribute(int attributeNameIndex,
                                                             DataInput input) throws IOException {
        if (input.readInt() != 2) throw new IllegalStateException();
        int signatureIndex = input.readShort();
        return new EncodedSignatureAttribute(attributeNameIndex, signatureIndex);
    }
}
