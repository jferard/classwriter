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

package com.github.jferard.classwriter.internal.context;

public class PaddingItem implements PositionedItem {
    public static final int PADDING_SIZE  = 4;
    private int offset;
    private int padding;

    public PaddingItem(int offset, int padding) {
        this.offset = offset;
        this.padding = padding;
    }

    @Override
    public void shift(int curShift) {
        this.offset += curShift;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int updateShift(int curShift, TargetItem targetItem) {
        final int newShift;
        final int mod = curShift % PADDING_SIZE;
        if (mod == 0) {
            newShift = curShift;
        } else if (mod < padding) {
            // padding += mod;
            newShift = curShift - mod;
        } else { // mod >= padding
            // padding += PADDING_SIZE - mod;
            newShift = curShift + PADDING_SIZE - mod;
        }
        return newShift;
    }

    @Override
    public void extractOffsets(OffsetsBuilder builder) {
        // do nothing
    }
}
