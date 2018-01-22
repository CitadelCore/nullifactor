package xyz.towerdevs.helios.base;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class HeliosOre extends HeliosBlock {
	
	private Item droppedItem;
	private int meta;
	private int min;
	private int max;

	public HeliosOre(String unlocalizedName, Material blockMaterial, String modId, Item droppedItem, int meta, int min, int max) {
		super(unlocalizedName, blockMaterial, modId);
		
		this.droppedItem = droppedItem;
		this.meta = meta;
		this.min = min;
		this.max = max;
	}
	
	public HeliosOre(String unlocalizedName, Material blockMaterial, String modId, Item droppedItem, int min, int max) {
		this(unlocalizedName, blockMaterial, modId, droppedItem, 0, min, max);
	}
	
	public HeliosOre(String unlocalizedName, Material blockMaterial, String modId, Item droppedItem) {
		this(unlocalizedName, blockMaterial, modId, droppedItem, 0, 1, 1);
	}
	
	@Override
	public Item getItemDropped(int metaData, Random random, int fortune) {
		return this.droppedItem;
	}
	
	@Override
	public int damageDropped(int metaData) {
		return this.meta;
	}
	
	@Override
	public int quantityDropped(int metaData, int fortune, Random random) {
		if (this.min >= this.max)
			return this.min;
		
		return this.min + random.nextInt(this.max - this.min + fortune + 1);
	}
}
