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
package com.github.jferard.classwriter.encoded.stackmap

import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedMockVerificationType(private val isLong: Boolean) :
        EncodedVerificationType {
    override fun write(encodedWriter: VerificationTypeEncodedWriter) {
        TODO("Not yet implemented")
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): VerificationType {
        TODO("Not yet implemented")
    }

    override fun getSize(pos: Int): Int = if (isLong) {
        2 * com.github.jferard.classwriter.bytecode.BytecodeHelper.BYTE_SIZE
    } else {
        com.github.jferard.classwriter.bytecode.BytecodeHelper.BYTE_SIZE
    }
}