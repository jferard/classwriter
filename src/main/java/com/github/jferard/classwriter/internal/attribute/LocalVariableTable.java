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

package com.github.jferard.classwriter.internal.attribute;

import com.github.jferard.classwriter.api.Descriptor;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable;
import com.github.jferard.classwriter.pool.Encodable;

public class LocalVariableTable implements Encodable<EncodedLocalVariableTable> {
    private final int startPc;
    private final int length;
    private final String name;
    private final Descriptor descriptor;
    private final int index;

    LocalVariableTable(int startPc, int length, String name, Descriptor descriptor, int index) {
        this.startPc = startPc;
        this.length = length;
        this.name = name;
        this.descriptor = descriptor;
        this.index = index;
    }

    @Override
    public EncodedLocalVariableTable encode(GlobalContext context, MethodContext codeContext) {
        int nameIndex = context.addUtf8ToPool(this.name);
        int descriptorIndex = context.addToPool(this.descriptor.toPoolEntry());
        return new EncodedLocalVariableTable(this.startPc, this.length, nameIndex, descriptorIndex, this.index);
    }
}
