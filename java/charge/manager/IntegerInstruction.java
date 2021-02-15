package charge.manager;

import net.minecraft.entity.Entity;

public class IntegerInstruction {
    public static int parser(Instruction instruction, InstructionsModel owner) {    //输入口

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

        if (order != null && order.substring(0,2).equals("II")) {
            String integer = order.substring(2,order.length()-1);   //去掉#命令符
            return Integer.parseInt(integer);
        }

        return unexpectedInput();
    }

    private static int unexpectedInput() {  //错误走这
        return 0;
    }
}
