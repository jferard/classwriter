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

package com.github.jferard.classwriter.encoded;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.FieldWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;

import java.util.List;

/**
 * <pre>{@code
 *  u2             fields_count;
 *  field_info     fields[fields_count];
 * }</pre>
 */
public class EncodedFields implements Encoded<FieldWritableFactory<?>> {
    private List<EncodedField> encodedFields;

    public EncodedFields(List<EncodedField> encodedFields) {
        this.encodedFields = encodedFields;
    }

    @Override
    public Writable<?> toWritable(FieldWritableFactory<?> writableFactory) {
        return writableFactory.fields(encodedFields);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.SHORT_SIZE +
                this.encodedFields.stream().mapToInt(EncodedField::getSize).sum();
    }

    public String toString() {
        return String.format("Fields %s", this.encodedFields);
    }
}
