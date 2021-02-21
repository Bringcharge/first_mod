package charge.manager.vec;

import charge.manager.Instruction;
import charge.manager.InstructionsModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VectorManager {
    //v00ϵ���Ǵ���
    public static Vec3d vecRevert(Vec3d vec1, InstructionsModel model) { //V001# ȡ��
        return vec1.inverse();
    }

    public static Vec3d vecScale(Vec3d vec1, int int1, InstructionsModel model) {   //V002# ����
        return vec1.scale(int1);
    }
    //v01��ʼ������������
    public static Vec3d vecAdd(Vec3d vec1, Vec3d vec2, InstructionsModel model) {   //V011# �����ӷ�
        return vec1.add(vec2);
    }

    public static Vec3d vecDec(Vec3d vec1, Vec3d vec2, InstructionsModel model) {   //V012# �������� ��Ȼûʲô����ļ������ṩһ�����߰���
        return  vec1.add(vec2.inverse());
    }

    //V02������
    public static Vec3d vecBlockPos(Vec3d vec1, Vec3d vec2, InstructionsModel model) {  //V020# ��vec
        World worldIn = model.user.world;
        if (worldIn != null && model.user != null) {
            //�鿴����λ��
            BlockRayTraceResult blockRayTraceResult = worldIn.rayTraceBlocks(new RayTraceContext(vec1, vec2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, model.user));
            return blockRayTraceResult.getHitVec();
        }
        return null;
    }

    //V1��ʼ�Ǹ������صĲ���
    public static Vec3d vecPlayerLook(InstructionsModel model) {    //V101# �������
        if (model.user != null) {
            return model.user.getLookVec().normalize();
        }
        return null;
    }

    public static Vec3d vecPlayerPos(InstructionsModel model) {     //V102# ���λ��
        if (model.user != null) {
            return model.user.getPositionVec();
        }
        return null;
    }

    public static Vec3d vecCameraPos(InstructionsModel model) {     //V103# �����λ��
        if (model.user != null) {
            return model.user.getPositionVec().add(new Vec3d(0,model.user.getEyeHeight(),0));
        }
        return null;
    }

    //V2��һЩblockϵ�еĲ��������������Ĳ���
    public static Vec3d vecBlockTarget (InstructionsModel model) {  //V201# block���������Ĳ���
            return model.targetVec; //�����ǿ�
    }

    // V3��һЩ��entity�йص�����
    public static Vec3d vecEntityPos (Entity entity, InstructionsModel model) { //V301# ʵ��λ��
        if (entity != null) {
            return entity.getPositionVec();
        }
        return null;
    }
}
