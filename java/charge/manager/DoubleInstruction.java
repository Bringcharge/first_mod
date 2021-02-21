package charge.manager;

import net.minecraft.entity.Entity;

public class DoubleInstruction {
    public static double parser(Instruction instruction, InstructionsModel owner) {    //����

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

        if (order != null && order.substring(0,2).equals("DI")) {
            String dou = order.substring(2,order.length()-1);   //ȥ��#�����
            return Double.parseDouble(dou);
        }

        return unexpectedInput();
    }

    private static double unexpectedInput() {  //��������
        return 0;
    }
}
