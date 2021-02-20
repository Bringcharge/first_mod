package charge.manager;

import net.minecraft.entity.Entity;

public class DoubleInstruction {
    public static double parser(Instruction instruction, InstructionsModel owner) {    //输入

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

        if (order != null && order.substring(0,2).equals("DI")) {
            String dou = order.substring(2,order.length()-1);   //去掉#命令符
            return Double.parseDouble(dou);
        }

        return unexpectedInput();
    }

    private static double unexpectedInput() {  //错误走这
        return 0;
    }
}
