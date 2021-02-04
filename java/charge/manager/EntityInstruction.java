package charge.manager;

import net.minecraft.entity.Entity;

public class EntityInstruction {
    public static Entity parser(String instruction, Entity owner) {    //输入口

        String order = null;
        for (int i = 0; i<instruction.length(); i++) {
            if (instruction.charAt(i) == '#') {
                order = instruction.substring(0, i);    //拿出命令
                if (i < instruction.length() -1) {
                    instruction = instruction.substring(i + 1); //设置新的值
                }
                break;
            }
        }

        if (order != null && order.equals("E123#")) {
            //return ...
        }

        return unexpectedInput();
    }

    private static Entity unexpectedInput() {  //所有的错误走这
        return null;
    }
}
