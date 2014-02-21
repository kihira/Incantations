package kihira.incantations.client.gui;

import kihira.incantations.incantation.Symbol;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class ButtonWritingDeskSymbol extends GuiButton {

	private final GuiWritingTable guiWritingTable;
	private final Symbol symbol;

	public ButtonWritingDeskSymbol(GuiWritingTable guiWritingTable, Symbol symbol, int id, int xPos, int yPos, int width, int height) {
		super(id, xPos, yPos, width, height, symbol.getIdentifier());
		this.guiWritingTable = guiWritingTable;
		this.symbol = symbol;
	}

	//This draws text in a tooltip when mouse is hovered over the button. Draw the crude translation of the symbol
	@Override
	public void func_146111_b(int par1, int par2) {
		this.guiWritingTable.drawTooltip(symbol.getIdentifier(), par1, par2);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (this.visible) {
			par1Minecraft.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int k = this.getHoverState(this.field_146123_n);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
			this.mouseDragged(par1Minecraft, par2, par3);

			if (symbol.getTexture() != null) {
				par1Minecraft.getTextureManager().bindTexture(symbol.getTexture());
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				this.drawScrollSymbol(this.xPosition + 2, this.yPosition + 2, 0, 0, 15, 15);
				GL11.glDisable(GL11.GL_BLEND);
			}
			else {
				this.drawCenteredString(par1Minecraft.fontRenderer, "§0" + this.symbol.getIdentifier(), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
			}
		}
	}

	private void drawScrollSymbol(int par1, int par2, int par3, int par4, int par5, int par6) {
		float f = 0.065F;
		float f1 = 0.065F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
		tessellator.draw();
	}
}
