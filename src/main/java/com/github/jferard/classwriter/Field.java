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

package com.github.jferard.classwriter;

import com.github.jferard.classwriter.api.FieldDescriptor;
import com.github.jferard.classwriter.internal.attribute.FieldAttribute;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.EncodedField;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.List;
import java.util.stream.Collectors;

public class Field implements Encodable<EncodedField> {
    private final short accessFlags;
    private final String name;
    private final FieldDescriptor descriptor;
    private List<FieldAttribute> attributes;

    public Field(short accessFlags, String name, FieldDescriptor descriptor,
                 List<FieldAttribute> attributes) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
    }

    @Override
    public EncodedField encode(GlobalContext context, MethodContext codeContext) {
        int nameIndex = context.addUtf8ToPool(name);
        int descriptorIndex = context.addToPool(descriptor.toPoolEntry());
        List<EncodedAttribute> encodedFieldAttributes = this.attributes.stream()
                .map(a -> a.encode(context, codeContext)).collect(Collectors.toList());
        return new EncodedField(this.accessFlags, nameIndex, descriptorIndex,
                encodedFieldAttributes);
    }
}
