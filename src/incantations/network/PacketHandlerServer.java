package incantations.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import incantations.inventory.ContainerWritingTable;
import incantations.util.LanguageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PacketHandlerServer implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		EntityPlayerMP entityPlayer = (EntityPlayerMP) player;
		if (packet.channel.equals("INC|WritingDesk") && entityPlayer.openContainer instanceof ContainerWritingTable) {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				ContainerWritingTable writingTable = (ContainerWritingTable) entityPlayer.openContainer;
				Slot slot = writingTable.getSlot(0);
				String incantation = inputStream.readUTF();
				//Get current modifier on scroll
				int modifier = 0;
				if (slot.getHasStack()) {
					ItemStack scroll = slot.getStack();
					if (scroll.hasTagCompound()) {
						NBTTagCompound tagCompound = scroll.getTagCompound();
						modifier = tagCompound.getInteger("modifier");
					}
				}
				//Calculate new modifier
				slot = writingTable.getSlot(3);
				ItemStack itemStack = slot.getStack();
				if (itemStack != null) {
					if (itemStack.getItem() == Item.glowstone) modifier += 2;
					else if (itemStack.getItem() instanceof ItemRedstone) modifier++;
					slot.decrStackSize(1);
				}
				//Write the scroll
				itemStack = LanguageUtil.writeScroll(incantation, modifier);
				slot = writingTable.getSlot(0);
				slot.putStack(itemStack);
				//Damage the writing tools
				slot = writingTable.getSlot(2);
				itemStack = slot.getStack();
				if (itemStack != null) {
					if (itemStack.getItemDamage() >= itemStack.getMaxDamage()) slot.putStack(null);
					else {
						itemStack.setItemDamage(itemStack.getItemDamage()+1);
						slot.putStack(itemStack);
					}
				}
				writingTable.getWritingDesk().onInventoryChanged();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
