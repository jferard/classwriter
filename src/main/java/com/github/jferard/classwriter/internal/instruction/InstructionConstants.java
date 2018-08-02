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

package com.github.jferard.classwriter.internal.instruction;

import com.github.jferard.classwriter.OpCodes;
import com.github.jferard.classwriter.internal.attribute.stackmap.VerificationType;
import com.github.jferard.classwriter.internal.instruction.base.ALoadInstruction;
import com.github.jferard.classwriter.internal.instruction.base.AStoreInstruction;
import com.github.jferard.classwriter.internal.instruction.base.AThrowInstruction;
import com.github.jferard.classwriter.internal.instruction.base.BaseInstruction;
import com.github.jferard.classwriter.internal.instruction.base.ConstInstruction;
import com.github.jferard.classwriter.internal.instruction.base.ConvertInstruction;
import com.github.jferard.classwriter.internal.instruction.base.Dup2Instruction;
import com.github.jferard.classwriter.internal.instruction.base.Dup2X1Instruction;
import com.github.jferard.classwriter.internal.instruction.base.Dup2X2Instruction;
import com.github.jferard.classwriter.internal.instruction.base.DupInstruction;
import com.github.jferard.classwriter.internal.instruction.base.DupX1Instruction;
import com.github.jferard.classwriter.internal.instruction.base.DupX2Instruction;
import com.github.jferard.classwriter.internal.instruction.base.NoArgInstruction;
import com.github.jferard.classwriter.internal.instruction.base.ReturnInstruction;
import com.github.jferard.classwriter.internal.instruction.base.StoreNInstruction;

import java.util.Collections;

