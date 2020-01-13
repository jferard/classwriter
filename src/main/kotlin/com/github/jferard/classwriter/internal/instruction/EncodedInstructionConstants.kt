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
package com.github.jferard.classwriter.internal.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.encoded.instruction.EncodedInstruction
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType
import com.github.jferard.classwriter.internal.instruction.base.*

internal object EncodedInstructionConstants {
    // array load and store
    val AALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.AALOAD)
    val AASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.AASTORE,
                    VerificationType.REFERENCE)
    val BALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.BALOAD)
    val BASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.BASTORE,
                    VerificationType.INTEGER)
    val CALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.CALOAD)
    val CASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.CASTORE,
                    VerificationType.INTEGER)
    val DALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.DALOAD)
    val DASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.DASTORE,
                    VerificationType.DOUBLE)
    val FALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.FALOAD)
    val FASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.FASTORE,
                    VerificationType.FLOAT)
    val IALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.IALOAD)
    val IASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.IASTORE,
                    VerificationType.INTEGER)
    val LALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.LALOAD)
    val LASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.LASTORE,
                    VerificationType.LONG)
    val SALOAD_INSTRUCTION: EncodedInstruction =
            EncodedALoadNInstruction(OpCodes.SALOAD)
    val SASTORE_INSTRUCTION: EncodedInstruction =
            AStoreInstruction(OpCodes.SASTORE,
                    VerificationType.INTEGER)
    // binary
    val DADD_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DADD,
                    VerificationType.DOUBLE)
    val DCMPG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DCMPG,
                    VerificationType.INTEGER)
    val DCMPL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DCMPL,
                    VerificationType.INTEGER)
    val DDIV_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DDIV,
                    VerificationType.DOUBLE)
    val DMUL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DMUL,
                    VerificationType.DOUBLE)
    val DREM_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DREM,
                    VerificationType.DOUBLE)
    val DSUB_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.DSUB,
                    VerificationType.DOUBLE)
    val FADD_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FADD,
                    VerificationType.FLOAT)
    val FCMPG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FCMPG,
                    VerificationType.INTEGER)
    val FCMPL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FCMPL,
                    VerificationType.INTEGER)
    val FDIV_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FDIV,
                    VerificationType.FLOAT)
    val FMUL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FMUL,
                    VerificationType.FLOAT)
    val FREM_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FREM,
                    VerificationType.FLOAT)
    val FSUB_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.FSUB,
                    VerificationType.FLOAT)
    val IADD_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IADD,
                    VerificationType.INTEGER)
    val IDIV_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IDIV,
                    VerificationType.INTEGER)
    val IMUL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IMUL,
                    VerificationType.INTEGER)
    val IREM_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IREM,
                    VerificationType.INTEGER)
    val ISUB_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.ISUB,
                    VerificationType.INTEGER)
    val IAND_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IAND,
                    VerificationType.INTEGER)
    val IOR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IOR,
                    VerificationType.INTEGER)
    val ISHL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.ISHL,
                    VerificationType.INTEGER)
    val ISHR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.ISHR,
                    VerificationType.INTEGER)
    val IUSHR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IUSHR,
                    VerificationType.INTEGER)
    val IXOR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.IXOR,
                    VerificationType.INTEGER)
    val LADD_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LADD,
                    VerificationType.LONG)
    val LCMP_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LCMP,
                    VerificationType.INTEGER)
    val LDIV_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LDIV,
                    VerificationType.LONG)
    val LMUL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LMUL,
                    VerificationType.LONG)
    val LREM_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LREM,
                    VerificationType.LONG)
    val LSUB_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LSUB,
                    VerificationType.LONG)
    val LAND_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LAND,
                    VerificationType.LONG)
    val LOR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LOR,
                    VerificationType.LONG)
    val LSHL_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LSHL,
                    VerificationType.LONG)
    val LSHR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LSHR,
                    VerificationType.LONG)
    val LUSHR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LUSHR,
                    VerificationType.LONG)
    val LXOR_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.binaryOp(OpCodes.LXOR,
                    VerificationType.LONG)
    // unary
    val DNEG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.unary(OpCodes.DNEG,
                    VerificationType.DOUBLE)
    val FNEG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.unary(OpCodes.FNEG,
                    VerificationType.FLOAT)
    val INEG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.unary(OpCodes.INEG,
                    VerificationType.INTEGER)
    val LNEG_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.unary(OpCodes.LNEG,
                    VerificationType.LONG)
    // push const
    val ACONST_NULL_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.ACONST_NULL, VerificationType.NULL)
    val DCONST_0_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.DCONST_0, VerificationType.DOUBLE)
    val DCONST_1_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.DCONST_1, VerificationType.DOUBLE)
    val FCONST_0_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.FCONST_0, VerificationType.FLOAT)
    val FCONST_1_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.FCONST_1, VerificationType.FLOAT)
    val FCONST_2_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.FCONST_2, VerificationType.FLOAT)
    val LCONST_0_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.LCONST_0, VerificationType.LONG)
    val LCONST_1_INSTRUCTION: EncodedInstruction = ConstInstruction(
            OpCodes.LCONST_1, VerificationType.LONG)
    // conversion
    val D2F_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.D2F,
                    VerificationType.DOUBLE, VerificationType.FLOAT)
    val D2I_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.D2I,
                    VerificationType.DOUBLE, VerificationType.INTEGER)
    val D2L_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.D2L,
                    VerificationType.DOUBLE, VerificationType.LONG)
    val F2D_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.F2D,
                    VerificationType.FLOAT, VerificationType.DOUBLE)
    val F2I_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.F2I,
                    VerificationType.FLOAT, VerificationType.INTEGER)
    val F2L_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.F2L,
                    VerificationType.FLOAT, VerificationType.LONG)
    val I2D_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2D,
                    VerificationType.INTEGER, VerificationType.DOUBLE)
    val I2F_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2F,
                    VerificationType.INTEGER, VerificationType.FLOAT)
    val I2L_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2L,
                    VerificationType.INTEGER, VerificationType.LONG)
    val I2B_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2B,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val I2C_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2C,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val I2S_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.I2S,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val L2D_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.L2D,
                    VerificationType.LONG, VerificationType.DOUBLE)
    val L2F_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.L2F,
                    VerificationType.LONG, VerificationType.FLOAT)
    val L2I_INSTRUCTION: EncodedInstruction =
            ConvertInstruction(OpCodes.L2I,
                    VerificationType.LONG, VerificationType.INTEGER)
    // misc
    val BREAKPOINT_INSTRUCTION: EncodedInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.BREAKPOINT)
    val IMPDEP1_INSTRUCTION: EncodedInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.IMPDEP1)
    val IMPDEP2_INSTRUCTION: EncodedInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.IMPDEP2)
    // dup
    val DUP_INSTRUCTION: EncodedInstruction = DupInstruction()
    val DUP_X1_INSTRUCTION: EncodedInstruction = DupX1Instruction()
    val DUP_X2_INSTRUCTION: EncodedInstruction = DupX2Instruction()
    val DUP2_INSTRUCTION: EncodedInstruction = Dup2Instruction()
    val DUP2_X1_INSTRUCTION: EncodedInstruction = Dup2X1Instruction()
    val DUP2_X2_INSTRUCTION: EncodedInstruction = Dup2X2Instruction()
    // return
    val ARETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.ARETURN)
    val DRETURN_INSTRUCTION: EncodedInstruction =
            ReturnInstruction(
                    OpCodes.DRETURN)
    val FRETURN_INSTRUCTION: EncodedInstruction =
            ReturnInstruction(
                    OpCodes.FRETURN)
    val IRETURN_INSTRUCTION: EncodedInstruction =
            ReturnInstruction(
                    OpCodes.IRETURN)
    val LRETURN_INSTRUCTION: EncodedInstruction =
            ReturnInstruction(
                    OpCodes.LRETURN)
    val RETURN_INSTRUCTION: ReturnInstruction =
            ReturnInstruction(
                    OpCodes.RETURN)
    // monitor
    val MONITORENTER_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.monitor(OpCodes.MONITORENTER)
    val MONITOREXIT_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.monitor(OpCodes.MONITOREXIT)
    val NOP_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.noLocalsNoSTack(OpCodes.NOP)
    // pop
    val POP_INSTRUCTION: EncodedInstruction = EncodedPopInstruction()
    val POP2_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.pop(OpCodes.POP2, -2)
    val SWAP_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.noLocalsNoSTack(OpCodes.SWAP)
    val ARRAYLENGTH_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.noLocals(OpCodes.ARRAYLENGTH,
                    1, listOf(VerificationType.INTEGER))
    val ATHROW_INSTRUCTION: EncodedInstruction = AThrowInstruction()
    val ICONST_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf(
            EncodedIConstNInstruction(OpCodes.ICONST_0),
            EncodedIConstNInstruction(OpCodes.ICONST_1),
            EncodedIConstNInstruction(OpCodes.ICONST_2),
            EncodedIConstNInstruction(OpCodes.ICONST_3),
            EncodedIConstNInstruction(OpCodes.ICONST_4),
            EncodedIConstNInstruction(OpCodes.ICONST_5))
    val ALOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf(
            EncodedALoadNInstruction(OpCodes.ALOAD_0),
            EncodedALoadNInstruction(OpCodes.ALOAD_1),
            EncodedALoadNInstruction(OpCodes.ALOAD_2),
            EncodedALoadNInstruction(OpCodes.ALOAD_3))
    val DLOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            NoArgInstruction.longLoadN(OpCodes.DLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_3, 3))
    val FLOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_3, 3))
    val ILOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedILoadNInstruction(OpCodes.ILOAD_0),
            EncodedILoadNInstruction(OpCodes.ILOAD_1),
            EncodedILoadNInstruction(OpCodes.ILOAD_2),
            EncodedILoadNInstruction(OpCodes.ILOAD_3))
    val LLOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            NoArgInstruction.longLoadN(OpCodes.LLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_3, 3))
    val ASTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedAStoreNInstruction(OpCodes.ASTORE_0),
            EncodedAStoreNInstruction(OpCodes.ASTORE_1),
            EncodedAStoreNInstruction(OpCodes.ASTORE_2),
            EncodedAStoreNInstruction(OpCodes.ASTORE_3))
    val DSTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            StoreNInstruction(OpCodes.DSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_3, 3,
                    VerificationType.INTEGER))
    val FSTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            StoreNInstruction(OpCodes.FSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_3, 3,
                    VerificationType.INTEGER))
    val ISTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedIStoreNInstruction(OpCodes.ISTORE_0),
            EncodedIStoreNInstruction(OpCodes.ISTORE_1),
            EncodedIStoreNInstruction(OpCodes.ISTORE_2),
            EncodedIStoreNInstruction(OpCodes.ISTORE_3))
    val LSTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            StoreNInstruction(OpCodes.LSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_3, 3,
                    VerificationType.INTEGER))
}