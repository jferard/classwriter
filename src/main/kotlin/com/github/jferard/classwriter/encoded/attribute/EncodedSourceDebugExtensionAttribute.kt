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
package com.github.jferard.classwriter.encoded.attribute

import com.github.jferard.classwriter.bytecode.BytecodeHelper
import com.github.jferard.classwriter.encoded.EncodedBootstrapMethod
import com.github.jferard.classwriter.internal.attribute.SourceFileAttribute
import com.github.jferard.classwriter.internal.context.GlobalContext
import com.github.jferard.classwriter.internal.context.MethodContext
import com.github.jferard.classwriter.tool.decoder.SourceDebugExtensionAttribute
import com.github.jferard.classwriter.writer.encoded.ClassFileAttributeEncodedWriter

/**
 * 4.7.10. The SourceFile Attribute (ClassFile structure)
 * ```
 * SourceFile_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 sourcefile_index;
 * }
 * ``` *
 */
class EncodedSourceDebugExtensionAttribute(private val attributeNameIndex: Int,
                                           private val debugExtension: ByteArray) :
        EncodedClassFileAttribute<SourceDebugExtensionAttribute, EncodedSourceDebugExtensionAttribute, ClassFileAttributeEncodedWriter> {
    override fun write(encodedWriter: ClassFileAttributeEncodedWriter) {
        TODO("not implemented")
    }

    override val size: Int =
            BytecodeHelper.SHORT_SIZE + BytecodeHelper.INT_SIZE + debugExtension.size

    override fun oGetBootstrapMethods(): List<EncodedBootstrapMethod>? {
        TODO("not implemented")
    }

    override fun decode(context: GlobalContext, codeContext: MethodContext): SourceDebugExtensionAttribute {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}