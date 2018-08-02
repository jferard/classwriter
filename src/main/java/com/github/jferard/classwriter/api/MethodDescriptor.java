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

import com.github.jferard.classwriter.internal.descriptor.MethodDescriptorBuilder;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;
import com.github.jferard.classwriter.pool.Utf8Entry;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MethodDescriptor implements Descriptor {
    public static final MethodDescriptor EMPTY = new MethodDescriptor(null,
            Collections.emptyList());

    public static MethodDescriptorBuilder builder() {
        return new MethodDescriptorBuilder();
    }
    private final ValueType ret;
    private final List<ValueType> params;

    public MethodDescriptor(ValueType ret, List<ValueType> params) {
        this.ret = ret;
        this.params = params;
    }

    public int getArgsStackDepth() {
        int ret = this.hasNonVoidRet() ? 1 : 0;
        ret += this.getArgsSize();
        return ret;
    }

    public boolean hasNonVoidRet() {
        return this.ret != null;
    }

    /**
     * 6.5. Instructions:
     * The count operand of the invokeinterface instruction records a measure of the number of
     * argument values, where an argument value of type long or type double contributes two units
     * to the count value and an argument of any other type contributes one unit. This
     * information can also be derived from the descriptor of the selected method. The redundancy
     * is historical.
     */
    public int getArgsSize() {
        return this.params.stream().mapToInt(ValueType::getSize).sum();
    }

    public int getArgsCount() {
        return this.params.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.params.forEach(sb::append);
        return "(" + sb.toString() + ")" + Objects.toString(this.ret, "V");
    }

    @Override
    public ConstantPoolEntry toPoolEntry() {
        return new Utf8Entry(this.toString());
    }

    public List<ValueType> getArgTypes() {
        return this.params;
    }

    public ValueType getRetType() {
        return this.ret;
    }
}
