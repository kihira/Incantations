package client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import incantation.Symbol;
import inventory.ContainerWritingTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import tileentity.TileEntityWritingDesk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiWritingTable extends GuiContainer {

	private TileEntityWritingDesk writingDesk;
	private static final ResourceLocation writingDeskTexture = new ResourceLocation("incantations", "textures/gui/container/writingtable.png");

	private final HashMap<Integer, Symbol> symbolButtonMap = new HashMap<Integer, Symbol>();
	//This is what is written on the scroll.
	private final ArrayList<String> scrollContentsArray = new ArrayList<String>();

	public GuiWritingTable(InventoryPlayer inventoryPlayer, TileEntityWritingDesk tileEntityWritingDesk) {
		super(new ContainerWritingTable(inventoryPlayer, tileEntityWritingDesk));
		this.writingDesk = tileEntityWritingDesk;
		this.xSize = 230;
		this.ySize = 230;
	}

	@Override
	public void initGui() {
		super.initGui();
		int i = 0;
		for (Map.Entry<String, Symbol> entry:Symbol.symbolMap.entrySet()) {
			symbolButtonMap.clear();
			symbolButtonMap.put(i, entry.getValue());
			i++;
		}
		//this.buttonList.add();
	}

	protected void actionPerformed(GuiButton guiButton) {
		if (symbolButtonMap.containsKey(guiButton.id)) {
			scrollContentsArray.add(symbolButtonMap.get(guiButton.id).getIdentifier());
			this.updateScreen();
		}
	}

	//TODO Draws the symbols on the scrolls
	private void drawScrollSymbols() {

	}

	public void drawTooltip(String string, int x, int y) {
		super.drawCreativeTabHoveringText(string, x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

	}
}
