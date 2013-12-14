package incantations.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import incantations.tileentity.TileEntityWritingDesk;

public class ContainerWritingTable extends Container {

	private TileEntityWritingDesk writingDesk;
	private SlotWritingTable scrollItemSlot;
	private SlotWritingTable researchNotesSlot;

	public ContainerWritingTable(InventoryPlayer inventoryPlayer, TileEntityWritingDesk tileEntityWritingDesk) {
		this.writingDesk = tileEntityWritingDesk;
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, 0, 150, 150));
		this.addSlotToContainer(new SlotWritingTable(tileEntityWritingDesk, 1, 170, 170));

		//Player Inventory
		byte b0 = 36;
		short short1 = 137;
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

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.writingDesk.isUseableByPlayer(entityplayer);
	}

	//TODO
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);
		System.out.println(par2);
		return itemstack;
	}
}
