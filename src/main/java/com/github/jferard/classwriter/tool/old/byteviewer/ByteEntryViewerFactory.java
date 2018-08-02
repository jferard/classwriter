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

package com.github.jferard.classwriter.tool.old.byteviewer;

import com.github.jferard.classwriter.tool.decoder.ConstantPoolEncodedEntryViewer;
import com.github.jferard.classwriter.tool.decoder.EntryViewerFactory;

import java.io.IOException;
import java.io.Writer;

public class ByteEntryViewerFactory implements EntryViewerFactory {
    public static final String NUM_FORMAT = "/* %4s */ ";

    @Override
    public void writeEntryCount(Writer writer, int entryCount) throws IOException {
        writer.append("/* CONSTANT POOL */\n");
        writer.append(String.format("%s, %s, // number of entries: %d\n",
                ByteViewerFactory.hex(entryCount + 1 >> 8), ByteViewerFactory.hex(entryCount + 1),
                entryCount));
    }

    @Override
    public ConstantPoolEncodedEntryViewer classEntryViewer(int nameIndex) {
        return new ByteIndexedEntryViewer("ConstantTags.CLASS", nameIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer fieldRefEntryViewer(int classIndex,
                                                              int nameAndTypeIndex) {
        return new ByteFieldOrMethodRefEntryViewer("ConstantTags.FIELDREF", classIndex,
                nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer methodRefEntryViewer(int classIndex,
                                                               int nameAndTypeIndex) {
        return new ByteFieldOrMethodRefEntryViewer("ConstantTags.METHODREF", classIndex,
                nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer interfaceMethodRefEntryViewer(int classIndex,
                                                                        int nameAndTypeIndex) {
        return new ByteFieldOrMethodRefEntryViewer("ConstantTags.INTERFACEMETHODREF", classIndex,
                nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer stringEntryViewer(int stringIndex) {
        return new ByteIndexedEntryViewer("ConstantTags.STRING", stringIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer integerEntryViewer(int i) {
        return new ByteNumberEntryViewer<>("ConstantTags.INTEGER", i);
    }

    @Override
    public ConstantPoolEncodedEntryViewer floatEntryViewer(float f) {
        return new ByteNumberEntryViewer<>("ConstantTags.FLOAT", f);
    }

    @Override
    public ConstantPoolEncodedEntryViewer longEntryViewer(long l) {
        return new ByteNumberEntryViewer<>("ConstantTags.LONG", l);
    }

    @Override
    public ConstantPoolEncodedEntryViewer doubleEntryViewer(double d) {
        return new ByteNumberEntryViewer<>("ConstantTags.DOUBLE", d);
    }

    @Override
    public ConstantPoolEncodedEntryViewer nameAndTypeEntryViewer(int nameIndex,
                                                                 int descriptorIndex) {
        return new ByteNameAndTypeEntryViewer(nameIndex, descriptorIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer utf8EntryViewer(String utf) {
        return new ByteUTF8EntryViewer(utf);
    }

    @Override
    public ConstantPoolEncodedEntryViewer methodHandleEntryViewer(byte referenceKind,
                                                                  int referenceIndex) {
        return new ByteMethodHandleEntryViewer(referenceKind, referenceIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer methodTypeEntryViewer(int descriptorIndex) {
        return new ByteIndexedEntryViewer("ConstantTags.METHODTYPE", descriptorIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer invokeDynamicEntryViewer(int bootstrapMethodAttrIndex,
                                                                   int nameAndTypeIndex) {
        throw new IllegalArgumentException();
    }

    @Override
    public ConstantPoolEncodedEntryViewer unknown(int tag) {
        throw new IllegalArgumentException();
    }
}
