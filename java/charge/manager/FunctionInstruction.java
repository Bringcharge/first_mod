package charge.manager;

import charge.manager.fucntion.FunctionsManager;
import net.minecraft.util.math.Vec3d;

public class FunctionInstruction {
    public static void parser(Instruction instruction, InstructionsModel owner) {    //输入口

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

        if (order != null && order.equals("F001#")) {   //落雷
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            if (p1 == null) {
                return;
            }
            FunctionsManager.lightningDown(p1,owner);
            return;
        }

        if (order != null && order.equals("F002#")) {   //射箭
            Vec3d p1 = InstructionsManager.vecWithString(instruction,owner);
            Vec3d p2 = InstructionsManager.vecWithString(instruction,owner);
            double p3 = InstructionsManager.doubleWithString(instruction,owner);
            String p4 = InstructionsManager.InstructionsBlocck(instruction,owner);
            FunctionsManager.ShotArrow(p1,p2,p3,p4,owner);
        }
        /*
        ...更多的函数
         */


        unexpectedInput();
    }

    private static void unexpectedInput() {  //所有的错误走这

    }
}
