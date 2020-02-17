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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.attribute.LocalVariableTypeTable
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

class EncodedLocalVariableTypeTable(private val startPc: Int, private val length: Int,
                                    private val nameIndex: Int,
                                    private val signatureIndex: Int,
                                    private val index: Int) :
        Encoded<LocalVariableTypeTable, EncodedLocalVariableTypeTable, CodeAttributeAttributeEncodedWriter> {
    override fun write(
            writableFactory: CodeAttributeAttributeEncodedWriter) {
        return writableFactory
                .localVariableTypeTable(startPc, length, nameIndex, signatureIndex,
                        index)
    }

    override fun getSize(pos: Int): Int = 5 * BytecodeHelper.SHORT_SIZE

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): LocalVariableTypeTable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}