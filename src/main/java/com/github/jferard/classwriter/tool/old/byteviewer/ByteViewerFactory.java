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

import com.github.jferard.classwriter.tool.decoder.AccessThisSuperViewer;
import com.github.jferard.classwriter.tool.decoder.ClassAttributeViewer;
import com.github.jferard.classwriter.tool.decoder.ClassAttributesViewer;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolEntryDecoder;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolViewer;
import com.github.jferard.classwriter.tool.decoder.EntryViewerFactory;
import com.github.jferard.classwriter.tool.decoder.FieldViewer;
import com.github.jferard.classwriter.tool.decoder.FieldsViewer;
import com.github.jferard.classwriter.tool.decoder.HeaderViewer;
import com.github.jferard.classwriter.tool.decoder.InterfaceViewer;
import com.github.jferard.classwriter.tool.decoder.InterfacesViewer;
import com.github.jferard.classwriter.tool.decoder.MethodAttributeViewerFactory;
import com.github.jferard.classwriter.tool.decoder.MethodDecoder;
import com.github.jferard.classwriter.tool.decoder.MethodsViewer;
import com.github.jferard.classwriter.tool.decoder.ClassFileViewerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ByteViewerFactory implements ClassFileViewerFactory {
    public static ByteViewerFactory create() {
        return new ByteViewerFactory(new ByteEntryViewerFactory(),
                new ByteMethodAttributeViewerFactory());
    }

    public static String hex(int shifted) {
        final String s;
        final int b = shifted & 0xff;
        if (b > Byte.MAX_VALUE) {
            s = String.format("(byte) 0x%02X", b);
        } else {
            s = String.format("0x%02X", b);
        }
        return s;
    }

    public static String chr(char b) {
        return String.format("'%c'", b);
    }

    private EntryViewerFactory entryViewerFactory;
    private MethodAttributeViewerFactory methodAttributeViewerFactory;

    ByteViewerFactory(EntryViewerFactory entryWriterFactory,
                      MethodAttributeViewerFactory methodAttributeViewerFactory) {
        this.entryViewerFactory = entryWriterFactory;
        this.methodAttributeViewerFactory = methodAttributeViewerFactory;
    }

    @Override
    public HeaderViewer header(int magic, int minorVersion, int majorVersion) {
        return new ByteHeaderViewer(magic, minorVersion, majorVersion);
    }

    @Override
    public AccessThisSuperViewer accessThisSuper(short accessFlags, short thisIndex,
                                                 short superIndex) {
        return new ByteAccessThisSuperViewer(accessFlags, thisIndex, superIndex);
    }

    @Override
    public InterfacesViewer interfaces(List<InterfaceViewer> interfaceViewers) {
        return new ByteInterfacesViewer(interfaceViewers);
    }

    @Override
    public FieldsViewer fields(List<FieldViewer> fieldViewers) {
        return new ByteFieldsViewer(fieldViewers);
    }

    @Override
    public MethodsViewer methods(List<MethodDecoder> methods) {
        throw new IllegalStateException(); //new ByteMethodsViewer(methods.stream().map(m -> m == null ? null : m.decode(this.methodAttributeViewerFactory))
                //.collect(Collectors.toList()));
    }

    @Override
    public ClassAttributesViewer classAttributes(List<ClassAttributeViewer> classAttributeViewers) {
        return new ByteClassAttributesViewer(classAttributeViewers);
    }
}
