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

import com.github.jferard.classwriter.Writable;

import java.io.IOException;
import java.io.Writer;

public class DecodedClassFile implements Writable<Writer> {
    private final int accessFlags;
    private final Decoded writablePool;
    private final int thisIndex;
    private final int superIndex;
    private final Writable<Writer> writableInterfaces;
    private final Writable<Writer> writableFields;
    private final Writable<Writer> writableMethods;
    private final Writable<Writer> writableAttributes;
    private Writable<Writer> writableHeader;

    public DecodedClassFile(Writable<Writer> writableHeader, Decoded writablePool, int accessFlags,
                            int thisIndex, int superIndex, Writable<Writer> writableInterfaces,
                            Writable<Writer> writableFields, Writable<Writer> writableMethods,
                            Writable<Writer> writableAttributes) {
        this.writableHeader = writableHeader;
        this.writablePool = writablePool;
        this.accessFlags = accessFlags;
        this.thisIndex = thisIndex;
        this.superIndex = superIndex;
        this.writableInterfaces = writableInterfaces;
        this.writableFields = writableFields;
        this.writableMethods = writableMethods;
        this.writableAttributes = writableAttributes;
    }

    @Override
    public void write(Writer output) throws IOException {
        output.append("/**************/\n");
        output.append("/* CLASS FILE */\n");
        output.append("/**************/\n");
        this.writableHeader.write(output);
        this.writablePool.write(output);
        output.write(this.accessFlags);
        output.write(this.thisIndex);
        output.write(this.superIndex);
        writableInterfaces.write(output);
        writableFields.write(output);
        writableMethods.write(output);
        this.writableAttributes.write(output);
    }
}
