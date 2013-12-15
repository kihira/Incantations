package incantations.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import incantations.inventory.ContainerWritingTable;
import incantations.util.LanguageUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
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
				ItemStack itemStack = LanguageUtil.writeScroll(inputStream.readUTF());
				slot.putStack(itemStack);
				System.out.println("Updated scroll server side");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
