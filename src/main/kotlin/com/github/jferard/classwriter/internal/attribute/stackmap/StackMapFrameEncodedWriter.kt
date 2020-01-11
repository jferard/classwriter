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
package com.github.jferard.classwriter.internal.attribute.stackmap

import com.github.jferard.classwriter.api.EncodedWriter
import com.github.jferard.classwriter.encoded.stackmap.EncodedStackMapFrame
import com.github.jferard.classwriter.encoded.stackmap.EncodedVerificationType

interface StackMapFrameEncodedWriter : EncodedWriter {
    fun chopFrame(k: Int, offsetDelta: Int)
    fun sameFrame(offsetDelta: Int)
    fun fullFrame(offsetDelta: Int,
                  encodedLocals: List<EncodedVerificationType>,
                  encodedStackItems: List<EncodedVerificationType>)

    fun appendFrame(k: Int, offsetDelta: Int,
                    encodedNewTypes: List<EncodedVerificationType>)

    fun localsSame1StackItemFrame(offsetDelta: Int,
                                  encodedFirstStackItemVerificationType: EncodedVerificationType)

    fun localsSame1StackItemFrameExtended(offsetDelta: Int,
                                          encodedFirstStackItemVerificationType: EncodedVerificationType)

    fun sameFrameExtended(offsetDelta: Int)
}