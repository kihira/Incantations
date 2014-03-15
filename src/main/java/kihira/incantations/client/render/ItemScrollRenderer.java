package kihira.incantations.client.render;

import kihira.incantations.incantation.Symbol;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemScrollRenderer implements IItemRenderer {

    private final ResourceLocation scrollTexture = new ResourceLocation("incantations", "textures/gui/scrollOpen.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
            case FIRST_PERSON_MAP: return true;
            default: return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        switch (helper) {
            case INVENTORY_BLOCK: return false;
            default: return true;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case FIRST_PERSON_MAP: renderScroll((TextureManager) data[1], item);
        }
    }

    private void renderScroll(TextureManager textureManager, ItemStack itemStack) {
        byte b1 = 0;
        byte b2 = 0;
        Tessellator tessellator = Tessellator.instance;
        float f = -0.0F;
        textureManager.bindTexture(this.scrollTexture);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glDisable(GL11.GL_ALPHA_TEST);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)((float)(b1 + 0) + f), (double)((float)(b2 + 128) - f), -0.009999999776482582D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)((float)(b1 + 128) - f), (double)((float)(b2 + 128) - f), -0.009999999776482582D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)((float)(b1 + 128) - f), (double)((float)(b2 + 0) + f), -0.009999999776482582D, 1.0D, 0.0D);
        tessellator.addVertexWithUV((double)((float)(b1 + 0) + f), (double)((float)(b2 + 0) + f), -0.009999999776482582D, 0.0D, 0.0D);
        tessellator.draw();
        //GL11.glEnable(GL11.GL_ALPHA_TEST);

        if (itemStack.hasTagCompound()) {
            NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
            String incantation = nbtTagCompound.getString("incantation");
            String[] symbols = incantation.split("|");
            int line = 0;
            int currentWidth = 0;
            int maxWidth = 100;
            for (String symbol1 : symbols) {
                Symbol symbol = Symbol.symbolMap.get(symbol1);
                if (symbol != null) {
                    if (symbol.getIdentifier().equals("⏎")) {
                        line++;
                        currentWidth = 0;
                    }
                    if ((currentWidth + symbol.getWidth() > maxWidth)) {
                        line++;
                        currentWidth = symbol.getWidth();
                    } else currentWidth += symbol.getWidth();
                    if (symbol.getTexture() != null) {
                        textureManager.bindTexture(symbol.getTexture());
                        this.drawScrollSymbol(11 + currentWidth, 16 + (line * 11), symbol.getU(), symbol.getV(), 12, 12, 16777215);
                    }
                }
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -0.04F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void drawScrollSymbol(int x, int y, int u, int v, int xSize, int ySize, int colour) {
        float r = (float)(colour >> 16 & 255) / 255.0F;
        float g = (float)(colour >> 8 & 255) / 255.0F;
        float b  = (float)(colour & 255) / 255.0F;
        float f = 0.08F;
        float f1 = 0.08F;
        GL11.glPushMatrix();
        GL11.glTranslatef(-8F, -8F, -0.02F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, true);
        GL11.glColor3f(0.5F, 0.5F, 1F);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + ySize, 0.001F, u * f, (v + ySize) * f1);
        tessellator.addVertexWithUV(x + xSize, y + ySize, 0.001F, (u + xSize) * f, (double)((float)(v + ySize) * f1));
        tessellator.addVertexWithUV(x + xSize, y, 0.001F, (u + xSize) * f, v * f1);
        tessellator.addVertexWithUV(x, y, 0.001F, u * f, v * f1);
        tessellator.draw();

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;

        //Second render
        //GL11.glColor4f(1F, 1F, 1F, 1F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
        //double alpha = Math.sin(worldTime / 20D) / 2D + 0.5;
        GL11.glColor4f(0.5F, 0.5F, 1F, 0.183F);
        //GL11.glScalef(1.1F, 1.1F, 1.1F);
        //GL11.glTranslatef(-2.5F, -2F, 0F);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + ySize, 0.001F, u * f, (v + ySize) * f1);
        tessellator.addVertexWithUV(x + xSize, y + ySize, 0.001F, (u + xSize) * f, (double)((float)(v + ySize) * f1));
        tessellator.addVertexWithUV(x + xSize, y, 0.001F, (u + xSize) * f, v * f1);
        tessellator.addVertexWithUV(x, y, 0.001F, u * f, v * f1);
        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
