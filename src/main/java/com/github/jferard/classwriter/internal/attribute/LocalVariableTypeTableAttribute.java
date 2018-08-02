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
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable;
import com.github.jferard.classwriter.encoded.attribute.EncodedVariableTypeTableAttribute;

import java.util.List;
import java.util.stream.Collectors;

public class LocalVariableTypeTableAttribute implements CodeAttributeAttribute {
    private static final String LOCAL_VARIABLE_TABLE = "LocalVariableTableType";
    private final List<LocalVariableTypeTable> localVariableTypes;

    public LocalVariableTypeTableAttribute(List<LocalVariableTypeTable> localVariableTypes) {
        this.localVariableTypes = localVariableTypes;
    }

    @Override
    public EncodedAttribute encode(GlobalContext context, MethodContext codeContext) {
        int attributeNameIndex = context
                .addUtf8ToPool(LocalVariableTypeTableAttribute.LOCAL_VARIABLE_TABLE);
        final List<EncodedLocalVariableTypeTable> encodedLocalVariableTypes =
                this.localVariableTypes
                .stream().map(lv -> lv.encode(context, codeContext)).collect(Collectors.toList());
        return new EncodedVariableTypeTableAttribute(attributeNameIndex, encodedLocalVariableTypes);
    }
}
