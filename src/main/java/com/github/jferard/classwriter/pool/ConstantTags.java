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

public class ConstantTags {
    public static final short CLASS = 7;
    public static final short FIELDREF = 9;
    public static final short METHODREF = 10;
    public static final short INTERFACEMETHODREF = 11;
    public static final short STRING = 8;
    public static final short INTEGER = 3;
    public static final short FLOAT = 4;
    public static final short LONG = 5;
    public static final short DOUBLE = 6;
    public static final short NAMEANDTYPE = 12;
    public static final short UTF8 = 1;
    public static final short METHODHANDLE = 15;
    public static final short METHODTYPE = 16;
    public static final short INVOKEDYNAMIC = 18;
}
