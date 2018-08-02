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

package com.github.jferard.classwriter.api;

import com.github.jferard.classwriter.Field;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.encoded.EncodedClassFile;
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.EncodedMethods;
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute;
import com.github.jferard.classwriter.internal.attribute.ClassFileAttribute;
import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.internal.context.MethodContext;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.pool.ConstantPoolWritableFactory;
import com.github.jferard.classwriter.pool.Encodable;
import com.github.jferard.classwriter.tool.decoder.EncodedInterfaces;

import java.io.DataOutput;
import java.util.List;
import java.util.stream.Collectors;

public class ClassFile implements Encodable<EncodedClassFile> {
    public static ClassFileBuilder builder(String className) {
        return new ClassFileBuilder(new PlainClassRef(className));
    }

    private final PlainClassRef thisClassRef;
    private final PlainClassRef superClassRef;
    private final int accessFlags;
    private final List<PlainClassRef> interfaces;
    private final List<Method> methods;
    private final List<ClassFileAttribute> attributes;
    private final List<Field> fields;
    private Header header;
    private ConstantPoolWritableFactory<DataOutput> poolWritableFactory;

    public ClassFile(Header header, int accessFlags, PlainClassRef thisClassRef,
                     PlainClassRef superClassRef, List<PlainClassRef> interfaces,
                     List<Field> fields, List<Method> methods,
                     List<ClassFileAttribute> attributes) {
        this.accessFlags = accessFlags;
        this.header = header;
        this.thisClassRef = thisClassRef;
        this.superClassRef = superClassRef;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
    }

    @Override
    public EncodedClassFile encode(GlobalContext context, MethodContext codeContext) {
        ConstantPool pool = context.getEncodedPool();
        int thisIndex = context.addToPool(this.thisClassRef.toEntry());
        int superIndex =
                this.superClassRef == null ? 0 : context.addToPool(this.superClassRef.toEntry());
        EncodedInterfaces encodedInterfaces = new EncodedInterfaces(
                this.interfaces.stream().map(i -> context.addToPool(i.toEntry()))
                        .collect(Collectors.toList()));
        EncodedFields encodedFields = new EncodedFields(
                this.fields.stream().map(f -> f.encode(context, MethodContext.create(0)))
                        .collect(Collectors.toList()));
        EncodedMethods encodedMethods = new EncodedMethods(
                this.methods.stream().map(f -> f.encode(context, MethodContext.create(0)))
                        .collect(Collectors.toList()));

        // add bootstrap methods to attributes here
        EncodedAttributes encodedAttributes = new EncodedAttributes(this.attributes.stream()
                .map(f -> (EncodedClassFileAttribute) f.encode(context, MethodContext.create(0)))
                .collect(Collectors.toList()));

        return new EncodedClassFile(this.header, pool, this.accessFlags, thisIndex, superIndex,
                encodedInterfaces, encodedFields, encodedMethods, encodedAttributes);
    }
}
