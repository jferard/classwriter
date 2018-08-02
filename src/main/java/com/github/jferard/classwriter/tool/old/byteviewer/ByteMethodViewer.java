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

package com.github.jferard.classwriter.tool.old.byteviewer;

import com.github.jferard.classwriter.internal.access.MethodAccess;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolViewer;
import com.github.jferard.classwriter.tool.decoder.MethodAttributeViewer;
import com.github.jferard.classwriter.tool.decoder.MethodViewer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ByteMethodViewer implements MethodViewer {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final List<MethodAttributeViewer> methodAttributeViewers;
    private ConstantPoolViewer constantPoolViewer;

    public ByteMethodViewer(int accessFlags, int nameIndex, int descriptorIndex,
                            List<MethodAttributeViewer> methodAttributeViewers) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.methodAttributeViewers = methodAttributeViewers;
    }

    @Override
    public void view(Writer w) throws IOException {
        w.append(FlagJoiner.getAccessFlags(MethodAccess.class, this.accessFlags));
        w.append(String.format("%s, %s, // name:%s -> %s\n",
                ByteViewerFactory.hex(this.nameIndex >> 8), ByteViewerFactory.hex(this.nameIndex),
                "#" + this.nameIndex, constantPoolViewer.entrySummary(this.nameIndex)));
        w.append(String.format("%s, %s, // descriptor: %s -> %s\n",
                ByteViewerFactory.hex(this.descriptorIndex >> 8),
                ByteViewerFactory.hex(this.descriptorIndex), "#" + this.descriptorIndex,
                constantPoolViewer.entrySummary(this.descriptorIndex)));
        for (MethodAttributeViewer methodAttributeViewer : this.methodAttributeViewers) {
            methodAttributeViewer.view(w);
        }
    }

    public void setConstantPoolViewer(ConstantPoolViewer constantPoolViewer) {
        this.constantPoolViewer = constantPoolViewer;
    }
}
