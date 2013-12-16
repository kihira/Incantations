package incantations.item;

import incantations.common.Incantations;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ItemResearchNotes extends Item {
	public ItemResearchNotes(int id) {
		super(id);
		setMaxDamage(1);
		setMaxStackSize(1);
		setUnlocalizedName("researchNotes");
		setCreativeTab(Incantations.tabIncantations);
		setHasSubtypes(true);
	}

	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		ItemStack itemStack = new ItemStack(id, 1, 0);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setString("res", "summon zombie");
		itemStack.setTagCompound(nbtTagCompound);
		list.add(new ItemStack(id, 1, 0));
	}
}
