package incantations.item;

import incantations.common.Incantations;
import incantations.incantation.IncantationSummon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemScroll extends Item {

	public ItemScroll(int par1) {
		super(par1);
		setUnlocalizedName("scroll");
		setTextureName("incantations:scroll");
		setMaxStackSize(1);
		setMaxDamage(1);
		setNoRepair();
		setHasSubtypes(true);
		setCreativeTab(Incantations.tabIncantations);
	}

	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		ItemStack itemStack = new ItemStack(id, 1, 0);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setString("incantation", "summon zombie");
		itemStack.setTagCompound(nbtTagCompound);
		list.add(new ItemStack(id, 1, 0));
	}

	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {

	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {

	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			if (itemStack.hasTagCompound()) {
				NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
				String incantation = nbtTagCompound.getString("incantation");
				System.out.println(incantation);
			}
		}
		return itemStack;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
			list.add("Incantation:");
			list.add(nbtTagCompound.getString("incantation"));
		}
	}
}
