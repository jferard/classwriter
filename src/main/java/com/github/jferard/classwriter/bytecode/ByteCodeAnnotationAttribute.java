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

package com.github.jferard.classwriter.bytecode;

import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 * <pre>{@code
 * RuntimeVisibleAnnotations_attribute {
 * u2         attribute_name_index;
 * u4         attribute_length;
 * u2         num_annotations;
 * annotation annotations[num_annotations];
 * }
 * }</pre>
 */
public class ByteCodeAnnotationAttribute implements ByteCode {
    private final int annotationsNameIndex;
    private final List<ByteCode> writableAnnotations;
    private int length;

    public ByteCodeAnnotationAttribute(int annotationsNameIndex, int length,
                                       List<ByteCode> writableAnnotations) {
        this.annotationsNameIndex = annotationsNameIndex;
        this.length = length;
        this.writableAnnotations = writableAnnotations;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.annotationsNameIndex);
        output.writeInt(this.length);
        for (ByteCode writableAnnotation : this.writableAnnotations) {
            writableAnnotation.write(output);
        }
    }
}
