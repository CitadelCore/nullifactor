package xyz.towerdevs.nullifactor.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.BlockReference;
import xyz.towerdevs.helios.base.HeliosMachineTileEntity;

public class TileEntityQuantumBase extends HeliosMachineTileEntity {
	private int masterX = 0, masterY = 0, masterZ = 0;
	protected boolean tileHasMaster = false;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			if (this.tileHasMaster) {
				
			}
		}
	}
	
	/** Checks whether the multiblock master at the specified coordinates is present and valid. */
	public boolean checkMasterValid() {
		TileEntity core = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		if (core != null && core instanceof TileEntityQuantumReactor)
			return true;
		
		return false;
	}
	
	public BlockReference getMaster() {
		return new BlockReference(null, this.masterX, this.masterY, this.masterZ);
	} 
	
	/** Sets the multiblock master of this block to the tile entity at the specified coordinates. */
	public void setMaster(int masterX, int masterY, int masterZ) {
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		this.tileHasMaster = true;
	}
	
	/** Resets the multiblock master and clears multiblock information. */
	public void resetMaster() {
		this.masterX = 0;
		this.masterY = 0;
		this.masterZ = 0;
		this.tileHasMaster = false;
	}
	
	public boolean hasMaster() {
		return this.tileHasMaster;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.masterX = nbt.getInteger("MasterX");
		this.masterY = nbt.getInteger("MasterY");
		this.masterZ = nbt.getInteger("MasterZ");
		this.tileHasMaster = nbt.getBoolean("TileHasMaster");
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setInteger("MasterX", this.masterX);
		nbt.setInteger("MasterY", this.masterY);
		nbt.setInteger("MasterZ", this.masterZ);
		nbt.setBoolean("TileHasMaster", this.tileHasMaster);
	}
	
	protected List<TileEntity> getTileEntitiesInLineOnAxis(World world, int xoffset, int yoffset, int zoffset) {
		List<TileEntity> entList = new ArrayList<TileEntity>();
		entList.add(this);
		
		TileEntity tile = world.getTileEntity(this.xCoord + xoffset, this.yCoord + yoffset, this.zCoord + zoffset);
		if (tile instanceof TileEntityQuantumBase) {
			TileEntityQuantumBase machine = (TileEntityQuantumBase)tile;
			
			entList.addAll(machine.getTileEntitiesAbove());
		}
		
		return entList;
	}
	
	public List<TileEntity> getTileEntitiesAbove() {
		return getTileEntitiesInLineOnAxis(this.worldObj, 0, 1, 0);
	}
	
	public List<TileEntity> getTileEntitiesBelow() {
		return getTileEntitiesInLineOnAxis(this.worldObj, 0, -1, 0);
	}
	
	public List<TileEntity> getAdjacentTileEntities() {
		List<TileEntity> entList = new ArrayList<TileEntity>();
		entList.add(this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord));
		entList.add(this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord));
		entList.add(this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord));
		entList.add(this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord));
		entList.add(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1));
		entList.add(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1));
		
		return entList;
	}
}
