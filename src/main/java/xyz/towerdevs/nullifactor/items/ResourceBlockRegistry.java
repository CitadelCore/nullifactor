package xyz.towerdevs.nullifactor.items;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.base.HeliosOre;
import xyz.towerdevs.helios.interfaces.IResourceRegistry;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.misc.NullifactorAchievementRegistry;

public enum ResourceBlockRegistry implements IResourceRegistry {
	// Resource Blocks
	BASEMACHINEFRAME("base_machine_frame", Material.iron),
	QUANTUMREACTORPYLON("quantum_reactor_pylon", Material.iron),
	QUANTUMREACTORPYLONTOP("quantum_reactor_pylon_top", Material.iron),
	;
	
	private final HeliosBlock heliosBlock;
	private final String blockName;
	public static final ResourceBlockRegistry[] blockRegistry = values();
	
	/** Registers a resource block - the texture name should be the same as the unlocalized name. */
	private ResourceBlockRegistry(String unlocalizedName, Material material) { 
		this.blockName = unlocalizedName;
		heliosBlock = new HeliosBlock(unlocalizedName, material, Nullifactor.MODID);
	}
	
	public HeliosBlock getBlock() { return heliosBlock; }
	public String getUnlocalizedName() { return blockName; }
	
	public static void PostReigsterBlocks() { 
		QUANTUMREACTORPYLON.heliosBlock.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		QUANTUMREACTORPYLONTOP.heliosBlock.setMultiSidedTexture("nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:quantum_reactor_pylon_top");
	}
}
