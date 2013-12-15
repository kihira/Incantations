package incantations.util;

import incantations.common.Incantations;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sun.awt.Symbol;

import java.util.ArrayList;

public class LanguageUtil {

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
