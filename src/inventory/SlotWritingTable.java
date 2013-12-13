package inventory;

import item.ItemResearchNotes;
import item.ItemScroll;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWritingTable extends Slot {

	public SlotWritingTable(IInventory par1IInventory, int par2, int par3, int par4) {
		super(par1IInventory, par2, par3, par4);
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if ((this.getSlotIndex() == 0) && (par1ItemStack.getItem() instanceof ItemScroll)) return true;
		else if ((this.getSlotIndex() == 1) && (par1ItemStack.getItem() instanceof ItemResearchNotes)) return true;
		else return false;
	}
}
