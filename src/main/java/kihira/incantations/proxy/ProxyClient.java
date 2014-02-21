package kihira.incantations.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import kihira.incantations.client.render.BlockWritingDeskRenderer;
import kihira.incantations.client.render.ItemWritingDeskRenderer;
import kihira.incantations.common.Incantations;
import kihira.incantations.tileentity.TileEntityWritingDesk;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class ProxyClient extends ProxyCommon {

	public static final ResourceLocation writingDeskTexture = new ResourceLocation("incantations", "textures/model/writingdesk1.png");
	public static final ResourceLocation writingDeskTextureScroll = new ResourceLocation("incantations", "textures/model/writingdesk2.png");

	public void registerRenderers() {
		BlockWritingDeskRenderer blockWritingDeskRenderer = new BlockWritingDeskRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWritingDesk.class, blockWritingDeskRenderer);
		MinecraftForgeClient.registerItemRenderer(Incantations.config.writingDeskID, new ItemWritingDeskRenderer());
	}
}
