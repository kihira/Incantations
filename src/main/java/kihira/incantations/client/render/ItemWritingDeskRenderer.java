package kihira.incantations.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import kihira.incantations.client.model.ModelWritingDesk;
import kihira.incantations.proxy.ClientProxy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemWritingDeskRenderer implements IItemRenderer {

	private final ModelWritingDesk modelWritingDesk;

	public ItemWritingDeskRenderer() {
		modelWritingDesk = new ModelWritingDesk();
	}
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				renderWritingDeskItem(0f, 0f, 0f, 1f);
				break;
			}
			case EQUIPPED: {
				renderWritingDeskItem(0.5f, 1.5f, 0.5f, 1f);
				break;
			}
			case INVENTORY: {
				renderWritingDeskItem(0f, 1f, 0f, 1f);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderWritingDeskItem(3f, 1.5f, 0.5f, 2f);
				break;
			}
			default: break;
		}
	}

	private void renderWritingDeskItem(float x, float y, float z, float scale) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ClientProxy.writingDeskTexture);
		GL11.glTranslated(x, y, z);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180.0f, 0f, 0f, 1f);
		modelWritingDesk.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
