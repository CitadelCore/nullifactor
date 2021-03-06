package xyz.towerdevs.helios.base;

import java.util.List;

import cofh.api.block.IBlockInfo;
import cofh.api.tileentity.ITileInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class HeliosBlockContainer extends HeliosBlock implements ITileEntityProvider, IBlockInfo {
	private Class<?> tileEntityClass;
	
	public HeliosBlockContainer(String unlocalizedName, Material blockMaterial, String modId, Class<?> tileEntityClass) {
		super(unlocalizedName, blockMaterial, modId);
		this.isBlockContainer = true;
		this.tileEntityClass = tileEntityClass;
		
		this.setHardness(20.0F);
		this.setHarvestLevel("pickaxe", 3);
		this.setStepSound(soundTypeMetal);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return (TileEntity) this.tileEntityClass.newInstance();
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

			HeliosMachineTileEntity machine = (HeliosMachineTileEntity) tile;

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
		super.breakBlock(world, x, y, z, block, state);
		world.removeTileEntity(x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (this.useSidedTextures) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof HeliosMachineTileEntity) {
				HeliosMachineTileEntity machine = (HeliosMachineTileEntity)tile;
				if (side == machine.getFacing())
					return this.FrontTexture;
			}
			
			if (side == 0)
				return this.BottomTexture;
			if (side == 1)
				return this.TopTexture;
			
			
			return this.SideTexture;
		}
		
		return super.getIcon(world, x, y, z, side);
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(world, x, y, z, p_149696_5_, p_149696_6_);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity != null ? tileEntity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }

	@Override
	public void getBlockInfo(IBlockAccess world, int x, int y, int z, ForgeDirection side, EntityPlayer player, List<IChatComponent> info, boolean debug) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof ITileInfo) {
			ITileInfo tileInfo = (ITileInfo) tileEntity;
			tileInfo.getTileInfo(info, side, player, debug);
		}
	}
}
