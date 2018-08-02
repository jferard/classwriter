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

package com.github.jferard.classwriter.internal.context;

import com.github.jferard.classwriter.api.ClassRef;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;

public class ReferenceVerificationType implements VerificationType {
    private ClassRef classRef;

    public ReferenceVerificationType(ClassRef classRef) {
        this.classRef = classRef;
    }

    @Override
    public boolean isLong() {
        return false;
    }

    @Override
    public boolean isAssignable(VerificationType expectedType) {
        return expectedType.isAssignable(VerificationType.REFERENCE);
    }

    @Override
    public EncodedVerificationType encode(GlobalContext context, MethodContext codeContext) {
        throw new IllegalStateException();
    }

    public boolean isArray() {
        return this.classRef.isArray();
    }

    public VerificationType getComponentVerficationType() {
        return this.classRef.componentType().getVerificationType();
    }
}
