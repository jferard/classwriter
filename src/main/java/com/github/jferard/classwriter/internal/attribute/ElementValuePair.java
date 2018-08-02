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
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValue;
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValuePair;
import com.github.jferard.classwriter.pool.Encodable;

/**
 * 4.7.16. The RuntimeVisibleAnnotations Attribute
 */
public class ElementValuePair implements Encodable<EncodedElementValuePair> {
    private final String elementName;
    private final ElementValue value;

    public ElementValuePair(final String elementName, ElementValue value) {
        this.elementName = elementName;
        this.value = value;
    }

    @Override
    public EncodedElementValuePair encode(GlobalContext context, MethodContext codeContext) {
        int elementNameIndex = context.addUtf8ToPool(this.elementName);
        EncodedElementValue encodedElementValue = value.encode(context, codeContext);
        return new EncodedElementValuePair(elementNameIndex, encodedElementValue);
    }
}
