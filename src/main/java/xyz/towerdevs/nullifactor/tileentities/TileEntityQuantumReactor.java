package xyz.towerdevs.nullifactor.tileentities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.nullifactor.blocks.BlockQuantumReactorPylonCap;

public class TileEntityQuantumReactor extends TileEntityQuantumBase {
	private List<TileEntityQuantumReactor> adjacentReactors = new ArrayList<TileEntityQuantumReactor>();
	private List<TileEntityQuantumBase> blocksInColumn = new ArrayList<TileEntityQuantumBase>();
	private boolean isColumnValid = false;
	private boolean isMasterCore = false;
	
	public void addAdjacentReactor(TileEntityQuantumReactor reactor) {
		this.adjacentReactors.add(reactor);
	}
	
	public List<TileEntityQuantumReactor> getAdjacentReactors() {
		this.adjacentReactors.clear();
		List<TileEntity> adjacent = this.getAdjacentTileEntities();
		
		for (int i = 0; i < adjacent.size(); i++) {
			TileEntity entity = adjacent.get(i);
			if (entity != null && entity instanceof TileEntityQuantumReactor && entity.yCoord == this.yCoord)
				this.adjacentReactors.add((TileEntityQuantumReactor) (entity));
		}
		
		return this.adjacentReactors;
	}
	
	public void validateMultiblock() throws Exception {
		List<TileEntityQuantumReactor> adj = this.getAdjacentReactors();
		if (adj.size() < 4)
			throw new Exception("Too few reactor cores for multiblock, or reactor core is not the center core.");
			
		for (int i = 0; i < adj.size(); i++) {
			TileEntityQuantumReactor core = adj.get(i);
			List<TileEntityQuantumBase> coreBlocks = core.getBlocksInColumn();
			
			if (coreBlocks.size() > 16)
				throw new Exception("Reactor stack on core " + i + " is too large. The maximum modules in a stack is 16.");
			
			if (coreBlocks.size() < 2)
				throw new Exception("Reactor stack on core " + i + " is too small. The smallest modules in a stack is 2, not including the pylon cap.");
			
			TileEntityQuantumBase lastBlock = coreBlocks.get(coreBlocks.size());
			Block capBlock = this.worldObj.getBlock(lastBlock.xCoord, lastBlock.yCoord + 1, lastBlock.zCoord);
			if (capBlock == null || !(capBlock instanceof BlockQuantumReactorPylonCap))
				throw new Exception("Reactor stack on core " + i + " is not capped.");
			
			//for (int i2 = 0; i < coreBlocks.size(); i++) {
				
			//}
		}
	}
	
	public List<TileEntityQuantumBase> getBlocksInColumn() {
		return this.blocksInColumn;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.isColumnValid = nbt.getBoolean("IsColumnValid");
	}
	
    @Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setBoolean("IsColumnValid", this.isColumnValid);
	}
}