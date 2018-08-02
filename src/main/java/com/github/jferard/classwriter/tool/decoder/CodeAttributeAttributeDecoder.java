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
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber;
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedLineNumberTableAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeAttributeAttributeDecoder implements Decoder<AttributeWritableFactory<?>> {
    final List<EncodedConstantPoolEntry> entries;

    public CodeAttributeAttributeDecoder(List<EncodedConstantPoolEntry> entries) {
        this.entries = entries;
    }

    @Override
    public EncodedCodeAttributeAttribute decode(DataInput input) throws IOException {
        int attributeNameIndex = input.readShort();
        final String attributeName = entries.get(attributeNameIndex).utf8Text();
        switch (attributeName) {
            case "LineNumberTable":
                return this.decodeLineNumberTable(attributeNameIndex, input);
            case "LocalVariableTable":
                return this.decodeLocalVariableTable(attributeNameIndex, input);
            case "LocalVariableTypeTable":
                return this.decodeLocalVariableTypeTable(attributeNameIndex, input);
            default:
                System.out.println(attributeName);

        }
        throw new IllegalStateException();
    }

    /**
     * LocalVariableTypeTable_attribute {
     *     u2 attribute_name_index;
     *     u4 attribute_length;
     *     u2 local_variable_type_table_length;
     *     {   u2 start_pc;
     *         u2 length;
     *         u2 name_index;
     *         u2 signature_index;
     *         u2 index;
     *     } local_variable_type_table[local_variable_type_table_length];
     * }
     * @param attributeNameIndex
     * @param input
     * @return
     */
    private EncodedCodeAttributeAttribute decodeLocalVariableTypeTable(int attributeNameIndex, DataInput input)
            throws IOException {
        int atrtibuteLength = input.readInt();
        int localVariableTableLength = input.readShort();
        List<EncodedLocalVariableTypeTable> encodedLocalVariableTables = new ArrayList<>(
                localVariableTableLength);
        for (int lv = 0; lv < localVariableTableLength; lv++) {
            int startPc = input.readShort();
            int length = input.readShort();
            int nameIndex = input.readShort();
            int signatureIndex = input.readShort();
            int index = input.readShort();
            encodedLocalVariableTables
                    .add(new EncodedLocalVariableTypeTable(startPc, length, nameIndex, signatureIndex,
                            index));
        }
        return new EncodeLocalVariableTypeTableAttribute(attributeNameIndex, encodedLocalVariableTables);
    }

    /**
     * LocalVariableTable_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 local_variable_table_length;
     * {   u2 start_pc;
     * u2 length;
     * u2 name_index;
     * u2 descriptor_index;
     * u2 index;
     * } local_variable_table[local_variable_table_length];
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     */
    private EncodedCodeAttributeAttribute decodeLocalVariableTable(int attributeNameIndex,
                                                                   DataInput input)
            throws IOException {
        int atrtibuteLength = input.readInt();
        int localVariableTableLength = input.readShort();
        List<EncodedLocalVariableTable> encodedLocalVariableTables = new ArrayList<>(
                localVariableTableLength);
        for (int lv = 0; lv < localVariableTableLength; lv++) {
            int startPc = input.readShort();
            int length = input.readShort();
            int nameIndex = input.readShort();
            int descriptorIndex = input.readShort();
            int index = input.readShort();
            encodedLocalVariableTables
                    .add(new EncodedLocalVariableTable(startPc, length, nameIndex, descriptorIndex,
                            index));
        }
        return new EncodeLocalVariableTableAttribute(attributeNameIndex, encodedLocalVariableTables);
    }

    /**
     * LineNumberTable_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 line_number_table_length;
     * {   u2 start_pc;
     * u2 line_number;
     * } line_number_table[line_number_table_length];
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     * @throws IOException
     */
    private EncodedCodeAttributeAttribute decodeLineNumberTable(int attributeNameIndex,
                                                                DataInput input)
            throws IOException {
        int lelngth = input.readInt();
        int lineNumberTableLength = input.readShort();
        List<PositionAndLineNumber> positionAndLineNumbers = new ArrayList<>(lineNumberTableLength);
        for (int ln = 0; ln < lineNumberTableLength; ln++) {
            int startPc = input.readShort();
            int lineNumber = input.readShort();
            positionAndLineNumbers.add(new PositionAndLineNumber(startPc, lineNumber));

        }
        return new EncodedLineNumberTableAttribute(attributeNameIndex, positionAndLineNumbers);
    }
}
