package incantations.common;

import incantations.block.BlockWritingDesk;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import incantations.tileentity.TileEntityWritingDesk;

@Mod(modid = "Incantations", name = "Incantations", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Incantations {

	@Mod.Instance(value = "Incantations")
	public static Incantations instance;

	public static Config config;
	public static final CreativeTabIncantations tabIncantations = new CreativeTabIncantations();
	private final GuiHandler guiHandler = new GuiHandler();

	private static ItemScroll itemScroll;
	private static ItemResearchNotes itemResearchNotes;
	private static BlockWritingDesk blockWritingDesk;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		config = new Config(e.getSuggestedConfigurationFile());

		itemScroll = new ItemScroll(config.scrollItemID);
		itemResearchNotes = new ItemResearchNotes(config.researchNotesID);
		blockWritingDesk = new BlockWritingDesk(config.writingDeskID);

		GameRegistry.registerBlock(blockWritingDesk, "blockWritingDesk");
		GameRegistry.registerTileEntity(TileEntityWritingDesk.class, "tileEntityWritingDesk");
		NetworkRegistry.instance().registerGuiHandler(instance, guiHandler);
	}
}
