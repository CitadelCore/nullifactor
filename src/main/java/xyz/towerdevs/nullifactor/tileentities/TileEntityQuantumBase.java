package xyz.towerdevs.nullifactor.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.HeliosMachineTileEntity;

public class TileEntityQuantumBase extends HeliosMachineTileEntity {
	private int masterX = 0, masterY = 0, masterZ = 0;
	private boolean tileHasMaster = false;
	
	public void resetTileEntity() {
		this.masterX = 0;
		this.masterY = 0;
		this.masterZ = 0;
		this.tileHasMaster = false;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			if (this.tileHasMaster) {
				
			}
		} else {
			
		}
	}
	
	public boolean checkHasMaster() {
		TileEntity core = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		if (core != null && core instanceof TileEntityQuantumReactor)
			return true;
		
		return false;
	}
	
	private List<TileEntity> getTileEntitiesOnYAxis(World world, int offset) {
		List<TileEntity> entList = new ArrayList<TileEntity>();
		entList.add(this);
		
		TileEntity tile = world.getTileEntity(this.xCoord, this.yCoord + offset, this.zCoord);
		if (tile instanceof TileEntityQuantumBase) {
			TileEntityQuantumBase machine = (TileEntityQuantumBase)tile;
			
			entList.addAll(machine.getTileEntitiesAbove());
		}
		
		return entList;
	}
	
	public List<TileEntity> getTileEntitiesAbove() {
		return getTileEntitiesOnYAxis(this.worldObj, 1);
	}
	
	public List<TileEntity> getTileEntitiesBelow() {
		return getTileEntitiesOnYAxis(this.worldObj, -1);
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
