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
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldsDecoder implements Decoder<FieldWritableFactory<?>> {
    public static FieldsDecoder create(final List<EncodedConstantPoolEntry> entries) {
        return new FieldsDecoder(new FieldDecoder(new FieldAttributeDecoder(entries)));
    }

    FieldDecoder fieldDecoder;

    public FieldsDecoder(FieldDecoder fieldDecoder) {
        this.fieldDecoder = fieldDecoder;
    }

    @Override
    public EncodedFields decode(DataInput input) throws IOException {
        int fieldsCount = input.readShort();
        List<EncodedField> encodedFields = new ArrayList<>(fieldsCount);
        for (int i = 0; i < fieldsCount; i++) {
            encodedFields.add(decodeField(input));
        }
        return new EncodedFields(encodedFields);
    }

    private EncodedField decodeField(DataInput input) throws IOException {
        return this.fieldDecoder.decode(input);
    }
}
