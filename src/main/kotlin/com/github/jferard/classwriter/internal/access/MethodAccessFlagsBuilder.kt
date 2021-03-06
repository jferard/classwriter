/*
 * ClassWriter - A minimal JVM bytecode writer. Creates classes, methods, interfaces...
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
 * Table 4.6-A. Method access and property flags
 */
class MethodAccessFlagsBuilder {
    private var accessFlags = 0
    /**
     * Declared public; may be accessed from outside its package.
     */
    fun publicFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_PUBLIC
        return this
    }

    /**
     * Declared private; accessible only within the defining class.
     */
    fun privateFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_PRIVATE
        return this
    }

    /**
     * Declared protected; may be accessed within subclasses.
     */
    fun protectedFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_PROTECTED
        return this
    }

    /**
     * Declared static.
     */
    fun staticFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_STATIC
        return this
    }

    /**
     * Declared final; must not be overridden (§5.4.5).
     */
    fun finalFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_FINAL
        return this
    }

    /**
     * Declared synchronized; invocation is wrapped by a monitor use.
     */
    fun synchronizedFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_SYNCHRONIZED
        return this
    }

    /**
     * A bridge method, generated by the compiler.
     */
    fun bridgeFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_BRIDGE
        return this
    }

    /**
     * Declared with variable number of arguments.
     */
    fun varargsFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_VARARGS
        return this
    }

    /**
     * Declared native; implemented in a language other than Java.
     */
    fun nativeFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_NATIVE
        return this
    }

    /**
     * Declared abstract; no implementation is provided.
     */
    fun abstractFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_ABSTRACT
        return this
    }

    /**
     * Declared strictfp; floating-point mode is FP-strict.
     */
    fun strictFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_STRICT
        return this
    }

    /**
     * Declared synthetic; not present in the source code.
     */
    fun syntheticFlag(): MethodAccessFlagsBuilder {
        accessFlags = accessFlags or MethodAccess.ACC_SYNTHETIC
        return this
    }

    fun build(): Int {
        return accessFlags
    }
}