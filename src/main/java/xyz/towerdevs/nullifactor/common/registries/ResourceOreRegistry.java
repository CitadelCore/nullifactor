package xyz.towerdevs.nullifactor.common.registries;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.base.HeliosOre;
import xyz.towerdevs.helios.interfaces.IResourceRegistry;
import xyz.towerdevs.nullifactor.Nullifactor;
import net.minecraft.init.Items;

public enum ResourceOreRegistry implements IResourceRegistry {
	QUANTANIUMORE("quantanium_ore", ResourceItemRegistry.QUANTANIUM.getItem(), 5.0F, 10.0F, 3, 1, 2),
	SERPENTINITEORE("serpentinite_ore", ResourceItemRegistry.ASBESTOSFIBRES.getItem(), 3.0F, 5.0F, 2, 8, 16),
	POTATOORE("potato_ore", Items.potato, 3.0F, 10.0F, 1, 4, 8, NullifactorAchievementRegistry.POTATOMINE);
	
	private final HeliosBlock heliosBlock;
	private final String oreName;
	public static final ResourceOreRegistry[] oreRegistry = values();
	
	/** Registers an ore - the texture name should be the same as the block name */
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel, int min, int max, NullifactorAchievementRegistry achOverride) {
		this.oreName = unlocalizedName;
		this.heliosBlock = new HeliosOre(unlocalizedName, Material.rock, Nullifactor.MODID, droppedItem, min, max);
		this.heliosBlock.setHardness(hardness);
		this.heliosBlock.setResistance(resistance);
		this.heliosBlock.setHarvestLevel("pickaxe", harvestLevel);
		
		if (achOverride != null)
			this.heliosBlock.setBrokenAchievement(achOverride);
	}
	
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel, int min, int max) {
		this(unlocalizedName, droppedItem, hardness, resistance, harvestLevel, min, max, null);
	}
	
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel) {
		this(unlocalizedName, droppedItem, hardness, resistance, harvestLevel, 1, 1);
	}
	
	public HeliosBlock getOre() { return this.heliosBlock; }
	public String getUnlocalizedName() { return this.oreName; }
}
