package xyz.towerdevs.nullifactor.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.base.BlockReference;
import xyz.towerdevs.helios.base.HeliosMachineTileEntity;

public class TileEntityQuantumBase extends HeliosMachineTileEntity {
	private int masterX = 0, masterY = 0, masterZ = 0;
	
	// Everything in centigrate
	// No "freedom units" here :D
	protected int casingTemperature; // Current temperature of the casing. Affected by singularity fusion events.
	protected int casingMaxTemperature = 1370; // Temperature at which the casing fails / melts
	protected boolean tileHasMaster = false;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			if (this.tileHasMaster) {
				
			}
		}
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.masterX = nbt.getInteger("MasterX");
		this.masterY = nbt.getInteger("MasterY");
		this.masterZ = nbt.getInteger("MasterZ");
		this.tileHasMaster = nbt.getBoolean("TileHasMaster");
		
		this.casingTemperature = nbt.getInteger("CasingTemperature");
		this.casingMaxTemperature = nbt.getInteger("CasingMaxTemperature");
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setInteger("MasterX", this.masterX);
		nbt.setInteger("MasterY", this.masterY);
		nbt.setInteger("MasterZ", this.masterZ);
		nbt.setBoolean("TileHasMaster", this.tileHasMaster);
		
		nbt.setInteger("CasingTemperature", this.casingTemperature);
		nbt.setInteger("CasingMaxTemperature", this.casingMaxTemperature);
	}
	
	/** Sets the temperature of the block. This will make it melt if it surpasses the maximum temperature. */
	public void setTemperature(int temp) {
		this.casingTemperature = temp;
		if (this.casingTemperature >= this.casingMaxTemperature)
			this.blockMeltAndFail();
	}
	
	public int getTemperature() {
		return this.casingTemperature;
	}
	
	public int getMaxTemperature() {
		return this.casingMaxTemperature;
	}
	
	/** Removes this tile entity and its attached block. */
	public void removeBlock() {
		this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
	}
	
	/** Forces the block to melt into lava, blazing pyrotheum or another molten substance. */
	public void blockMeltAndFail() {
		this.removeBlock();
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.flowing_lava);
	}
	
	/** Checks whether the multiblock master at the specified coordinates is present and valid. */
	public boolean checkMasterValid() {
		TileEntity core = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		if (core != null && core instanceof TileEntityQuantumReactor)
			return true;
		
		return false;
	}
	
	/** Retrieves the coordinates of the master, as a BlockReference. */
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
	
	/** Returns whether the block has a master (is in a multiblock). */
	public boolean hasMaster() {
		return this.tileHasMaster;
	}
	
	protected List<TileEntity> getTileEntitiesInLineOnAxis(World world, ForgeDirection direction, boolean sameMasterOnly) {
		List<TileEntity> entList = new ArrayList<TileEntity>();
		entList.add(this);
		
		int xoffset = 0;
		int yoffset = 0;
		int zoffset = 0;
		while (true) {
			if (direction == ForgeDirection.UP) {
				yoffset++;
			} else if (direction == ForgeDirection.DOWN) {
				yoffset--;
			} else if (direction == ForgeDirection.NORTH) {
				xoffset--;
			} else if (direction == ForgeDirection.SOUTH) {
				xoffset++;
			} else if (direction == ForgeDirection.EAST) {
				zoffset++;
			} else if (direction == ForgeDirection.WEST) {
				zoffset--;
			} else {
				break;
			}
			
			TileEntity tile = world.getTileEntity(this.xCoord + xoffset, this.yCoord + yoffset, this.zCoord + zoffset);
			if (tile != null && tile instanceof TileEntityQuantumBase) {
				TileEntityQuantumBase machine = (TileEntityQuantumBase)tile;
				BlockReference machineMaster = machine.getMaster();
				BlockReference thisMaster = this.getMaster();
				
				if (sameMasterOnly) {
					if (machineMaster.x == thisMaster.x && machineMaster.y == thisMaster.y && machineMaster.z == thisMaster.z) {
						entList.add(machine);
					} else {
						break;
					}
				} else {
					entList.add(machine);
				}
			} else {
				break;
			}
		}			
		
		return entList;
	}
	
	public List<TileEntity> getTileEntitiesAbove() {
		return getTileEntitiesInLineOnAxis(this.worldObj, ForgeDirection.UP, false);
	}
	
	public List<TileEntity> getTileEntitiesBelow() {
		return getTileEntitiesInLineOnAxis(this.worldObj, ForgeDirection.DOWN, false);
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
