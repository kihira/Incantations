package incantations.inventory;

import incantations.tileentity.TileEntityWritingDesk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWritingTable extends Container {

	private TileEntityWritingDesk writingDesk;

	public ContainerWritingTable(InventoryPlayer inventoryPlayer, TileEntityWritingDesk tileEntityWritingDesk) {
		this.writingDesk = tileEntityWritingDesk;
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -1, 137, 11));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -2, 155, 11));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -3, 155, 41));
		this.addSlotToContainer(new Slot(tileEntityWritingDesk, -4, 155, 97));

		//Player Inventory
		byte b0 = 8;
		short short1 = 146;
		int i;
		for (i = 0; i < 3; ++i){
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, b0 + i * 18, 58 + short1));
		}
	}

	public TileEntityWritingDesk getWritingDesk() {
		return writingDesk;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.writingDesk.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotID);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotID > -5 && slotID < 0) {
				if (!this.mergeItemStack(itemstack1, 0, 37, false)) return null;
			}
			else if (slotID >= 0 && slotID < 37) {
				//TODO fix
				if (!this.mergeItemStack(itemstack1, -1, -4, false)) return null;
			}
			if (itemstack1.stackSize == 0) slot.putStack(null);
			else slot.onSlotChanged();
			if (itemstack1.stackSize == itemstack.stackSize) return null;
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}
