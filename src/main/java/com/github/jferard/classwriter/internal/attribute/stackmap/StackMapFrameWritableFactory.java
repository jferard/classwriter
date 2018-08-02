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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.WritableFactory;
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType;

import java.util.List;

public interface StackMapFrameWritableFactory<O> extends WritableFactory<O> {
    Writable<O> chopFrame(int k, int offsetDelta);

    Writable<O> sameFrame(int offsetDelta);

    Writable<O> fullFrame(int offsetDelta, List<EncodedVerificationType> encodedLocals,
                          List<EncodedVerificationType> encodedStackItems);

    Writable<O> appendFrame(int k, int offsetDelta, List<EncodedVerificationType> encodedNewTypes);

    Writable<O> localsSame1StackItemFrame(int offsetDelta,
                                          EncodedVerificationType encodedFirstStackItemVerificationType);

    Writable<O> localsSame1StackItemFrameExtended(int offsetDelta, EncodedVerificationType encodedFirstStackItemVerificationType);

    Writable<O> sameFrameExtended(int offsetDelta);
}
