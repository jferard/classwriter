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

package com.github.jferard.classwriter.api;

import com.github.jferard.classwriter.internal.attribute.CodeAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.EncodedMethod;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.Collections;
import java.util.List;

public class Method implements Encodable<EncodedMethod> {
    private final String name;
    private final MethodDescriptor descriptor;
    private int accessFlags;
    private CodeAttribute codeAttribute;

    public Method(int accessFlags, String name, MethodDescriptor descriptor,
                  CodeAttribute codeAttribute) {
        this.name = name;
        this.accessFlags = accessFlags;
        this.descriptor = descriptor;
        this.codeAttribute = codeAttribute;
    }

    @Override
    public EncodedMethod encode(GlobalContext context, MethodContext codeContext) {
        int nameIndex = context.addUtf8ToPool(this.name);
        int descriptorIndex = context.addToPool(this.descriptor.toPoolEntry());
        List<? extends EncodedAttribute> attributes = Collections
                .singletonList(this.codeAttribute.encode(context, MethodContext.create(0)));
        return new EncodedMethod(this.accessFlags, nameIndex, descriptorIndex, attributes);
    }
}
