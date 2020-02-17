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

import com.github.jferard.classwriter.encoded.Encoded
import com.github.jferard.classwriter.writer.encoded.AnnotationEncodedWriter
import com.github.jferard.classwriter.internal.attribute.ElementValue

/** 4.7.16.1. The element_value structure
 * ```
 * element_value {
 *      u1 tag;
 *      union {
 *          u2 const_value_index;
 *
*           {   u2 type_name_index;
 *              u2 const_name_index;
 *          } enum_const_value;
 *
 *          u2 class_info_index;
 *
 *          annotation annotation_value;
 *
 *          {   u2            num_values;
 *              element_value values[num_values];
 *          } array_value;
 *      } value;
 * }
 * ``` *
 */
interface EncodedElementValue : Encoded<ElementValue, EncodedElementValue, AnnotationEncodedWriter>