package incantations.inventory;

import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import incantations.item.ItemWritingTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWritingTable extends Slot {

	public SlotWritingTable(IInventory par1IInventory, int index, int x, int y) {
		super(par1IInventory, index, x, y);
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return (this.getSlotIndex() == -1) && (par1ItemStack.getItem() instanceof ItemScroll) || (this.getSlotIndex() == -2) && (par1ItemStack.getItem() instanceof ItemResearchNotes) || (this.getSlotIndex() == -3) && (par1ItemStack.getItem() instanceof ItemWritingTools) || (this.getSlotIndex() == -4) && (par1ItemStack.getItem() instanceof ItemWritingTools);
	}

	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {

	}
}
