package xyz.towerdevs.nullifactor.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.base.BlockReference;
import xyz.towerdevs.nullifactor.blocks.BlockQuantumReactorPylonCap;

public class TileEntityQuantumReactor extends TileEntityQuantumBase {
	private static int maxReactorRadius = 9;
	private static int maxReactorStackHeight = 16;
	
	private List<TileEntityQuantumReactor> adjacentReactors = new ArrayList<TileEntityQuantumReactor>();
	private List<TileEntityQuantumBase> blocksInColumn = new ArrayList<TileEntityQuantumBase>();
	private boolean isColumnValid = false;
	private boolean tileIsMaster = false;
	
	private BlockReference minBlock;
	private BlockReference maxBlock;
	
	public void addAdjacentReactor(TileEntityQuantumReactor reactor) {
		this.adjacentReactors.add(reactor);
	}
	
	/** Sets this reactor core as the multiblock master. */
	public void setCoreAsMaster() {
		this.tileIsMaster = true;
	}
	
	public boolean isCoreMaster() {
		return this.tileIsMaster;
	}
	
	@Override
	public void resetMaster() {
		super.resetMaster();
		this.tileIsMaster = false;
		this.minBlock = null;
		this.maxBlock = null;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.tileIsMaster = nbt.getBoolean("TileIsMaster");
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setBoolean("TileIsMaster", this.tileIsMaster);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			//if (!this.tileHasMaster && this.validateMultiblock(false)) {
				//this.constructMultiblock();
			//}
		}
	}
	
	public ConnectedReactorResult getConnectedReactors(int maxSearchRadius) {
		// Get tile entities directly adjacent to reactor
		
		List<TileEntityQuantumReactor> reactors = new ArrayList<TileEntityQuantumReactor>();
		int searchRadius = 1;
		int currBlockCount = 0;
		int prevReactors = 0;
		
		while (true) {
			List<TileEntityQuantumReactor> foundReactors = this.getReactorsWithinRadius(searchRadius);
			
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
		
		TileEntity tempReactor = this.worldObj.getTileEntity(this.xCoord + searchRadius, this.yCoord, this.zCoord + searchRadius);
		if (tempReactor instanceof TileEntityQuantumReactor) {
			TileEntityQuantumReactor maxReactor = (TileEntityQuantumReactor) tempReactor;
			List<TileEntityQuantumBase> rblocks = maxReactor.getBlocksInColumn();
			TileEntityQuantumBase maxBlock = rblocks.get(rblocks.size() - 1);
			
			BlockReference minCoord = new BlockReference(null, this.xCoord - searchRadius, this.yCoord, this.zCoord - searchRadius);
			BlockReference maxCoord = new BlockReference(null, maxBlock.xCoord, maxBlock.yCoord, maxBlock.zCoord);
			
			return new ConnectedReactorResult(reactors, searchRadius, minCoord, maxCoord);
		}
		
		return new ConnectedReactorResult(reactors, searchRadius, new BlockReference(null, this.xCoord, this.yCoord, this.zCoord), new BlockReference(null, this.xCoord, this.yCoord, this.zCoord));
	}

    private List<TileEntityQuantumReactor> getReactorsWithinRadius(int radius) {
    	List<TileEntityQuantumReactor> entList = new ArrayList<TileEntityQuantumReactor>();
    	
    	for (int x = this.xCoord - radius; x <= this.xCoord + radius; x++) {
                for (int z = this.zCoord - radius; z <= this.zCoord + radius; z++) {
                	TileEntity ent = this.worldObj.getTileEntity(x, this.yCoord, z);
                	if (!(x == this.xCoord && z == this.zCoord) && ent != null && ent instanceof TileEntityQuantumReactor)
                		entList.add((TileEntityQuantumReactor) ent);
                }
        }
    	
    	return entList;
    }
    
    private List<TileEntityQuantumBase> getTilesWithinReactor() {
    	List<TileEntityQuantumBase> entList = new ArrayList<TileEntityQuantumBase>();
    	if (this.minBlock == null || this.maxBlock == null)
    		return entList;
    	
    	for (int x = this.xCoord - this.minBlock.x; x <= this.xCoord + this.maxBlock.x; x++) {
    		for (int y = this.yCoord - this.minBlock.y; y <= this.yCoord + this.maxBlock.y; y++) {
    			for (int z = this.zCoord - this.minBlock.z; z <= this.zCoord + this.maxBlock.z; z++) {
                	TileEntity ent = this.worldObj.getTileEntity(x, y, z);
                	if (ent != null && ent instanceof TileEntityQuantumBase)
                		entList.add((TileEntityQuantumBase) ent);
                }
    		}
        }
	
	    return entList;
    }
    
    public boolean validateMultiblock(boolean verbose) {
    	return this.validateMultiblock(verbose, false);
    }
	
	private boolean validateMultiblock(boolean verbose, boolean setMinMax) {
		ConnectedReactorResult res = this.getConnectedReactors(TileEntityQuantumReactor.maxReactorRadius);
		List<TileEntityQuantumReactor> adj = res.reactors;
		if (adj.size() < 8) {
			this.logIfVerbose("Too few reactor cores for multiblock, or reactor core is not the center core.", verbose);
			return false;
		}
			
		// Iterate through the adjacent reactor cores
		for (int i = 0; i < adj.size(); i++) {
			TileEntityQuantumReactor core = adj.get(i);
			List<TileEntityQuantumBase> coreBlocks = core.getBlocksInColumn();
			
			if (coreBlocks.size() > 16) {
				this.logIfVerbose("Reactor stack on core " + i + " is too large. The maximum modules in a stack is " + TileEntityQuantumReactor.maxReactorStackHeight + ".", verbose);
				return false;
			}
			
			if (coreBlocks.size() < 2) {
				this.logIfVerbose("Reactor stack on core " + i + " is too small. The smallest modules in a stack is 2, not including the pylon cap.", verbose);
				return false;
			}
			
			TileEntityQuantumBase lastBlock = coreBlocks.get(coreBlocks.size() - 1);
			Block capBlock = this.worldObj.getBlock(lastBlock.xCoord, lastBlock.yCoord + 1, lastBlock.zCoord);
			if (capBlock == null || !(capBlock instanceof BlockQuantumReactorPylonCap)) {
				this.logIfVerbose("Reactor stack on core " + i + " is not capped.", verbose);
			}
			
			//for (int i2 = 0; i < coreBlocks.size(); i++) {
				
			//}
		}
		
		// Verify content of the inner cores
		ConnectedReactorResult cont = this.getConnectedReactors(res.radiusFound - 1);
		List<TileEntityQuantumReactor> contadj = cont.reactors;
		
		for (int i = 0; i < contadj.size(); i++) {
			TileEntityQuantumReactor core = contadj.get(i);
			List<TileEntityQuantumBase> coreBlocks = core.getBlocksInColumn();
			
			for (int i2 = 0; i2 < coreBlocks.size(); i2++) {
				TileEntityQuantumBase coreBlock = coreBlocks.get(i2);
				if (coreBlock == null) {
					this.logIfVerbose("Invalid module on core " + i + "! The inner reactor chamber must contain singularity frames only with no air blocks.", verbose);
					return false;
				}
			}
		}
		
		if (setMinMax) {
			this.minBlock = res.minBlock;
			this.maxBlock = res.maxBlock;
		}
		
		this.logIfVerbose("Multiblock is valid.", verbose);
		return true;
	}
	
	public void logIfVerbose(String text, boolean verbose) {
		if (verbose)
			System.out.println(text);
	}
	
	/** Constructs the reactor multiblock by setting all of the sub-blocks to this master. */
	public void constructMultiblock() {
		if (this.validateMultiblock(false, true)) {
			ConnectedReactorResult res = this.getConnectedReactors(TileEntityQuantumReactor.maxReactorRadius);
			List<TileEntityQuantumReactor> adj = res.reactors;
			
			for (int i = 0; i < adj.size(); i++) {
				TileEntityQuantumReactor core = adj.get(i);
				core.setMaster(this.xCoord, this.yCoord, this.zCoord);
				
				List<TileEntityQuantumBase> coreBlocks = core.getBlocksInColumn();
				
				for (int i2 = 0; i2 < coreBlocks.size(); i2++) {
					TileEntityQuantumBase coreBlock = coreBlocks.get(i2);
					if (coreBlock != null)
						coreBlock.setMaster(this.xCoord, this.yCoord, this.zCoord);
				}
			}
			
			this.setCoreAsMaster();
		}
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
	
	public List<TileEntityQuantumBase> getBlocksInColumn() {
		List<TileEntity> blocksAbove = this.getTileEntitiesAbove();
		List<TileEntityQuantumBase> blocksFiltered = new ArrayList<TileEntityQuantumBase>();
		for (int i = 0; i < blocksAbove.size(); i++) {
			TileEntity ent = blocksAbove.get(i);
			if (ent != null && ent instanceof TileEntityQuantumBase)
				blocksFiltered.add((TileEntityQuantumBase) ent);
		}
		
		return blocksFiltered;
	}
}