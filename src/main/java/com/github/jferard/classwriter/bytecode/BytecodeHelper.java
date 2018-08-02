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

package com.github.jferard.classwriter.bytecode;

public class BytecodeHelper {
    public static final int BYTE_SIZE = 1;
    public static final int SHORT_SIZE = 2;
    public static final int INT_SIZE = 4;
    public static final int FLOAT_SIZE = 4;
    public static final int DOUBLE_SIZE = 8;
    public static final int LONG_SIZE = 8;
    private static final int SHIFT_ONE;
    public static final int BYTE_MAX;
    private static final int SHIFT_TWO;
    private static final int SHIFT_THREE;
    private static final int SHIFT_FOUR;
    private static final int SHIFT_FIVE;
    private static final int SHIFT_SIX;
    private static final int SHIFT_SEVEN;
    public static final int SHORT_MAX;
    public static final int INT_MAX;

    static {
        SHIFT_ONE = 8;
        BYTE_MAX = (1 << SHIFT_ONE) - 1;
        SHIFT_TWO = 2 * SHIFT_ONE;
        SHORT_MAX = (1 << SHIFT_TWO) - 1;
        SHIFT_THREE = 3 * SHIFT_ONE;
        INT_MAX = (1 << SHIFT_THREE) - 1;
        SHIFT_FOUR = 4 * SHIFT_ONE;
        SHIFT_FIVE = 5 * SHIFT_ONE;
        SHIFT_SIX = 6 * SHIFT_ONE;
        SHIFT_SEVEN = 7 * SHIFT_ONE;
    }
}
