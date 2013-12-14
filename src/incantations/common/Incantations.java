package incantations.common;

import incantations.block.BlockWritingDesk;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import incantations.incantation.Symbol;
import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import incantations.tileentity.TileEntityWritingDesk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.File;

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
		registerSymbols();
	}

	private void registerSymbols() {
		String[] alphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		for (int i = 1; i <= 26; i++) {
			ResourceLocation resourceLocation = new ResourceLocation("incantations", "textures/gui/symbols/symbol" + i + ".png");
			new Symbol(alphabet[i-1], resourceLocation);
			System.out.println("Mapped the English character \"" + alphabet[i-1] + "\" to the symbol \"symbol" + i + "\"");
		}
	}
}