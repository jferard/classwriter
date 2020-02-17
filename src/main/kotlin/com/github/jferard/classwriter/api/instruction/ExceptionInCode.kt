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
package com.github.jferard.classwriter.api.instruction
import com.github.jferard.classwriter.api.Label
import com.github.jferard.classwriter.api.PlainClassRef
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.ClassEntry
import com.github.jferard.classwriter.pool.Encodable
import com.github.jferard.classwriter.writer.encodable.MethodEncodableWriter

class ExceptionInCode(private val classRef: PlainClassRef,
                      private val label: Label,
                      private val startOffset: Int, private val endOffset: Int) :
        Encodable<ExceptionInCode, EncodedExceptionInCode, MethodEncodableWriter> {

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedExceptionInCode {
        val classIndex: Int =
                context.addToPool(ClassEntry(classRef))
        val handlerOffset: Int = codeContext.getLabelOffset(label)
        return EncodedExceptionInCode(startOffset, endOffset, handlerOffset, classIndex)
    }

    companion object {
        fun builder(classRef: PlainClassRef, label: Label,
                    startOffset: Int): ExceptionInCodeBuilder {
            return ExceptionInCodeBuilder(classRef, label, startOffset)
        }
    }

    override fun write(encodableWriter: MethodEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}