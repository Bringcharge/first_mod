package charge.entity;

import charge.manager.Instruction;
import charge.manager.InstructionsManager;
import charge.manager.InstructionsModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.BiConsumer;


public class ChargeArrow extends ArrowEntity {
    public ChargeArrow(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }
    public ChargeArrow(World worldIn, double x, double y, double z) {
        super(worldIn,x,y,z);
    }
    public ChargeArrow(World worldIn, LivingEntity shooter) { super(worldIn,shooter); }

    private BiConsumer<Instruction, InstructionsModel> biConsumer;

    public void setBack(BiConsumer<Instruction, InstructionsModel>  biConsumer) {
        this.biConsumer = biConsumer;
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) { //����entity
        super.onHit(raytraceResultIn);
        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            EntityRayTraceResult entityRes = (EntityRayTraceResult)raytraceResultIn;
            //�������ص���ʽ
            Instruction instruction = new Instruction();
            InstructionsModel instructionsModel = new InstructionsModel();
            if (this.biConsumer != null) {
                this.biConsumer.accept(instruction,instructionsModel);  //����Ѱ�����
                instructionsModel.targetEntity = entityRes.getEntity();    //�����������
                instructionsModel.targetVec = entityRes.getHitVec();       //��ʱ�����Ĳ���
                InstructionsManager.functionWithString(instruction, instructionsModel); //���ú���
            }
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {  //���е���
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
            Vec3d vec3d = blockraytraceresult.getHitVec();
            //�������ص���ʽ
            Instruction instruction = new Instruction();
            InstructionsModel instructionsModel = new InstructionsModel();
            if (this.biConsumer != null) {
                this.biConsumer.accept(instruction,instructionsModel);  //����Ѱ�����
                instructionsModel.targetVec = vec3d;    //�����������
                InstructionsManager.functionWithString(instruction, instructionsModel);  //���ú���
            }
        }
    }
}
