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
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;

import java.util.List;

/**
 * 4.7.3. The Code Attribute
 * <pre>{@code
 * Code_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 max_stack;
 *     u2 max_locals;
 *     u4 code_length;
 *     u1 code[code_length];
 *     u2 exception_table_length;
 *     {   u2 start_pc;
 *         u2 end_pc;
 *         u2 handler_pc;
 *         u2 catch_type;
 *     } exception_table[exception_table_length];
 *     u2 attributes_count;
 *     attribute_info encodedAttributes[attributes_count];
 * }
 * }</pre>
 */
public class EncodedCodeAttribute implements EncodedMethodAttribute {
    private static final int MAX_STACK_FIELD_SIZE = BytecodeHelper.SHORT_SIZE;
    private static final int MAX_LOCALS_FIELD_SIZE = BytecodeHelper.SHORT_SIZE;
    private static final int CODE_LENGTH_FIELD_SIZE = BytecodeHelper.INT_SIZE;
    private static final int EXCEPTION_TABLE_LENGTH_FIELD_SIZE = BytecodeHelper.SHORT_SIZE;
    private static final int ATTRIBUTES_COUNT_FIELD_SIZE = BytecodeHelper.SHORT_SIZE;

    private final int attributeNameIndex;
    private final int maxStack;
    private final int maxLocals;
    private final int codeLength;
    private final EncodedInstruction encodedInstruction;
    private final List<EncodedExceptionInCode> exceptions;
    private final List<EncodedAttribute> encodedAttributes;
    private final int attributesLength;

    public EncodedCodeAttribute(int attributeNameIndex, int maxStack, int maxLocals, int codeLength,
                                final EncodedInstruction encodedInstruction,
                                final List<EncodedExceptionInCode> exceptions, int attributesLength,
                                final List<EncodedAttribute> encodedAttributes) {
        this.attributeNameIndex = attributeNameIndex;
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.encodedInstruction = encodedInstruction;
        this.exceptions = exceptions;
        this.attributesLength = attributesLength;
        this.encodedAttributes = encodedAttributes;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.codeAttribute(this.attributeNameIndex, this.maxStack, this.maxLocals,
                this.codeLength, this.encodedInstruction, this.exceptions,
                this.encodedAttributes.size(),
                this.encodedAttributes);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + MAX_STACK_FIELD_SIZE +
                MAX_LOCALS_FIELD_SIZE + CODE_LENGTH_FIELD_SIZE + this.codeLength +
                EXCEPTION_TABLE_LENGTH_FIELD_SIZE +
                this.exceptions.stream().mapToInt(EncodedExceptionInCode::getSize).sum() +
                ATTRIBUTES_COUNT_FIELD_SIZE +
                this.encodedAttributes.stream().mapToInt(Encoded::getSize).sum();
    }

}
