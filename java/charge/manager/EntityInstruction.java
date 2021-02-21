package charge.manager;

import charge.manager.entity.EntityManager;
import net.minecraft.entity.Entity;

public class EntityInstruction {
    public static Entity parser(Instruction instruction, InstructionsModel owner) {    //输入口

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
        if (order != null && order.equals("E101#")) {
            return EntityManager.entityOfUser(owner);
        }

        if (order != null && order.equals("E201#")) {
            return EntityManager.entityBlockTarget(owner);
        }

        return unexpectedInput();
    }

    private static Entity unexpectedInput() {  //错误走这
        return null;
    }
}
