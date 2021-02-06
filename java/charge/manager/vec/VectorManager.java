package charge.manager.vec;

import charge.manager.InstructionsModel;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VectorManager {
    //v00系列是处理
    public static Vec3d vecRevert(Vec3d vec1, InstructionsModel model) { //V001#
        return vec1.inverse();
    }

    //v01开始的是四则运算
    public static Vec3d vecAdd(Vec3d vec1, Vec3d vec2, InstructionsModel model) {   //V011# 向量加法
        return vec1.add(vec2);
    }

    public static Vec3d vecDec(Vec3d vec1, Vec3d vec2, InstructionsModel model) {   //V012# 向量减法 虽然没什么意义的减法，提供一个工具罢了
        return  vec1.add(vec2.inverse());
    }

    //V02是阻塞
    public static Vec3d vecBlockPos(Vec3d vec1, Vec3d vec2, InstructionsModel model) {  //V020# 从vec
        World worldIn = model.user.world;
        if (worldIn != null && model.user != null) {
            //查看阻塞位置
            BlockRayTraceResult blockRayTraceResult = worldIn.rayTraceBlocks(new RayTraceContext(vec1, vec2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, model.user));
            return blockRayTraceResult.getHitVec();
        }
        return null;
    }

    //V1开始是跟玩家相关的操作
    public static Vec3d vecPlayerLook(InstructionsModel model) {    //V101# 玩家视线
        if (model.user != null) {
            return model.user.getLookVec().normalize();
        }
        return null;
    }

    public static Vec3d vecPlayerPos(InstructionsModel model) {     //V102# 玩家位置
        if (model.user != null) {
            return model.user.getPositionVec();
        }
        return null;
    }

    public static Vec3d vecCameraPos(InstructionsModel model) {     //V103# 摄像机位置
        if (model.user != null) {
            return model.user.getPositionVec().add(new Vec3d(0,model.user.getEyeHeight(),0));
        }
        return null;
    }
}
