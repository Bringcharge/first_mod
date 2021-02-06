package charge.manager;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.NBTTextComponent;


//中转器，传入参数统一有string（命令）和entity（触发者）两个，其余的功能依赖这两个功能实现。需要重构，带额外参数的话，再把entity封装成package

public class InstructionsManager {
    public static Entity entityWithString(String instruction, InstructionsModel owner) { //返还Entity的方法
        return EntityInstruction.parser(instruction, owner);
    }

    public static Vec3d vecWithString(String instruction, InstructionsModel owner) { //返还Vec3d的方法
        return VecInstruction.parser(instruction, owner);
    }

    public static int integerWithString(String instruction, InstructionsModel owner) { //返还integer的方法
        return IntegerInstruction.parser(instruction, owner);
    }

    public static double doubleWithString(String instruction, InstructionsModel owner) { //返还double的方法
        return DoubleInstruction.parser(instruction, owner);
    }

    public static void functionWithString(String instruction, InstructionsModel owner) { //只是去执行函数的方法
        FunctionInstruction.parser(instruction, owner);
    }
}
