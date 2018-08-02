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

package com.github.jferard.classwriter.tool.decoder;

import com.github.jferard.classwriter.encoded.pool.EncodedClassEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedFieldRefEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedInvokeDynamicEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedMethodHandleEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedMethodRefEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedMethodTypeEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedNameAndTypeEntry;
import com.github.jferard.classwriter.encoded.pool.EncodedStringEntry;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.pool.ConstantTags;
import com.github.jferard.classwriter.pool.DoubleEntry;
import com.github.jferard.classwriter.pool.FloatEntry;
import com.github.jferard.classwriter.pool.IntegerEntry;
import com.github.jferard.classwriter.pool.LongEntry;
import com.github.jferard.classwriter.pool.Utf8Entry;

import java.io.DataInput;
import java.io.IOException;

public class ConstantPoolDecoder implements Decoder<ConstantPoolWritableFactory<?>> {
    @Override
    public ConstantPool decode(DataInput input) throws IOException {
        final int entryCount = input.readShort() - 1;
        ConstantPool constantPool = new ConstantPool();
        for (int i = 0; i < entryCount; i++) {
            constantPool.addEncoded(this.readNextEntry(input));
        }
        return constantPool;
    }

    private EncodedConstantPoolEntry readNextEntry(DataInput input) throws IOException {
        int tag = input.readByte();
        return this.readNextEntryFromTag(tag, input);
    }

    private EncodedConstantPoolEntry readNextEntryFromTag(int tag, DataInput input)
            throws IOException {
        switch (tag) {
            case ConstantTags.CLASS:
                return new EncodedClassEntry(input.readShort());
            case ConstantTags.FIELDREF:
                return new EncodedFieldRefEntry(input.readShort(), input.readShort());
            case ConstantTags.METHODREF:
                return new EncodedMethodRefEntry(input.readShort(), input.readShort());
            case ConstantTags.INTERFACEMETHODREF:
                return new EncodedMethodRefEntry(input.readShort(), input.readShort());
            case ConstantTags.STRING:
                return new EncodedStringEntry(input.readShort());
            case ConstantTags.INTEGER:
                return new IntegerEntry(input.readInt());
            case ConstantTags.FLOAT:
                return new FloatEntry(Float.intBitsToFloat(input.readInt()));
            case ConstantTags.LONG:
                return new LongEntry(input.readLong());
            case ConstantTags.DOUBLE:
                return new DoubleEntry(input.readDouble());
            case ConstantTags.NAMEANDTYPE:
                return new EncodedNameAndTypeEntry(input.readShort(), input.readShort());
            case ConstantTags.UTF8:
                return new Utf8Entry(input.readUTF());
            case ConstantTags.METHODHANDLE:
                return new EncodedMethodHandleEntry(input.readByte(), input.readShort());
            case ConstantTags.METHODTYPE:
                return new EncodedMethodTypeEntry(input.readShort());
            case ConstantTags.INVOKEDYNAMIC:
                return new EncodedInvokeDynamicEntry(input.readShort(), input.readShort());
            default:
                throw new IllegalArgumentException(Integer.toString(tag));
        }
    }
}

