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

package com.github.jferard.classwriter.tool.parser

import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeEncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedUnitializedVerificationType(private val offset: Int) : EncodedVerificationType {
    override fun write(encodedWriter: VerificationTypeEncodedWriter) {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): VerificationType {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSize(pos: Int): Int = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.

}
