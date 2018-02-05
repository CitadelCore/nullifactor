package xyz.towerdevs.nullifactor.common.registries;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;

public enum WorldGeneratorRegistry {
	QUANTANIUM(ResourceOreRegistry.QUANTANIUMORE.getOre(), 4, 5, 0, 40),
	POTATO(ResourceOreRegistry.POTATOORE.getOre(), 8, 40, 30, 60),
	SERPENTINITE(ResourceOreRegistry.SERPENTINITEORE.getOre(), 8, 20, 30, 60),
	
	;
	private WorldGenMinable minable;
	private int dimId;
	private int chanceToSpawn;
	private int minHeight;
	private int maxHeight;
	
	public static final WorldGeneratorRegistry[] generators = values();
	private WorldGeneratorRegistry(Block ore, int count, int chanceToSpawn, int minHeight, int maxHeight, int dimId) {
		this.minable = new WorldGenMinable(ore, count);
		this.chanceToSpawn = chanceToSpawn;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.dimId = dimId;
	}
	
	private WorldGeneratorRegistry(Block ore, int count, int chanceToSpawn, int minHeight, int maxHeight) {
		this(ore, count, chanceToSpawn, minHeight, maxHeight, 0);
	}

	public WorldGenMinable GetGenerator() { return this.minable; }
	
	public int GetDimId() { return this.dimId; }
	public int GetChanceToSpawn() { return this.chanceToSpawn; }
	public int GetMinHeight() { return this.minHeight; }
	public int GetMaxHeight() { return this.maxHeight; }
}
