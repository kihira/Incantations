package incantations.item;

import incantations.common.Incantations;
import incantations.util.LanguageUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemResearchNotes extends Item {
	public ItemResearchNotes(int id) {
		super(id);
		setMaxDamage(1);
		setMaxStackSize(1);
		setUnlocalizedName("researchNotes");
		setTextureName("incantations:researchNotes");
		setCreativeTab(Incantations.tabIncantations);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		//The cheaty research book
		ItemStack itemStack = new ItemStack(id, 1, 0);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagCompound researchTagCompound = new NBTTagCompound();
		NBTTagList nbtTagList = new NBTTagList();
		int i = 0;
		for (Map.Entry<String, String> entry:LanguageUtil.translationList.entrySet()) {
			nbtTagList.appendTag(new NBTTagString(String.valueOf(i), entry.getKey()));
			i++;
		}
		researchTagCompound.setTag("words", nbtTagList);
		nbtTagCompound.setCompoundTag("research", researchTagCompound);
		itemStack.setTagCompound(nbtTagCompound);
		list.add(itemStack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!entityPlayer.isSneaking()) entityPlayer.openGui(Incantations.instance, 1, world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
		return itemStack;
	}
}
