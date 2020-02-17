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
/**
 * 4.7. Attributes
 *
 *
 * Five attributes are critical to correct interpretation of the class file by the Java Virtual
 * Machine: ConstantValue*, Code, StackMapTable, Exceptions, BootstrapMethods
 *
 *
 * Twelve attributes are critical to correct interpretation of the class file by the class
 * libraries of the Java SE platform: InnerClasses, EnclosingMethod, Synthetic, Signature,
 * RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations,
 * RuntimeInvisibleParameterAnnotations, RuntimeVisibleTypeAnnotations,
 * RuntimeInvisibleTypeAnnotations, AnnotationDefault, MethodParameters
 *
 *
 * Six attributes are not critical to correct interpretation of the class file by either the Java
 * Virtual Machine or the class libraries of the Java SE platform, but are useful for tools:
 * SourceFile, SourceDebugExtension, LineNumberTable, LocalVariableTable, LocalVariableTypeTable,
 * Deprecated
 */
package com.github.jferard.classwriter.internal.attribute

