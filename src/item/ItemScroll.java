package item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	}

	public void getSubItems(int par1, CreativeTabs creativeTabs, List list) {

	}

	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {

	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {

	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {

	}
}
