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

import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClass
import com.github.jferard.classwriter.encoded.attribute.EncodedInnerClassesAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException
import java.util.*

/**
 * 4.7.6. The InnerClasses Attribute
 * ```
 * InnerClasses_attribute {
 *      u2 attribute_name_index;
 *      u4 attribute_length;
 *      u2 number_of_classes;
 *      {   u2 inner_class_info_index;
 *          u2 outer_class_info_index;
 *          u2 inner_name_index;
 *          u2 inner_class_access_flags;
 *      } classes[number_of_classes];
 * }
 * ```
 */
class InnerClassesAttributeParser : ClassAttributeParser {
    @Throws(IOException::class)
    override fun decode(attributeNameIndex: Int,
                        input: DataInput): EncodedClassFileAttribute<*,*,ClassFileAttributeEncodedWriter> {
        val length = input!!.readInt()
        val numberOfClasses = input.readShort().toInt()
        val encodedInnerClasses: MutableList<EncodedInnerClass> =
                ArrayList(numberOfClasses)
        for (c in 0 until numberOfClasses) {
            val encodedInnerClass = readEncodedInnerClass(input)
            encodedInnerClasses.add(encodedInnerClass)
        }
        //TODO: check the length
        return EncodedInnerClassesAttribute(attributeNameIndex, encodedInnerClasses)
    }

    override fun parse(
            input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * ```u2 inner_class_info_index;
     * u2 outer_class_info_index;
     * u2 inner_name_index;
     * u2 inner_class_access_flags;
    * ``` *
     *
     * @param input
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readEncodedInnerClass(input: DataInput): EncodedInnerClass {
        val innerClassIndex = input!!.readShort().toInt()
        val outerClassIndex = input.readShort().toInt()
        val innerNameIndex = input.readShort().toInt()
        val innerClassAccess = input.readShort().toInt()
        return EncodedInnerClass(innerClassIndex, outerClassIndex, innerNameIndex,
                innerClassAccess)
    }
}