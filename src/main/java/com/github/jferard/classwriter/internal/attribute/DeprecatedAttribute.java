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
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedDeprecatedAttribute;

/**
 * 4.7.15. The Deprecated Attribute
 * <pre>{@code
 * Deprecated_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 * }</pre>
 */
public class DeprecatedAttribute
        implements ClassFileAttribute, FieldAttribute, MethodInfoAttribute {
    public static final DeprecatedAttribute INSTANCE = new DeprecatedAttribute();
    private static final String DEPRECATED_NAME = "Deprecated";

    @Override
    public EncodedAttribute encode(GlobalContext context, MethodContext codeContext) {
        int attributeNameIndex = context.addUtf8ToPool(DEPRECATED_NAME);
        return new EncodedDeprecatedAttribute(attributeNameIndex);
    }
}
