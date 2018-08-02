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

package com.github.jferard.classwriter.api;

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;

/**
 * 4.3.2. Field Descriptors
 */
public final class PrimitiveValueType implements ValueType {
    public static PrimitiveValueType integer(String text) {
        return PrimitiveValueType.oneWord(text, VerificationType.INTEGER);
    }

    public static PrimitiveValueType oneWord(String text, VerificationType verificationType) {
        return new PrimitiveValueType(text, 1, verificationType);
    }

    public static PrimitiveValueType twoWords(String text, VerificationType verificationType) {
        return new PrimitiveValueType(text, 2, verificationType);
    }

    private final String text;
    private final int size;
    private VerificationType verificationType;

    public PrimitiveValueType(String text, int size, VerificationType verificationType) {
        this.text = text;
        this.size = size;
        this.verificationType = verificationType;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PrimitiveValueType)) return false;

        PrimitiveValueType other = (PrimitiveValueType) o;
        return other.size == this.size && other.text.equals(this.text);
    }


    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(this.size) + this.text.hashCode();
    }

    public VerificationType getVerificationType() {
        return this.verificationType;
    }

    public byte getAType() {
        switch (text) {
            case "B": return 4;
        }
        return -1;
    }
}
