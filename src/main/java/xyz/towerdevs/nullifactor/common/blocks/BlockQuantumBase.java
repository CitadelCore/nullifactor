package xyz.towerdevs.nullifactor.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.HeliosBlockContainer;
import xyz.towerdevs.helios.instantiable.TileEntityReference;
import xyz.towerdevs.nullifactor.common.registries.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumBase;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactor;

public class BlockQuantumBase extends HeliosBlockContainer {
	public BlockQuantumBase(String unlocalizedName, Material blockMaterial, String modId, Class<?> tileEntityClass) {
		super(unlocalizedName, blockMaterial, modId, tileEntityClass);
	}
	
	/** Resets the multiblock if it becomes invalid. */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if (tile != null && tile instanceof TileEntityQuantumBase) {
			TileEntityQuantumBase base = (TileEntityQuantumBase) tile;
			if (base.hasMaster()) {
				if (base instanceof TileEntityQuantumReactor) {
					TileEntityQuantumReactor core = (TileEntityQuantumReactor) base;
					if (core.isCoreMaster() && !core.validateMultiblock().isValid)
						core.destroyMultiblock();
				} else {
					if (!base.checkMasterValid())
						base.resetMaster();
				}
			}
		}
		
		super.onNeighborBlockChange(world, x, y, z, block);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		
		if (world.isRemote)
			return false;
		
		TileEntity selfTile = world.getTileEntity(x, y, z);
		if (selfTile == null || !(selfTile instanceof TileEntityQuantumBase))
			return false;
		
		TileEntityQuantumBase base = (TileEntityQuantumBase) selfTile;
		
		if (player.getHeldItem() != null && player.getHeldItem().getItem() == ResourceItemRegistry.THERMOMETER.getItem()) {
			player.addChatMessage(new ChatComponentText("Block temperature: " + (int) base.getTemperature() + "/" + base.getMaxTemperature() + " degrees"));
			return true;
		}
		
		if (player.isSneaking()) {
			if (!base.hasMaster()) {
				player.addChatMessage(new ChatComponentText("[DEBUG]: Tile has no master and is not part of a multiblock structure."));
				return false;
			}
			
			if (base.checkMasterValid()) {
				player.addChatMessage(new ChatComponentText("[DEBUG]: Master reactor core has passed structural validation."));
			} else {
				player.addChatMessage(new ChatComponentText("[DEBUG]: Master reactor core failed to pass structural validation."));
			}
		}
		
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		TileEntity selfTile = world.getTileEntity(x, y, z);
		if (selfTile != null && selfTile instanceof TileEntityQuantumBase) {
			TileEntityQuantumBase base = (TileEntityQuantumBase) selfTile;
			
			if (base.hasMaster()) {
				TileEntityReference coord = base.getMaster();
				TileEntity master = world.getTileEntity(coord.x, coord.y, coord.z);
				
				if (master != null && master instanceof TileEntityQuantumReactor) {
					TileEntityQuantumReactor core = (TileEntityQuantumReactor) master;
					if (core.isCoreMaster())
						core.destroyMultiblock();
				}
			}
		}
		
		super.breakBlock(world, x, y, z, block, state);
	}
}
