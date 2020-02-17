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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.api.instruction.ExceptionInCode
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter

/**
 * 4.7.3. The Code Attribute > exception_table[]
 */
class EncodedExceptionInCode(private val startPc: Int, private val endPc: Int,
                             private val handlerPc: Int,
                             private val catchTypeIndex: Int) :
        Encoded<ExceptionInCode, EncodedExceptionInCode, MethodAttributeEncodedWriter> {
    override fun write(encodedWriter: MethodAttributeEncodedWriter) {
        return encodedWriter.exceptionInCode(startPc, endPc, handlerPc, catchTypeIndex)
    }

    override fun getSize(pos: Int): Int = 4 * BytecodeHelper.SHORT_SIZE

    override fun decode(context: GlobalContext, codeContext: MethodContext): ExceptionInCode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}