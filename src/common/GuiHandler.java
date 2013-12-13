package common;

import client.gui.GuiWritingTable;
import cpw.mods.fml.common.network.IGuiHandler;
import inventory.ContainerWritingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tileentity.TileEntityWritingDesk;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) world.getBlockTileEntity(x, y, z);
		if (ID == 0) return new ContainerWritingTable(player.inventory, tileEntityWritingDesk);
		else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) world.getBlockTileEntity(x, y, z);
		if (ID == 0) return new GuiWritingTable(player.inventory, tileEntityWritingDesk);
		else return null;
	}
}
