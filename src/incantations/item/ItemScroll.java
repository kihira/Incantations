package incantations.item;

import incantations.common.Incantations;
import incantations.incantation.Incantation;
import incantations.util.LanguageUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class ItemScroll extends Item {

	private int wordCount = 0;
	private int validIncantationCount = 0;

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

	public void onUsingItemTick(ItemStack itemStack, EntityPlayer player, int count) {
		if (!player.worldObj.isRemote) {
			if (count == 0) return;
			if (count % 40 == 0) {
				NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
				String incantation = nbtTagCompound.getString("incantation");
				String[] words = LanguageUtil.cleanIncantation(incantation).split(" ");
				Incantation incan = Incantation.incantationHashMap.get(words[0]);
				if ((wordCount < words.length) && (validIncantationCount >= wordCount)) {
					player.sendChatToPlayer(ChatMessageComponent.createFromText("§3§o" + WordUtils.capitalize(LanguageUtil.cleanIncantation(words[wordCount])) + "..."));
					wordCount++;
				}
				else if (wordCount == words.length) {
					incan.doIncantation(incantation, player);
					player.setCurrentItemOrArmor(0, null);
				}
				else {
					player.sendChatToPlayer(ChatMessageComponent.createFromText("You try to read the next word however the incantation is poorly written and causes the scroll to malfunction"));
					incan.doFailedIncantation(incantation, wordCount, player);
					player.setCurrentItemOrArmor(0, null);
				}
			}
		}
	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		validIncantationCount = 0;
		wordCount = 0;
	}

	public int getMaxItemUseDuration(ItemStack itemStack) {
		return 7200;
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			if (itemStack.hasTagCompound()) {
				NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
				String incantation = nbtTagCompound.getString("incantation");
				incantation = LanguageUtil.cleanIncantation(incantation);
				String[] words = incantation.split(" ");
				Incantation incan = Incantation.incantationHashMap.get(words[0]);
				if (incan != null) {
					validIncantationCount = incan.isValidIncantation(incantation, entityPlayer);
					wordCount = 0;
					entityPlayer.setItemInUse(itemStack, itemStack.getMaxItemUseDuration());
					entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("You begin to read the scroll..."));
				}
				else {
					entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("You try to read the scroll however the incantation is poorly written and causes the symbols to ignite"));
					entityPlayer.setFire(5);
					entityPlayer.setCurrentItemOrArmor(0, new ItemStack(itemStack.itemID, 1, 0));
					return new ItemStack(itemStack.itemID, 1, 0);
				}
			}
		}
		return itemStack;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		if (itemStack.hasTagCompound() && entityPlayer.capabilities.isCreativeMode) {
			NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
			list.add("Incantation:");
			list.add(LanguageUtil.cleanIncantation(nbtTagCompound.getString("incantation")));
		}
	}
}
