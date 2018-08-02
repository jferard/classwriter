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

package com.github.jferard.classwriter.tool.javapviewer;

import com.github.jferard.classwriter.tool.decoder.ConstantPoolEncodedEntryViewer;
import com.github.jferard.classwriter.tool.decoder.EntryViewerFactory;

import java.io.IOException;
import java.io.Writer;

public class JavapEntryViewerFactory implements EntryViewerFactory {
    public static final String CODE_FORMAT = "%-19s";
    public static final String NUM_FORMAT = "%4s = ";

    @Override
    public void writeEntryCount(Writer writer, int entryCount) throws IOException {
        writer.append(String.format("%s entries\n", entryCount));
    }

    @Override
    public ConstantPoolEncodedEntryViewer classEntryViewer(int nameIndex) {
        return new JavapIndexedEntryViewer("Class", nameIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer fieldRefEntryViewer(int classIndex,
                                                              int nameAndTypeIndex) {
        return new JavapFieldOrMethodRefEntryViewer("FieldRef", classIndex, nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer methodRefEntryViewer(int classIndex,
                                                               int nameAndTypeIndex) {
        return new JavapFieldOrMethodRefEntryViewer("MethodRef", classIndex, nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer interfaceMethodRefEntryViewer(int classIndex,
                                                                        int nameAndTypeIndex) {
        return new JavapFieldOrMethodRefEntryViewer("InterfaceMethodRef", classIndex,
                nameAndTypeIndex);

    }

    @Override
    public ConstantPoolEncodedEntryViewer stringEntryViewer(int stringIndex) {
        return new JavapIndexedEntryViewer("String", stringIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer integerEntryViewer(int i) {
        return new JavapNumberEntryViewer<>("Integer", i);
    }

    @Override
    public ConstantPoolEncodedEntryViewer floatEntryViewer(float f) {
        return new JavapNumberEntryViewer<>("Float", f);
    }

    @Override
    public ConstantPoolEncodedEntryViewer longEntryViewer(long l) {
        return new JavapNumberEntryViewer<>("Long", l);
    }

    @Override
    public ConstantPoolEncodedEntryViewer doubleEntryViewer(double d) {
        return new JavapNumberEntryViewer<>("Double", d);
    }

    @Override
    public ConstantPoolEncodedEntryViewer nameAndTypeEntryViewer(int nameIndex,
                                                                 int descriptorIndex) {
        return new JavapNameAndTypeEntryViewer(nameIndex, descriptorIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer utf8EntryViewer(String utf) {
        return new JavapUTF8EntryViewer(utf);
    }

    @Override
    public ConstantPoolEncodedEntryViewer methodHandleEntryViewer(byte referenceKind,
                                                                  int referenceIndex) {
        return new JavapMethodHandleEntryViewer(referenceKind, referenceIndex);
    }

    @Override
    public ConstantPoolEncodedEntryViewer methodTypeEntryViewer(int descriptorIndex) {
        return new JavapIndexedEntryViewer("MethodType", descriptorIndex);
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
