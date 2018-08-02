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

package com.github.jferard.classwriter.internal.instruction.base;

import com.github.jferard.classwriter.internal.context.MethodContext;

public class PaddingHelper {
    public static final int PADDING_SIZE = 4;

    public int computePadding(int offsetBeforePadding) {
        return (PADDING_SIZE - (offsetBeforePadding % PADDING_SIZE)) % PADDING_SIZE;
    }

    public void placeOffsetBeforePadding(MethodContext codeContext) {
        codeContext.offsetDelta(1); // skip one byte for instruction code
    }

    public void placeOffsetAfterLookupSwitch(MethodContext codeContext, int padding, int size) {
        codeContext.offsetDelta(padding); // after padding
        codeContext.offsetDelta((4 + 4) * (1 + size)); // after instructions
    }

}
