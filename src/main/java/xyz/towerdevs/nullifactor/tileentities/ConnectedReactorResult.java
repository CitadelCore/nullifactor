package xyz.towerdevs.nullifactor.tileentities;

import java.util.List;

import xyz.towerdevs.helios.base.BlockReference;

public class ConnectedReactorResult {
	public List<TileEntityQuantumReactor> reactors;
	public int radiusFound;
	BlockReference minBlock;
	BlockReference maxBlock;
	public ConnectedReactorResult(List<TileEntityQuantumReactor> reactors, int radiusFound, BlockReference minBlock, BlockReference maxBlock) {
		this.reactors = reactors;
		this.radiusFound = radiusFound;
		this.minBlock = minBlock;
		this.maxBlock = maxBlock;
	}
}
