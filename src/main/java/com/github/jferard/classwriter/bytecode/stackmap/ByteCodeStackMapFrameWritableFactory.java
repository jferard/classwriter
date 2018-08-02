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

import com.github.jferard.classwriter.bytecode.ByteCode;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;
import com.github.jferard.classwriter.internal.attribute.stackmap.StackMapFrameWritableFactory;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeWritableFactory;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ByteCodeStackMapFrameWritableFactory
        implements StackMapFrameWritableFactory<DataOutput> {
    private VerificationTypeWritableFactory<DataOutput> verificationTypeFactory;

    public ByteCodeStackMapFrameWritableFactory(
            VerificationTypeWritableFactory<DataOutput> verificationTypeFactory) {
        this.verificationTypeFactory = verificationTypeFactory;
    }

    @Override
    public ByteCode chopFrame(int k, int offsetDelta) {
        return new ByteCodeChopFrame(k, offsetDelta);
    }

    @Override
    public ByteCode sameFrame(int offsetDelta) {
        return new ByteCodeSameFrame(offsetDelta);
    }

    @Override
    public ByteCode fullFrame(int offsetDelta,
                                          List<EncodedVerificationType> encodedLocals,
                                          List<EncodedVerificationType> encodedStackItems) {
        List<ByteCode> writableLocals = encodedLocals.stream()
                .map(l -> (ByteCode) l.toWritable(this.verificationTypeFactory))
                .collect(Collectors.toList());
        List<ByteCode> writableStackItems = encodedStackItems.stream()
                .map(l -> (ByteCode) l.toWritable(this.verificationTypeFactory))
                .collect(Collectors.toList());

        return new ByteCodeFullFrame(offsetDelta, writableLocals, writableStackItems);
    }

    @Override
    public ByteCode appendFrame(int k, int offsetDelta,
                                            List<EncodedVerificationType> encodedNewTypes) {
        List<ByteCode> writableNewTypes = encodedNewTypes.stream()
                .map(ent -> (ByteCode) ent.toWritable(this.verificationTypeFactory))
                .collect(Collectors.toList());
        return new ByteCodeAppendFrame(k, offsetDelta, writableNewTypes);
    }

    @Override
    public ByteCode localsSame1StackItemFrame(int offsetDelta,
                                                          EncodedVerificationType encodedFirstStackItemVerificationType) {
        final ByteCode writable = (ByteCode) encodedFirstStackItemVerificationType
                .toWritable(this.verificationTypeFactory);
        return new ByteCodeLocalsSame1StackItemFrame(offsetDelta,
                writable);
    }

    @Override
    public ByteCode localsSame1StackItemFrameExtended(int offsetDelta,
                                                                  EncodedVerificationType encodedFirstStackItemVerificationType) {
        final ByteCode writable = (ByteCode) encodedFirstStackItemVerificationType
                .toWritable(this.verificationTypeFactory);
        return new ByteCodeLocalsSame1StackItemFrameExtended(offsetDelta,
                writable);
    }

    @Override
    public ByteCode sameFrameExtended(int offsetDelta) {
        return new ByteCodeSameFrameExtended(offsetDelta);
    }
}
