package xyz.towerdevs.nullifactor.common.tileentities;

import java.util.ArrayList;
import java.util.List;

import com.brandon3055.brandonscore.common.handlers.ProcessHandler;
import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.reactor.ReactorExplosion;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.instantiable.BlockReference;
import xyz.towerdevs.nullifactor.common.blocks.BlockQuantumReactorPylonCap;

public class TileEntityQuantumReactor extends TileEntityQuantumBase {
	private static int maxReactorRadius = 9;
	private static int maxReactorStackHeight = 16;
	
	private boolean tileIsMaster = false;
	
	// Variables below are used only by the master core.
	
	/** Whether the reactor is active. This does NOT mean all fuel cores are online, but at least one is. */
    private boolean reactorActive = false;
    
    /** Total strength of the containment field produced by all electromagnets combined. 
     * If this falls below one of the singularities' threshold, the reactor will be doomed. */
    private int containmentStrength;
    
    /** Number of ticks that the reactor takes until it will perform the next update cycle.
     * This only controls power output and containment field strength. The singularities are independent from this update. */
    private static int reactorTickInterval = 20;
	
	private int minx, miny, minz;
	private int maxx, maxy, maxz;
	private boolean minMaxSet = false;
	
	//private ReactorExplosion explosionProcess;
	private List<TileEntityQuantumBase> containmentElectromagnets;
	private List<TileEntityQuantumBase> powerTaps;
	
	public TileEntityQuantumReactor() {
		this.canUpdate = true;
	}
	
	/** Subclass which returns the multiblock validation result. */
    public static class MultiblockValidationResult {
    	public boolean isValid = false;
    	public String error;
		public MultiblockValidationResult(boolean isValid, String error) {
			this.isValid = isValid;
			this.error = error;
		}
		
		public MultiblockValidationResult(boolean valid) {
			this(valid, null);
		}
	}
	
	/** Sets this reactor core as the multiblock master. */
	public void setCoreAsMaster() {
		this.tileIsMaster = true;
	}
	
	/** Retrieves whether this core is the master of the multiblock. */
	public boolean isCoreMaster() {
		return this.tileIsMaster;
	}
	
