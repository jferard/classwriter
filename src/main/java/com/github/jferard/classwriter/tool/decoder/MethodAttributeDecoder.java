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
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode;
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedSignatureAttribute;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodAttributeDecoder implements Decoder<AttributeWritableFactory<?>> {
    final List<EncodedConstantPoolEntry> entries;
    private CodeAttributeAttributeDecoder codeAttributeAttributeDecoder;

    public MethodAttributeDecoder(CodeAttributeAttributeDecoder codeAttributeAttributeDecoder,
                                  List<EncodedConstantPoolEntry> entries) {
        this.codeAttributeAttributeDecoder = codeAttributeAttributeDecoder;
        this.entries = entries;
    }

    @Override
    public EncodedMethodAttribute decode(DataInput input) throws IOException {
        int attributeNameIndex = input.readShort();
        final String attributeName = entries.get(attributeNameIndex).utf8Text();
        switch (attributeName) {
            case "Code":
                return this.readCodeAttribute(attributeNameIndex, input);
            case "Signature":
                return this.readSignatureAttribute(attributeNameIndex, input);
            default:
                System.out.println("method attr decoder: " + attributeName);
                ;
        }
        throw new IllegalStateException();
    }

    /**
     * Signature_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 signature_index;
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     */
    private EncodedMethodAttribute readSignatureAttribute(int attributeNameIndex, DataInput input)
            throws IOException {
        long length = input.readInt();
        int signatureIndex = input.readShort();
        return new EncodedSignatureAttribute(attributeNameIndex, signatureIndex);
    }

    private EncodedMethodAttribute readCodeAttribute(int attributeNameIndex, DataInput input)
            throws IOException {
        long length = input.readInt();
        int maxStack = input.readShort();
        int maxLocals = input.readShort();
        long codeLength = input.readInt();
        byte[] code = new byte[(int) codeLength];
        input.readFully(code);
        int exceptionTableLength = input.readShort();
        List<EncodedExceptionInCode> encodedExceptions = new ArrayList<>(exceptionTableLength);
        for (int e = 0; e < exceptionTableLength; e++) {
            int startPc = input.readShort();
            int endPc = input.readShort();
            int handlerPc = input.readShort();
            int catchType = input.readShort();
            encodedExceptions.add(new EncodedExceptionInCode(startPc, endPc, handlerPc, catchType));
        }
        int attributesCount = input.readShort();
        List<EncodedAttribute> encodedAttributes = new ArrayList<>(attributesCount);
        for (int a = 0; a < attributesCount; a++) {
            encodedAttributes.add(this.codeAttributeAttributeDecoder.decode(input));
        }
        return new EncodedCodeAttribute(attributeNameIndex, maxStack, maxLocals, (int) codeLength,
                null, encodedExceptions, attributesCount, encodedAttributes);
    }
}
