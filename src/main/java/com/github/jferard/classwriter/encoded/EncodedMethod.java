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
import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;

import java.util.List;

/**
 * 4.6. Methods
 * <pre>{@code
 * method_info {
 *     u2             access_flags;
 *     u2             name_index;
 *     u2             descriptor_index;
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 * }</pre>
 */
public class EncodedMethod implements Encoded<MethodWritableFactory<?>> {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final List<? extends EncodedAttribute> attributes;

    public EncodedMethod(int accessFlags, int nameIndex, int descriptorIndex,
                         List<? extends EncodedAttribute> attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    @Override
    public Writable<?> toWritable(MethodWritableFactory<?> writableFactory) {
        return writableFactory.method(accessFlags, nameIndex, descriptorIndex, attributes);
    }


    @Override
    public int getSize() {
        return 4 * BytecodeHelper.SHORT_SIZE +
                this.attributes.stream().mapToInt(Encoded::getSize).sum();
    }

    @Override
    public String toString() {
        return String.format("Method [access=%s, name=%s, descriptor=%s, attributes=%s]",
                this.accessFlags, this.nameIndex, this.descriptorIndex, this.attributes);
    }
}

