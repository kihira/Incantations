package incantations.client.gui;

import incantations.incantation.Symbol;
import incantations.util.LanguageUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiResearchBook extends GuiScreen {

	private final ResourceLocation bookTexture = new ResourceLocation("textures/gui/book.png");
	private int currentPage = 0;

	private NBTTagList wordsKnownList;

	private GuiButton prevPageButton;
	private GuiButton nextPageButton;

	public GuiResearchBook(ItemStack itemStack) {
		if (itemStack.hasTagCompound()) {
			this.wordsKnownList = itemStack.getTagCompound().getCompoundTag("research").getTagList("words");
		}
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		int i = (this.width - 192) / 2;
		this.buttonList.add(this.nextPageButton = new GuiButton(0, i + 120, 150 + (byte) 2, 23, 20, ">"));
		this.buttonList.add(this.prevPageButton = new GuiButton(1, i + 38, 150 + (byte) 2, 23, 20, "<"));
		this.updateButtons();
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) this.currentPage++;
		else if (par1GuiButton.id == 1) this.currentPage--;
		this.updateButtons();
	}

	public void drawScreen(int par1, int par2, float par3) {
		int j = (this.width - 192) / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.bookTexture);
		this.drawTexturedModalRect(j, (byte)2, 0, 0, 192, 192);

		//Draw the research
		if (this.wordsKnownList != null) {
			for (int i = 0; i < 4; i++) {
				if (i + (currentPage * 4) >= this.wordsKnownList.tagCount()) break;
				NBTTagString wordData = (NBTTagString) this.wordsKnownList.tagAt(i + (currentPage * 4));
				String word = wordData.data;
				String[] characters = word.split("|");
				int k = 0;
				//Draw the symbols first
				for (String character:characters) {
					Symbol symbol = Symbol.symbolMap.get(character);
					if (symbol != null) {
						if (k > 11) break;
						this.mc.getTextureManager().bindTexture(symbol.getTexture());
						drawScrollSymbol(j + 37 + (k * symbol.getWidth()), 20 + (i * 33), symbol.getU(), symbol.getV(), 12, 12);
						k++;
					}
					/*
					else {
						symbol = Symbol.symbolMap.get("?");
						this.mc.getTextureManager().bindTexture(symbol.getTexture());
						drawScrollSymbol(j + 37 + (k * symbol.getWidth()), 20 + (i * 30), symbol.getU(), symbol.getV(), 12, 12);
					}
					k++;
					*/
				}
				word = LanguageUtil.cleanIncantation(word);
				//Then draw the dirty english version
				this.fontRenderer.drawString(word, j + 39, 33 + (i * 33), 0);
				//Now the English translation
				this.fontRenderer.drawString(LanguageUtil.getEnglishWord(word), j + 39, 42 + (i * 33), 0);
			}
		}
		super.drawScreen(par1, par2, par3);
	}

	private void updateButtons() {
		this.prevPageButton.drawButton = this.currentPage != 0;
		this.nextPageButton.drawButton = this.currentPage < this.wordsKnownList.tagCount()/4;
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
}
