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
package com.github.jferard.classwriter.pool

import com.github.jferard.classwriter.api.EncodedWriter
import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * An element that can be encoded.
 *
 * @param <E> type of the element (= Self)
 * @param <F> type of the encoded element
 * @param <W> type of the writer of the element
 */
interface Encodable<E, out F, in W> where E : Encodable<E, F, W>, W: EncodableWriter {
    /**
     * Writes this
     * @param encodedWriter the writer
     */
    fun write(encodableWriter: W)

    /**
     * Return an encoded element, ie Strings are replaced by indices, etc.
     *
     * @param context the global context
     * @param codeContext the method context
     * @return the encoded element
     */
    fun encode(context: GlobalContext, codeContext: MethodContext): F
}