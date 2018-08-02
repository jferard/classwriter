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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolEncodedEntryViewer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ByteUTF8EntryViewer implements ConstantPoolEncodedEntryViewer {
    private static byte[] getBytes(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writable.write(new DataOutputStream(out));
        return out.toByteArray();
    }

    private final String text;

    public ByteUTF8EntryViewer(String text) {
        this.text = text;
    }

    @Override
    public String view(List<ConstantPoolEncodedEntryViewer> entries, int i) {
        byte[] bytes = this.text.getBytes(StandardCharsets.UTF_8);
        String str = IntStream.range(0, bytes.length).mapToObj(j -> {
            int b = bytes[j] & 0xff;
            return b >= 32 ? ByteViewerFactory.chr((char) b) : ByteViewerFactory.hex(b);
        }).collect(Collectors.joining(", "));
        final int length = this.text.length();
        return String.format(ByteEntryViewerFactory.NUM_FORMAT + "%s, %s, %s, %s, // %s", "#" + i,
                "ConstantTags.UTF8", ByteViewerFactory.hex(length >> 8), ByteViewerFactory.hex(length), str,
                "u\"" + this.text + "\"");
    }

    @Override
    public String viewSummary(List<ConstantPoolEncodedEntryViewer> entries) {
        return this.text;
    }
}
