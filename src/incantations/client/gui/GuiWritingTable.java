package incantations.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import incantations.incantation.Symbol;
import incantations.inventory.ContainerWritingTable;
import incantations.util.LanguageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import incantations.tileentity.TileEntityWritingDesk;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiWritingTable extends GuiContainer {

	private final TileEntityWritingDesk writingDesk;
	private short scrollAmount;
	private boolean hasScroll = false;
	private static final ResourceLocation writingDeskTexture = new ResourceLocation("incantations", "textures/gui/container/writingdesk.png");

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
		this.symbolButtonMap.clear();
		for (Map.Entry<String, Symbol> entry:Symbol.symbolMap.entrySet()) {
			this.symbolButtonMap.put(i, entry.getValue());
			if (i <= 13) {
				//if even, draw to the left hand side
				if (i % 2 == 0) this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 177, this.guiTop + 5 + (i * 9), 20, 20));
				else this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 196, this.guiTop + 5 + ((i - 1) * 9), 20, 20));
				//System.out.println("Moved button to " + (this.guiLeft + 177) + ", " + (this.guiTop + 5 + (i * 9)));
			}
			else {
				ButtonWritingDeskSymbol buttonWritingDeskSymbol = new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 177, this.guiTop + 200, 20, 20);
				buttonWritingDeskSymbol.drawButton = false;
				this.buttonList.add(buttonWritingDeskSymbol);
			}
			i++;
		}
		this.buttonList.add(new ButtonWritingDeskScroll(this, -1, this.guiLeft + 177, this.guiTop, 177, 0, 30, 5));
		this.buttonList.add(new ButtonWritingDeskScroll(this, -2, this.guiLeft + 177, this.guiTop + 134, 177, 134, 30, 5));
		//Clear the contents as this method is called on window rescale as well to prevent render duping
		this.scrollContentsArray.clear();
		//Loads the symbols
		this.loadScrollData();
		LanguageUtil.loadTranslationList();
	}

	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		//Reload the data each screen draw to prevent dupe renders
		this.scrollContentsArray.clear();
		if (this.writingDesk.getStackInSlot(-1) != null) this.loadScrollData();
		this.drawScroll();
	}

	private void loadScrollData() {
		ItemStack itemStack = this.writingDesk.getStackInSlot(-1);
		if ((itemStack != null) && (itemStack.hasTagCompound())) {
			hasScroll = true;
			NBTTagCompound tagCompound = itemStack.getTagCompound();
			String incantation = tagCompound.getString("incantation");
			String[] characters = incantation.split("¦");
			for (String character : characters) {
				if (character != null) this.scrollContentsArray.add(character);
			}
			this.drawScroll();
		}
	}

	protected void actionPerformed(GuiButton guiButton) {
		if (this.symbolButtonMap.containsKey(guiButton.id)) {
			if (this.scrollContentsArray.size() >= 90) return;
			//Check we have all needed equipment
			if ((this.writingDesk.getStackInSlot(-3) != null) && (this.writingDesk.getStackInSlot(-5) != null) && (this.writingDesk.getStackInSlot(-1) != null)) {
				this.writingDesk.onInventoryChanged();
				this.scrollContentsArray.add(symbolButtonMap.get(guiButton.id).getIdentifier());

				//Send scroll data to server
				ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
				DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
				try {
					System.out.println(this.scrollContentsArray);
					dataoutputstream.writeUTF(this.makeIncantationString(this.scrollContentsArray));
					this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload("INC|WritingDesk", bytearrayoutputstream.toByteArray()));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//If scroll button is hit, scroooooooooll
		else if (guiButton.id == -2) {
			if (scrollAmount < 10) {
				scrollAmount++;
				int j = 0;
				for (Object object:this.buttonList) {
					if (j > (13 + (scrollAmount * 2))) break;
					GuiButton button = (GuiButton) object;
					if (button instanceof ButtonWritingDeskSymbol) {
						if (button.id <= ((scrollAmount * 2) - 1)) {
							//Hide and disable the button as we have scrolled past it
							button.drawButton = false;
							button.enabled = false;
							button.yPosition = 1;
							button.xPosition = 500;
						}
						else {
							button.drawButton = true;
							button.enabled = true;
							if (j % 2 == 0) {
								button.xPosition = this.guiLeft + 177;
								button.yPosition = this.guiTop + 5 + ((j - (scrollAmount * 2)) * 9);
							}
							else {
								button.xPosition = this.guiLeft + 196;
								button.yPosition = this.guiTop + 5 + (((j - (scrollAmount * 2)) - 1) * 9);
							}
						}
					}
					j++;
				}
			}
		}
		//Scroll up
		else if (guiButton.id == -1) {
			if (scrollAmount > 0) {
				scrollAmount--;
				int j = 0;
				for (Object object:this.buttonList) {
					if (j > (15 + (scrollAmount * 2))) break;
					GuiButton button = (GuiButton) object;
					if (button instanceof ButtonWritingDeskSymbol) {
						if (button.id >= (15 + ((scrollAmount * 2) - 1))) {
							//Hide and disable the button as we have scrolled past it
							button.drawButton = false;
							button.enabled = false;
							button.yPosition = 1;
							button.xPosition = 500;
						}
						else {
							button.drawButton = true;
							button.enabled = true;
							if (j % 2 == 0) {
								button.xPosition = this.guiLeft + 177;
								button.yPosition = this.guiTop + 5 + ((j - (scrollAmount * 2)) * 9);
							}
							else {
								button.xPosition = this.guiLeft + 196;
								button.yPosition = this.guiTop + 5 + (((j - (scrollAmount * 2)) - 1) * 9);
							}
						}
					}
					j++;
				}
			}
		}
	}

	private String makeIncantationString(ArrayList<String> incantationList) {
		StringBuilder sb = new StringBuilder();
		for (String character:incantationList) {
			sb.append(character);
			sb.append("¦");
		}
		return sb.toString();
	}

	private void drawScroll() {
		int line = 0;
		int currentWidth = 0;
		int maxWidth = 100;
		for (int i = 0; i < scrollContentsArray.size(); i++) {
			Symbol symbol = Symbol.symbolMap.get(this.scrollContentsArray.get(i));
			if (symbol != null) {
				if (symbol.getIdentifier().equals("⏎")) {
					line++;
					currentWidth = 0;
				}
				else if (symbol.getTexture() != null) {
					if ((currentWidth + symbol.getWidth() > maxWidth)) {
						line++;
						currentWidth = symbol.getWidth();
					}
					else currentWidth += symbol.getWidth();
					Minecraft.getMinecraft().getTextureManager().bindTexture(symbol.getTexture());
					this.drawScrollSymbol(this.guiLeft + 11 + currentWidth, this.guiTop + 16 + (line * 11), symbol.getU(), symbol.getV(), 12, 12);
				}
			}
		}
	}

	private void drawScrollSymbol(int par1, int par2, int par3, int par4, int par5, int par6) {
		float f = 0.08F;
		float f1 = 0.08F;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawTooltip(String string, int x, int y) {
		super.drawCreativeTabHoveringText(string, x, y);
	}

	public ResourceLocation getGuiTextures() {
		return writingDeskTexture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		RenderHelper.disableStandardItemLighting();
		for (Object aButtonList : this.buttonList) {
			GuiButton guibutton = (GuiButton) aButtonList;
			if (guibutton.func_82252_a()) {
				guibutton.func_82251_b(par1 - this.guiLeft, par2 - this.guiTop);
				break;
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
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
