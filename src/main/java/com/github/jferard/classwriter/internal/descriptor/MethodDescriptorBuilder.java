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

package com.github.jferard.classwriter.internal.descriptor;

import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.api.MethodDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MethodDescriptorBuilder {
    private ValueType ret;
    private final List<ValueType> params;

    public MethodDescriptorBuilder() {
        this.ret = null;
        this.params = new ArrayList<>();
    }

    public MethodDescriptorBuilder params(ValueType... parameters) {
        Collections.addAll(this.params, parameters);
        return this;
    }

    public MethodDescriptorBuilder ret(ValueType ret) {
        this.ret = ret;
        return this;
    }

    public MethodDescriptor build() {
        return new MethodDescriptor(this.ret, this.params);
    }
}
