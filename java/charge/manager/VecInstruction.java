package charge.manager;

import charge.manager.vec.VectorManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class VecInstruction {
    public static Vec3d parser(Instruction instruction, InstructionsModel owner) {    //输入口

        String order = null;
        for (int i = 0; i<instruction.str.length(); i++) {
            if (instruction.str.charAt(i) == '#') {
                order = instruction.str.substring(0, i+1);    //拿出命令
                if (i < instruction.str.length() -1) {
                    instruction.str = instruction.str.substring(i + 1); //设置新的值
                }
                break;
            }
        }

        if (order != null && order.equals("V001#")) {   //向量取反
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            if (p1 == null) {
                return  null;
            }
            return VectorManager.vecRevert(p1,owner);
        }

        if (order != null && order.equals("V002#")) {   //向量缩放
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            int p2 = InstructionsManager.integerWithString(instruction,owner);
            if (p1 == null) {
                return  null;
            }
            return VectorManager.vecScale(p1,p2,owner);
        }

        if (order != null && order.equals("V011#")) {   //向量加法
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            Vec3d p2 = InstructionsManager.vecWithString(instruction,owner);
            if (p1 == null || p2 == null) {
                return  null;
            }
            return VectorManager.vecAdd(p1,p2,owner);
        }

        if (order != null && order.equals("V012#")) {   //向量减法
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            Vec3d p2 = InstructionsManager.vecWithString(instruction,owner);
            if (p1 == null || p2 == null) {
                return  null;
            }
            return VectorManager.vecDec(p1,p2,owner);
        }

        if (order != null && order.equals("V101#")) {   //玩家视线
            return VectorManager.vecPlayerLook(owner);
        }

        if (order != null && order.equals("V102#")) {   //玩家位置
            return VectorManager.vecPlayerPos(owner);
        }

        if (order != null && order.equals("V103#")) {   //摄像机位置
            return VectorManager.vecCameraPos(owner);
        }

        return unexpectedInput();
    }

    private static Vec3d unexpectedInput() {  //所有的错误走这
        return null;
    }
}
