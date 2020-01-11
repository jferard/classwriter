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
package com.github.jferard.classwriter.tool.byteviewer

import java.util.*
import java.util.stream.Collectors

internal class FlagJoiner {
    private var cast = false
    private val fieldNames: MutableList<String>
    fun add(flag: Int, fieldName: String) {
        if (flag > Byte.MAX_VALUE) cast = true
        fieldNames.add(fieldName)
    }

    override fun toString(): String {
        if (fieldNames.isEmpty()) return "0x00"
        var s = fieldNames.stream().collect(Collectors.joining(" | "))
        if (cast) {
            s = if (fieldNames.size == 1) "(byte) $s" else "(byte) ($s)"
        }
        return s
    }

    companion object {
        fun getAccessFlags(clazz: Class<*>, accessFlags: Int): String {
            val fields = clazz.fields
            val firstByteJoiner = FlagJoiner()
            val secondByteJoiner = FlagJoiner()
            try {
                for (field in fields) {
                    val flag = field.getInt(null)
                    if (accessFlags and flag != 0) {
                        val fieldName = clazz.simpleName + "." + field.name
                        if (flag > 0xff) {
                            firstByteJoiner.add(flag, fieldName)
                        } else {
                            secondByteJoiner.add(flag, fieldName)
                        }
                    }
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            return String.format("%s, %s, // access flags\n", firstByteJoiner,
                    secondByteJoiner)
        }
    }

    init {
        fieldNames = ArrayList()
    }
}