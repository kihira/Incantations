package incantations.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class ButtonWritingDeskScroll extends GuiButton {

	private final GuiWritingTable guiWritingTable;
	private final int uPos;
	private final int vPos;

	public ButtonWritingDeskScroll(GuiWritingTable guiWritingTable, int id, int xPos, int yPos, int uPos, int vPos, int width, int height) {
		super(id, xPos, yPos, width, height, "");
		this.guiWritingTable = guiWritingTable;
		this.uPos = uPos;
		this.vPos = vPos;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		//TODO keep all textures in same file?
		par1Minecraft.getTextureManager().bindTexture(guiWritingTable.getGuiTextures());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(this.xPosition, this.yPosition, this.uPos, this.vPos, this.width, this.height);
	}
}
