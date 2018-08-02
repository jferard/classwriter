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

import java.io.IOException;
import java.io.Writer;

public interface EntryViewerFactory {
    ConstantPoolEncodedEntryViewer classEntryViewer(int readShort);

    ConstantPoolEncodedEntryViewer fieldRefEntryViewer(int classIndex, int nameAndTypeIndex);

    ConstantPoolEncodedEntryViewer methodRefEntryViewer(int classIndex, int nameAndTypeIndex);

    ConstantPoolEncodedEntryViewer interfaceMethodRefEntryViewer(int classIndex,
                                                                 int nameAndTypeIndex);

    ConstantPoolEncodedEntryViewer stringEntryViewer(int stringIndex);

    ConstantPoolEncodedEntryViewer integerEntryViewer(int i);

    ConstantPoolEncodedEntryViewer floatEntryViewer(float f);

    ConstantPoolEncodedEntryViewer longEntryViewer(long l);

    ConstantPoolEncodedEntryViewer doubleEntryViewer(double d);

    ConstantPoolEncodedEntryViewer nameAndTypeEntryViewer(int nameIndex, int descriptorIndex);

    ConstantPoolEncodedEntryViewer utf8EntryViewer(String utf);

    ConstantPoolEncodedEntryViewer methodHandleEntryViewer(byte referenceKind,
                                                           int referenceIndex);

    ConstantPoolEncodedEntryViewer methodTypeEntryViewer(int descriptorIndex);

    ConstantPoolEncodedEntryViewer invokeDynamicEntryViewer(int bootstrapMethodAttrIndex,
                                                            int nameAndTypeIndex);

    ConstantPoolEncodedEntryViewer unknown(int tag);

    void writeEntryCount(Writer writer, int entryCount) throws IOException;
}
