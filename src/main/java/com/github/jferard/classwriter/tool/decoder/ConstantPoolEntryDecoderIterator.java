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

import com.github.jferard.classwriter.pool.ConstantTags;

import java.io.DataInput;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConstantPoolEntryDecoderIterator implements Iterator<ConstantPoolEntryDecoder> {
    private final DataInput input;
    private int entryCount;

    public ConstantPoolEntryDecoderIterator(DataInput input, int entryCount) {
        this.input = input;
        this.entryCount = entryCount;
    }

    @Override
    public ConstantPoolEntryDecoder next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        try {
            int tag = this.input.readByte();
            final ConstantPoolEntryDecoder ret = this.readNext(tag);
            this.entryCount--;
            return ret;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return this.entryCount > 0;
    }

    private ConstantPoolEntryDecoder readNext(int tag) throws IOException {
        switch (tag) {
            case ConstantTags.CLASS:
                return new ClassEntryDecoder(this.input.readShort());
            case ConstantTags.FIELDREF:
                return new FieldRefEntryDecoder(this.input.readShort(), this.input.readShort());
            case ConstantTags.METHODREF:
                return new MethodRefEntryDecoder(this.input.readShort(), this.input.readShort());
            case ConstantTags.INTERFACEMETHODREF:
                return new InterfaceMethodRefEntryDecoder(this.input.readShort(),
                        this.input.readShort());
            case ConstantTags.STRING:
                return new StringEntryDecoder(this.input.readShort());
            case ConstantTags.INTEGER:
                return new IntegerEntryDecoder(this.input.readInt());
            case ConstantTags.FLOAT:
                return new FloatEntryDecoder(Float.intBitsToFloat(this.input.readInt()));
            case ConstantTags.LONG:
                return new LongEntryDecoder(this.input.readLong());
            case ConstantTags.DOUBLE:
                return new DoubleEntryDecoder(this.input.readDouble());
            case ConstantTags.NAMEANDTYPE:
                return new NameAndTypeEntryDecoder(this.input.readShort(), this.input.readShort());
            case ConstantTags.UTF8:
                return new Utf8EntryDecoder(this.input.readUTF());
            case ConstantTags.METHODHANDLE:
                return new MethodHandleEntryDecoder(this.input.readByte(), this.input.readShort());
            case ConstantTags.METHODTYPE:
                return new MethodTypeEntryDecoder(this.input.readShort());
            case ConstantTags.INVOKEDYNAMIC:
                return new InvokeDynamicEntryDecoder(this.input.readShort(), this.input.readShort());
            default:
                return new UnknownEntryDecoder(tag);
        }
    }
}
