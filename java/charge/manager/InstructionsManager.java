package charge.manager;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.NBTTextComponent;


//��ת�����������ͳһ��string�������entity�������ߣ�����������Ĺ�����������������ʵ�֡���Ҫ�ع�������������Ļ����ٰ�entity��װ��package

public class InstructionsManager {
    public static Entity entityWithString(Instruction instruction, InstructionsModel owner) { //����Entity�ķ���
        return EntityInstruction.parser(instruction, owner);
    }

    public static Vec3d vecWithString(Instruction instruction, InstructionsModel owner) { //����Vec3d�ķ���
        return VecInstruction.parser(instruction, owner);
    }

    public static int integerWithString(Instruction instruction, InstructionsModel owner) { //����integer�ķ���
        return IntegerInstruction.parser(instruction, owner);
    }

    public static double doubleWithString(Instruction instruction, InstructionsModel owner) { //����double�ķ���
        return DoubleInstruction.parser(instruction, owner);
    }

    public static void functionWithString(Instruction instruction, InstructionsModel owner) { //ֻ��ȥִ�к����ķ���
        FunctionInstruction.parser(instruction, owner);
    }

    public static boolean booleanWithString(Instruction instruction, InstructionsModel owner) { //bool���͵��ж�
        return BooleanInstruction.parser(instruction, owner);
    }

    public static String instructionsBlocck (Instruction instruction, InstructionsModel owner) { //���Զ������֣����ڴ�������
        String order = null;
        int stackCount = 0;
        if (instruction.str.charAt(0) != '{') {
            return "";
        }
        for (int i = 0; i<instruction.str.length(); i++) {
            if (instruction.str.charAt(i) == '{') {
                stackCount++;
            }
            if (instruction.str.charAt(i) == '}') {
                stackCount--;
                if (stackCount == 0) {
                    order = instruction.str.substring(1, i);    //�ó�����
                    if (i < instruction.str.length()) {
                        instruction.str = instruction.str.substring(i + 1); //�����µ�ֵ
                    }
                    break;
                }
            }
        }
        return order;
    }
}
