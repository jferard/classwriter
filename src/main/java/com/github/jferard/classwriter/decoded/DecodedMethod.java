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

import com.github.jferard.classwriter.tool.old.byteviewer.ByteViewerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
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
public class DecodedMethod implements Decoded {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final List<Decoded> decodedAttributes;

    public DecodedMethod(int accessFlags, int nameIndex, int descriptorIndex,
                         List<Decoded> decodedAttributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.decodedAttributes = decodedAttributes;
    }

    @Override
    public void write(Writer output) throws IOException {
        output.append("// method */\n");
        output.append(String.format("%s, %s, // accessFlags: %s\n",
                ByteViewerFactory.hex(accessFlags >> 8), ByteViewerFactory.hex(accessFlags),
                accessFlags));
        output.append(
                String.format("%s, %s, // nameIndex: %s\n", ByteViewerFactory.hex(nameIndex >> 8),
                        ByteViewerFactory.hex(nameIndex), nameIndex));
        output.append(String.format("%s, %s, // descriptorIndex: %s\n",
                ByteViewerFactory.hex(descriptorIndex >> 8), ByteViewerFactory.hex(descriptorIndex),
                descriptorIndex));
        for (Decoded decodedAttribute : this.decodedAttributes) {
            decodedAttribute.write(output);
        }
    }
}
