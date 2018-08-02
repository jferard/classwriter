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

package com.github.jferard.classwriter.tool.old.byteviewer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FlagJoiner {
    public static String getAccessFlags(Class<?> clazz, int accessFlags) {
        Field[] fields = clazz.getFields();
        FlagJoiner firstByteJoiner = new FlagJoiner();
        FlagJoiner secondByteJoiner = new FlagJoiner();
        try {
            for (Field field : fields) {
                final int flag = field.getInt(null);
                if ((accessFlags & flag) != 0) {
                    final String fieldName = clazz.getSimpleName() + "." + field.getName();
                    if (flag > 0xff) {
                        firstByteJoiner.add(flag, fieldName);
                    } else {
                        secondByteJoiner.add(flag, fieldName);
                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return String.format("%s, %s, // access flags\n", firstByteJoiner, secondByteJoiner);
    }

    private boolean cast;
    private List<String> fieldNames;

    FlagJoiner() {
        cast = false;
        fieldNames = new ArrayList<>();
    }

    public void add(int flag, String fieldName) {
        if (flag > Byte.MAX_VALUE) this.cast = true;

        this.fieldNames.add(fieldName);
    }

    @Override
    public String toString() {
        if (this.fieldNames.isEmpty()) return "0x00";

        String s = this.fieldNames.stream().collect(Collectors.joining(" | "));
        if (this.cast) {
            if (this.fieldNames.size() == 1) s = "(byte) " + s;
            else s = "(byte) (" + s + ")";
        }
        return s;
    }
}
