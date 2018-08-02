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
public class ClassAccessFlagsBuilder {
    private int accessFlags;

    /**
     * Declared public; may be accessed from outside its package.
     */
    public ClassAccessFlagsBuilder publicFlag() {
        this.accessFlags |= ClassAccess.ACC_PUBLIC;
        return this;
    }

    /**
     * Declared final; no subclasses allowed.
     */
    public ClassAccessFlagsBuilder finalFlag() {
        this.accessFlags |= ClassAccess.ACC_FINAL;
        return this;
    }

    /**
     * Treat superclass methods specially when invoked by the invokespecial instruction.
     */
    public ClassAccessFlagsBuilder superFlag() {
        this.accessFlags |= ClassAccess.ACC_SUPER;
        return this;
    }

    /**
     * Is an interface, not a class.
     */
    public ClassAccessFlagsBuilder interfaceFlag() {
        this.accessFlags |= ClassAccess.ACC_INTERFACE;
        return this;
    }

    /**
     * Declared abstract; must not be instantiated.
     */
    public ClassAccessFlagsBuilder abstractFlag() {
        this.accessFlags |= ClassAccess.ACC_ABSTRACT;
        return this;
    }

    /**
     * Declared synthetic; not present in the source code.
     */
    public ClassAccessFlagsBuilder syntheticFlag() {
        this.accessFlags |= ClassAccess.ACC_SYNTHETIC;
        return this;
    }

    /**
     * Declared as an annotation type.
     */
    public ClassAccessFlagsBuilder annotationFlag() {
        this.accessFlags |= ClassAccess.ACC_ANNOTATION;
        return this;
    }

    /**
     * Declared as an enum type.
     */
    public ClassAccessFlagsBuilder enumFlag() {
        this.accessFlags |= ClassAccess.ACC_ENUM;
        return this;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }
}
