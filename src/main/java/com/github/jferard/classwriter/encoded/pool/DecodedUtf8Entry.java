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
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DecodedUtf8Entry implements DecodedPoolEntry {
    /*
    private static byte[] getBytes(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writable.write(new DataOutputStream(out));
        return out.toByteArray();
    }*/

    private String text;

    public DecodedUtf8Entry(String text) {
        this.text = text;
    }

    @Override
    public void write(Writer output) throws IOException {
        byte[] bytes = this.text.getBytes(StandardCharsets.UTF_8);
        String str = IntStream.range(0, bytes.length).mapToObj(j -> {
            int b = bytes[j] & 0xff;
            return b >= 32 ? ByteViewerFactory.chr((char) b) : ByteViewerFactory.hex(b);
        }).collect(Collectors.joining(", "));
        final int length = this.text.length();
        output.write(String.format("%s, %s, %s, %s, // %s", "ConstantTags.UTF8",
                ByteViewerFactory.hex(length >> 8), ByteViewerFactory.hex(length), str,
                "u\"" + this.text + "\""));
    }
}
