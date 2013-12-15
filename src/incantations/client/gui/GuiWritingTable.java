package incantations.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import incantations.incantation.Symbol;
import incantations.inventory.ContainerWritingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
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
		symbolButtonMap.clear();
		for (Map.Entry<String, Symbol> entry:Symbol.symbolMap.entrySet()) {
			symbolButtonMap.put(i, entry.getValue());
			if (i <= 13) {
				//if even, draw to the left hand side
				if (i % 2 == 0) this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 177, this.guiTop + 5 + (i * 9), 20, 20));
				else this.buttonList.add(new ButtonWritingDeskSymbol(this, entry.getValue(), i, this.guiLeft + 196, this.guiTop + 5 + ((i - 1) * 9), 20, 20));
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

	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		//Render symbols on the scroll
		//Down
		boolean flag = false;
		for (int k = 0; k < 10; k++) {
			//Across
			for (int i = 0; i < 11; i++) {
				if (scrollContentsArray.size() <= (i + (k * 10)))  {
					flag = true;
					break;
				}
				Symbol symbol = Symbol.symbolMap.get(scrollContentsArray.get(i + (k * 10)));
				Minecraft.getMinecraft().getTextureManager().bindTexture(symbol.getTexture());
				this.drawScrollSymbol(this.guiLeft + 11 + (i * 10), this.guiTop + 16 + (k * 10), 0, 0, 12, 12);
			}
			if (flag) break;
		}
	}

	protected void actionPerformed(GuiButton guiButton) {
		if (this.symbolButtonMap.containsKey(guiButton.id)) {
			if (this.scrollContentsArray.size() >= 110) return;
			this.scrollContentsArray.add(symbolButtonMap.get(guiButton.id).getIdentifier());
			double offset = Math.ceil(this.scrollContentsArray.size() / 14F) * 10;
			Symbol symbol = this.symbolButtonMap.get(guiButton.id);
			Minecraft.getMinecraft().getTextureManager().bindTexture(symbol.getTexture());
			this.drawScrollSymbol(this.guiLeft + 11 + ((this.scrollContentsArray.size() % 14) * 10), this.guiTop + 15 + (int)offset, 0, 0, 12, 12);

			//Send scroll data to server
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
			try {
				dataoutputstream.writeUTF(this.makeIncantationString(this.scrollContentsArray));
				this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload("INC|WritingDesk", bytearrayoutputstream.toByteArray()));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String makeIncantationString(ArrayList<String> incantationList) {
		String s = "";
		for (String character:incantationList) {
			s += character;
		}
		return s;
	}

	//TODO Draws the symbols on the scrolls
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
