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
package com.github.jferard.classwriter.internal.access


/**
 * Table 4.5-A. Field access and property flags
 */
class FieldAccessFlagsBuilder {
    private var accessFlags = 0
    /**
     * Declared public; may be accessed from outside its package.
     */
    fun publicFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_PUBLIC
        return this
    }

    /**
     * Declared private; usable only within the defining class.
     */
    fun privateFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_PRIVATE
        return this
    }

    /**
     * Declared protected; may be accessed within subclasses.
     */
    fun protectedFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_PROTECTED
        return this
    }

    /**
     * Declared static.
     */
    fun staticFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_STATIC
        return this
    }

    /**
     * Declared final; never directly assigned to after object construction (JLS §17.5).
     */
    fun finalFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_FINAL
        return this
    }

    /**
     * Declared volatile; cannot be cached.
     */
    fun volatileFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_VOLATILE
        return this
    }

    /**
     * Declared transient; not written or read by a persistent object manager.
     */
    fun transientFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_TRANSIENT
        return this
    }

    /**
     * Declared synthetic; not present in the source code.
     */
    fun syntheticFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_SYNTHETIC
        return this
    }

    /**
     * Declared as an element of an enum.
     */
    fun enumFlag(): FieldAccessFlagsBuilder {
        accessFlags =
                accessFlags or FieldAccess.ACC_ENUM
        return this
    }
}