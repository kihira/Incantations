package common;

import block.BlockWritingDesk;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import item.ItemResearchNotes;
import item.ItemScroll;

@Mod(modid = "incantations", name = "common.Incantations", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Incantations {

	public static Config config;

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
	}
}
