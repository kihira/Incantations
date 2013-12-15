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

	public static ItemStack writeScroll(ArrayList<String> incantation) {
		String s = "";
		for (String string:incantation) {
			s += string;
		}
		return writeScroll(s);
	}

	public static ItemStack writeScroll(String incantation) {
		ItemStack itemStack = new ItemStack(Incantations.config.scrollItemID, 1, 0);
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString("incantation", incantation);
		itemStack.writeToNBT(tagCompound);
		return itemStack;
	}
}
