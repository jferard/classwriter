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
import com.github.jferard.classwriter.encoded.attribute.EncodedConstantElementValue;
import com.github.jferard.classwriter.encoded.attribute.EncodedElementValue;
import com.github.jferard.classwriter.pool.ConstantPoolEntryFactory;

public class ConstantElementValue implements ElementValue {
    private Object o;

    public ConstantElementValue(Object o) {
        this.o = o;
    }

    @Override
    public EncodedElementValue encode(GlobalContext context, MethodContext codeContext) {
        final int tag = ElementValueFactory.getTag(o);
        final int elementValueIndex = context.addToPool(ConstantPoolEntryFactory.create(this.o));
        return new EncodedConstantElementValue(tag, elementValueIndex);
    }
}
