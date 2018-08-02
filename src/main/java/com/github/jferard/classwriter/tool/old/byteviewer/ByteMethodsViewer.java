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

import com.github.jferard.classwriter.tool.decoder.ConstantPoolViewer;
import com.github.jferard.classwriter.tool.decoder.MethodViewer;
import com.github.jferard.classwriter.tool.decoder.MethodsViewer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ByteMethodsViewer implements MethodsViewer {
    private List<MethodViewer> methodViewers;
    private ConstantPoolViewer constantPoolViewer;

    public ByteMethodsViewer(List<MethodViewer> methodViewers) {
        this.methodViewers = methodViewers;
    }

    @Override
    public void view(Writer w) throws IOException {
        int count = this.methodViewers.size();
        w.append(String.format("%s, %s, // methods count: %d\n", ByteViewerFactory.hex(count >> 8),
                ByteViewerFactory.hex(count), count));
        for (MethodViewer methodViewer : this.methodViewers) {
            methodViewer.setConstantPoolViewer(this.constantPoolViewer);
            methodViewer.view(w);
        }
    }

    @Override
    public void setConstantPoolViewer(ConstantPoolViewer constantPoolViewer) {
        this.constantPoolViewer = constantPoolViewer;
    }
}
