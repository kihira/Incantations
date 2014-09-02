package kihira.incantations.common.inventory;

import kihira.incantations.common.item.ItemInkVial;
import kihira.incantations.common.item.ItemQuill;
import kihira.incantations.common.item.ItemResearchNotes;
import kihira.incantations.common.item.ItemScroll;
import kihira.incantations.common.tileentity.TileEntityWritingDesk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;

public class ContainerWritingTable extends Container {

	private TileEntityWritingDesk writingDesk;

	public ContainerWritingTable(InventoryPlayer inventoryPlayer, TileEntityWritingDesk tileEntityWritingDesk) {
		this.writingDesk = tileEntityWritingDesk;
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -1, 137, 11));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -2, 155, 11));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -3, 155, 41));
		this.addSlotToContainer(new Slot(tileEntityWritingDesk, -4, 155, 97));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, -5, 137, 41));

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
		System.out.println(slotID);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotID >= 0 && slotID <= 4) {
				if (!this.mergeItemStack(itemstack1, 5, 40, false)) return null;
			}
			else if (slotID >= 5 && slotID <= 40) {
				if (itemstack1.getItem() instanceof ItemQuill) {
					if (!this.mergeItemStack(itemstack1, 4, 5, false)) return null;
				}
				else if (itemstack1.getItem() instanceof ItemInkVial) {
					if (!this.mergeItemStack(itemstack1, 2, 3, false)) return null;
				}
				else if (itemstack1.getItem() instanceof ItemScroll) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) return null;
				}
				else if (itemstack1.getItem() instanceof ItemResearchNotes) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) return null;
				}
				else if (itemstack1.getItem() instanceof ItemRedstone || itemstack1.getItem() == Items.glowstone_dust) {
					if (!this.mergeItemStack(itemstack1, 3, 4, false)) return null;
				}
			}
			if (itemstack1.stackSize == 0) slot.putStack(null);
			else slot.onSlotChanged();
			if (itemstack1.stackSize == itemstack.stackSize) return null;
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}
