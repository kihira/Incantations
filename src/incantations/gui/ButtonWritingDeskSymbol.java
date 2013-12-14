package incantations.gui;

import incantations.incantation.Symbol;
import net.minecraft.client.gui.GuiButton;

public class ButtonWritingDeskSymbol extends GuiButton {

	private final GuiWritingTable guiWritingTable;
	private final Symbol symbol;

	public ButtonWritingDeskSymbol(GuiWritingTable guiWritingTable, Symbol symbol, int id, int xPos, int yPos, int height, int width) {
		super(id, xPos, yPos, height, width, "");
		this.guiWritingTable = guiWritingTable;
		this.symbol = symbol;
	}

	//This draws text in a tooltip when mouse is hovered over the button. Draw the crude translation of the symbol
	public void func_82251_b(int par1, int par2) {
		this.guiWritingTable.drawTooltip(symbol.getIdentifier(), par1, par2);
	}
}
