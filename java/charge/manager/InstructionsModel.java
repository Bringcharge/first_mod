package charge.manager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;

public class InstructionsModel {    //һ��Model,���ڴ�Ÿ�����Ҫ��������ݽṹ
    public Entity user;
    public Item holder;
    //����ֵ
    public Entity targetEntity;
    public Vec3d targetVec;
    //

    public void copy(InstructionsModel oldModel) { //��������һ��������ͬ��ʵ��������ʵ��������ͬһ��ָ��
        this.holder = oldModel.holder;
        this.user = oldModel.user;
        this.targetEntity = oldModel.targetEntity;
        this.targetVec = oldModel.targetVec;
    }
}
