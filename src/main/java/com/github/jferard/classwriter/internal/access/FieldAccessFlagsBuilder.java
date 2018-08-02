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
 * Table 4.5-A. Field access and property flags
 */
public class FieldAccessFlagsBuilder {
    private int accessFlags;

    /**
     * Declared public; may be accessed from outside its package.
     */
    public FieldAccessFlagsBuilder publicFlag() {
        this.accessFlags |= FieldAccess.ACC_PUBLIC;
        return this;
    }

    /**
     * Declared private; usable only within the defining class.
     */
    public FieldAccessFlagsBuilder privateFlag() {
        this.accessFlags |= FieldAccess.ACC_PRIVATE;
        return this;
    }

    /**
     * Declared protected; may be accessed within subclasses.
     */
    public FieldAccessFlagsBuilder protectedFlag() {
        this.accessFlags |= FieldAccess.ACC_PROTECTED;
        return this;
    }

    /**
     * Declared static.
     */
    public FieldAccessFlagsBuilder staticFlag() {
        this.accessFlags |= FieldAccess.ACC_STATIC;
        return this;
    }

    /**
     * Declared final; never directly assigned to after object construction (JLS §17.5).
     */
    public FieldAccessFlagsBuilder finalFlag() {
        this.accessFlags |= FieldAccess.ACC_FINAL;
        return this;
    }

    /**
     * Declared volatile; cannot be cached.
     */
    public FieldAccessFlagsBuilder volatileFlag() {
        this.accessFlags |= FieldAccess.ACC_VOLATILE;
        return this;
    }

    /**
     * Declared transient; not written or read by a persistent object manager.
     */
    public FieldAccessFlagsBuilder transientFlag() {
        this.accessFlags |= FieldAccess.ACC_TRANSIENT;
        return this;
    }

    /**
     * Declared synthetic; not present in the source code.
     */
    public FieldAccessFlagsBuilder syntheticFlag() {
        this.accessFlags |= FieldAccess.ACC_SYNTHETIC;
        return this;
    }

    /**
     * Declared as an element of an enum.
     */
    public FieldAccessFlagsBuilder enumFlag() {
        this.accessFlags |= FieldAccess.ACC_ENUM;
        return this;
    }
}
