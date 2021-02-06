package charge.manager;

import charge.manager.fucntion.FunctionsManager;
import net.minecraft.util.math.Vec3d;

public class FunctionInstruction {
    public static void parser(String instruction, InstructionsModel owner) {    //输入口

        String order = null;
        for (int i = 0; i<instruction.length(); i++) {
            if (instruction.charAt(i) == '#') {
                order = instruction.substring(0, i+1);    //拿出命令
                if (i < instruction.length() -1) {
                    instruction = instruction.substring(i + 1); //设置新的值
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

        /*
        ...更多的函数
         */


        unexpectedInput();
    }

    private static void unexpectedInput() {  //所有的错误走这

    }
}
