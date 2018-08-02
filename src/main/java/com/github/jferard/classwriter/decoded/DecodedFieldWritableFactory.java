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
import com.github.jferard.classwriter.api.FieldWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedField;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class DecodedFieldWritableFactory implements FieldWritableFactory<Writer> {
    public static DecodedFieldWritableFactory create() {
        return new DecodedFieldWritableFactory(DecodedAttributeWritableFactory.create());
    }

    private final DecodedAttributeWritableFactory attributeWritableFactory;

    public DecodedFieldWritableFactory(DecodedAttributeWritableFactory attributeWritableFactory) {
        this.attributeWritableFactory = attributeWritableFactory;
    }

    @Override
    public Writable<Writer> field(int accessFlags, int nameIndex, int descriptorIndex,
                                  List<EncodedAttribute> encodedAttributes) {
        final List<Decoded> decodedAttributes = encodedAttributes.stream()
                .map(ea -> (Decoded) ea.toWritable(this.attributeWritableFactory))
                .collect(Collectors.toList());
        return new DecodedField(accessFlags, nameIndex, descriptorIndex, decodedAttributes);
    }

    @Override
    public Writable<Writer> fields(List<EncodedField> encodedFields) {
        final List<Decoded> decodedFields = encodedFields.stream()
                .map(ef -> (Decoded) ef.toWritable(this)).collect(Collectors.toList());
        return new DecodedFields(decodedFields);
    }
}
