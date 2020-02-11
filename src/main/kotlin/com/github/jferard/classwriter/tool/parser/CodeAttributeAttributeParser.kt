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
package com.github.jferard.classwriter.tool.parser

import com.github.jferard.classwriter.encoded.attribute.EncodedCodeAttributeAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedLineNumberTableAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTable
import com.github.jferard.classwriter.encoded.attribute.EncodedLocalVariableTypeTable
import com.github.jferard.classwriter.encoded.pool.EncodedConstantPoolEntry
import com.github.jferard.classwriter.encoded.stackmap.*
import com.github.jferard.classwriter.internal.attribute.PositionAndLineNumber
import com.github.jferard.classwriter.internal.attribute.StackMapTableAttribute
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationTypeConstants
import com.github.jferard.classwriter.writer.encoded.CodeAttributeAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException
import java.util.*

class CodeAttributeAttributeParser(
        val entries: List<EncodedConstantPoolEntry>) :
        Parser<EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter>> {
    @Throws(IOException::class)
    override fun parse(
            input: DataInput): EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter> {
        val attributeNameIndex = input.readUnsignedShort()
        val attributeName = entries[attributeNameIndex - 1].utf8Text()
        return when (attributeName) {
            StackMapTableAttribute.STACK_MAP_TABLE_NAME -> parseStackMapTableAttribute(
                    attributeNameIndex, input)
            "LineNumberTable" -> decodeLineNumberTable(attributeNameIndex, input)
            "LocalVariableTable" -> decodeLocalVariableTable(attributeNameIndex, input)
            "LocalVariableTypeTable" -> decodeLocalVariableTypeTable(attributeNameIndex,
                    input)
            else -> throw IllegalArgumentException(attributeName)
        }
    }

    /** 4.7.4. The StackMapTable Attribute */
    private fun parseStackMapTableAttribute(attributeNameIndex: Int,
                                            input: DataInput): EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter> {
        val length = input.readInt()
        val numberOfEntries = input.readShort()
        val encodedStackMapFrames = (1..numberOfEntries).map { parseStackMapTableEntry(input) }
        return EncodedStackMapTableAttribute(attributeNameIndex, encodedStackMapFrames)
    }

    /**
     * ```
     * union stack_map_frame {
     *      same_frame;
     *      same_locals_1_stack_item_frame;
     *      same_locals_1_stack_item_frame_extended;
     *      chop_frame;
     *      same_frame_extended;
     *      append_frame;
     *      full_frame;
     * }
     * ```
     */
    private fun parseStackMapTableEntry(input: DataInput): EncodedStackMapFrame {
        return when (val tag = input.readUnsignedByte()) {
            in 0..63 -> EncodedSameFrame(tag)
            in 64..127 -> EncodedSameLocals1StackItemFrame(tag, parseVerificationType(input))
            in 128..246 -> throw IllegalArgumentException("Future use")
            StackMapFrameConstants.SAME_LOCALS_1_STACK_ITEM_EXTENDED -> {
                val offsetDelta = input.readShort()
                val verificationType = parseVerificationType(input)
                EncodedSameLocals1StackItemFrameExtended(offsetDelta.toInt(), verificationType)
            }
            in 248..250 -> {
                val offsetDelta = input.readShort()
                EncodedChopFrame(tag, offsetDelta.toInt())
            }
            StackMapFrameConstants.SAME_FRAME_EXTENDED -> {
                val offsetDelta = input.readShort()
                EncodedSameFrameExtended(offsetDelta.toInt())
            }
            in 252..254 -> {
                val k = tag - StackMapFrameConstants.SAME_FRAME_EXTENDED
                val offsetDelta = input.readShort()
                val verificationTypes = (1..k).map { parseVerificationType(input) }
                EncodedAppendFrame(tag, offsetDelta.toInt(), verificationTypes)
            }
            StackMapFrameConstants.FULL_FRAME -> {
                val offsetDelta = input.readShort()
                val numberOfLocals = input.readShort()
                val encodedLocals = (1..numberOfLocals).map { parseVerificationType(input) }
                val numberOfStackItems = input.readShort()
                val encodedStackItems = (1..numberOfStackItems).map { parseVerificationType(input) }
                EncodedFullFrame(offsetDelta.toInt(), encodedLocals, encodedStackItems)
            }
            else -> throw IllegalArgumentException("Should not happen $tag")
        }
    }

    private fun parseVerificationType(input: DataInput): EncodedVerificationType {
        val tag = input.readByte()
        return when (tag.toInt()) {
            VerificationTypeConstants.TOP_CODE -> VerificationType.TOP
            VerificationTypeConstants.INTEGER_CODE -> VerificationType.INTEGER
            VerificationTypeConstants.FLOAT_CODE -> VerificationType.FLOAT
            VerificationTypeConstants.LONG_CODE -> VerificationType.LONG
            VerificationTypeConstants.DOUBLE_CODE -> VerificationType.DOUBLE
            VerificationTypeConstants.NULL_CODE -> VerificationType.NULL
            VerificationTypeConstants.UNINITIALIZED_THIS_CODE -> VerificationType.UNITIALIZED_THIS
            VerificationTypeConstants.OBJECT_CODE -> {
                val classIndex = input.readUnsignedShort()
                EncodedObjectVerificationType(classIndex)
            }
            VerificationTypeConstants.UNINITIALIZED_VARIABLE_CODE -> {
                val offset = input.readUnsignedShort()
                EncodedUnitializedVerificationType(offset)
            }
            else -> throw IllegalArgumentException("Should not happen")
        }

    }

    /**
     * ```
     * LocalVariableTypeTable_attribute {
     *      u2 attribute_name_index;
     *      u4 attribute_length;
     *      u2 local_variable_type_table_length;
     *      {   u2 start_pc;
     *          u2 length;
     *          u2 name_index;
     *          u2 signature_index;
     *          u2 index;
     *      } local_variable_type_table[local_variable_type_table_length];
     *  }
     * ```
     *
     * @param attributeNameIndex
     * @param input
     * @return
     */
    @Throws(IOException::class)
    private fun decodeLocalVariableTypeTable(attributeNameIndex: Int,
                                             input: DataInput): EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter> {
        val attributeLength = input.readInt()
        val localVariableTableLength = input.readShort().toInt()
        val encodedLocalVariableTables: MutableList<EncodedLocalVariableTypeTable> =
                ArrayList(localVariableTableLength)
        for (lv in 0 until localVariableTableLength) {
            val startPc = input.readShort().toInt()
            val length = input.readShort().toInt()
            val nameIndex = input.readShort().toInt()
            val signatureIndex = input.readShort().toInt()
            val index = input.readShort().toInt()
            encodedLocalVariableTables
                    .add(EncodedLocalVariableTypeTable(startPc, length, nameIndex,
                            signatureIndex, index))
        }
        return EncodeLocalVariableTypeTableAttribute(attributeNameIndex,
                encodedLocalVariableTables)
    }

    /**
     * LocalVariableTable_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 local_variable_table_length;
     * {   u2 start_pc;
     * u2 length;
     * u2 name_index;
     * u2 descriptor_index;
     * u2 index;
     * } local_variable_table[local_variable_table_length];
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     */
    @Throws(IOException::class)
    private fun decodeLocalVariableTable(attributeNameIndex: Int,
                                         input: DataInput): EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter> {
        val attributeLength = input.readInt()
        val localVariableTableLength = input.readShort().toInt()
        val encodedLocalVariableTables: MutableList<EncodedLocalVariableTable> =
                ArrayList(localVariableTableLength)
        for (lv in 0 until localVariableTableLength) {
            val startPc = input.readShort().toInt()
            val length = input.readShort().toInt()
            val nameIndex = input.readShort().toInt()
            val descriptorIndex = input.readShort().toInt()
            val index = input.readShort().toInt()
            encodedLocalVariableTables
                    .add(EncodedLocalVariableTable(startPc, length, nameIndex, descriptorIndex,
                            index))
        }
        return EncodedLocalVariableTableAttribute(attributeNameIndex,
                encodedLocalVariableTables)
    }

    /**
     * LineNumberTable_attribute {
     * u2 attribute_name_index;
     * u4 attribute_length;
     * u2 line_number_table_length;
     * {   u2 start_pc;
     * u2 line_number;
     * } line_number_table[line_number_table_length];
     * }
     *
     * @param attributeNameIndex
     * @param input
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun decodeLineNumberTable(attributeNameIndex: Int,
                                      input: DataInput): EncodedCodeAttributeAttribute<*, *, CodeAttributeAttributeEncodedWriter> {
        val length = input.readInt()
        val lineNumberTableLength = input.readShort().toInt()
        val positionAndLineNumbers: MutableList<PositionAndLineNumber> =
                ArrayList(lineNumberTableLength)
        for (ln in 0 until lineNumberTableLength) {
            val startPc = input.readShort().toInt()
            val lineNumber = input.readShort().toInt()
            positionAndLineNumbers.add(PositionAndLineNumber(startPc, lineNumber))
        }
        return EncodedLineNumberTableAttribute(attributeNameIndex, positionAndLineNumbers)
    }

}