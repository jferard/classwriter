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

/**
 * 5.4.3.5. Method Type and Method Handle Resolution
 */
public class MethodHandleKind {
    /**
     * getfield C.f:T
     */
    public static final int GET_FIELD = 1;
    /**
     * getstatic C.f:T
     */
    public static final int GET_STATIC = 2;
    /**
     * putfield C.f:T
     */
    public static final int PUT_FIELD = 3;
    /**
     * putstatic C.f:T
     */
    public static final int PUT_STATIC = 4;
    /**
     * invokevirtual C.m:(A*)T
     */
    public static final int INVOKE_VIRTUAL = 5;
    /**
     * invokestatic C.m:(A*)T
     */
    public static final int INVOKE_STATIC = 6;
    /**
     * invokespecial C.m:(A*)T
     */
    public static final int INVOKE_SPECIAL = 7;
    /**
     * new C; dup; invokespecial C.<init>:(A*)V
     */
    public static final int NEW_INVOKE_SPECIAL = 8;
    /**
     * invokeinterface C.m:(A*)T
     */
    public static final int INVOKE_INTERFACE = 9;

    public static String toString(int kind) {
        switch (kind) {
            case GET_FIELD:
                return "Getfield";
            case GET_STATIC:
                return "Getstatic";
            case PUT_FIELD:
                return "Putfield";
            case PUT_STATIC:
                return "Putstatic";
            case INVOKE_VIRTUAL:
                return "Invokevirtual";
            case INVOKE_STATIC:
                return "Invokestatic";
            case INVOKE_SPECIAL:
                return "Invokespecial";
            case NEW_INVOKE_SPECIAL:
                return "Newinvokespecial";
            case INVOKE_INTERFACE:
                return "Invokeinterface";
            default:
                throw new IllegalArgumentException("Bad kind: " + kind);
        }
    }
}
