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
package com.github.jferard.classwriter.api

import com.github.jferard.classwriter.pool.ConstantPoolEntry
import com.github.jferard.classwriter.pool.FieldRefEntry
import com.github.jferard.classwriter.tool.FieldTypeHelper
import java.io.PrintStream
import kotlin.reflect.KClass

/**
 * 4.4.2. The CONSTANT_Fieldref_info, CONSTANT_Methodref_info, and
 * CONSTANT_InterfaceMethodref_info Structures
 * CONSTANT_Fieldref_info {
 * u1 tag;
 * u2 class_index;
 * u2 name_and_type_index;
 * }
 */
class FieldRef(override val classRef: PlainClassRef, override val name: String,
               val type: ValueType) : FieldOrMethodRef {

    override fun toDescriptor(): FieldDescriptor {
        return FieldDescriptor(type)
    }

    override fun toEntry(): ConstantPoolEntry {
        return FieldRefEntry(this)
    }

    val size: Int
        get() = type.getSize(0)

    companion object {
        @kotlin.jvm.JvmStatic
        fun create(className: String, fieldName: String,
                   fieldClass: Class<PrintStream>): FieldRef {
            return FieldRef(PlainClassRef(className), fieldName, FieldTypeHelper.get(fieldClass))
        }

        fun create(jclass: Class<*>, fieldName: String): FieldRef {
            val field = jclass.getDeclaredField(fieldName)
            return FieldRef(PlainClassRef(jclass.canonicalName), fieldName,
                    FieldTypeHelper.get(field.type))
        }
    }

}