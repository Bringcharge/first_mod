package charge.manager;

import charge.manager.bool.BooleanManager;
import net.minecraft.entity.Entity;

public class BooleanInstruction {
    public static boolean parser(Instruction instruction, InstructionsModel owner) {    //����

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

        if (order != null && order.equals("B001#")) {   //�ж�һ��entity�ǲ��ǿ�
            Entity p1 = InstructionsManager.entityWithString(instruction, owner);
            return BooleanManager.entityNotNull(p1, owner);
        }

        return unexpectedInput();
    }

    private static boolean unexpectedInput() {  //��������
        return false;
    }
}
