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
package com.github.jferard.classwriter.encoded

import com.github.jferard.classwriter.Field
import com.github.jferard.classwriter.writer.encoded.FieldEncodedWriter
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * ```
 *     u2             fields_count;
 *     field_info     fields[fields_count];
 * ``` *
 */
class EncodedFields(private val encodedFields: List<EncodedField>) :
        Encoded<List<Field>, EncodedFields, FieldEncodedWriter> {
    override fun write(encodedWriter: FieldEncodedWriter) {
        return encodedWriter.fields(encodedFields)
    }

    override val size: Int
        get() = BytecodeHelper.SHORT_SIZE + encodedFields.map(EncodedField::size).sum()

    override fun toString(): String {
        return "Fields[\n${encodedFields.joinToString("\n")}\n]"
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): List<Field> {
        return encodedFields.map { f -> f.decode(context, codeContext) }
    }

}