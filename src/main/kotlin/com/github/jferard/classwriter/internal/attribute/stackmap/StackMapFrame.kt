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

import com.github.jferard.classwriter.encoded.stackmap.*
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.pool.Encodable

/**
 * 4.7.4. The StackMapTable Attribute
 * For every instruction that adds or remove a local variable or a stackItems variable, there
 * must be a stackItems map frame giving the type of the variable.
 *
 * 4.10.1.4. Stack Map Frame Representation
 */
class StackMapFrame(previous: StackMapFrame?, offset: Int,
                    locals: List<VerificationType>,
                    stackItems: List<VerificationType>) :
        Encodable<StackMapFrame, EncodedStackMapFrame, StackMapFrameEncodableWriter> {
    private val previous: StackMapFrame?
    private val locals: List<VerificationType>
    private val stackItems: List<VerificationType>
    private val offset: Int
    val size: Int
        get() = 0

    override fun encode(context: GlobalContext,
                        codeContext: MethodContext): EncodedStackMapFrame {
        val encodedFrame: EncodedStackMapFrame
        val offsetDelta = computeOffsetDelta()
        encodedFrame = if (locals == previous!!.locals) {
            sameLocalsFrame(context, offsetDelta)
        } else {
            differentLocalsFrame(context, offsetDelta)
        }
        return encodedFrame
    }

    private fun computeOffsetDelta(): Int {
        return if (previous!!.isFirst) offset - previous.offset else offset - previous.offset + 1
    }

    private val isFirst: Boolean
        get() = previous == null

    private fun sameLocalsFrame(context: GlobalContext, offsetDelta: Int): EncodedStackMapFrame {
        return when {
            stackItems.isEmpty() -> {
                sameFrame(offsetDelta)
            }
            stackItems.size == 1 -> {
                sameLocals1StackItemFrame(context, offsetDelta)
            }
            else -> {
                fullFrame(context, offsetDelta)
            }
        }
    }

    private fun sameFrame(offsetDelta: Int): EncodedStackMapFrame {
        return if (offsetDelta < MAX_SAME) {
            EncodedSameFrame(offsetDelta)
        } else {
            EncodedSameFrameExtended(offsetDelta)
        }
    }

    fun sameLocals1StackItemFrame(context: GlobalContext,
                                  offsetDelta: Int): EncodedStackMapFrame {
        val encodedFirstStackItemVerificationType: EncodedVerificationType = stackItems[0]
                .encode(context, MethodContext.create(0))
        return if (offsetDelta < MAX_SAME) {
            EncodedSameLocals1StackItemFrame(offsetDelta + 64,
                    encodedFirstStackItemVerificationType)
        } else {
            EncodedSameLocals1StackItemFrameExtended(offsetDelta,
                    encodedFirstStackItemVerificationType)
        }
    }

    fun fullFrame(context: GlobalContext, offsetDelta: Int): EncodedStackMapFrame {
        val encodedLocals: List<EncodedVerificationType> =
                locals.map { t: VerificationType ->
                    t.encode(context, MethodContext.create(0))
                }
        val encodedStackItems: List<EncodedVerificationType> =
                stackItems.map { t: VerificationType ->
                    t.encode(context, MethodContext.create(0))
                }
        return EncodedFullFrame(offsetDelta, encodedLocals, encodedStackItems)
    }

    fun differentLocalsFrame(context: GlobalContext, offsetDelta: Int): EncodedStackMapFrame {
        val encodedFrame: EncodedStackMapFrame
        encodedFrame = if (stackItems.isEmpty()) {
            val size = locals.size
            val previousSize = previous!!.locals.size
            val k = size - previousSize
            if (k > 0 && previous.locals == locals.subList(0, previousSize)) {
                appendFrame(context, offsetDelta, size, previousSize, k)
            } else if (k < 0 && locals == previous.locals.subList(0, size)) {
                EncodedChopFrame(-k, offsetDelta)
            } else {
                fullFrame(context, offsetDelta)
            }
        } else {
            fullFrame(context, offsetDelta)
        }
        return encodedFrame
    }

    private fun appendFrame(context: GlobalContext, offsetDelta: Int, size: Int,
                            previousSize: Int, k: Int): EncodedStackMapFrame {
        val encodedFrame: EncodedStackMapFrame
        val encodedNewTypes: List<EncodedVerificationType> =
                locals.subList(previousSize, size).map { t: VerificationType ->
                    t.encode(context, MethodContext.create(0))
                }
        encodedFrame = EncodedAppendFrame(k, offsetDelta, encodedNewTypes)
        return encodedFrame
    }

    companion object {
        private const val MAX_SAME = 64
    }

    init {
        require(offset > previous!!.offset)
        this.previous = previous
        this.offset = offset
        this.locals = locals
        this.stackItems = stackItems
    }

    override fun write(encodableWriter: StackMapFrameEncodableWriter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}