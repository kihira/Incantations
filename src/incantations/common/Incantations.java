package incantations.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import incantations.block.BlockWritingDesk;
import incantations.incantation.Symbol;
import incantations.item.ItemQuill;
import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import incantations.item.ItemInkVial;
import incantations.network.PacketHandlerClient;
import incantations.network.PacketHandlerServer;
import incantations.proxy.ProxyCommon;
import incantations.tileentity.TileEntityWritingDesk;
import net.minecraft.util.ResourceLocation;

@Mod(modid = "Incantations", name = "Incantations", version = "1.0.0")
@NetworkMod(clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"INC|WritingDesk"}, packetHandler = PacketHandlerClient.class), serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"INC|WritingDesk"}, packetHandler = PacketHandlerServer.class), clientSideRequired = true, serverSideRequired = false)
public class Incantations {

	@Mod.Instance(value = "Incantations")
	public static Incantations instance;

	public static Config config;
	public static final CreativeTabIncantations tabIncantations = new CreativeTabIncantations();
	private final GuiHandler guiHandler = new GuiHandler();

	public static ItemScroll itemScroll;
	private static ItemResearchNotes itemResearchNotes;
	private static ItemInkVial itemInkVial;
	private static ItemQuill itemQuill;
	private static BlockWritingDesk blockWritingDesk;

	@SidedProxy(clientSide = "incantations.proxy.ProxyClient", serverSide = "incantations.proxy.ProxyCommon")
	public static ProxyCommon proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		config = new Config(e.getSuggestedConfigurationFile());

		itemScroll = new ItemScroll(config.scrollItemID);
		GameRegistry.registerItem(itemScroll, "itemScroll");
		itemResearchNotes = new ItemResearchNotes(config.researchNotesID);
		GameRegistry.registerItem(itemResearchNotes, "itemResearchNotes");
		itemInkVial = new ItemInkVial(config.inkVialItemID);
		GameRegistry.registerItem(itemInkVial, "itemInkVial");
		itemQuill = new ItemQuill(config.quillItemID);
		GameRegistry.registerItem(itemQuill, "itemQuill");
		blockWritingDesk = new BlockWritingDesk(config.writingDeskID);
		GameRegistry.registerBlock(blockWritingDesk, "blockWritingDesk");

		GameRegistry.registerTileEntity(TileEntityWritingDesk.class, "tileEntityWritingDesk");
		NetworkRegistry.instance().registerGuiHandler(instance, guiHandler);
		registerSymbols();
		proxy.registerRenderers();
	}

	private void registerSymbols() {
		String[] alphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " "};
		for (int i = 1; i <= alphabet.length; i++) {
			ResourceLocation resourceLocation = new ResourceLocation("incantations", "textures/gui/symbols/symbol" + i + ".png");
			new Symbol(alphabet[i-1], resourceLocation);
			System.out.println("Mapped the English character \"" + alphabet[i-1] + "\" to the symbol \"symbol" + i + "\"");
		}
	}

	private void registerCraftingRecipes() {

	}
}
