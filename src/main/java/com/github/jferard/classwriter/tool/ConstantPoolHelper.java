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

package com.github.jferard.classwriter.tool;

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.api.ClassWritableFactory;
import com.github.jferard.classwriter.decoded.DecodedClassWritableFactory;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.encoded.Encoded;
import com.github.jferard.classwriter.encoded.EncodedClassFile;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.pool.DecodedConstantPoolWritableFactory;
import com.github.jferard.classwriter.tool.decoder.ClassAttributesDecoder;
import com.github.jferard.classwriter.tool.decoder.ClassFileDecoder;
import com.github.jferard.classwriter.tool.decoder.ConstantPoolDecoder;
import com.github.jferard.classwriter.tool.decoder.HeaderDecoder;
import com.github.jferard.classwriter.tool.decoder.InterfacesDecoder;
import com.github.jferard.classwriter.tool.decoder.ClassFileViewerFactory;
import com.github.jferard.classwriter.tool.old.byteviewer.ByteViewerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

public class ConstantPoolHelper {
    public static String viewPool(GlobalContext context) throws IOException {
        /*
        ConstantPoolWritableFactory<Writer> writableFactory =
                new DecodedConstantPoolWritableFactory();
        Writer sw = new StringWriter();
        final Writable<Writer> writablePool = (Writable<Writer>) context.getWritablePool(writableFactory);
        writablePool.write(sw);
        */
        return "sw.toString();";
    }

    public static String viewClass(InputStream inputStream) throws IOException {
        DataInput input = new DataInputStream(inputStream);
        Writer w = new StringWriter();
        final ConstantPoolDecoder constantPoolDecoder = new ConstantPoolDecoder();
        final HeaderDecoder headerDecoder = new HeaderDecoder();
        final InterfacesDecoder interfacesDecoder = new InterfacesDecoder();
        final ClassFileDecoder classFileDecoder = new ClassFileDecoder(headerDecoder,
                constantPoolDecoder, interfacesDecoder);
        final Encoded<ClassWritableFactory<?>> encodedClassFile = classFileDecoder.decode(input);
        final Writable<Writer> writable = (Writable<Writer>) encodedClassFile
                .toWritable(DecodedClassWritableFactory.create());
        writable.write(w);
        return w.toString();
    }

    public static String viewClass(EncodedClassFile encodedClassFile) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Writable<Writer> writableClassFile = (Writable<Writer>) encodedClassFile.toWritable(
                DecodedClassWritableFactory.create());
        final StringWriter output = new StringWriter();
        writableClassFile.write(output);

        return output.toString();
    }
}

