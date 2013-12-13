package tileentity;

import item.ItemResearchNotes;
import item.ItemScroll;
import item.ItemWritingTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWritingDesk extends TileEntity implements IInventory {

	private ItemStack itemScroll;
	private ItemStack itemResearchNotes;
	private ItemStack itemWritingTools;

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagCompound tag = (NBTTagCompound) par1NBTTagCompound.getTag("itemScroll");
		this.itemScroll = ItemStack.loadItemStackFromNBT(tag);
		tag = (NBTTagCompound) par1NBTTagCompound.getTag("itemResearchNotes");
		this.itemResearchNotes = ItemStack.loadItemStackFromNBT(tag);
		tag = (NBTTagCompound) par1NBTTagCompound.getTag("itemWritingTools");
		this.itemWritingTools = ItemStack.loadItemStackFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagCompound tag = new NBTTagCompound();
		if (this.itemScroll != null) this.itemScroll.writeToNBT(tag);
		par1NBTTagCompound.setTag("itemScroll", tag);
		if (this.itemResearchNotes != null) this.itemResearchNotes.writeToNBT(tag);
		par1NBTTagCompound.setTag("itemResearchNotes", tag);
		if (this.itemWritingTools != null) this.itemWritingTools.writeToNBT(tag);
		par1NBTTagCompound.setTag("itemWritingTools", tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		readFromNBT(pkt.data);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i == 1) return itemScroll;
		else if (i == 2) return itemResearchNotes;
		else if (i == 3) return itemWritingTools;
		else return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (i == 1) return itemScroll;
		else if (i == 2) return itemResearchNotes;
		else if (i == 3) return itemWritingTools;
		else return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (itemstack != null) {
			if ((i == 1) && (itemstack.getItem() instanceof ItemScroll)) itemScroll = itemstack;
			else if ((i == 2) && (itemstack.getItem() instanceof ItemResearchNotes)) itemResearchNotes = itemstack;
			else if ((i == 3) && (itemstack.getItem() instanceof ItemWritingTools)) itemWritingTools = itemstack;
		}
	}

	@Override
	public String getInvName() {
		return "Writing Desk";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return getDistanceFrom(entityplayer.posX, entityplayer.posY, entityplayer.posZ) < 5;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return (i == 1) && (itemstack.getItem() instanceof ItemScroll) || (i == 2) && (itemstack.getItem() instanceof ItemResearchNotes) || (i == 3) && (itemstack.getItem() instanceof ItemWritingTools);
	}
}
