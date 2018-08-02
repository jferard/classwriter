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

import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.pool.Encodable;
import com.github.jferard.classwriter.pool.Utf8Entry;

public class InnerClass implements Encodable<EncodedInnerClass> {
    public static final int SIZE = 4 * BytecodeHelper.SHORT_SIZE;

    private final PlainClassRef innerClassRef;
    private final PlainClassRef outerClassRef;
    private final int innerAccessFlags;

    public InnerClass(PlainClassRef innerClassRef, PlainClassRef outerClassRef,
                      int innerAccessFlags) {
        this.innerClassRef = innerClassRef;
        this.outerClassRef = outerClassRef;
        this.innerAccessFlags = innerAccessFlags;
    }

    @Override
    public EncodedInnerClass encode(GlobalContext context, MethodContext codeContext) {
        final int innerClassIndex = context.addToPool(this.innerClassRef.toEntry());
        final int innerClassNameIndex;
        if (this.innerClassRef.isAnonymous()) {
            innerClassNameIndex = 0;
        } else {
            innerClassNameIndex = context.addToPool(new Utf8Entry(
                    this.innerClassRef.getInternalName()));
        }
        final int outerClassIndex;
        if (this.outerClassRef == null) {
            outerClassIndex = 0;
        } else {
            outerClassIndex = context.addToPool(this.outerClassRef.toEntry());
        }
        assert innerClassNameIndex != 0 || outerClassIndex == 0;
        return new EncodedInnerClass(innerClassIndex, outerClassIndex, innerClassNameIndex, innerAccessFlags);
    }
}
