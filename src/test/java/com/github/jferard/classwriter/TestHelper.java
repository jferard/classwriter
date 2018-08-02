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

package com.github.jferard.classwriter;

import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestHelper {
    public static void assertWritableEquals(byte[] bytes, Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writable.write(new DataOutputStream(out));
        Assertions.assertEquals(toList(bytes), toList(out.toByteArray()));
        Assertions.assertArrayEquals(bytes, out.toByteArray());
    }

    private static List<Byte> toList(byte[] bytes) {
        return IntStream.range(0, bytes.length).mapToObj(i->bytes[i]).collect(Collectors.toList());
    }

    public static byte[] toBytes(int[] opcodes) {
        byte[] bytes = new byte[opcodes.length];
        for (int i = 0; i < opcodes.length; i++) {
            bytes[i] = (byte) opcodes[i];
        }
        return bytes;
    }

    public static String toByteArrayRepresentationWithText(Writable writable) throws IOException {
        byte[] bytes = getBytes(writable);
        return mapBytesAndJoin(bytes, i -> {
            int b = bytes[i] & 0xff;
            return b >= 32 ? String.format("'%c'", (char) b) : String.format("0x%02X", b);
        });
    }

    public static String toByteArrayRepresentation(Writable writable) throws IOException {
        byte[] bytes = getBytes(writable);
        return mapBytesAndJoin(bytes, i -> String.format("0x%02X", bytes[i]));
    }

    private static String mapBytesAndJoin(byte[] bytes, IntFunction<String> mapper) {
        return "{" + IntStream.range(0, bytes.length).mapToObj(mapper)
                .collect(Collectors.joining(", ")) + "}";
    }

    private static byte[] getBytes(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writable.write(new DataOutputStream(out));
        return out.toByteArray();
    }
}
