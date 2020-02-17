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
package com.github.jferard.classwriter.pool

/**
 * 5.4.3.5. Method Type and Method Handle Resolution
 */
object MethodHandleKind {
    /**
     * getfield C.f:T
     */
    const val GET_FIELD = 1
    /**
     * getstatic C.f:T
     */
    const val GET_STATIC = 2
    /**
     * putfield C.f:T
     */
    const val PUT_FIELD = 3
    /**
     * putstatic C.f:T
     */
    const val PUT_STATIC = 4
    /**
     * invokevirtual C.m:(A*)T
     */
    const val INVOKE_VIRTUAL = 5
    /**
     * invokestatic C.m:(A*)T
     */
    const val INVOKE_STATIC = 6
    /**
     * invokespecial C.m:(A*)T
     */
    const val INVOKE_SPECIAL = 7
    /**
     * new C; dup; invokespecial C.<init>:(A*)V
    </init> */
    const val NEW_INVOKE_SPECIAL = 8
    /**
     * invokeinterface C.m:(A*)T
     */
    const val INVOKE_INTERFACE = 9

    fun toString(kind: Int): String {
        return when (kind) {
            GET_FIELD -> "Getfield"
            GET_STATIC -> "Getstatic"
            PUT_FIELD -> "Putfield"
            PUT_STATIC -> "Putstatic"
            INVOKE_VIRTUAL -> "Invokevirtual"
            INVOKE_STATIC -> "Invokestatic"
            INVOKE_SPECIAL -> "Invokespecial"
            NEW_INVOKE_SPECIAL -> "Newinvokespecial"
            INVOKE_INTERFACE -> "Invokeinterface"
            else -> throw IllegalArgumentException("Bad kind: $kind")
        }
    }
}