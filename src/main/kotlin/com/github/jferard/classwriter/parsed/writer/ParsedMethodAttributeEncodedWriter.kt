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
package com.github.jferard.classwriter.parsed.writer

import com.github.jferard.classwriter.encoded.attribute.EncodedAnnotation
import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedExceptionInCode
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.tool.decoder.EncodedInstructions
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import com.github.jferard.classwriter.writer.encoded.MethodAttributeEncodedWriter
import java.io.Writer

class ParsedMethodAttributeEncodedWriter(private val output: Writer,
                                         private val parsedInstructionFactory: ParsedInstructionEncodedWriter,
                                         private val parsedMethodWritableFactory: ParsedMethodEncodedWriter) :
        MethodAttributeEncodedWriter {
    override fun codeAttribute(attributeNameIndex: Int, maxStack: Int, maxLocals: Int,
                               encodedCode: EncodedInstruction,
                               encodedExceptions: List<EncodedExceptionInCode>,
                               encodedAttributes: List<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>>) {
        TODO("not implemented")
    }

    override fun annotationAttribute(annotationsNameIndex: Int,
                                     encodedAnnotations: List<EncodedAnnotation>) {
        TODO("not implemented")
    }

    companion object {
        fun create(output: Writer): ParsedMethodAttributeEncodedWriter {
            return ParsedMethodAttributeEncodedWriter(
                    output,
                    ParsedInstructionEncodedWriter(),
                    ParsedMethodEncodedWriter.create(output))
        }
    }

}