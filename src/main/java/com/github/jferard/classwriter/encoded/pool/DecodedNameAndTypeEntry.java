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

package com.github.jferard.classwriter.encoded.pool;

import com.github.jferard.classwriter.decoded.Decoded;
import com.github.jferard.classwriter.decoded.DecodedPoolEntry;
import com.github.jferard.classwriter.tool.old.byteviewer.ByteViewerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class DecodedNameAndTypeEntry implements DecodedPoolEntry {
    private final int nameIndex;
    private final int descriptorIndex;
    private final Decoded writableName;
    private final Decoded writableDescriptor;

    public DecodedNameAndTypeEntry(int nameIndex, int descriptorIndex, Decoded writableName,
                                   Decoded writableDescriptor) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.writableName = writableName;
        this.writableDescriptor = writableDescriptor;
    }

    @Override
    public void write(Writer output) throws IOException {
        output.write(String.format("%s, %s, %s, %s, %s, // %s", "ConstantTags.NAMEANDTYPE",
                ByteViewerFactory.hex(this.nameIndex >> 8), ByteViewerFactory.hex(this.nameIndex),
                ByteViewerFactory.hex(this.descriptorIndex >> 8),
                ByteViewerFactory.hex(this.descriptorIndex),
                "#" + this.nameIndex + ":#" + this.descriptorIndex + " -> "));
        this.writableName.write(output);
        output.write(':');
        StringWriter sw = new StringWriter();
        this.writableDescriptor.write(sw);
        output.write(sw.toString().replace('/', '.'));
    }
}
