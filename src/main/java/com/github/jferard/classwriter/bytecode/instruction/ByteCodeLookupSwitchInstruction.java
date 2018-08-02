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

package com.github.jferard.classwriter.bytecode.instruction;

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.Writable;

import java.io.DataOutput;
import java.io.IOException;

public class ByteCodeLookupSwitchInstruction implements Writable<DataOutput> {
    private final int defaultOffset;
    private final int[] match_and_offsets;

    public ByteCodeLookupSwitchInstruction(int defaultOffset, int[] match_and_offsets) {
        this.defaultOffset = defaultOffset;
        this.match_and_offsets = match_and_offsets;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(OpCodes.LOOKUPSWITCH);
        // output.padTo4();
        output.writeInt(this.defaultOffset);
        output.writeInt(this.match_and_offsets.length);
        for (final int match_or_offset : this.match_and_offsets) {
            output.writeInt(match_or_offset);
        }
    }

}
