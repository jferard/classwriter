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

package com.github.jferard.classwriter.encoded.attribute;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

import java.util.List;

/** <pre>{@code
 * LocalVariableTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_table_length;
 *     {   u2 start_pc;
 *         u2 length;
 *         u2 name_index;
 *         u2 descriptor_index;
 *         u2 index;
 *     } local_variable_table[local_variable_table_length];
 * }
 * }</pre> */
public class EncodedVariableTableAttribute implements EncodedAttribute {
    private final int attributeNameIndex;
    private final List<EncodedLocalVariableTable> encodedLocalVariables;

    public EncodedVariableTableAttribute(int attributeNameIndex,
                                         List<EncodedLocalVariableTable> encodedLocalVariables) {
        this.attributeNameIndex = attributeNameIndex;
        this.encodedLocalVariables = encodedLocalVariables;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.variableTableAttribute(this.attributeNameIndex, this.encodedLocalVariables);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + this.getLength();
    }

    private int getLength() {
        return this.encodedLocalVariables.stream().mapToInt(EncodedLocalVariableTable::getSize).sum();
    }
}
