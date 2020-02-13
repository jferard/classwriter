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
package com.github.jferard.classwriter.api.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.base.*
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

internal object InstructionConstants {
    // array load and store
    val AALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.AALOAD, VerificationType.REFERENCE)
    val AASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.AASTORE, VerificationType.REFERENCE)
    val BALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.BALOAD, VerificationType.INTEGER)
    val BASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.BASTORE, VerificationType.INTEGER)
    val CALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.CALOAD, VerificationType.INTEGER)
    val CASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.CASTORE, VerificationType.INTEGER)
    val DALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.DALOAD, VerificationType.DOUBLE)
    val DASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.DASTORE, VerificationType.DOUBLE)
    val FALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.FALOAD, VerificationType.FLOAT)
    val FASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.FASTORE, VerificationType.FLOAT)
    val IALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.IALOAD, VerificationType.INTEGER)
    val IASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.IASTORE, VerificationType.INTEGER)
    val LALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.LALOAD, VerificationType.LONG)
    val LASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.LASTORE, VerificationType.LONG)
    val SALOAD_INSTRUCTION: BaseInstruction =
            XALoadInstruction(OpCodes.SALOAD, VerificationType.INTEGER)
    val SASTORE_INSTRUCTION: BaseInstruction =
            XAStoreInstruction(OpCodes.SASTORE, VerificationType.INTEGER)

    // binary
    val DADD_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DADD,
                    VerificationType.DOUBLE)
    val DCMPG_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DCMPG,
                    VerificationType.INTEGER)
    val DCMPL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DCMPL,
                    VerificationType.INTEGER)
    val DDIV_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DDIV,
                    VerificationType.DOUBLE)
    val DMUL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DMUL,
                    VerificationType.DOUBLE)
    val DREM_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DREM,
                    VerificationType.DOUBLE)
    val DSUB_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.DSUB,
                    VerificationType.DOUBLE)
    val FADD_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FADD,
                    VerificationType.FLOAT)
    val FCMPG_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FCMPG,
                    VerificationType.INTEGER)
    val FCMPL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FCMPL,
                    VerificationType.INTEGER)
    val FDIV_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FDIV,
                    VerificationType.FLOAT)
    val FMUL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FMUL,
                    VerificationType.FLOAT)
    val FREM_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FREM,
                    VerificationType.FLOAT)
    val FSUB_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.FSUB,
                    VerificationType.FLOAT)
    val IADD_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IADD,
                    VerificationType.INTEGER)
    val IDIV_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IDIV,
                    VerificationType.INTEGER)
    val IMUL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IMUL,
                    VerificationType.INTEGER)
    val IREM_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IREM,
                    VerificationType.INTEGER)
    val ISUB_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.ISUB,
                    VerificationType.INTEGER)
    val IAND_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IAND,
                    VerificationType.INTEGER)
    val IOR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IOR,
                    VerificationType.INTEGER)
    val ISHL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.ISHL,
                    VerificationType.INTEGER)
    val ISHR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.ISHR,
                    VerificationType.INTEGER)
    val IUSHR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IUSHR,
                    VerificationType.INTEGER)
    val IXOR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.IXOR,
                    VerificationType.INTEGER)
    val LADD_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LADD,
                    VerificationType.LONG)
    val LCMP_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LCMP,
                    VerificationType.INTEGER)
    val LDIV_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LDIV,
                    VerificationType.LONG)
    val LMUL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LMUL,
                    VerificationType.LONG)
    val LREM_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LREM,
                    VerificationType.LONG)
    val LSUB_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LSUB,
                    VerificationType.LONG)
    val LAND_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LAND,
                    VerificationType.LONG)
    val LOR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LOR,
                    VerificationType.LONG)
    val LSHL_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LSHL,
                    VerificationType.LONG)
    val LSHR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LSHR,
                    VerificationType.LONG)
    val LUSHR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LUSHR,
                    VerificationType.LONG)
    val LXOR_INSTRUCTION: BaseInstruction =
            BinaryOpInstruction(OpCodes.LXOR,
                    VerificationType.LONG)

    // unary
    val DNEG_INSTRUCTION: BaseInstruction =
            NoArgInstruction.unary(OpCodes.DNEG,
                    VerificationType.DOUBLE)
    val FNEG_INSTRUCTION: BaseInstruction =
            NoArgInstruction.unary(OpCodes.FNEG,
                    VerificationType.FLOAT)
    val INEG_INSTRUCTION: BaseInstruction =
            NoArgInstruction.unary(OpCodes.INEG,
                    VerificationType.INTEGER)
    val LNEG_INSTRUCTION: BaseInstruction =
            NoArgInstruction.unary(OpCodes.LNEG,
                    VerificationType.LONG)

    // push const
    val ACONST_NULL_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.ACONST_NULL, VerificationType.NULL)
    val DCONST_0_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.DCONST_0, VerificationType.DOUBLE)
    val DCONST_1_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.DCONST_1, VerificationType.DOUBLE)
    val FCONST_0_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.FCONST_0, VerificationType.FLOAT)
    val FCONST_1_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.FCONST_1, VerificationType.FLOAT)
    val FCONST_2_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.FCONST_2, VerificationType.FLOAT)
    val LCONST_0_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.LCONST_0, VerificationType.LONG)
    val LCONST_1_INSTRUCTION: BaseInstruction = ConstInstruction(
            OpCodes.LCONST_1, VerificationType.LONG)

    // conversion
    val D2F_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.D2F,
                    VerificationType.DOUBLE, VerificationType.FLOAT)
    val D2I_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.D2I,
                    VerificationType.DOUBLE, VerificationType.INTEGER)
    val D2L_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.D2L,
                    VerificationType.DOUBLE, VerificationType.LONG)
    val F2D_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.F2D,
                    VerificationType.FLOAT, VerificationType.DOUBLE)
    val F2I_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.F2I,
                    VerificationType.FLOAT, VerificationType.INTEGER)
    val F2L_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.F2L,
                    VerificationType.FLOAT, VerificationType.LONG)
    val I2D_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2D,
                    VerificationType.INTEGER, VerificationType.DOUBLE)
    val I2F_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2F,
                    VerificationType.INTEGER, VerificationType.FLOAT)
    val I2L_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2L,
                    VerificationType.INTEGER, VerificationType.LONG)
    val I2B_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2B,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val I2C_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2C,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val I2S_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.I2S,
                    VerificationType.INTEGER, VerificationType.INTEGER)
    val L2D_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.L2D,
                    VerificationType.LONG, VerificationType.DOUBLE)
    val L2F_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.L2F,
                    VerificationType.LONG, VerificationType.FLOAT)
    val L2I_INSTRUCTION: BaseInstruction =
            ConvertInstruction(OpCodes.L2I,
                    VerificationType.LONG, VerificationType.INTEGER)

    // misc
    val BREAKPOINT_INSTRUCTION: BaseInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.BREAKPOINT)
    val IMPDEP1_INSTRUCTION: BaseInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.IMPDEP1)
    val IMPDEP2_INSTRUCTION: BaseInstruction = NoArgInstruction.noLocalsNoSTack(
            OpCodes.IMPDEP2)

    // dup
    val DUP_INSTRUCTION: BaseInstruction = DupInstruction()
    val DUP_X1_INSTRUCTION: BaseInstruction = DupX1Instruction()
    val DUP_X2_INSTRUCTION: BaseInstruction = DupX2Instruction()
    val DUP2_INSTRUCTION: BaseInstruction = Dup2Instruction()
    val DUP2_X1_INSTRUCTION: BaseInstruction = Dup2X1Instruction()
    val DUP2_X2_INSTRUCTION: BaseInstruction = Dup2X2Instruction()

    // return
    val ARETURN_INSTRUCTION: BaseInstruction =
            ReturnInstruction(
                    OpCodes.ARETURN)
    val DRETURN_INSTRUCTION: BaseInstruction =
            ReturnInstruction(
                    OpCodes.DRETURN)
    val FRETURN_INSTRUCTION: BaseInstruction =
            ReturnInstruction(
                    OpCodes.FRETURN)
    val IRETURN_INSTRUCTION: BaseInstruction =
            ReturnInstruction(
                    OpCodes.IRETURN)
    val LRETURN_INSTRUCTION: BaseInstruction =
            ReturnInstruction(
                    OpCodes.LRETURN)
    val RETURN_INSTRUCTION: ReturnInstruction =
            ReturnInstruction(
                    OpCodes.RETURN)

    // monitor
    val MONITORENTER_INSTRUCTION: BaseInstruction =
            NoArgInstruction.monitor(OpCodes.MONITORENTER)
    val MONITOREXIT_INSTRUCTION: BaseInstruction =
            NoArgInstruction.monitor(OpCodes.MONITOREXIT)
    val NOP_INSTRUCTION: BaseInstruction =
            NoArgInstruction.noLocalsNoSTack(OpCodes.NOP)

    // pop
    val POP_INSTRUCTION: BaseInstruction =
            NoArgInstruction.pop(OpCodes.POP, -1)
    val POP2_INSTRUCTION: BaseInstruction =
            NoArgInstruction.pop(OpCodes.POP2, -2)
    val SWAP_INSTRUCTION: BaseInstruction =
            NoArgInstruction.noLocalsNoSTack(OpCodes.SWAP)
    val ARRAYLENGTH_INSTRUCTION: BaseInstruction = ArrayLengthInstruction()
    val ATHROW_INSTRUCTION: BaseInstruction = AThrowInstruction()
    val ICONST_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            ConstInstruction(OpCodes.ICONST_M1,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_0,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_1,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_2,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_3,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_4,
                    VerificationType.INTEGER),
            ConstInstruction(OpCodes.ICONST_5,
                    VerificationType.INTEGER))
    val ALOAD_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_3, 3))
    val DLOAD_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            NoArgInstruction.longLoadN(OpCodes.DLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_3, 3))
    val FLOAD_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_3, 3))
    val ILOAD_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_3, 3))
    val LLOAD_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            NoArgInstruction.longLoadN(OpCodes.LLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_3, 3))
    val ASTORE_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            StoreNInstruction(OpCodes.ASTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ASTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ASTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ASTORE_3, 3,
                    VerificationType.INTEGER))
    val DSTORE_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            StoreNInstruction(OpCodes.DSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.DSTORE_3, 3,
                    VerificationType.INTEGER))
    val FSTORE_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            StoreNInstruction(OpCodes.FSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.FSTORE_3, 3,
                    VerificationType.INTEGER))
    val ISTORE_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            StoreNInstruction(OpCodes.ISTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ISTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ISTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.ISTORE_3, 3,
                    VerificationType.INTEGER))
    val LSTORE_INSTRUCTIONS: Array<BaseInstruction> = arrayOf<BaseInstruction>(
            StoreNInstruction(OpCodes.LSTORE_0, 0,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_1, 1,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_2, 2,
                    VerificationType.INTEGER),
            StoreNInstruction(OpCodes.LSTORE_3, 3,
                    VerificationType.INTEGER))
}