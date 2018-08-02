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

import com.github.jferard.classwriter.tool.decoder.ConstantPoolEncodedEntryViewer;

import java.util.List;

class ByteIndexedEntryViewer implements ConstantPoolEncodedEntryViewer {
    private final String code;
    private final int index;

    public ByteIndexedEntryViewer(String code, int index) {
        this.code = code;
        this.index = index;
    }

    @Override
    public String view(List<ConstantPoolEncodedEntryViewer> entries, int i) {
        return String
                .format(ByteEntryViewerFactory.NUM_FORMAT + "%s, %s, %s, // %s", "#" + i, this.code,
                        ByteViewerFactory.hex(this.index >> 8), ByteViewerFactory.hex(this.index),
                        "#" + this.index + " -> \"" + this.viewSummary(entries) + "\"");
    }

    @Override
    public String viewSummary(List<ConstantPoolEncodedEntryViewer> entries) {
        return entries.get(this.index).viewSummary(entries).replace('/', '.');
    }
}
