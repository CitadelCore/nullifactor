package xyz.towerdevs.helios.base;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class HeliosBlockContainer extends HeliosBlock implements ITileEntityProvider {
	private Class<?> tileEntityClass;
	
	public HeliosBlockContainer(String unlocalizedName, Material blockMaterial, String modId, Class<?> tileEntityClass) {
		super(unlocalizedName, blockMaterial, modId);
		this.isBlockContainer = true;
		this.tileEntityClass = tileEntityClass;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return (TileEntity) tileEntityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		TileEntity tile = world.getTileEntity(x, y, z);
		  if (tile instanceof HeliosMachineTileEntity) {

			HeliosMachineTileEntity machine = (HeliosMachineTileEntity)tile;

	        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        	machine.setFacing(2);
	        if (l == 1)
	        	machine.setFacing(5);
	        if (l == 2)
	        	machine.setFacing(3);
	        if (l == 3)
	        	machine.setFacing(4);
	      }
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		world.removeTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, state);
	}
}
