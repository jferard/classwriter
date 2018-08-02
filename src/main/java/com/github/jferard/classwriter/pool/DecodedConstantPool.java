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

package com.github.jferard.classwriter.pool;

import com.github.jferard.classwriter.decoded.Decoded;
import com.github.jferard.classwriter.tool.old.byteviewer.ByteViewerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/** <pre>{@code
 * u2             constant_pool_count;
 * cp_info        constant_pool[constant_pool_count-1];
 * }</pre> */
public class DecodedConstantPool implements Decoded {
    private List<Decoded> writableEntries;

    public DecodedConstantPool(List<Decoded> writableEntries) {
        this.writableEntries = writableEntries;
    }


    @Override
    public void write(Writer writer) throws IOException {
        writer.append("/* CONSTANT POOL */\n");
        this.viewEntryCount(writer, this.writableEntries.size());
        for (int i = 1; i < writableEntries.size(); i++) {
            Decoded entry = writableEntries.get(i);
            System.out.println(entry);
            if (entry != null) {
                writer.append(String.format("/* %4s */", "#"+i));
                entry.write(writer);
                writer.append('\n');
            }
        }
    }

    private void viewEntryCount(Writer writer, int entryCount) throws IOException {
        writer.append(String.format("%s, %s, // number of writableEntries: %d\n",
                ByteViewerFactory.hex(entryCount + 1 >> 8), ByteViewerFactory.hex(entryCount + 1),
                entryCount));
    }
}
