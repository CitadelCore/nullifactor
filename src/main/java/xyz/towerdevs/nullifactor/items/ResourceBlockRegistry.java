package xyz.towerdevs.nullifactor.items;

import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.interfaces.IResourceRegistry;
import xyz.towerdevs.nullifactor.Nullifactor;

public enum ResourceBlockRegistry implements IResourceRegistry {
	// Resource Blocks
	BASEMACHINEFRAME("base_machine_frame", Material.iron),
	;
	
	private final HeliosBlock heliosBlock;
	private final String blockName;
	public static final ResourceBlockRegistry[] blockRegistry = values();
	
	/** Registers a resource block - the texture name should be the same as the unlocalized name. */
	private ResourceBlockRegistry(String unlocalizedName, Material material) { 
		this.blockName = unlocalizedName;
		this.heliosBlock = new HeliosBlock(unlocalizedName, material, Nullifactor.MODID);
	}
	
	public HeliosBlock getBlock() { return this.heliosBlock; }
	public String getUnlocalizedName() { return this.blockName; }
	
	public static void PostReigsterBlocks() { 
	}
}
