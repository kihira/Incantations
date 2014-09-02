package kihira.incantations.common.block;

import kihira.incantations.Incantations;
import kihira.incantations.common.tileentity.TileEntityWritingDesk;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockWritingDesk extends Block implements ITileEntityProvider {

	public BlockWritingDesk() {
		super(Material.wood);
		setBlockName("writingDesk");
		setHardness(10f);
		setBlockTextureName("minecraft:wood_oak");
		setCreativeTab(Incantations.tabIncantations);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityWritingDesk();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int meta, float par7, float par8, float par9) {
		if (!entityPlayer.isSneaking()) entityPlayer.openGui(Incantations.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		int l = MathHelper.floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (l == 0) par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		if (l == 1) par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		if (l == 2) par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		if (l == 3) par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		par1World.markBlockForUpdate(par2, par3, par4);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityWritingDesk tileEntityWritingDesk = (TileEntityWritingDesk) world.getTileEntity(x, y, z);
		for (int i = -5; i <= -1; i++) {
			ItemStack itemStack = tileEntityWritingDesk.getStackInSlot(i);
			if (itemStack != null) {
				EntityItem entityItem = new EntityItem(world, x, y, z, itemStack);
				entityItem.motionX = world.rand.nextGaussian() * 0.05f;
				entityItem.motionY = world.rand.nextGaussian() * 0.05f + 0.2f;
				entityItem.motionZ = world.rand.nextGaussian() * 0.05f;
				world.spawnEntityInWorld(entityItem);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
