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

import com.github.jferard.classwriter.api.FieldWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedField;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** <pre>{@code
 * field_info {
 *     u2             access_flags;
 *     u2             name_index;
 *     u2             descriptor_index;
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 * }</pre> */
public class FieldDecoder implements Decoder<FieldWritableFactory<?>> {
    public static FieldDecoder create(List<EncodedConstantPoolEntry> entries) {
        return new FieldDecoder(new FieldAttributeDecoder(entries));
    }

    private FieldAttributeDecoder attributeDecoder;

    public FieldDecoder(FieldAttributeDecoder attributeDecoder) {
        this.attributeDecoder = attributeDecoder;
    }

    @Override
    public EncodedField decode(DataInput input) throws IOException {
        int accessFlags = input.readShort();
        int nameIndex = input.readShort();
        int descriptorIndex = input.readShort();
        int attributesCount = input.readShort();
        List<EncodedAttribute> attributes = new ArrayList<>();
        for (int i=0; i<attributesCount; i++) {
            attributes.add(this.decodeAttribute(input));
        }
        return new EncodedField(accessFlags, nameIndex, descriptorIndex, attributes);
    }

    private EncodedAttribute decodeAttribute(DataInput input) throws IOException {
        return this.attributeDecoder.decode(input);
    }
}
