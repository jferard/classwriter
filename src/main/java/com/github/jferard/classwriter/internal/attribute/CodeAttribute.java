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

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.attribute.EncodedAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttribute;
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode;
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction;
import com.github.jferard.classwriter.internal.instruction.Instruction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.3. The Code Attribute
 */
public class CodeAttribute implements MethodInfoAttribute {
    public static final String CODE_NAME = "Code";
    private final Instruction code;
    private final List<CodeAttributeAttribute> attributes;
    private List<VerificationType> argVerificationTypes;

    public CodeAttribute(final Instruction code, List<VerificationType> argVerificationTypes,
                         final List<CodeAttributeAttribute> attributes) {
        this.code = code;
        this.argVerificationTypes = argVerificationTypes;
        this.attributes = attributes;
    }

    @Override
    public EncodedAttribute encode(GlobalContext context, MethodContext codeContext) {
        int attributeNameIndex = context.addUtf8ToPool(CODE_NAME);
        this.argVerificationTypes.forEach(avt -> codeContext.localsPush(avt));
        this.code.preprocess(context, codeContext);
        int codeLength = codeContext.getCurOffset();
        EncodedInstruction encodedInstruction = this.code.encode(context, MethodContext.create(0));
        List<EncodedExceptionInCode> encodedExceptionsInCode = codeContext.getExceptions().stream()
                .map(e -> e.encode(context, MethodContext.create(0))).collect(Collectors.toList());
        List<EncodedAttribute> encodedAttributes = this.attributes.stream()
                .map(a -> a.encode(context, MethodContext.create(0))).collect(Collectors.toList());
        int attributesLength = encodedAttributes.stream().mapToInt(EncodedAttribute::getSize).sum();
        return new EncodedCodeAttribute(attributeNameIndex, codeContext.getMaxStack(),
                codeContext.getMaxLocals(), codeLength, encodedInstruction, encodedExceptionsInCode,
                attributesLength, encodedAttributes);
    }
}
