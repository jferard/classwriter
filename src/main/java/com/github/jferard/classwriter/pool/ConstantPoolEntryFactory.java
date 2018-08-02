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

package com.github.jferard.classwriter.pool;

import com.github.jferard.classwriter.api.MethodDescriptor;
import com.github.jferard.classwriter.api.MethodHandle;
import com.github.jferard.classwriter.api.PlainClassRef;
import com.github.jferard.classwriter.pool.ClassEntry;
import com.github.jferard.classwriter.pool.ConstantPoolEntry;
import com.github.jferard.classwriter.pool.DoubleEntry;
import com.github.jferard.classwriter.pool.FloatEntry;
import com.github.jferard.classwriter.pool.IntegerEntry;
import com.github.jferard.classwriter.pool.LongEntry;
import com.github.jferard.classwriter.pool.MethodHandleEntry;
import com.github.jferard.classwriter.pool.MethodTypeEntry;
import com.github.jferard.classwriter.pool.StringEntry;

public class ConstantPoolEntryFactory {
    public static ConstantPoolEntry create(Object o) {
        if (o instanceof String) {
            return new StringEntry((String) o);
        } else if (o instanceof PlainClassRef) {
            return new ClassEntry((PlainClassRef) o);
        } else if (o instanceof Integer) {
            return new IntegerEntry((Integer) o);
        } else if (o instanceof Long) {
            return new LongEntry((Long) o);
        } else if (o instanceof Float) {
            return new FloatEntry((Float) o);
        } else if (o instanceof Double) {
            return new DoubleEntry((Double) o);
        } else if (o instanceof MethodHandle) {
            return new MethodHandleEntry((MethodHandle) o);
        } else if (o instanceof MethodDescriptor) {
            return new MethodTypeEntry((MethodDescriptor) o);
        }
        throw new IllegalStateException();
    }
}
