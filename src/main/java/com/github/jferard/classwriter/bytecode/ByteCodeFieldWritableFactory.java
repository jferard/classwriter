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
import com.github.jferard.classwriter.api.FieldWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedField;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeFieldWritableFactory implements FieldWritableFactory<DataOutput> {
    private AttributeWritableFactory<?> writableFactory;

    @Override
    public ByteCode field(int accessFlags, int nameIndex, int descriptorIndex,
                          List<EncodedAttribute> encodedAttributes) {
        List<ByteCode> writableAttributes = encodedAttributes.stream()
                .map(a -> (ByteCode) a.toWritable(writableFactory)).collect(Collectors.toList());

        return new ByteCodeField(accessFlags, nameIndex, descriptorIndex, writableAttributes);
    }

    @Override
    public Writable<DataOutput> fields(List<EncodedField> encodedFields) {
        List<ByteCode> writableFields = encodedFields.stream()
                .map(ef -> (ByteCode) ef.toWritable(this)).collect(Collectors.toList());
        return new ByteCodeFields(writableFields);
    }
}
