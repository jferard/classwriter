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
import com.github.jferard.classwriter.encoded.attribute.EncodedConstantValueAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedFieldAttribute;
import com.github.jferard.classwriter.pool.DoubleEntry;

/**
 * 4.7.2. The ConstantValue Attribute
 * For a double.
 */
public class ConstantDoubleValueAttribute implements FieldAttribute {
    private final double value;

    ConstantDoubleValueAttribute(double value) {
        this.value = value;
    }

    @Override
    public EncodedFieldAttribute encode(GlobalContext pool, MethodContext codeContext) {
        int attributeNameIndex = pool.addUtf8ToPool(ConstantValueAttributeFactory.CONSTANT_VALUE_NAME);
        int valueIndex = pool.addToPool(new DoubleEntry(this.value));
        return new EncodedConstantValueAttribute(attributeNameIndex, valueIndex);
    }
}
