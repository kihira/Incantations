package incantations.inventory;

import incantations.item.ItemResearchNotes;
import incantations.item.ItemScroll;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWritingTable extends Slot {

	public SlotWritingTable(IInventory par1IInventory, int index, int x, int y) {
		super(par1IInventory, index, x, y);
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if ((this.getSlotIndex() == 0) && (par1ItemStack.getItem() instanceof ItemScroll)) return true;
		else if ((this.getSlotIndex() == 1) && (par1ItemStack.getItem() instanceof ItemResearchNotes)) return true;
		else return false;
	}
}
