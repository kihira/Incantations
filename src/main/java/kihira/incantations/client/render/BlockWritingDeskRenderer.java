package kihira.incantations.client.render;

import kihira.incantations.client.model.ModelWritingDesk;
import kihira.incantations.proxy.ProxyClient;
import kihira.incantations.tileentity.TileEntityWritingDesk;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class BlockWritingDeskRenderer extends TileEntitySpecialRenderer {

	private final ModelWritingDesk modelWritingDesk;

	public BlockWritingDeskRenderer() {
		modelWritingDesk = new ModelWritingDesk();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslated(x + 0.5d, y + 1.5001d, z + 0.5d);
		//GL11.glRotatef(180F, 0F, 0F, 1F);
		TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) tileentity;
		if (tileEntityWritingDesk.getBlockMetadata() == 2) GL11.glRotatef(180F, 0F, 0F, 1F);
		else if (tileEntityWritingDesk.getBlockMetadata() == 3) GL11.glRotatef(180F, 90F, 0F, 1F);
		else if (tileEntityWritingDesk.getBlockMetadata() == 4) GL11.glRotatef(180F, 90F, 0F, 1F);
		else if (tileEntityWritingDesk.getBlockMetadata() == 5) GL11.glRotatef(180F, 0F, 0F, 1F);
		GL11.glScalef(1F, 1F, 1F);
		if (tileEntityWritingDesk.getStackInSlot(-1) != null) bindTexture(ProxyClient.writingDeskTextureScroll);
		else bindTexture(ProxyClient.writingDeskTexture);
		modelWritingDesk.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
