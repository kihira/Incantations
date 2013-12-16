package incantations.util;

import incantations.common.Incantations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import sun.awt.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LanguageUtil {

	public static final HashMap<String, String> translationList = new HashMap<String, String>();

	public static String cleanIncantation(String incantation) {
		incantation = incantation.replace("¦", "");
		incantation = incantation.replace("⏎", " ");
		return incantation;
	}

	public static void loadTranslationList() {
		ResourceLocation resourceLocation = new ResourceLocation("incantations", "lang/unknown.lang");
		try {
			Resource resource = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation);
			Iterator iterator = IOUtils.readLines(resource.getInputStream(), Charsets.UTF_8).iterator();
			while (iterator.hasNext()) {
				String s = (String) iterator.next();
				if (!s.isEmpty()) {
					String[] astring = s.split("=");
					if (astring.length == 2) translationList.put(astring[0], astring[1]);
				}
			}
			System.out.println("Loaded translation list!");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String validateAndTranslate(ArrayList<Symbol> symbols) {
		//TODO
		return null;
	}

	public static ItemStack writeScroll(ArrayList<String> incantation, int modifier) {
		String s = null;
		for (String string:incantation) {
			s += string;
		}
		return writeScroll(s, modifier);
	}

	public static ItemStack writeScroll(String incantation, int modifier) {
		ItemStack itemStack = new ItemStack(Incantations.itemScroll);
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString("incantation", incantation);
		tagCompound.setInteger("modifier", modifier);
		itemStack.setTagCompound(tagCompound);
		return itemStack;
	}

	public static ItemStack increaseScrollModifier(ItemStack itemStack, int modifierIncrease) {
		NBTTagCompound tagCompound = itemStack.getTagCompound();
		int oldModifier = tagCompound.getInteger("modifier");
		tagCompound.setInteger("modifier", oldModifier + modifierIncrease);
		itemStack.setTagCompound(tagCompound);
		return itemStack;
	}
}
