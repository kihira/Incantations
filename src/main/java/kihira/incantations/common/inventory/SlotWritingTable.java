package kihira.incantations.common.inventory;

import kihira.incantations.common.item.ItemQuill;
import kihira.incantations.common.item.ItemResearchNotes;
import kihira.incantations.common.item.ItemScroll;
import kihira.incantations.common.item.ItemInkVial;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWritingTable extends Slot {

	public SlotWritingTable(IInventory par1IInventory, int index, int x, int y) {
		super(par1IInventory, index, x, y);
	}

    @Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return ((this.getSlotIndex() == -1) && (par1ItemStack.getItem() instanceof ItemScroll)) || ((this.getSlotIndex() == -2) && (par1ItemStack.getItem() instanceof ItemResearchNotes)) || ((this.getSlotIndex() == -3) && (par1ItemStack.getItem() instanceof ItemInkVial)) || ((this.getSlotIndex() == -5) && (par1ItemStack.getItem() instanceof ItemQuill));
	}

    @Override
	public int getSlotStackLimit() {
		return 1;
	}
}
