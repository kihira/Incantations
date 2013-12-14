package incantations.block;

import incantations.common.Incantations;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import incantations.tileentity.TileEntityWritingDesk;

public class BlockWritingDesk extends Block implements ITileEntityProvider {
	public BlockWritingDesk(int id) {
		super(id, Material.wood);
		setUnlocalizedName("writingDesk");
		setHardness(10f);
		setCreativeTab(Incantations.tabIncantations);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWritingDesk();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int meta, float par7, float par8, float par9) {
		if (!entityPlayer.isSneaking()) entityPlayer.openGui(Incantations.instance, 0, world, x, y, z);
		return true;
	}
}
