package incantations.gui;

import incantations.incantation.Symbol;
import net.minecraft.client.gui.GuiButton;

public class ButtonWritingDeskScroll extends GuiButton {

	private final GuiWritingTable guiWritingTable;
	private final Symbol symbol;

	public ButtonWritingDeskScroll(GuiWritingTable guiWritingTable, Symbol symbol, int id, int xPos, int yPos, int height, int width) {
		super(id, xPos, yPos, height, width, "");
		this.guiWritingTable = guiWritingTable;
		this.symbol = symbol;
	}
}
