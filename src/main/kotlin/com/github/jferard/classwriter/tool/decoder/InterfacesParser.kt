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
package com.github.jferard.classwriter.tool.decoder

import com.github.jferard.classwriter.writer.encoded.ClassEncodedWriter
import com.github.jferard.classwriter.encoded.EncodedInterfaces
import java.io.DataInput
import java.io.IOException
import java.util.*

/**
 * 4.1. The ClassFile Structure
 * u2             interfaces_count;
 * u2             interfaces[interfaces_count];
 */
class InterfacesParser :
        Parser<EncodedInterfaces> {
    @Throws(IOException::class)
    override fun parse(input: DataInput): EncodedInterfaces {
        val interfacesCount = input.readShort().toInt()
        val encodedInterfaces: MutableList<Int> =
                ArrayList(interfacesCount)
        for (i in 0 until interfacesCount) {
            encodedInterfaces.add(input.readShort().toInt())
        }
        return EncodedInterfaces(encodedInterfaces)
    }
}