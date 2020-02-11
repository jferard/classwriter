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

import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.encoded.attribute.EncodedBootstrapMethodsAttribute
import com.github.jferard.classwriter.encoded.attribute.EncodedClassFileAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter
import java.io.DataInput
import java.io.IOException
import java.util.*

/**
 * 4.7.23. The BootstrapMethods Attribute
 * ```BootstrapMethods_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 num_bootstrap_methods;
 * {   u2 bootstrap_method_ref;
 * u2 num_bootstrap_arguments;
 * u2 bootstrap_arguments[num_bootstrap_arguments];
 * } bootstrap_methods[num_bootstrap_methods];
 * }
 * ``` *
 */
class BootstrapMethodsAttributeParser : ClassAttributeParser {
    @Throws(IOException::class)
    override fun decode(attributeNameIndex: Int,
                        input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        val length = input.readInt()
        val numBootstrapMethods = input.readShort().toInt()
        val encodedBootstrapMethods: MutableList<EncodedBootstrapMethod> =
                ArrayList(numBootstrapMethods)
        for (bsm in 0 until numBootstrapMethods) {
            val encodedBootstrapMethod = readEncodedBootstrapMethod(input)
            encodedBootstrapMethods.add(encodedBootstrapMethod)
        }
        //TODO: check the length
        return EncodedBootstrapMethodsAttribute(attributeNameIndex, encodedBootstrapMethods)
    }

    override fun parse(
            input: DataInput): EncodedClassFileAttribute<*, *, ClassFileAttributeEncodedWriter> {
        throw NotImplementedError() //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * ```
     * u2 bootstrap_method_ref;
     * u2 num_bootstrap_arguments;
     * u2 bootstrap_arguments[num_bootstrap_arguments];
     * ```
     */
    @Throws(IOException::class)
    private fun readEncodedBootstrapMethod(input: DataInput): EncodedBootstrapMethod {
        val bootstrapMethodRef = input.readShort().toInt()
        val numBootstrapArguments = input.readShort().toInt()
        val bootstrapArguments: MutableList<Int> =
                ArrayList(numBootstrapArguments)
        for (bsa in 0 until numBootstrapArguments) {
            val bootstrapArgument = input.readShort().toInt()
            bootstrapArguments.add(bootstrapArgument)
        }
        return EncodedBootstrapMethod(bootstrapMethodRef, bootstrapArguments)
    }
}