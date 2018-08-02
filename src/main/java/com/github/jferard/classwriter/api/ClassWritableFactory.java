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

import com.github.jferard.classwriter.Writable;
import com.github.jferard.classwriter.encoded.EncodedAttributes;
import com.github.jferard.classwriter.encoded.EncodedFields;
import com.github.jferard.classwriter.encoded.EncodedMethods;
import com.github.jferard.classwriter.pool.ConstantPool;
import com.github.jferard.classwriter.tool.decoder.EncodedInterfaces;

import java.util.List;

public interface ClassWritableFactory<O> extends WritableFactory<O> {
    Writable<O> classFile(Header header, ConstantPool pool, int accessFlags, int thisIndex, int superIndex, EncodedInterfaces interfaceIndices,
                          EncodedFields encodedFields, EncodedMethods encodedMethods,
                          EncodedAttributes encodedAttributes);

    Writable<O> header(int minorVersion, int majorVersion);

    Writable<O> interfaces(List<Integer> encodedInterfaces);
}
