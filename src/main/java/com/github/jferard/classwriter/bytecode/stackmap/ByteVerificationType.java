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

package com.github.jferard.classwriter.bytecode.stackmap;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeWritableFactory;
import com.github.jferard.classwriter.bytecode.BytecodeHelper;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;

/**
 * top, integer, float, null, uninitialized_this, long, double
 */
public class ByteVerificationType implements VerificationType, EncodedVerificationType {
    private int code;
    private VerificationType parent;

    public ByteVerificationType(int code, VerificationType parent) {
        this.code = code;
        this.parent = parent;
    }

    @Override
    public EncodedVerificationType encode(GlobalContext context, MethodContext codeContext) {
        return this;
    }

    @Override
    public boolean isLong() {
        return false;
    }

    @Override
    public boolean isAssignable(VerificationType expectedType) {
        return this.equals(expectedType) ||
                this.parent != null && this.parent.isAssignable(expectedType);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ByteVerificationType)) {
            return false;
        }

        ByteVerificationType other = (ByteVerificationType) o;
        return this.code == other.code;

    }

    @Override
    public Writable<?> toWritable(VerificationTypeWritableFactory<?> writableFactory) {
        return writableFactory.byteVerificationType(this.code);
    }

    @Override
    public int getSize() {
        return BytecodeHelper.BYTE_SIZE;
    }
}
