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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

/**
 * 4.3.2. Field Descriptors
 */
class PrimitiveValueType(private val text: String, override val size: Int,
                         override val verificationType: VerificationType) : ValueType {
    override fun toString(): String {
        return text!!
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is PrimitiveValueType) return false
        val other = o as PrimitiveValueType
        return other!!.size == size && other.text == text
    }

    override fun hashCode(): Int {
        return 31 * Integer.hashCode(size) + text.hashCode()
    }

    val aType: Byte
        get() {
            when (text) {
                "B" -> return 4
            }
            return -1
        }

    companion object {
        fun integer(text: String): PrimitiveValueType {
            return oneWord(text, VerificationType.INTEGER)
        }

        fun oneWord(text: String,
                    verificationType: VerificationType): PrimitiveValueType {
            return PrimitiveValueType(text, 1, verificationType)
        }

        fun twoWords(text: String,
                     verificationType: VerificationType): PrimitiveValueType {
            return PrimitiveValueType(text, 2, verificationType)
        }
    }

}