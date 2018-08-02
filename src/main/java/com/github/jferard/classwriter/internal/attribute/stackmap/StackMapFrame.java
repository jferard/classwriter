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

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.encoded.stackmap.EncodedAppendFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedChopFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedFullFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedLocalsSame1StackItemFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedLocalsSame1StackItemFrameExtended;
import com.github.jferard.classwriter.encoded.stackmap.EncodedSameFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedSameFrameExtended;
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;
import com.github.jferard.classwriter.pool.Encodable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 4.7.4. The StackMapTable Attribute
 * For every instruction that adds or remove a local variable or a stackItems variable, there
 * must be
 * a stackItems map frame giving the type of the variable.
 * <p>
 * 4.10.1.4. Stack Map Frame Representation
 */
public class StackMapFrame implements Encodable<EncodedStackMapFrame> {
    private static final int MAX_SAME = 64;

    private final StackMapFrame previous;
    private final List<VerificationType> locals;
    private final List<VerificationType> stackItems;
    private int offset;

    public StackMapFrame(StackMapFrame previous, int offset, List<VerificationType> locals,
                         List<VerificationType> stackItems) {
        if (offset <= previous.offset) {
            throw new IllegalArgumentException();
        }
        this.previous = previous;
        this.offset = offset;
        this.locals = locals;
        this.stackItems = stackItems;
    }

    public int getSize() {
        return 0;
    }

    @Override
    public EncodedStackMapFrame encode(GlobalContext context, MethodContext codeContext) {
        final EncodedStackMapFrame encodedFrame;
        final int offsetDelta = this.computeOffsetDelta();
        if (this.locals.equals(this.previous.locals)) {
            encodedFrame = this.sameLocalsFrame(context, offsetDelta);
        } else {
            encodedFrame = this.differentLocalsFrame(context, offsetDelta);
        }
        return encodedFrame;
    }

    private int computeOffsetDelta() {
        int offsetDelta;
        if (this.previous.isFirst()) offsetDelta = this.offset - this.previous.offset;
        else offsetDelta = this.offset - this.previous.offset + 1;
        return offsetDelta;
    }

    private boolean isFirst() {
        return this.previous == null;
    }

    public EncodedStackMapFrame sameLocalsFrame(GlobalContext context, int offsetDelta) {
        EncodedStackMapFrame encodedFrame;
        if (this.stackItems.isEmpty()) {
            encodedFrame = this.sameFrame(offsetDelta);
        } else if (this.stackItems.size() == 1) {
            encodedFrame = this.sameLocals1StackItemFrame(context, offsetDelta);
        } else {
            encodedFrame = this.fullFrame(context, offsetDelta);
        }
        return encodedFrame;
    }

    private EncodedStackMapFrame sameFrame(int offsetDelta) {
        EncodedStackMapFrame encodedFrame;
        if (offsetDelta < MAX_SAME) {
            encodedFrame = new EncodedSameFrame(offsetDelta);
        } else {
            encodedFrame = new EncodedSameFrameExtended(offsetDelta);
        }
        return encodedFrame;
    }

    public EncodedStackMapFrame sameLocals1StackItemFrame(GlobalContext context, int offsetDelta) {
        EncodedStackMapFrame encodedFrame;
        EncodedVerificationType encodedFirstStackItemVerificationType = this.stackItems.get(0)
                .encode(context, MethodContext.create(0));
        if (offsetDelta < MAX_SAME) {
            encodedFrame = new EncodedLocalsSame1StackItemFrame(offsetDelta,
                    encodedFirstStackItemVerificationType);
        } else {
            encodedFrame = new EncodedLocalsSame1StackItemFrameExtended(offsetDelta,
                    encodedFirstStackItemVerificationType);
        }
        return encodedFrame;
    }

    public EncodedStackMapFrame fullFrame(GlobalContext context, int offsetDelta) {
        final List<EncodedVerificationType> encodedLocals = this.locals.stream()
                .map(t -> t.encode(context, MethodContext.create(0))).collect(Collectors.toList());
        final List<EncodedVerificationType> encodedStackItems = this.stackItems.stream()
                .map(t -> t.encode(context, MethodContext.create(0))).collect(Collectors.toList());
        return new EncodedFullFrame(offsetDelta, encodedLocals, encodedStackItems);
    }

    public EncodedStackMapFrame differentLocalsFrame(GlobalContext context, int offsetDelta) {
        EncodedStackMapFrame encodedFrame;
        if (this.stackItems.isEmpty()) {
            int size = this.locals.size();
            int previousSize = this.previous.locals.size();
            int k = size - previousSize;
            if (k > 0 && this.previous.locals.equals(this.locals.subList(0, previousSize))) {
                encodedFrame = this.appendFrame(context, offsetDelta, size, previousSize, k);
            } else if (k < 0 && this.locals.equals(this.previous.locals.subList(0, size))) {
                encodedFrame = new EncodedChopFrame(-k, offsetDelta);
            } else {
                encodedFrame = this.fullFrame(context, offsetDelta);
            }
        } else {
            encodedFrame = this.fullFrame(context, offsetDelta);
        }
        return encodedFrame;
    }

    private EncodedStackMapFrame appendFrame(GlobalContext context, int offsetDelta, int size,
                                             int previousSize, int k) {
        EncodedStackMapFrame encodedFrame;
        List<EncodedVerificationType> encodedNewTypes = this.locals.subList(previousSize, size)
                .stream().map(t -> t.encode(context, MethodContext.create(0)))
                .collect(Collectors.toList());
        encodedFrame = new EncodedAppendFrame(k, offsetDelta, encodedNewTypes);
        return encodedFrame;
    }

}
