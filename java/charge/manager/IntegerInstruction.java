package charge.manager;

import net.minecraft.entity.Entity;

public class IntegerInstruction {
    public static int parser(Instruction instruction, InstructionsModel owner) {    //�����

        String order = null;
        for (int i = 0; i<instruction.str.length(); i++) {
            if (instruction.str.charAt(i) == '#') {
                order = instruction.str.substring(0, i+1);    //�ó�����
                if (i < instruction.str.length()) {
                    instruction.str = instruction.str.substring(i + 1); //�����µ�ֵ
                }
                break;
            }
        }

        if (order != null && order.substring(0,2).equals("II")) {
            String integer = order.substring(2,order.length()-1);   //ȥ��#�����
            return Integer.parseInt(integer);
        }

        return unexpectedInput();
    }

    private static int unexpectedInput() {  //��������
        return 0;
    }
}
