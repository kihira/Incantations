package kihira.incantations.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import kihira.incantations.block.BlockWritingDesk;
import kihira.incantations.incantation.IncantationAttack;
import kihira.incantations.incantation.IncantationSummon;
import kihira.incantations.incantation.IncantationTeleport;
import kihira.incantations.incantation.Symbol;
import kihira.incantations.item.ItemInkVial;
import kihira.incantations.item.ItemQuill;
import kihira.incantations.item.ItemResearchNotes;
import kihira.incantations.item.ItemScroll;
import kihira.incantations.network.PacketHandler;
import kihira.incantations.proxy.ProxyCommon;
import kihira.incantations.tileentity.TileEntityWritingDesk;
import kihira.incantations.util.LanguageUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "Incantations", name = "Incantations", version = "1.0.0")
public class Incantations {

	@Mod.Instance(value = "Incantations")
	public static Incantations instance;

	public static Config config;
	public static final CreativeTabIncantations tabIncantations = new CreativeTabIncantations();
    public static final PacketHandler networkWrapper = new PacketHandler();
    public static final Logger logger = LogManager.getLogger("Incantations");

	public static final ItemScroll itemScroll = new ItemScroll();
	public static final ItemResearchNotes itemResearchNotes = new ItemResearchNotes();
	public static final ItemInkVial itemInkVial = new ItemInkVial();
	public static final ItemQuill itemQuill = new ItemQuill();
	public static final BlockWritingDesk blockWritingDesk = new BlockWritingDesk();

	@SidedProxy(clientSide = "kihira.incantations.proxy.ProxyClient", serverSide = "kihira.incantations.proxy.ProxyCommon")
	public static ProxyCommon proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		config = new Config(e.getSuggestedConfigurationFile());

		GameRegistry.registerItem(itemScroll, "itemScroll");
		GameRegistry.registerItem(itemResearchNotes, "itemResearchNotes");
		GameRegistry.registerItem(itemInkVial, "itemInkVial");
		GameRegistry.registerItem(itemQuill, "itemQuill");
		GameRegistry.registerBlock(blockWritingDesk, "blockWritingDesk");

		GameRegistry.registerTileEntity(TileEntityWritingDesk.class, "tileEntityWritingDesk");
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
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
	 	GameRegistry.addShapelessRecipe(new ItemStack(itemQuill), new ItemStack(Items.feather), new ItemStack(Items.gold_nugget));
		GameRegistry.addShapelessRecipe(new ItemStack(itemInkVial), new ItemStack(Items.glass_bottle), new ItemStack(Items.dye));
		GameRegistry.addShapedRecipe(new ItemStack(blockWritingDesk), "PP ", "WCW", "WWW", 'P', new ItemStack(Blocks.wooden_slab), 'W', new ItemStack(Blocks.planks), 'C', new ItemStack(Blocks.crafting_table));
		GameRegistry.addShapedRecipe(new ItemStack(itemScroll), "LSL", "sPs", "LSL", 'S', new ItemStack(Items.stick), 's', new ItemStack(Items.string), 'L', new ItemStack(Items.leather), 'P', new ItemStack(Items.paper));
	}
}
