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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;
import com.github.jferard.classwriter.pool.MethodRefEntry;

import java.util.List;

public class MethodRef implements FieldOrMethodRef {
    private final PlainClassRef classRef;
    private final String methodName;
    private final MethodDescriptor descriptor;

    public MethodRef(PlainClassRef classRef, String methodName, MethodDescriptor descriptor) {
        this.classRef = classRef;
        this.methodName = methodName;
        this.descriptor = descriptor;
    }

    @Override
    public String getName() {
        return this.methodName;
    }

    @Override
    public Descriptor toDescriptor() {
        return this.descriptor;
    }

    @Override
    public PlainClassRef getClassRef() {
        return this.classRef;
    }

    @Override
    public ConstantPoolEntry toEntry() {
        return new MethodRefEntry(this);
    }

    public int getArgsStackDepth() {
        return this.descriptor.getArgsStackDepth();
    }

    public List<ValueType> getArgTypes() {
        return this.descriptor.getArgTypes();
    }

    public ValueType getRetType() {
        return this.descriptor.getRetType();
    }

    public VerificationType getClassVerificationType() {
        return this.getClassRef().toValueType().getVerificationType();
    }
}
