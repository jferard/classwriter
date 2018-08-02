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

package com.github.jferard.classwriter.tool.javapviewer;

import com.github.jferard.classwriter.tool.decoder.ConstantPoolEncodedEntryViewer;

import java.util.List;

public class JavapNumberEntryViewer<T extends Number> implements ConstantPoolEncodedEntryViewer {
    private final String code;
    private final T number;

    public JavapNumberEntryViewer(String code, T number) {
        this.code = code;
        this.number = number;
    }

    @Override
    public String view(List<ConstantPoolEncodedEntryViewer> entries, int i) {
        return String
                .format(JavapEntryViewerFactory.NUM_FORMAT + JavapEntryViewerFactory.CODE_FORMAT +"%-16s// %s",  "#" + i,this.code, this.number, this.viewSummary(entries));
    }

    @Override
    public String viewSummary(List<ConstantPoolEncodedEntryViewer> entries) {
        throw new IllegalStateException();
    }
}
