package block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tileentity.TileEntityWritingDesk;

public class BlockWritingDesk extends Block implements ITileEntityProvider {
	public BlockWritingDesk(int id) {
		super(id, Material.wood);
		setUnlocalizedName("writingDesk");
		setHardness(10f);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWritingDesk();
	}
}
