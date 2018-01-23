package xyz.towerdevs.nullifactor.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactor;

public class BlockQuantumReactor extends HeliosBlock implements ITileEntityProvider {
	
	private IIcon top, sides, front;

	public static final BlockQuantumReactor instance = new BlockQuantumReactor();
	public BlockQuantumReactor() {
		super("quantum_reactor", Material.iron, "nullifactor");
		
		this.setHarvestLevel("pickaxe", 3);
		this.isBlockContainer = true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityQuantumReactor();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		world.removeTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, state);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.top = iconRegister.registerIcon("nullifactor:base_machine_frame");
		this.sides = iconRegister.registerIcon("nullifactor:quantum_reactor_side");
		this.front = iconRegister.registerIcon("nullifactor:quantum_reactor_side");
	}
	
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1)
			return top;
		
		return sides;
	}

	/**public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityQuantumReactor) {

        	TileEntityQuantumReactor reactor = (TileEntityQuantumReactor)tile;

            int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

            if (l == 0)
            	reactor.setFacing(2);
            if (l == 1)
            	reactor.setFacing(5);
            if (l == 2)
            	reactor.setFacing(3);
            if (l == 3)
            	reactor.setFacing(4);
        }
	}*/
}
