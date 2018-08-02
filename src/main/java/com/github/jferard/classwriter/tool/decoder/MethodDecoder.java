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

import com.github.jferard.classwriter.api.MethodWritableFactory;
import com.github.jferard.classwriter.encoded.EncodedMethod;
import com.github.jferard.classwriter.encoded.attribute.EncodedMethodAttribute;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodDecoder implements Decoder<MethodWritableFactory<?>> {
    public static MethodDecoder create(List<EncodedConstantPoolEntry> entries) {
        return new MethodDecoder(
                new MethodAttributeDecoder(new CodeAttributeAttributeDecoder(entries), entries));
    }

    private MethodAttributeDecoder methodAttributeDecoder;

    public MethodDecoder(MethodAttributeDecoder methodAttributeDecoder) {
        this.methodAttributeDecoder = methodAttributeDecoder;
    }

    @Override
    public EncodedMethod decode(DataInput input) throws IOException {
        int accessFlags = input.readShort();
        int nameIndex = input.readShort();
        int descriptorIndex = input.readShort();
        int attributesCount = input.readShort();
        List<EncodedMethodAttribute> encodedAttributes = new ArrayList<>(attributesCount);
        for (int i = 0; i < attributesCount; i++) {
            encodedAttributes.add(this.methodAttributeDecoder.decode(input));
        }
        return new EncodedMethod(accessFlags, nameIndex, descriptorIndex, encodedAttributes);
    }
}