	@Override
	public void resetMaster() {
		super.resetMaster();
		this.tileIsMaster = false;
		this.minx = 0;
		this.miny = 0;
		this.minz = 0;
		this.maxx = 0;
		this.maxy = 0;
		this.maxz = 0;
		
		this.minMaxSet = false;
		this.containmentElectromagnets = null;
		this.powerTaps = null;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.tileIsMaster = nbt.getBoolean("TileIsMaster");
		
		if (nbt.getBoolean("MinMaxSet")) {
			this.minMaxSet = true;
			
			this.minx = nbt.getInteger("MinX");
			this.miny = nbt.getInteger("MinY");
			this.minz = nbt.getInteger("MinZ");
			this.maxx = nbt.getInteger("MaxX");
			this.maxy = nbt.getInteger("MaxY");
			this.maxz = nbt.getInteger("MaxZ");
		}
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setBoolean("TileIsMaster", this.tileIsMaster);
		nbt.setBoolean("MinMaxSet", this.minMaxSet);
		nbt.setInteger("MinX", this.minx);
		nbt.setInteger("MinY", this.miny);
		nbt.setInteger("MinZ", this.minz);
		nbt.setInteger("MaxX", this.maxx);
		nbt.setInteger("MaxY", this.maxy);
		nbt.setInteger("MaxZ", this.maxz);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			if (this.tileIsMaster) {
				if (this.containmentElectromagnets == null)
					this.containmentElectromagnets = this.getReactorBlocks(TileEntityQuantumReactorContainmentElectromagnet.class);
				
				if (this.powerTaps == null)
					this.powerTaps = this.getReactorBlocks(TileEntityQuantumReactorPowerTap.class);
				
				if (this.currentTick == reactorTickInterval) {
					this.performReactorUpdate();
					
				}
			}
			
			// Interval reset should be called on the server only.
			if (this.currentTick == reactorTickInterval) {
				this.currentTick = 0;
			} else {
				this.currentTick++;
			}
		}
	}
	
	/** Performs a reactor update. Is performed each {@link reactorTickInterval}. */
	private void performReactorUpdate() {
		this.updateElectromagnetContainment();
	}
	
	/** Updates the containment electromagnet strength.
	 * This updates the {@link containmentStrength} field so the singularities know how much containment is available. */
	private void updateElectromagnetContainment() {
		if (this.containmentElectromagnets == null)
			return;
		
		int newContainment = 0;
		for (int i = 0; i < this.containmentElectromagnets.size(); i++) {
			TileEntityQuantumBase base = this.containmentElectromagnets.get(i);
			
			if (base instanceof TileEntityQuantumReactorContainmentElectromagnet) {
				TileEntityQuantumReactorContainmentElectromagnet electromagnet = (TileEntityQuantumReactorContainmentElectromagnet) base;
				
				newContainment = newContainment + electromagnet.getContainmentStrength();
			}
		}
		
		this.containmentStrength = newContainment;
	}
	
	public List<TileEntityQuantumBase> getContainmentElectromagnets() {
		return this.containmentElectromagnets;
	}
	
	public List<TileEntityQuantumBase> getPowerTaps() {
		return this.powerTaps;
	}
	
	public int getContainmentStrength() {
		return this.containmentStrength;
	}
	
	/** Sets the reactor power state, online or offline. 
	 * No failsafe checks are performed by this method. */
	public void setReactorOnline(boolean online) {
		this.reactorActive = online;
		
		if (this.reactorActive) {
			
		} else {
			
		}
	}
	
	/** Gets the reactors in a ring around this core reactor in the radius defined by maxSearchRadius. 
	 * Returns a {@link ConnectedReactorResult} with the reactors, found radius, and min and max coords of the multiblock. */
	public ConnectedReactorResult getConnectedReactors(int maxSearchRadius) {
		// Get tile entities directly adjacent to reactor
		
		List<TileEntityQuantumReactor> reactors = new ArrayList<TileEntityQuantumReactor>();
		int searchRadius = 1;
		int currBlockCount = 0;
		int prevReactors = 0;
		
		while (true) {
			List<TileEntityQuantumReactor> foundReactors = this.getReactorsWithinRadius(searchRadius, true);
			
			currBlockCount = (currBlockCount + 8);
			
			if ((foundReactors.size() - prevReactors) == currBlockCount) {
				reactors = foundReactors;
				prevReactors = prevReactors + (foundReactors.size() - prevReactors);
			} else {
				break;
			}
			
			if (searchRadius >= maxSearchRadius)
				break;
			
			searchRadius++;
		}
		
		reactors.add(0, this);
		int finalSearchRadius = searchRadius;
		
		if (searchRadius > 1)
			finalSearchRadius--;
		
		TileEntity tempReactor = this.worldObj.getTileEntity(this.xCoord + finalSearchRadius, this.yCoord, this.zCoord + finalSearchRadius);
		if (tempReactor instanceof TileEntityQuantumReactor) {
			TileEntityQuantumReactor maxReactor = (TileEntityQuantumReactor) tempReactor;
			List<TileEntityQuantumBase> rblocks = maxReactor.getTileEntitiesInColumn();
			TileEntityQuantumBase maxBlock = rblocks.get(rblocks.size() - 1);
			
			BlockReference minCoord = new BlockReference(null, this.xCoord - finalSearchRadius, this.yCoord, this.zCoord - finalSearchRadius);
			BlockReference maxCoord = new BlockReference(null, maxBlock.xCoord, maxBlock.yCoord, maxBlock.zCoord);
			
			return new ConnectedReactorResult(reactors, searchRadius, minCoord, maxCoord);
		}
		
		return new ConnectedReactorResult(reactors, searchRadius, new BlockReference(null, this.xCoord, this.yCoord, this.zCoord), new BlockReference(null, this.xCoord, this.yCoord, this.zCoord));
	}

    private List<TileEntityQuantumReactor> getReactorsWithinRadius(int radius, boolean excludeSelf) {
    	List<TileEntityQuantumReactor> entList = new ArrayList<TileEntityQuantumReactor>();
    	
    	for (int x = this.xCoord - radius; x <= this.xCoord + radius; x++) {
                for (int z = this.zCoord - radius; z <= this.zCoord + radius; z++) {
                	TileEntity ent = this.worldObj.getTileEntity(x, this.yCoord, z);
                	if (excludeSelf) {
                		if (!(x == this.xCoord && z == this.zCoord) && ent != null && ent instanceof TileEntityQuantumReactor)
                    		entList.add((TileEntityQuantumReactor) ent);
                	} else {
                		if (ent != null && ent instanceof TileEntityQuantumReactor)
                    		entList.add((TileEntityQuantumReactor) ent);
                	}
                }
        }
    	
    	return entList;
    }
    
    private List<TileEntityQuantumBase> getTilesWithinReactor() {
    	List<TileEntityQuantumBase> entList = new ArrayList<TileEntityQuantumBase>();
    	if (!this.minMaxSet)
    		return entList;
    	
    	for (int x = this.minx; x <= this.maxx; x++) {
    		for (int y = this.miny; y <= this.maxy; y++) {
    			for (int z = this.minz; z <= this.maxz; z++) {
                	TileEntity ent = this.worldObj.getTileEntity(x, y, z);
                	if (ent != null && ent instanceof TileEntityQuantumBase)
                		entList.add((TileEntityQuantumBase) ent);
                }
    		}
        }
	
	    return entList;
    }
    
    public MultiblockValidationResult validateMultiblock() {
    	return this.validateMultiblock(true);
    }
	
	private MultiblockValidationResult validateMultiblock(boolean setMinMax) {
		ConnectedReactorResult res = this.getConnectedReactors(TileEntityQuantumReactor.maxReactorRadius);
		List<TileEntityQuantumReactor> adj = res.reactors;
		if (adj.size() < 8)
			return new MultiblockValidationResult(false, "Too few reactor cores for multiblock, or reactor core is not the center core.");
		
		int firstCoreSize = adj.get(0).getTileEntitiesInColumn().size();
		// Iterate through the adjacent reactor cores
		for (int i = 0; i < adj.size(); i++) {
			TileEntityQuantumReactor core = adj.get(i);
			List<TileEntityQuantumBase> coreBlocks = core.getTileEntitiesInColumn();
			
			if (firstCoreSize != coreBlocks.size())
				return new MultiblockValidationResult(false, "Number of blocks in the reactor stacks do not match. Please ensure all reactor stacks are the same height.");
			
			if (coreBlocks.size() > 16)
				return new MultiblockValidationResult(false, "Reactor stack on core " + i + " is too large. The maximum modules in a stack is " + TileEntityQuantumReactor.maxReactorStackHeight + ".");
			
			if (coreBlocks.size() < 2)
				return new MultiblockValidationResult(false, "Reactor stack on core " + i + " is too small. The smallest modules in a stack is 2, not including the pylon cap.");
			
			TileEntityQuantumBase lastBlock = coreBlocks.get(coreBlocks.size() - 1);
			Block capBlock = this.worldObj.getBlock(lastBlock.xCoord, lastBlock.yCoord, lastBlock.zCoord);
			if (capBlock == null || !(capBlock instanceof BlockQuantumReactorPylonCap))
				return new MultiblockValidationResult(false, "Reactor stack on core " + i + " is not capped.");
			
			//for (int i2 = 0; i < coreBlocks.size(); i++) {
				
			//}
		}
		
		// Verify content of the inner cores
		List<TileEntityQuantumReactor> contadj;
		contadj = this.getReactorsWithinRadius(res.radiusFound - 2, false);
		
		for (int i = 0; i < contadj.size(); i++) {
			TileEntityQuantumReactor core = contadj.get(i);
			List<TileEntityQuantumBase> coreBlocks = core.getTileEntitiesInColumn();
			
			for (int i2 = 0; i2 < coreBlocks.size(); i2++) {
				if (i2 != 0) {
					TileEntityQuantumBase coreBlock = coreBlocks.get(i2);
					
					if ((coreBlock == null || !(coreBlock instanceof TileEntityQuantumReactorSingularity)) && !(i2 == coreBlocks.size() - 1 && this.worldObj.getBlock(coreBlock.xCoord, coreBlock.yCoord, coreBlock.zCoord) instanceof BlockQuantumReactorPylonCap))
						return new MultiblockValidationResult(false, "Invalid module on core " + i + "! The inner reactor chamber must contain singularity frames only with no air blocks.");
				}
			}
		}
		
		if (setMinMax) {
			this.minx = res.minBlock.x;
			this.miny = res.minBlock.y;
			this.minz = res.minBlock.z;
			this.maxx = res.maxBlock.x;
			this.maxy = res.maxBlock.y;
			this.maxz = res.maxBlock.z;
			
			this.minMaxSet = true;
		}
		
		return new MultiblockValidationResult(true);
	}
	
	/** Constructs the reactor multiblock by setting all of the sub-blocks to this master.
	 * This does not attempt to validate the multiblock! That must be done independantly.
	 */
	public void constructMultiblock() {
		ConnectedReactorResult res = this.getConnectedReactors(TileEntityQuantumReactor.maxReactorRadius);
		List<TileEntityQuantumReactor> adj = res.reactors;
		
		for (int i = 0; i < adj.size(); i++) {
			TileEntityQuantumReactor core = adj.get(i);
			core.setMaster(this.xCoord, this.yCoord, this.zCoord);
			
			List<TileEntityQuantumBase> coreBlocks = core.getTileEntitiesInColumn();
			
			for (int i2 = 0; i2 < coreBlocks.size(); i2++) {
				TileEntityQuantumBase coreBlock = coreBlocks.get(i2);
				if (coreBlock != null)
					coreBlock.setMaster(this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		this.setMaster(this.xCoord, this.yCoord, this.zCoord);
		this.setCoreAsMaster();
	}
	
	/** Destroys the multiblock by resetting all of the sub-blocks. */
	public void destroyMultiblock() {
		List<TileEntityQuantumBase> tilesInReactor = this.getTilesWithinReactor();
		for (int i = 0; i < tilesInReactor.size(); i++) {
			TileEntityQuantumBase base = tilesInReactor.get(i);
			base.resetMaster();
		}
		
		this.resetMaster();
	}
	
	public List<TileEntityQuantumBase> getTileEntitiesInColumn() {
		List<TileEntity> blocksAbove = this.getTileEntitiesInLineOnAxis(this.worldObj, ForgeDirection.UP, false);
		List<TileEntityQuantumBase> blocksFiltered = new ArrayList<TileEntityQuantumBase>();
		for (int i = 0; i < blocksAbove.size(); i++) {
			TileEntity ent = blocksAbove.get(i);
			if (ent != null && ent instanceof TileEntityQuantumBase)
				blocksFiltered.add((TileEntityQuantumBase) ent);
		}
		
		return blocksFiltered;
	}
	
	private List<TileEntityQuantumBase> getReactorBlocks(Class<? extends TileEntityQuantumBase> tileEntityClass) {
		List<TileEntityQuantumBase> tiles = this.getTilesWithinReactor();
		List<TileEntityQuantumBase> fTiles = new ArrayList<TileEntityQuantumBase>();
		
		for (int i = 0; i < tiles.size(); i++) {
			TileEntityQuantumBase tile = tiles.get(i);
			
			if (tile.getClass() == tileEntityClass) {
				fTiles.add(tile);
			}
		}
		
		return fTiles;
	}
	
	/** Fails the reactor, explosively. If DE is installed it will use the ReactorExplosion from it, otherwise a typical Explosion will be used as a fallback. */
	public void failExplosively() {
		ProcessHandler.addProcess(new ReactorExplosion(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 50F));
	}
}