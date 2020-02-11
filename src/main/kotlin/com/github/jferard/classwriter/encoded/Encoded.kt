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

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.Writable
import com.github.jferard.classwriter.api.EncodedWriter
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext

/**
 * @param <F> the factory that produces the writable
</F> */
interface Encoded<out E, F, in W> : Sized where F : Encoded<E, F, W>, W: EncodedWriter {
    /**
     * Writes this
     * @param encodedWriter the writer
     */
    fun write(encodedWriter: W)

    /**
     * Return the decoded version of this
     */
    fun decode(context: GlobalContext,
               codeContext: MethodContext): E
}