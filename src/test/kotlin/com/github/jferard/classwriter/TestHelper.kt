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
package com.github.jferard.classwriter

import org.junit.jupiter.api.Assertions
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.function.IntFunction
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.experimental.and

object TestHelper {
    @Throws(IOException::class)
    fun assertWritableEquals(bytes: ByteArray,
                             writable: Writable<DataOutputStream?>) {
        val out = ByteArrayOutputStream()
        writable.write(DataOutputStream(out))
        Assertions.assertEquals(toList(bytes), toList(out.toByteArray()))
        Assertions.assertArrayEquals(bytes, out.toByteArray())
    }

    private fun toList(bytes: ByteArray): List<Byte> {
        return IntStream.range(0, bytes.size).mapToObj { i: Int -> bytes[i] }
                .collect(
                        Collectors.toList())
    }

    fun toBytes(opcodes: IntArray): ByteArray {
        val bytes = ByteArray(opcodes.size)
        for (i in opcodes.indices) {
            bytes[i] = opcodes[i].toByte()
        }
        return bytes
    }

    @Throws(IOException::class)
    fun toByteArrayRepresentationWithText(
            writable: Writable<DataOutputStream>): String {
        val bytes = getBytes(writable)
        return mapBytesAndJoin(bytes, IntFunction { i: Int ->
            val b: Int = (bytes[i] and 0xff.toByte()).toInt()
            if (b >= 32) String.format("'%c'", b.toChar()) else String.format(
                    "0x%02X", b)
        })
    }

    @Throws(IOException::class)
    fun toByteArrayRepresentation(writable: Writable<DataOutputStream>): String {
        val bytes = getBytes(writable)
        return mapBytesAndJoin(bytes, IntFunction { i: Int -> String.format("0x%02X", bytes[i]) })
    }

    private fun mapBytesAndJoin(bytes: ByteArray,
                                mapper: IntFunction<String>): String {
        return "{" + IntStream.range(0, bytes.size).mapToObj(mapper)
                .collect(Collectors.joining(", ")) + "}"
    }

    @Throws(IOException::class)
    private fun getBytes(writable: Writable<DataOutputStream>): ByteArray {
        val out = ByteArrayOutputStream()
        writable.write(DataOutputStream(out))
        return out.toByteArray()
    }
}