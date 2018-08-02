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

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod;
import com.github.jferard.classwriter.api.BootstrapMethod;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedBootstrapMethodsAttribute;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.List;
import java.util.stream.Collectors;

public class BootstrapMethodsAttribute implements Encodable<EncodedBootstrapMethodsAttribute> {
    private static final String BOOTSTRAP_METHODS_NAME = "BootstrapMethods";
    private List<BootstrapMethod> bootstrapMethods;

    public BootstrapMethodsAttribute(List<BootstrapMethod> bootstrapMethods) {
        this.bootstrapMethods = bootstrapMethods;
    }

    public EncodedBootstrapMethodsAttribute encode(GlobalContext pool, MethodContext codeContext) {
        int nameIndex = pool.addUtf8ToPool(BOOTSTRAP_METHODS_NAME);
        List<EncodedBootstrapMethod> encodedBootstrapMethods = this.bootstrapMethods.stream()
                .map(bsm -> bsm.encode(pool, MethodContext.create(0))).collect(Collectors.toList());
        return new EncodedBootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods);
    }
}
