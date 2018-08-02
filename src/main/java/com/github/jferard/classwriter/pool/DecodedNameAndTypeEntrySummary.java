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
import com.github.jferard.classwriter.Writable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class DecodedNameAndTypeEntrySummary implements DecodedPoolEntrySummary {
    private final Writable<Writer> decodedName;
    private final Writable<Writer> decodedDescriptor;

    public DecodedNameAndTypeEntrySummary(Writable<Writer> decodedName,
                                          Writable<Writer> decodedDescriptor) {
        this.decodedName = decodedName;
        this.decodedDescriptor = decodedDescriptor;
    }

    @Override
    public void write(Writer output) throws IOException {
        this.decodedName.write(output);
        output.append(':');
        StringWriter sw = new StringWriter();
        this.decodedDescriptor.write(sw);
        output.write(sw.toString().replace('/', '.'));
    }
}
