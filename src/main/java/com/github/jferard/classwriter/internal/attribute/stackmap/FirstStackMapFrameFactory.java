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

package com.github.jferard.classwriter.internal.attribute.stackmap;

import com.github.jferard.classwriter.internal.access.MethodAccess;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.api.ValueType;
import com.github.jferard.classwriter.api.MethodDescriptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FirstStackMapFrameFactory {
    public StackMapFrame create(PlainClassRef classRef, String methodName, MethodDescriptor descriptor,
                                int accessFlags) {
        List<VerificationType> args = this.expandArgs(descriptor.getArgTypes());
        if ((accessFlags & MethodAccess.ACC_STATIC) == 0) {
            args.add(methodName
                    .equals("<init>") ? VerificationType.UNITIALIZED_THIS :
                    VerificationType.fromClassRef(classRef));
        }
        return new StackMapFrame(null, 0, args, Collections.emptyList());
    }

    /**
     * expandTypeList(RawArgs, Args)
     */
    private List<VerificationType> expandArgs(List<ValueType> args) {
        return args.stream().flatMap(a -> (a.getSize() == 1 ? Collections
                .singletonList(VerificationType.fromValueType(a)) : Arrays
                .asList(VerificationType.fromValueType(a), VerificationType.TOP)).stream())
                .collect(Collectors.toList());
    }
}
