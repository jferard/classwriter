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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.Sized
import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.attribute.BootstrapMethodsAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter

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
class EncodedBootstrapMethodsAttribute(private val nameIndex: Int,
                                       private val encodedBootstrapMethods: List<EncodedBootstrapMethod>) :
        EncodedClassFileAttribute<BootstrapMethodsAttribute, EncodedBootstrapMethodsAttribute, ClassFileAttributeEncodedWriter> {
    override fun write(encodedWriter: ClassFileAttributeEncodedWriter) {
        encodedWriter.bootstrapMethodsAttribute(nameIndex, encodedBootstrapMethods)
    }

    override fun getSize(pos: Int): Int = BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + length

    private val length: Int = Sized.listSize(0, encodedBootstrapMethods)

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        return encodedBootstrapMethods
    }

    override fun decode(context: GlobalContext,
                        codeContext: MethodContext): BootstrapMethodsAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}