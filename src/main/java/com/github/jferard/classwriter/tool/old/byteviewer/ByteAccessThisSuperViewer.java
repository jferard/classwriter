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

import com.github.jferard.classwriter.internal.access.ClassAccess;
import com.github.jferard.classwriter.tool.decoder.AccessThisSuperViewer;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolViewer;

import java.io.IOException;
import java.io.Writer;

public class ByteAccessThisSuperViewer implements AccessThisSuperViewer {
    private final int thisIndex;
    private final int superIndex;
    private int accessFlags;
    private ConstantPoolViewer viewer;

    public ByteAccessThisSuperViewer(int accessFlags, int thisIndex, int superIndex) {
        this.accessFlags = accessFlags;
        this.thisIndex = thisIndex;
        this.superIndex = superIndex;
    }

    @Override
    public void setConstantPoolViewer(ConstantPoolViewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void view(Writer w) throws IOException {
        w.append(FlagJoiner.getAccessFlags(ClassAccess.class, this.accessFlags));
        w.append(String.format("%s, %s, // this: %s -> %s\n",
                ByteViewerFactory.hex(this.thisIndex >> 8), ByteViewerFactory.hex(this.thisIndex),
                "#" + this.thisIndex, viewer.entrySummary(this.thisIndex)));
        w.append(String.format("%s, %s, // super: %s -> %s\n",
                ByteViewerFactory.hex(this.superIndex), ByteViewerFactory.hex(this.superIndex >> 8),
                "#" + this.superIndex, viewer.entrySummary(this.superIndex)));
    }
}