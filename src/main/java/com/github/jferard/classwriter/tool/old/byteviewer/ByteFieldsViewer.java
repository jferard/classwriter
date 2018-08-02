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

import com.github.jferard.classwriter.tool.decoder.FieldViewer;
import com.github.jferard.classwriter.tool.decoder.FieldsViewer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ByteFieldsViewer implements FieldsViewer {
    private List<FieldViewer> fieldViewers;

    public ByteFieldsViewer(List<FieldViewer> fieldViewers) {
        this.fieldViewers = fieldViewers;
    }

    @Override
    public void view(Writer w) throws IOException {
        int count = this.fieldViewers.size();
        w.append(String.format("%s, %s, // fields count: %d\n", ByteViewerFactory.hex(count >> 8),
                ByteViewerFactory.hex(count), count));
        for (FieldViewer fieldViewer : this.fieldViewers) {
            fieldViewer.view(w);
        }
    }
}
