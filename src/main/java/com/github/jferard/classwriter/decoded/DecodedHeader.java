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

package com.github.jferard.classwriter.decoded;

import com.github.jferard.classwriter.decoded.Decoded;
import com.github.jferard.classwriter.encoded.EncodedClassFile;
import com.github.jferard.classwriter.tool.old.byteviewer.ByteViewerFactory;

import java.io.IOException;
import java.io.Writer;

public class DecodedHeader implements Decoded {
    private final int minorVersion;
    private final int majorVersion;

    public DecodedHeader(int minorVersion, int majorVersion) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
    }

    @Override
    public void write(Writer output) throws IOException {
        output.append("/* HEADER */\n");
        output.append(String.format("%s, %s, %s, %s, // magic number\n",
                ByteViewerFactory.hex(EncodedClassFile.MAGIC_NUMBER >> 24),
                ByteViewerFactory.hex(EncodedClassFile.MAGIC_NUMBER >> 16),
                ByteViewerFactory.hex(EncodedClassFile.MAGIC_NUMBER >> 8),
                ByteViewerFactory.hex(EncodedClassFile.MAGIC_NUMBER)));

        output.append(String.format("%s, %s, // %d -> minor version\n",
                ByteViewerFactory.hex(this.minorVersion >> 8),
                ByteViewerFactory.hex(this.minorVersion), this.minorVersion));

        output.append(String.format("%s, %s, // %d -> major version\n",
                ByteViewerFactory.hex(this.majorVersion >> 8),
                ByteViewerFactory.hex(this.majorVersion), this.majorVersion));
    }
}
