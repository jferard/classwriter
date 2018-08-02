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

import com.github.jferard.classwriter.internal.context.GlobalContext;
import com.github.jferard.classwriter.api.FieldDescriptor;
import com.github.jferard.classwriter.api.ValueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstantPoolTest {
    @Test
    public void testAddTwice() {
        GlobalContext pool = GlobalContext.create();
        int i = pool.addUtf8ToPool("a");
        int j = pool.addUtf8ToPool("a");
        int k = pool.addUtf8ToPool("b");

        Assertions.assertEquals(1, i);
        Assertions.assertEquals(1, j);
        Assertions.assertEquals(2, k);
    }

    @Test
    public void testAddTwice2() {
        GlobalContext pool = GlobalContext.create();

        int i = pool.addUtf8ToPool("a");
        int j = pool.addUtf8ToPool("b");
        int k = pool.addUtf8ToPool("a");

        Assertions.assertEquals(1, i);
        Assertions.assertEquals(2, j);
        Assertions.assertEquals(1, k);
    }

    @Test
    public void testAddTwice3() {
        GlobalContext pool = GlobalContext.create();

        int i = pool.addToPool(new NameAndTypeEntry("a", new FieldDescriptor(ValueType.BOOLEAN)));
        int j = pool.addToPool(new NameAndTypeEntry("a", new FieldDescriptor(ValueType.BOOLEAN)));

        Assertions.assertEquals(3, i);
        Assertions.assertEquals(3, j);
    }
}