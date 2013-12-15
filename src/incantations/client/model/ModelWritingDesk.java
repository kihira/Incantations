package incantations.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWritingDesk extends ModelBase {

	//fields
	ModelRenderer base;
	ModelRenderer writingtable;

	public ModelWritingDesk() {
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 16, 13, 16);
		base.setRotationPoint(-8F, 11F, -8F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		writingtable = new ModelRenderer(this, 0, 30);
		writingtable.addBox(0F, 0F, 0F, 11, 4, 14);
		writingtable.setRotationPoint(-7.5F, 10.46667F, -7F);
		writingtable.setTextureSize(128, 64);
		writingtable.mirror = true;
		setRotation(writingtable, 0.1919862F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		base.render(f5);
		writingtable.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}
