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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.ClassEncodableWriter
import com.github.jferard.classwriter.writer.encoded.ClassEncodedWriter

class Header(private val minorVersion: Int, private val majorVersion: Int) :
        Encoded<Header, Header, ClassEncodedWriter>, Encodable<Header, Header, ClassEncodableWriter> {
    override fun write(encodedWriter: ClassEncodedWriter) {
        encodedWriter.header(minorVersion, majorVersion)
    }

    override fun write(encodableWriter: ClassEncodableWriter) {
        encodableWriter.header(minorVersion, majorVersion)
    }

    override fun getSize(pos: Int): Int = 2 * BytecodeHelper.SHORT_SIZE

    override fun toString(): String {
        return String.format("Header %s-%s", minorVersion, majorVersion)
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): Header {
        return this
    }

    override fun encode(context: GlobalContext, codeContext: MethodContext): Header {
        return this
    }

}