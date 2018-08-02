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
import com.github.jferard.classwriter.encoded.attribute.EncodedSourceFileAttribute;

/**
 * 4.7.10. The SourceFile Attribute
 */
public class SourceFileAttribute implements ClassFileAttribute {
    private static final String SOURCE_FILE_NAME = "SourceFile";
    private final String sourceFileName;

    public SourceFileAttribute(final String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    @Override
    public EncodedClassFileAttribute encode(GlobalContext pool, MethodContext codeContext) {
        int attributeNameIndex = pool.addUtf8ToPool(SOURCE_FILE_NAME);
        int sourceFileNameIndex = pool.addUtf8ToPool(this.sourceFileName);
        return new EncodedSourceFileAttribute(attributeNameIndex, sourceFileNameIndex);
    }
}
