package charge.manager;

import charge.manager.bool.BooleanManager;
import net.minecraft.entity.Entity;

public class BooleanInstruction {
    public static boolean parser(Instruction instruction, InstructionsModel owner) {    //输入

        String order = null;
        for (int i = 0; i<instruction.str.length(); i++) {
            if (instruction.str.charAt(i) == '#') {
                order = instruction.str.substring(0, i+1);    //拿出命令
                if (i < instruction.str.length()) {
                    instruction.str = instruction.str.substring(i + 1); //设置新的值
                }
                break;
            }
        }

        if (order != null && order.equals("B001#")) {   //判断一个entity是不是空
            Entity p1 = InstructionsManager.entityWithString(instruction, owner);
            return BooleanManager.entityNotNull(p1, owner);
        }

        return unexpectedInput();
    }

    private static boolean unexpectedInput() {  //错误走这
        return false;
    }
}
