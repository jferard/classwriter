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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.parsed.Decoded
import java.io.IOException
import java.io.Writer

/** ```
 * u2             constant_pool_count;
 * cp_info        constant_pool[constant_pool_count-1];
 * ```
 */
class DecodedConstantPool(private val writableEntries: List<Decoded>) :
        Decoded {
    @Throws(IOException::class)
    override fun write(output: Writer) {
        output.append("/* CONSTANT POOL */\n")
        // viewEntryCount(output, writableEntries.size)
        for (i in 1 until writableEntries.size) {
            val entry = writableEntries[i]
            if (entry != null) {
                output.append(String.format("/* %4s */", "#$i"))
                entry.write(output)
                output.append('\n')
            }
        }
    }


}