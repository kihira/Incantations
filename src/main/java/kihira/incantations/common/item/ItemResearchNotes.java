package kihira.incantations.common.item;

import kihira.incantations.Incantations;
import kihira.incantations.util.LanguageUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ItemResearchNotes extends Item {
	public ItemResearchNotes() {
		setMaxDamage(1);
		setMaxStackSize(1);
		setUnlocalizedName("researchNotes");
		setTextureName("incantations:researchnotes");
		setCreativeTab(Incantations.tabIncantations);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
		//The cheaty research book
		ItemStack itemStack = new ItemStack(item, 1, 0);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagCompound researchTagCompound = new NBTTagCompound();
		NBTTagList nbtTagList = new NBTTagList();
		int i = 0;
		for (Map.Entry<String, String> entry:LanguageUtil.translationList.entrySet()) {
			nbtTagList.appendTag(new NBTTagString(String.valueOf(i)));
			i++;
		}
		researchTagCompound.setTag("words", nbtTagList);
		researchTagCompound.setBoolean("cheatybook", true);
		nbtTagCompound.setTag("research", researchTagCompound);
		itemStack.setTagCompound(nbtTagCompound);
		list.add(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!entityPlayer.isSneaking()) entityPlayer.openGui(Incantations.instance, 1, world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
		return itemStack;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound nbtTagCompound = itemStack.getTagCompound().getCompoundTag("research");
			if (nbtTagCompound.getBoolean("cheatybook")) {
				list.add("Shows all translations and allows use");
				list.add("of keyboard in writing desk GUI");
			}
		}
	}
}
