package xyz.towerdevs.nullifactor.common.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.MathUtilities;
import xyz.towerdevs.helios.base.HeliosMachineTileEntity;
import xyz.towerdevs.helios.instantiable.BlockReference;
import xyz.towerdevs.helios.instantiable.TileEntityReference;
import xyz.towerdevs.helios.registries.HeatConductivity;

public class TileEntityQuantumBase extends HeliosMachineTileEntity {
	private int masterX = 0, masterY = 0, masterZ = 0;
	
	// All units are in celsius.
	protected double casingTemperature; // Current temperature of the casing. Affected by singularity fusion events.
	protected boolean tileHasMaster = false;
	
	// Properties at which this block conducts heat.
	protected HeatConductivity heatConductivity = HeatConductivity.STEEL;
	protected List<TileEntity> adjacentTileEntities;
	
	public TileEntityQuantumBase() {
		this.canUpdate = true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {		
			if (this.adjacentTileEntities == null) {
				this.adjacentTileEntities = this.getAdjacentTileEntities();
			} else if (this.currentTick % 80 == 0) {
				this.adjacentTileEntities = this.getAdjacentTileEntities();
			}
			
			this.radiateHeat();
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
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setInteger("MasterX", this.masterX);
		nbt.setInteger("MasterY", this.masterY);
		nbt.setInteger("MasterZ", this.masterZ);
		nbt.setBoolean("TileHasMaster", this.tileHasMaster);
		
		nbt.setDouble("CasingTemperature", this.casingTemperature);
		nbt.setInteger("CasingMaxTemperature", this.heatConductivity.getMeltingPoint());
	}
	
	/** Sets the temperature of the block. This will make it melt if it surpasses the maximum temperature. */
	public void setTemperature(double temp) {
		if (this.tileEntityInvalid)
			return;
		
		this.casingTemperature = temp;
		if (this.casingTemperature >= this.heatConductivity.getMeltingPoint())
			this.blockMeltAndFail();
	}
	
	/** Increases the temperature of the block by the specified amount. */
	public void addTemperature(double temp) {
		this.setTemperature(this.getTemperature() + temp);
	}
	
	/** Returns the current temperature of the block. */
	public double getTemperature() {
		return this.casingTemperature;
	}
	
	/** Returns the temperature at which this block will melt. */
	public int getMaxTemperature() {
		return this.heatConductivity.getMeltingPoint();
	}
	
	/** Radiates heat to the blocks around itself. */
	public void radiateHeat() {
		if (this.adjacentTileEntities == null || this.adjacentTileEntities.size() <= 0 || this.casingTemperature == 0)
			return;
		
		// Base heat to transfer without heat loss calculations applied
		double maxHeatTransfer = Math.min(this.casingTemperature, (this.heatConductivity.getHeatConductivity() * 10));
		
		// Total heat transferred out of this entity.
		double heatTotal = (int) Math.floor(maxHeatTransfer - (maxHeatTransfer / 8.0F));
		
		for (int i = 0; i < this.adjacentTileEntities.size(); i++) {
			TileEntity ent = this.adjacentTileEntities.get(i);
			if (ent instanceof TileEntityQuantumBase) {
				TileEntityQuantumBase base = (TileEntityQuantumBase) ent;
				
				// By default, heat loss is 1/8th of total heat per tick.
				base.addTemperature(heatTotal / this.adjacentTileEntities.size());
				
				// Removes temperature from this block
				float tempToRemove = (float) (maxHeatTransfer / this.adjacentTileEntities.size());
				this.setTemperature(Math.max(0, this.casingTemperature - tempToRemove));
			}
		}
		
		// Radiant heat
		this.setTemperature(Math.max(0, Math.floor(this.getTemperature() - maxHeatTransfer / 32.0F)));
	}
	
	/** Removes this tile entity and its attached block. */
	public void removeBlock() {
		this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
		this.invalidate();
	}
	
	/** Forces the block to melt into lava, blazing pyrotheum or another molten substance. */
	public void blockMeltAndFail() {
		//if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != Blocks.flowing_lava)
			//return;
		
		// 1/4 chance the block melts
		if (!MathUtilities.randomBoolean(0.5F))
			return;
		
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
	
	/** Retrieves the master, as a TileEntityReference. */
	public TileEntityReference getMaster() {
		
		TileEntity master = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		if (master != null && master instanceof TileEntityQuantumReactor) {
			TileEntityQuantumReactor core = (TileEntityQuantumReactor) master;
			return new TileEntityReference(core, this.masterX, this.masterY, this.masterZ);
		}
		
		return new TileEntityReference(null, this.masterX, this.masterY, this.masterZ);
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
	
	/** Gets all blocks on a line in a specified {@link ForgeDirection}, until we hit air. */
	protected List<BlockReference> getBlocksInLineOnAxis(World world, ForgeDirection direction) {
		List<BlockReference> blockList = new ArrayList<BlockReference>();
		
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
			
			Block block = world.getBlock(this.xCoord + xoffset, this.yCoord + yoffset, this.zCoord + zoffset);
			
			if (block != null && block != Blocks.air) {
				blockList.add(new BlockReference(block, this.xCoord + xoffset, this.yCoord + yoffset, this.zCoord + zoffset));
			} else {
				break;
			}
		}
		
		return blockList;
	}
	
	protected List<TileEntity> getTileEntitiesInLineOnAxis(World world, ForgeDirection direction, boolean sameMasterOnly) {
		List<BlockReference> blockList = this.getBlocksInLineOnAxis(world, direction);
		
		List<TileEntity> entList = new ArrayList<TileEntity>();
		entList.add(this);
		
		for (int i = 0; i < blockList.size(); i++) {
			BlockReference block = blockList.get(i);
			
			TileEntity tile = world.getTileEntity(block.x, block.y, block.z);
			if (tile != null && tile instanceof TileEntityQuantumBase) {
				TileEntityQuantumBase machine = (TileEntityQuantumBase)tile;
				TileEntityReference machineMaster = machine.getMaster();
				TileEntityReference thisMaster = this.getMaster();
				
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
