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
		setMaxStackSize(1);
		setMaxDamage(1);
		setNoRepair();
		setHasSubtypes(true);
		setCreativeTab(Incantations.tabIncantations);
	}

	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		ItemStack itemStack = new ItemStack(id, 1, 0);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setString("incantations/incantation", "summon zombie");
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

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (!par2World.isRemote) {
			IncantationSummon incantationSummon = new IncantationSummon();
			incantationSummon.doIncantation("summon zombie fast", par3EntityPlayer);
		}
		return par1ItemStack;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
	 	if (entityPlayer.capabilities.isCreativeMode) {
			if (itemStack.hasTagCompound()) {
				NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
				list.add("Incantation:");
				list.add(nbtTagCompound.getString("incantations/incantation"));
			}
		}
	}
}
