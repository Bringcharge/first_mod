package charge.manager;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.NBTTextComponent;


//中转器，传入参数统一有string（命令）和entity（触发者）两个，其余的功能依赖这两个功能实现。需要重构，带额外参数的话，再把entity封装成package

public class InstructionsManager {
    public static Entity entityWithString(Instruction instruction, InstructionsModel owner) { //返还Entity的方法
        return EntityInstruction.parser(instruction, owner);
    }

    public static Vec3d vecWithString(Instruction instruction, InstructionsModel owner) { //返还Vec3d的方法
        return VecInstruction.parser(instruction, owner);
    }

    public static int integerWithString(Instruction instruction, InstructionsModel owner) { //返还integer的方法
        return IntegerInstruction.parser(instruction, owner);
    }

    public static double doubleWithString(Instruction instruction, InstructionsModel owner) { //返还double的方法
        return DoubleInstruction.parser(instruction, owner);
    }

    public static void functionWithString(Instruction instruction, InstructionsModel owner) { //只是去执行函数的方法
        FunctionInstruction.parser(instruction, owner);
    }

    public static boolean booleanWithString(Instruction instruction, InstructionsModel owner) { //bool类型的判断
        return BooleanInstruction.parser(instruction, owner);
    }

    public static String instructionsBlocck (Instruction instruction, InstructionsModel owner) { //非自动机部分，用于处理代码块
        String order = null;
        int stackCount = 0;
        for (int i = 0; i<instruction.str.length(); i++) {
            if (instruction.str.charAt(i) == '{') {
                stackCount++;
            }
            if (instruction.str.charAt(i) == '}') {
                stackCount--;
                if (stackCount == 0) {
                    order = instruction.str.substring(1, i);    //拿出命令
                    if (i < instruction.str.length()) {
                        instruction.str = instruction.str.substring(i + 1); //设置新的值
                    }
                    break;
                }
            }
        }
        return order;
    }
}
