package incantations.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import incantations.incantation.Symbol;
import incantations.inventory.ContainerWritingTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import incantations.tileentity.TileEntityWritingDesk;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiWritingTable extends GuiContainer {

	private final TileEntityWritingDesk writingDesk;
	private short scrollAmount;
	private static final ResourceLocation writingDeskTexture = new ResourceLocation("incantations", "textures/gui/container/writingtable.png");

	private final HashMap<Integer, Symbol> symbolButtonMap = new HashMap<Integer, Symbol>();
	//This is what is written on the scroll.
	private final ArrayList<String> scrollContentsArray = new ArrayList<String>();

	public GuiWritingTable(InventoryPlayer inventoryPlayer, TileEntityWritingDesk tileEntityWritingDesk) {
		super(new ContainerWritingTable(inventoryPlayer, tileEntityWritingDesk));
		this.writingDesk = tileEntityWritingDesk;
		this.xSize = 240;
		this.ySize = 230;
	}

	@Override
	public void initGui() {
		super.initGui();
		int i = 0;
		for (Map.Entry<String, Symbol> entry:Symbol.symbolMap.entrySet()) {
			symbolButtonMap.clear();
			symbolButtonMap.put(i, entry.getValue());
			if (i <= 17) {
				//if even, draw to the left hand side
				if (i % 2 == 0) this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 177, this.guiTop + 5 + (i * 7), 14, 14));
				else this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 191, this.guiTop + 5 + ((i - 1) * 7), 14, 14));
			}
			else {
				ButtonWritingDeskSymbol buttonWritingDeskSymbol = new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 177, this.guiTop + 200, 15, 15);
				buttonWritingDeskSymbol.drawButton = false;
				this.buttonList.add(buttonWritingDeskSymbol);
			}
			i++;
		}
		this.buttonList.add(new ButtonWritingDeskScroll(this, -1, this.guiLeft + 177, this.guiTop, 177, 0, 30, 5));
		//TODO Fix this button
		//this.buttonList.add(new ButtonWritingDeskScroll(this, -2, this.guiLeft + 177, this.guiTop + 150, 177, 150, 30, 2));
	}

	protected void actionPerformed(GuiButton guiButton) {
		System.out.println("Yay you pressed " + guiButton.id);
		if (symbolButtonMap.containsKey(guiButton.id)) {
			scrollContentsArray.add(symbolButtonMap.get(guiButton.id).getIdentifier());
			//this.updateScreen();
		}
	}

	//TODO Draws the symbols on the scrolls
	private void drawScrollSymbols() {

	}

	public void drawTooltip(String string, int x, int y) {
		super.drawCreativeTabHoveringText(string, x, y);
	}

	public ResourceLocation getGuiTextures() {
		return writingDeskTexture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		/*
		RenderHelper.disableStandardItemLighting();
		for (Object aButtonList : this.buttonList) {
			GuiButton guibutton = (GuiButton) aButtonList;
			if (guibutton.func_82252_a()) {
				guibutton.func_82251_b(par1 - this.guiLeft, par2 - this.guiTop);
				break;
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
		*/
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(writingDeskTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
