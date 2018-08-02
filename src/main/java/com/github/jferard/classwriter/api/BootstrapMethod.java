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

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.pool.ConstantPoolEntryFactory;
import com.github.jferard.classwriter.pool.Encodable;
import com.github.jferard.classwriter.pool.MethodHandleEntry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.23. The BootstrapMethods Attribute
 * <pre>{@code
 * bootstrap_method {
 *     u2 bootstrap_method_ref;
 *     u2 num_bootstrap_arguments;
 *     u2 bootstrap_arguments[num_bootstrap_arguments];
 * }
 * }</pre>
 */
public class BootstrapMethod implements Encodable<EncodedBootstrapMethod> {
    private final List<Object> arguments;
    private final MethodHandle methodHandle;

    /**
     * @param methodHandle a methodRef + a kind of handle
     * @param arguments    the arguments
     */
    BootstrapMethod(MethodHandle methodHandle, List<Object> arguments) {
        this.methodHandle = methodHandle;
        this.arguments = arguments;
    }

    @Override
    public EncodedBootstrapMethod encode(GlobalContext pool, MethodContext codeContext) {
        int methodRefIndex = pool.addToPool(new MethodHandleEntry(this.methodHandle));
        List<Integer> argumentIndexes = this.arguments.stream()
                .map(a -> ConstantPoolEntryFactory.create(a).addToPool(pool))
                .collect(Collectors.toList());
        return new EncodedBootstrapMethod(methodRefIndex, argumentIndexes);
    }
}
