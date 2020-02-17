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
package com.github.jferard.classwriter.encoded.instruction

import com.github.jferard.classwriter.OpCodes
import com.github.jferard.classwriter.api.instruction.base.*
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType

internal object EncodedInstructionConstants {
    // array load and store
    val AALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.AALOAD)
    val AASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.AASTORE)
    val BALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.BALOAD)
    val BASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.BASTORE)
    val CALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.CALOAD)
    val CASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.CASTORE)
    val DALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.DALOAD)
    val DASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.DASTORE)
    val FALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.FALOAD)
    val FASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.FASTORE)
    val IALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.IALOAD)
    val IASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.IASTORE)
    val LALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.LALOAD)
    val LASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.LASTORE)
    val SALOAD_INSTRUCTION: EncodedInstruction =
            EncodedXALoadInstruction(OpCodes.SALOAD)
    val SASTORE_INSTRUCTION: EncodedInstruction =
            EncodedXAStoreInstruction(OpCodes.SASTORE)

    // binary
    val DADD_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DADD)
    val DCMPG_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DCMPG)
    val DCMPL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DCMPL)
    val DDIV_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DDIV)
    val DMUL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DMUL)
    val DREM_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DREM)
    val DSUB_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.DSUB)
    val FADD_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FADD)
    val FCMPG_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FCMPG)
    val FCMPL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FCMPL)
    val FDIV_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FDIV)
    val FMUL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FMUL)
    val FREM_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FREM)
    val FSUB_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.FSUB)
    val IADD_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IADD)
    val IDIV_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IDIV)
    val IMUL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IMUL)
    val IREM_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IREM)
    val ISUB_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.ISUB)
    val IAND_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IAND)
    val IOR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IOR)
    val ISHL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.ISHL)
    val ISHR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.ISHR)
    val IUSHR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IUSHR)
    val IXOR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.IXOR)
    val LADD_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LADD)
    val LCMP_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LCMP)
    val LDIV_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LDIV)
    val LMUL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LMUL)
    val LREM_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LREM)
    val LSUB_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LSUB)
    val LAND_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LAND)
    val LOR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LOR)
    val LSHL_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LSHL)
    val LSHR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LSHR)
    val LUSHR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LUSHR)
    val LXOR_INSTRUCTION: EncodedInstruction =
            EncodedBinaryOpInstruction(OpCodes.LXOR)

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
    val ACONST_NULL_INSTRUCTION: EncodedInstruction = EncodedAConstNullInstruction()
    val DCONST_N_INSTRUCTIONS = arrayOf(
            EncodedDConstNInstruction(OpCodes.DCONST_0),
            EncodedDConstNInstruction(OpCodes.DCONST_1))
    val FCONST_N_INSTRUCTIONS = arrayOf(
            EncodedFConstNInstruction(OpCodes.FCONST_0),
            EncodedFConstNInstruction(OpCodes.FCONST_1),
            EncodedFConstNInstruction(OpCodes.FCONST_2))
    val LCONST_N_INSTRUCTIONS = arrayOf(
            EncodedLConstNInstruction(OpCodes.LCONST_0),
            EncodedLConstNInstruction(OpCodes.LCONST_1))

    // conversion
    val D2F_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.D2F)
    val D2I_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.D2I)
    val D2L_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.D2L)
    val F2D_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.F2D)
    val F2I_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.F2I)
    val F2L_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.F2L)
    val I2D_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2D)
    val I2F_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2F)
    val I2L_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2L)
    val I2B_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2B)
    val I2C_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2C)
    val I2S_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.I2S)
    val L2D_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.L2D)
    val L2F_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.L2F)
    val L2I_INSTRUCTION: EncodedInstruction = EncodedConvertInstruction(OpCodes.L2I)

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
    val DUP2_X2_INSTRUCTION: EncodedInstruction = EncodedDup2X2Instruction()

    // return
    val ARETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.ARETURN)
    val DRETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.DRETURN)
    val FRETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.FRETURN)
    val IRETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.IRETURN)
    val LRETURN_INSTRUCTION: EncodedInstruction = ReturnInstruction(OpCodes.LRETURN)
    val RETURN_INSTRUCTION: ReturnInstruction = ReturnInstruction(OpCodes.RETURN)

    // monitor
    val MONITORENTER_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.monitor(OpCodes.MONITORENTER)
    val MONITOREXIT_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.monitor(OpCodes.MONITOREXIT)
    val NOP_INSTRUCTION: EncodedInstruction =
            EncodedNopInstruction()

    // pop
    val POP_INSTRUCTION: EncodedInstruction =
            EncodedPopInstruction()
    val POP2_INSTRUCTION: EncodedInstruction =
            NoArgInstruction.pop(OpCodes.POP2, -2)
    val SWAP_INSTRUCTION: EncodedInstruction = EncodedSwapInstruction()
    val ARRAYLENGTH_INSTRUCTION: EncodedInstruction = EncodedArrayLengthInstruction()
    val ATHROW_INSTRUCTION: EncodedInstruction = EncodedAThrowInstruction()
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
            EncodedDLoadNInstruction(OpCodes.DLOAD_0),
            EncodedDLoadNInstruction(OpCodes.DLOAD_1),
            EncodedDLoadNInstruction(OpCodes.DLOAD_2),
            EncodedDLoadNInstruction(OpCodes.DLOAD_3))
    val FLOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedFLoadNInstruction(OpCodes.FLOAD_0),
            EncodedFLoadNInstruction(OpCodes.FLOAD_1),
            EncodedFLoadNInstruction(OpCodes.FLOAD_2),
            EncodedFLoadNInstruction(OpCodes.FLOAD_3))
    val ILOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedILoadNInstruction(OpCodes.ILOAD_0),
            EncodedILoadNInstruction(OpCodes.ILOAD_1),
            EncodedILoadNInstruction(OpCodes.ILOAD_2),
            EncodedILoadNInstruction(OpCodes.ILOAD_3))
    val LLOAD_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedLLoadNInstruction(OpCodes.LLOAD_0),
            EncodedLLoadNInstruction(OpCodes.LLOAD_1),
            EncodedLLoadNInstruction(OpCodes.LLOAD_2),
            EncodedLLoadNInstruction(OpCodes.LLOAD_3))
    val ASTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedAStoreNInstruction(OpCodes.ASTORE_0),
            EncodedAStoreNInstruction(OpCodes.ASTORE_1),
            EncodedAStoreNInstruction(OpCodes.ASTORE_2),
            EncodedAStoreNInstruction(OpCodes.ASTORE_3))
    val DSTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedDStoreNInstruction(OpCodes.DSTORE_0),
            EncodedDStoreNInstruction(OpCodes.DSTORE_1),
            EncodedDStoreNInstruction(OpCodes.DSTORE_2),
            EncodedDStoreNInstruction(OpCodes.DSTORE_3))
    val FSTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedFStoreNInstruction(OpCodes.FSTORE_0),
            EncodedFStoreNInstruction(OpCodes.FSTORE_1),
            EncodedFStoreNInstruction(OpCodes.FSTORE_2),
            EncodedFStoreNInstruction(OpCodes.FSTORE_3))
    val ISTORE_INSTRUCTIONS: Array<EncodedInstruction> = arrayOf<EncodedInstruction>(
            EncodedIStoreNInstruction(
                    OpCodes.ISTORE_0),
            EncodedIStoreNInstruction(
                    OpCodes.ISTORE_1),
            EncodedIStoreNInstruction(
                    OpCodes.ISTORE_2),
            EncodedIStoreNInstruction(
                    OpCodes.ISTORE_3))
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