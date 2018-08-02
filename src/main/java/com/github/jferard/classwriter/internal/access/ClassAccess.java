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

package com.github.jferard.classwriter.internal.access;

/**
 * Table 4.1-B. Class access and property modifiers
 */
public class ClassAccess {
    public static final int ACC_PUBLIC = AccessFlags.ACC_PUBLIC;
    public static final int ACC_FINAL = AccessFlags.ACC_FINAL;
    public static final int ACC_SUPER = AccessFlags.ACC_SUPER;
    public static final int ACC_INTERFACE = AccessFlags.ACC_INTERFACE;
    public static final int ACC_ABSTRACT = AccessFlags.ACC_ABSTRACT;
    public static final int ACC_SYNTHETIC = AccessFlags.ACC_SYNTHETIC;
    public static final int ACC_ANNOTATION = AccessFlags.ACC_ANNOTATION;
    public static final int ACC_ENUM = AccessFlags.ACC_ENUM;

    public static ClassAccessFlagsBuilder builder() {
        return new ClassAccessFlagsBuilder();
    }
}

