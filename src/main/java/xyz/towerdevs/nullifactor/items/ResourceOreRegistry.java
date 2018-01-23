package xyz.towerdevs.nullifactor.items;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.base.HeliosOre;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.misc.NullifactorAchievementRegistry;
import net.minecraft.init.Items;

public enum ResourceOreRegistry {
	QUANTANIUMORE("quantanium_ore", ResourceItemRegistry.QUANTANIUM.getItem(), 3.0F, 10.0F, 3, 2, 4),
	POTATOORE("potato_ore", Items.potato, 3.0F, 10.0F, 1, 4, 8, NullifactorAchievementRegistry.POTATOMINE);
	
	private final HeliosBlock heliosBlock;
	private final String oreName;
	public static final ResourceOreRegistry[] itemRegistry = values();
	
	/** Registers an ore - the texture name should be the same as the block name */
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel, int min, int max, NullifactorAchievementRegistry achOverride) {
		this.oreName = unlocalizedName;
		heliosBlock = new HeliosOre(unlocalizedName, Material.rock, Nullifactor.MODID, droppedItem, min, max);
		heliosBlock.setHardness(hardness);
		heliosBlock.setResistance(resistance);
		heliosBlock.setHarvestLevel("pickaxe", harvestLevel);
		
		if (achOverride != null)
			heliosBlock.setBrokenAchievement(achOverride);
	}
	
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel, int min, int max) {
		this(unlocalizedName, droppedItem, hardness, resistance, harvestLevel, min, max, null);
	}
	
	private ResourceOreRegistry(String unlocalizedName, Item droppedItem, float hardness, float resistance, int harvestLevel) {
		this(unlocalizedName, droppedItem, hardness, resistance, harvestLevel, 1, 1);
	}
	
	public HeliosBlock getOre() { return heliosBlock; }
	public String getUnlocalizedName() { return oreName; }
}
