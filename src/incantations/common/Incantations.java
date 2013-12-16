package incantations.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import incantations.block.BlockWritingDesk;
import incantations.incantation.IncantationAttack;
import incantations.incantation.IncantationSummon;
import incantations.incantation.IncantationTeleport;
import incantations.incantation.Symbol;
import incantations.item.ItemQuill;
import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import incantations.item.ItemInkVial;
import incantations.network.PacketHandlerClient;
import incantations.network.PacketHandlerServer;
import incantations.proxy.ProxyCommon;
import incantations.tileentity.TileEntityWritingDesk;
import incantations.util.LanguageUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mod(modid = "Incantations", name = "Incantations", version = "1.0.0")
@NetworkMod(clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"INC|WritingDesk"}, packetHandler = PacketHandlerClient.class), serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {"INC|WritingDesk"}, packetHandler = PacketHandlerServer.class), clientSideRequired = true, serverSideRequired = false)
public class Incantations {

	@Mod.Instance(value = "Incantations")
	public static Incantations instance;

	public static Config config;
	public static final CreativeTabIncantations tabIncantations = new CreativeTabIncantations();

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
		NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
		registerSymbols();
		registerIncantations();
		registerCraftingRecipes();
		proxy.registerRenderers();
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) LanguageUtil.loadTranslationList();
	}


	private void registerSymbols() {
		String[] alphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "nu-", "-er", "-es", "-ed", "-et", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 1; i <= alphabet.length; i++) {
			new Symbol(alphabet[i-1], new ResourceLocation("incantations", "textures/gui/symbols/symbol" + i + ".png"));
		}
		new Symbol(" ", null);
		//new Symbol("'", new ResourceLocation("incantations", "textures/gui/symbols/apostrophe.png")).setUV(5, 0).setWidth(5);
		new Symbol("'", new ResourceLocation("incantations", "textures/gui/symbols/apostrophe.png")).setUV(3, 0);
		new Symbol(".", new ResourceLocation("incantations", "textures/gui/symbols/fullstop.png")).setUV(5, 0);
		new Symbol(":", new ResourceLocation("incantations", "textures/gui/symbols/colon.png")).setUV(5, 0);
		new Symbol(",", new ResourceLocation("incantations", "textures/gui/symbols/comma.png")).setUV(4, 0);
		new Symbol("-", new ResourceLocation("incantations", "textures/gui/symbols/dash.png"));
		new Symbol("!", new ResourceLocation("incantations", "textures/gui/symbols/exclamation.png")).setUV(4, 0);
		new Symbol("?", new ResourceLocation("incantations", "textures/gui/symbols/question.png"));
		new Symbol("⏎", new ResourceLocation("incantations", "textures/gui/symbols/return.png"));
		new Symbol(";", new ResourceLocation("incantations", "textures/gui/symbols/semicolon.png")).setUV(4, 0);
	}

	private void registerIncantations() {
		new IncantationSummon();
		new IncantationTeleport();
		new IncantationAttack();
	}

	private void registerCraftingRecipes() {
	 	GameRegistry.addShapelessRecipe(new ItemStack(itemQuill), new ItemStack(Item.feather), new ItemStack(Item.goldNugget));
		GameRegistry.addShapelessRecipe(new ItemStack(itemInkVial), new ItemStack(Item.glassBottle), new ItemStack(Item.dyePowder));
		GameRegistry.addShapedRecipe(new ItemStack(blockWritingDesk), "PP ", "WCW", "WWW", 'P', new ItemStack(Block.woodSingleSlab), 'W', new ItemStack(Block.planks), 'C', new ItemStack(Block.workbench));
		GameRegistry.addShapedRecipe(new ItemStack(itemScroll), "LSL", "sPs", "LSL", 'S', new ItemStack(Item.stick), 's', new ItemStack(Item.silk), 'L', new ItemStack(Item.leather), 'P', new ItemStack(Item.paper));
	}
}
