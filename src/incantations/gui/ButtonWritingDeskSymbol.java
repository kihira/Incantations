package incantations.gui;

import incantations.incantation.Symbol;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
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
	public void func_82251_b(int par1, int par2) {
		this.guiWritingTable.drawTooltip(symbol.getIdentifier(), par1, par2);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (this.drawButton) {
			par1Minecraft.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int k = this.getHoverState(this.field_82253_i);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
			this.mouseDragged(par1Minecraft, par2, par3);

			//TODO keep all textures in same file?
			par1Minecraft.getTextureManager().bindTexture(symbol.getTexture());
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.height, this.width);
			//System.out.println("Drawing a symbol at " + this.xPosition + " " + this.yPosition + " for button id " + this.id + " and symbol " + this.symbol.getIdentifier());
		}
	}
}
