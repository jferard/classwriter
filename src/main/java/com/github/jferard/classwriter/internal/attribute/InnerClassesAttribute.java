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

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass;
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClassesAttribute;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.6. The InnerClasses Attribute
 */
public class InnerClassesAttribute implements ClassFileAttribute {
    public static final String INNER_CLASSES_NAME = "InnerClasses";
    private final List<InnerClass> innerClasses;

    InnerClassesAttribute(final List<InnerClass> innerClasses) {
        this.innerClasses = innerClasses;
    }

    @Override
    public EncodedClassFileAttribute encode(final GlobalContext pool, MethodContext codeContext) {
        int attributeNameIndex = pool.addUtf8ToPool(INNER_CLASSES_NAME);
        final List<EncodedInnerClass> encodedInnerClasses = this.innerClasses.stream()
                .map(ic -> ic.encode(pool, MethodContext.create(0))).collect(Collectors.toList());
        return new EncodedInnerClassesAttribute(attributeNameIndex,
                encodedInnerClasses);
    }
}
