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
package com.github.jferard.classwriter.bytecode

import com.github.jferard.classwriter.encoded.stackmap.EncodedByteVerificationType
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodableWriter
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * top, integer, float, null, uninitialized_this, long, double
 */
class ByteVerificationType(private val code: Int, private val parent: VerificationType?) :
        VerificationType {
    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedVerificationType {
        return EncodedByteVerificationType(code)
    }

    override val isLong: Boolean = false

    override fun isAssignable(expectedType: VerificationType): Boolean {
        return this == expectedType ||
                parent != null && parent.isAssignable(expectedType)
    }

    override fun write(encodableWriter: VerificationTypeEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) {
            return true
        }
        if (o !is ByteVerificationType) {
            return false
        }
        return code == o.code
    }
}