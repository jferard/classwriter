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

package com.github.jferard.classwriter.encoded.attribute;

import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.AttributeWritableFactory;

/** 4.7.6. The InnerClasses Attribute
 * <pre>{@code
 * {   u2 inner_class_info_index;
 *     u2 outer_class_info_index;
 *     u2 inner_name_index;
 *     u2 inner_class_access_flags;
 * } classes
 * }</pre>
 */
public class EncodedInnerClass implements Encoded<AttributeWritableFactory<?>> {
    private final int innerClassIndex;
    private final int outerClassIndex;
    private final int innerClassNameIndex;
    private final int innerAccessFlags;

    public EncodedInnerClass(int innerClassIndex, int outerClassIndex, int innerClassNameIndex,
                             int innerAccessFlags) {
        this.innerClassIndex = innerClassIndex;
        this.outerClassIndex = outerClassIndex;
        this.innerClassNameIndex = innerClassNameIndex;
        this.innerAccessFlags = innerAccessFlags;
    }

    @Override
    public Writable<?> toWritable(AttributeWritableFactory<?> writableFactory) {
        return writableFactory.innerClass(innerClassIndex, outerClassIndex, innerClassNameIndex,
                innerAccessFlags);
    }

    @Override
    public int getSize() {
        return 4 * BytecodeHelper.SHORT_SIZE;
    }
}
