package kihira.incantations.common;

import kihira.incantations.client.gui.GuiResearchBook;
import kihira.incantations.client.gui.GuiWritingTable;
import cpw.mods.fml.common.network.IGuiHandler;
import kihira.incantations.inventory.ContainerWritingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import kihira.incantations.tileentity.TileEntityWritingDesk;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if ((ID == 0) && (tileEntity instanceof TileEntityWritingDesk)) {
			TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) tileEntity;
			return new ContainerWritingTable(player.inventory, tileEntityWritingDesk);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if ((ID == 0) && (tileEntity instanceof TileEntityWritingDesk)) {
			TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) tileEntity;
			return new GuiWritingTable(player.inventory, tileEntityWritingDesk);
		}
		else if (ID == 1) return new GuiResearchBook(player.getCurrentEquippedItem());
		return null;
	}
}
