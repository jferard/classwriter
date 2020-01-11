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


internal object AccessFlags {
    /**
     * Declared public; may be accessed from outside its package.
     */
    const val ACC_PUBLIC = 0x0001
    /**
     * Declared private; usable only within the defining class.
     */
    const val ACC_PRIVATE = 0x0002
    /**
     * Declared protected; may be accessed within subclasses.
     */
    const val ACC_PROTECTED = 0x0004
    /**
     * Declared static.
     */
    const val ACC_STATIC = 0x0008
    /**
     * Declared final;
     * Class: no subclasses allowed.
     * Field/Method: never directly assigned to after object construction (JLS §17.5).
     */
    const val ACC_FINAL = 0x0010
    /**
     * Treat superclass methods specially when invoked by the invokespecial instruction.
     */
    const val ACC_SUPER = 0x0020
    /**
     * Declared synchronized; invocation is wrapped by a monitor use.
     */
    const val ACC_SYNCHRONIZED = 0x0020
    /**
     * Declared volatile; cannot be cached.
     */
    const val ACC_VOLATILE = 0x0040
    /**
     * A bridge method, generated by the compiler.
     */
    const val ACC_BRIDGE = 0x0040
    /**
     * Declared transient; not written or read by a persistent object manager.
     */
    const val ACC_TRANSIENT = 0x0080
    /**
     * Declared with variable number of arguments.
     */
    const val ACC_VARARGS = 0x0080
    /**
     * Declared native; implemented in a language other than Java.
     */
    const val ACC_NATIVE = 0x0100
    /**
     * Is an interface, not a class.
     */
    const val ACC_INTERFACE = 0x0200
    /**
     * Declared abstract; must not be instantiated.
     */
    const val ACC_ABSTRACT = 0x0400
    /**
     * Declared strictfp; floating-point mode is FP-strict.
     */
    const val ACC_STRICT = 0x0800
    /**
     * Declared synthetic; not present in the source code.
     */
    const val ACC_SYNTHETIC = 0x1000
    /**
     * Declared as an annotation type.
     */
    const val ACC_ANNOTATION = 0x2000
    /**
     * Declared as an enum type.
     */
    const val ACC_ENUM = 0x4000
}