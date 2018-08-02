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

import com.github.jferard.classwriter.tool.javapviewer.JavapEntryViewerFactory;

import java.util.List;

public class InvokeDynamicEntryViewer implements ConstantPoolEncodedEntryViewer {
    private final int bootstrapMethodAttrIndex;
    private final int nameAndTypeIndex;

    public InvokeDynamicEntryViewer(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public String view(List<ConstantPoolEncodedEntryViewer> entries, int i) {
        return String
                .format(JavapEntryViewerFactory.CODE_FORMAT + "%-16s// %s", "#" + i,
                        "InvokeDynamic",
                        "#" + this.bootstrapMethodAttrIndex + ":#" + this.nameAndTypeIndex,
                        this.viewSummary(entries));
    }

    @Override
    public String viewSummary(List<ConstantPoolEncodedEntryViewer> entries) {
        // needs the 4.7.23. The BootstrapMethods Attribute
        return "?" + entries.get(this.nameAndTypeIndex).viewSummary(entries);
    }
}
