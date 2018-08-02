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
import com.github.jferard.classwriter.internal.attribute.ConstantValueAttributeFactory;
import com.github.jferard.classwriter.encoded.attribute.EncodedConstantValueAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedSignatureAttribute;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class FieldAttributeDecoder implements Decoder<AttributeWritableFactory<?>> {
    final List<EncodedConstantPoolEntry> entries;

    public FieldAttributeDecoder(List<EncodedConstantPoolEntry> entries) {
        this.entries = entries;
    }

    @Override
    public EncodedFieldAttribute decode(DataInput input) throws IOException {
        int index = input.readShort();
        final EncodedConstantPoolEntry constantPoolEntry = this.entries.get(index);
        System.out.println("new attribute: " + constantPoolEntry.utf8Text());
        switch (constantPoolEntry.utf8Text()) {
            case ConstantValueAttributeFactory.CONSTANT_VALUE_NAME:
                if (input.readInt() != 4)
                    throw new IllegalStateException();
                int constantIndex = input.readShort();
                return new EncodedConstantValueAttribute(index, constantIndex);
            case ConstantValueAttributeFactory.SIGNATURE_NAME:
                if (input.readInt() != 2)
                    throw new IllegalStateException();
                int signatureIndex = input.readShort();
                return new EncodedSignatureAttribute(index, signatureIndex);
            default:
                throw new IllegalStateException(constantPoolEntry.utf8Text());
        }
    }
}