class InstructionConstants {
    // array load and store
    public static final BaseInstruction AALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.AALOAD,
            VerificationType.REFERENCE);
    public static final BaseInstruction AASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.AASTORE,
            VerificationType.REFERENCE);

    public static final BaseInstruction BALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.BALOAD,
            VerificationType.INTEGER);
    public static final BaseInstruction BASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.BASTORE,
            VerificationType.INTEGER);

    public static final BaseInstruction CALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.CALOAD,
            VerificationType.INTEGER);
    public static final BaseInstruction CASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.CASTORE,
            VerificationType.INTEGER);

    public static final BaseInstruction DALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.DALOAD,
            VerificationType.DOUBLE);
    public static final BaseInstruction DASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.DASTORE,
            VerificationType.DOUBLE);

    public static final BaseInstruction FALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.FALOAD,
            VerificationType.FLOAT);
    public static final BaseInstruction FASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.FASTORE,
            VerificationType.FLOAT);

    public static final BaseInstruction IALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.IALOAD,
            VerificationType.INTEGER);
    public static final BaseInstruction IASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.IASTORE,
            VerificationType.INTEGER);

    public static final BaseInstruction LALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.LALOAD,
            VerificationType.LONG);
    public static final BaseInstruction LASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.LASTORE,
            VerificationType.LONG);

    public static final BaseInstruction SALOAD_INSTRUCTION = new ALoadInstruction(OpCodes.SALOAD,
            VerificationType.INTEGER);
    public static final BaseInstruction SASTORE_INSTRUCTION = new AStoreInstruction(OpCodes.SASTORE,
            VerificationType.INTEGER);

    // binary
    public static final BaseInstruction DADD_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DADD, VerificationType.DOUBLE);
    public static final BaseInstruction DCMPG_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DCMPG, VerificationType.INTEGER);
    public static final BaseInstruction DCMPL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DCMPL, VerificationType.INTEGER);
    public static final BaseInstruction DDIV_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DDIV, VerificationType.DOUBLE);
    public static final BaseInstruction DMUL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DMUL, VerificationType.DOUBLE);
    public static final BaseInstruction DREM_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DREM, VerificationType.DOUBLE);
    public static final BaseInstruction DSUB_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.DSUB, VerificationType.DOUBLE);

    public static final BaseInstruction FADD_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FADD, VerificationType.FLOAT);
    public static final BaseInstruction FCMPG_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FCMPG, VerificationType.INTEGER);
    public static final BaseInstruction FCMPL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FCMPL, VerificationType.INTEGER);
    public static final BaseInstruction FDIV_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FDIV, VerificationType.FLOAT);
    public static final BaseInstruction FMUL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FMUL, VerificationType.FLOAT);
    public static final BaseInstruction FREM_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FREM, VerificationType.FLOAT);
    public static final BaseInstruction FSUB_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.FSUB, VerificationType.FLOAT);

    public static final BaseInstruction IADD_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IADD, VerificationType.INTEGER);
    public static final BaseInstruction IDIV_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IDIV, VerificationType.INTEGER);
    public static final BaseInstruction IMUL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IMUL, VerificationType.INTEGER);
    public static final BaseInstruction IREM_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IREM, VerificationType.INTEGER);
    public static final BaseInstruction ISUB_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.ISUB, VerificationType.INTEGER);

    public static final BaseInstruction IAND_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IAND, VerificationType.INTEGER);
    public static final BaseInstruction IOR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IOR, VerificationType.INTEGER);
    public static final BaseInstruction ISHL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.ISHL, VerificationType.INTEGER);
    public static final BaseInstruction ISHR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.ISHR, VerificationType.INTEGER);
    public static final BaseInstruction IUSHR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IUSHR, VerificationType.INTEGER);
    public static final BaseInstruction IXOR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.IXOR, VerificationType.INTEGER);

    public static final BaseInstruction LADD_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LADD, VerificationType.LONG);
    public static final BaseInstruction LCMP_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LCMP, VerificationType.INTEGER);
    public static final BaseInstruction LDIV_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LDIV, VerificationType.LONG);
    public static final BaseInstruction LMUL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LMUL, VerificationType.LONG);
    public static final BaseInstruction LREM_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LREM, VerificationType.LONG);
    public static final BaseInstruction LSUB_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LSUB, VerificationType.LONG);

    public static final BaseInstruction LAND_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LAND, VerificationType.LONG);
    public static final BaseInstruction LOR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LOR, VerificationType.LONG);
    public static final BaseInstruction LSHL_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LSHL, VerificationType.LONG);
    public static final BaseInstruction LSHR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LSHR, VerificationType.LONG);
    public static final BaseInstruction LUSHR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LUSHR, VerificationType.LONG);
    public static final BaseInstruction LXOR_INSTRUCTION = NoArgInstruction
            .binaryOp(OpCodes.LXOR, VerificationType.LONG);


    // unary
    public static final BaseInstruction DNEG_INSTRUCTION = NoArgInstruction
            .unary(OpCodes.DNEG, VerificationType.DOUBLE);
    public static final BaseInstruction FNEG_INSTRUCTION = NoArgInstruction
            .unary(OpCodes.FNEG, VerificationType.FLOAT);
    public static final BaseInstruction INEG_INSTRUCTION = NoArgInstruction
            .unary(OpCodes.INEG, VerificationType.INTEGER);
    public static final BaseInstruction LNEG_INSTRUCTION = NoArgInstruction
            .unary(OpCodes.LNEG, VerificationType.LONG);

    // push const
    public static final BaseInstruction ACONST_NULL_INSTRUCTION = new ConstInstruction(
            OpCodes.ACONST_NULL, VerificationType.NULL);

    public static final BaseInstruction DCONST_0_INSTRUCTION = new ConstInstruction(
            OpCodes.DCONST_0, VerificationType.DOUBLE);
    public static final BaseInstruction DCONST_1_INSTRUCTION = new ConstInstruction(
            OpCodes.DCONST_1, VerificationType.DOUBLE);

    public static final BaseInstruction FCONST_0_INSTRUCTION = new ConstInstruction(
            OpCodes.FCONST_0, VerificationType.FLOAT);
    public static final BaseInstruction FCONST_1_INSTRUCTION = new ConstInstruction(
            OpCodes.FCONST_1, VerificationType.FLOAT);
    public static final BaseInstruction FCONST_2_INSTRUCTION = new ConstInstruction(
            OpCodes.FCONST_2, VerificationType.FLOAT);
    public static final BaseInstruction LCONST_0_INSTRUCTION = new ConstInstruction(
            OpCodes.LCONST_0, VerificationType.LONG);
    public static final BaseInstruction LCONST_1_INSTRUCTION = new ConstInstruction(
            OpCodes.LCONST_1, VerificationType.LONG);
    // conversion
    public static final BaseInstruction D2F_INSTRUCTION = new ConvertInstruction(OpCodes.D2F,
            VerificationType.DOUBLE, VerificationType.FLOAT);
    public static final BaseInstruction D2I_INSTRUCTION = new ConvertInstruction(OpCodes.D2I,
            VerificationType.DOUBLE, VerificationType.INTEGER);
    public static final BaseInstruction D2L_INSTRUCTION = new ConvertInstruction(OpCodes.D2L,
            VerificationType.DOUBLE, VerificationType.LONG);
    public static final BaseInstruction F2D_INSTRUCTION = new ConvertInstruction(OpCodes.F2D,
            VerificationType.FLOAT, VerificationType.DOUBLE);
    public static final BaseInstruction F2I_INSTRUCTION = new ConvertInstruction(OpCodes.F2I,
            VerificationType.FLOAT, VerificationType.INTEGER);
    public static final BaseInstruction F2L_INSTRUCTION = new ConvertInstruction(OpCodes.F2L,
            VerificationType.FLOAT, VerificationType.LONG);
    public static final BaseInstruction I2D_INSTRUCTION = new ConvertInstruction(OpCodes.I2D,
            VerificationType.INTEGER, VerificationType.DOUBLE);
    public static final BaseInstruction I2F_INSTRUCTION = new ConvertInstruction(OpCodes.I2F,
            VerificationType.INTEGER, VerificationType.FLOAT);
    public static final BaseInstruction I2L_INSTRUCTION = new ConvertInstruction(OpCodes.I2L,
            VerificationType.INTEGER, VerificationType.LONG);
    public static final BaseInstruction I2B_INSTRUCTION = new ConvertInstruction(OpCodes.I2B,
            VerificationType.INTEGER, VerificationType.INTEGER);
    public static final BaseInstruction I2C_INSTRUCTION = new ConvertInstruction(OpCodes.I2C,
            VerificationType.INTEGER, VerificationType.INTEGER);
    public static final BaseInstruction I2S_INSTRUCTION = new ConvertInstruction(OpCodes.I2S,
            VerificationType.INTEGER, VerificationType.INTEGER);
    public static final BaseInstruction L2D_INSTRUCTION = new ConvertInstruction(OpCodes.L2D,
            VerificationType.LONG, VerificationType.DOUBLE);
    public static final BaseInstruction L2F_INSTRUCTION = new ConvertInstruction(OpCodes.L2F,
            VerificationType.LONG, VerificationType.FLOAT);
    public static final BaseInstruction L2I_INSTRUCTION = new ConvertInstruction(OpCodes.L2I,
            VerificationType.LONG, VerificationType.INTEGER);

    // misc
    public static final BaseInstruction BREAKPOINT_INSTRUCTION = NoArgInstruction
            .noLocalsNoSTack(OpCodes.BREAKPOINT);
    public static final BaseInstruction IMPDEP1_INSTRUCTION = NoArgInstruction
            .noLocalsNoSTack(OpCodes.IMPDEP1);
    public static final BaseInstruction IMPDEP2_INSTRUCTION = NoArgInstruction
            .noLocalsNoSTack(OpCodes.IMPDEP2);

    // dup
    public static final BaseInstruction DUP_INSTRUCTION = new DupInstruction();
    public static final BaseInstruction DUP_X1_INSTRUCTION = new DupX1Instruction();
    public static final BaseInstruction DUP_X2_INSTRUCTION = new DupX2Instruction();
    public static final BaseInstruction DUP2_INSTRUCTION = new Dup2Instruction();
    public static final BaseInstruction DUP2_X1_INSTRUCTION = new Dup2X1Instruction();
    public static final BaseInstruction DUP2_X2_INSTRUCTION = new Dup2X2Instruction();

    // return
    public static final BaseInstruction ARETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.ARETURN);
    public static final BaseInstruction DRETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.DRETURN);
    public static final BaseInstruction FRETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.FRETURN);
    public static final BaseInstruction IRETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.IRETURN);
    public static final BaseInstruction LRETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.LRETURN);
    public static final ReturnInstruction RETURN_INSTRUCTION = new ReturnInstruction(
            OpCodes.RETURN);

    // monitor
    public static final BaseInstruction MONITORENTER_INSTRUCTION = NoArgInstruction
            .monitor(OpCodes.MONITORENTER);
    public static final BaseInstruction MONITOREXIT_INSTRUCTION = NoArgInstruction
            .monitor(OpCodes.MONITOREXIT);

    public static final BaseInstruction NOP_INSTRUCTION = NoArgInstruction
            .noLocalsNoSTack(OpCodes.NOP);

    // pop
    public static final BaseInstruction POP_INSTRUCTION = NoArgInstruction.pop(OpCodes.POP, -1);
    public static final BaseInstruction POP2_INSTRUCTION = NoArgInstruction.pop(OpCodes.POP2, -2);

    public static final BaseInstruction SWAP_INSTRUCTION = NoArgInstruction
            .noLocalsNoSTack(OpCodes.SWAP);


    public static final BaseInstruction ARRAYLENGTH_INSTRUCTION = NoArgInstruction
            .noLocals(OpCodes.ARRAYLENGTH, 1, Collections.singletonList(VerificationType.INTEGER));

    public static final BaseInstruction ATHROW_INSTRUCTION = new AThrowInstruction();

    public static final BaseInstruction[] ICONST_INSTRUCTIONS = {
            new ConstInstruction(OpCodes.ICONST_M1, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_0, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_1, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_2, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_3, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_4, VerificationType.INTEGER),
            new ConstInstruction(OpCodes.ICONST_5, VerificationType.INTEGER)};

    public static final BaseInstruction[] ALOAD_INSTRUCTIONS = {
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.ALOAD_3, 3)};

    public static final BaseInstruction[] DLOAD_INSTRUCTIONS = {
            NoArgInstruction.longLoadN(OpCodes.DLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.DLOAD_3, 3)};

    public static final BaseInstruction[] FLOAD_INSTRUCTIONS = {
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.FLOAD_3, 3)};

    public static final BaseInstruction[] ILOAD_INSTRUCTIONS = {
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_0, 0),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_1, 1),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_2, 2),
            NoArgInstruction.stdLoadN(OpCodes.ILOAD_3, 3)};

    public static final BaseInstruction[] LLOAD_INSTRUCTIONS = {
            NoArgInstruction.longLoadN(OpCodes.LLOAD_0, 0),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_1, 1),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_2, 2),
            NoArgInstruction.longLoadN(OpCodes.LLOAD_3, 3)};

    public static final BaseInstruction[] ASTORE_INSTRUCTIONS = {
            new StoreNInstruction(OpCodes.ASTORE_0, 0, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ASTORE_1, 1, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ASTORE_2, 2, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ASTORE_3, 3, VerificationType.INTEGER)};

    public static final BaseInstruction[] DSTORE_INSTRUCTIONS = {
            new StoreNInstruction(OpCodes.DSTORE_0, 0, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.DSTORE_1, 1, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.DSTORE_2, 2, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.DSTORE_3, 3, VerificationType.INTEGER)};

    public static final BaseInstruction[] FSTORE_INSTRUCTIONS = {
            new StoreNInstruction(OpCodes.FSTORE_0, 0, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.FSTORE_1, 1, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.FSTORE_2, 2, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.FSTORE_3, 3, VerificationType.INTEGER)};

    public static final BaseInstruction[] ISTORE_INSTRUCTIONS = {
            new StoreNInstruction(OpCodes.ISTORE_0, 0, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ISTORE_1, 1, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ISTORE_2, 2, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.ISTORE_3, 3, VerificationType.INTEGER)};

    public static final BaseInstruction[] LSTORE_INSTRUCTIONS = {
            new StoreNInstruction(OpCodes.LSTORE_0, 0, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.LSTORE_1, 1, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.LSTORE_2, 2, VerificationType.INTEGER),
            new StoreNInstruction(OpCodes.LSTORE_3, 3, VerificationType.INTEGER)};
}
