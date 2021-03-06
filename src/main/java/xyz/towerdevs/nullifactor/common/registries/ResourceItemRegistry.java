package xyz.towerdevs.nullifactor.common.registries;

import net.minecraft.item.EnumRarity;
import xyz.towerdevs.helios.ItemUtilities;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.helios.base.HeliosItem.AvaritiaHaloType;
import xyz.towerdevs.helios.interfaces.IResourceRegistry;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.items.ItemSingularity;
import fox.spiteful.avaritia.items.LudicrousItems;

public enum ResourceItemRegistry implements IResourceRegistry {
	// Singularities
	SINGULBEDROCK("bedrock_singularity", ItemSingularity.class),
	SINGULDIAMOND("diamond_entangled_singularity", ItemSingularity.class),
	SINGULEMERALD("emerald_entangled_singularity", ItemSingularity.class),
	SINGULCORRUPTED("corrupted_singularity", ItemSingularity.class),
	
	// Emerald
	EMERALDINGOT("emerald_ingot"),
	
	// Diamond
	DIAMONDINGOT("diamond_ingot"),
	
	// Quantanium
	QUANTANIUM("quantanium_raw"),
	QUANTANIUMGEM("quantanium_gem"),
	QUANTANIUMINGOT("quantanium_ingot"),
	QUANTANIUMROD("quantanium_rod"),
	QUANTANIUMPANEL("quantanium_panel"),
	
	// Asbestos
	ASBESTOSFIBRES("asbestos_fibres"),
	ASBESTOSBRICK("asbestos_brick"),
	
	// Fuel Cells
	QUANTUMFUELCELL("quantum_fuelcell", 1, 5000),
	DEPLETEDFUELCELL("depleted_fuelcell"),
	BASEFUELCELL("base_fuelcell"),
	
	// Alloys
	DIAMONDEMERALDINGOT("alloy_diamond_emerald"),
	DIAMONDEMERALDINGOTPANEL("alloy_diamond_emerald_panel"),
	DIAMONDEMERALDFUEL("alloy_diamond_emerald_fuel"),
	DEPLETEDDIAMONDEMERALDFUEL("depleted_diamond_emerald_fuel"),
	
	HIGHDENSITYALLOYINGOT("high_density_alloy"),
	HIGHDENSITYALLOYPANEL("high_density_alloy_panel"),
	
	// Crafting misc
	SUPERCPLATE("superconducting_plate"),
	SHIELDPLATE("shielding_plate"),
	
	// Draconic Evolution extensions
	CRYSTALMATRIXCORE("crystal_matrix_core"),
	NEUTRONIUMCORE("neutronium_core"),
	NULLIFACTIONCORE("nullifaction_core"),
	
	// Other
	TESTTUBESMV("testtube_smv"),
	TESTTUBEBLOOD("testtube_blood"),
	POLONIUMJAR("polonium_jar"),
	MASKMORKO("mask_morko"),
	MASKWHITE("mask_white"),
	
	// Tools
	THERMOMETER("tool_thermometer", 1, -1),
	
	;
	
	private final HeliosItem heliosItem;
	private final String itemName;
	public static final ResourceItemRegistry[] itemRegistry = values();
	
	/** Registers an item - the texture name should be the same as the item name */
	private ResourceItemRegistry(String unlocalizedName, int maxStackSize, int maxDamage, Class<? extends HeliosItem> itemClass) {
		this.itemName = unlocalizedName;
		this.heliosItem = ItemUtilities.newResourceItem(unlocalizedName, Nullifactor.MODID + ":" + unlocalizedName, itemClass);
		this.heliosItem.setMaxStackSize(maxStackSize);
		this.heliosItem.registerInformationText();
		
		if (maxDamage != -1)
			this.heliosItem.setMaxDamage(maxDamage);
	}
	
	private ResourceItemRegistry(String unlocalizedName, int maxStackSize, int maxDamage) {
		this(unlocalizedName, maxStackSize, maxDamage, HeliosItem.class);
	}
	
	private ResourceItemRegistry(String unlocalizedName, Class<? extends HeliosItem> itemClass) {
		this(unlocalizedName, 64, -1, itemClass);
	}
	
	private ResourceItemRegistry(String unlocalizedName) {
		this(unlocalizedName, HeliosItem.class);
	}
	
	public static void PostReigsterItems() {
		SINGULBEDROCK.heliosItem.setRarity(EnumRarity.epic);
		SINGULDIAMOND.heliosItem.setRarity(EnumRarity.uncommon);
		SINGULEMERALD.heliosItem.setRarity(EnumRarity.uncommon);
		SINGULCORRUPTED.heliosItem.setRarity(LudicrousItems.cosmic);
		
		CRYSTALMATRIXCORE.heliosItem.setRarity(EnumRarity.rare);
		
		NEUTRONIUMCORE.heliosItem.setRarity(EnumRarity.epic);
		NEUTRONIUMCORE.heliosItem.registerEnchantmentEffect();
		NEUTRONIUMCORE.heliosItem.registerAvaritiaSpecialHalo(4, false, 0xFF000000, AvaritiaHaloType.NOISE);
		
		NULLIFACTIONCORE.heliosItem.setRarity(LudicrousItems.cosmic);
		NULLIFACTIONCORE.heliosItem.registerEnchantmentEffect();
		NULLIFACTIONCORE.heliosItem.registerAvaritiaSpecialHalo(8, true, 0x8E2222, AvaritiaHaloType.REGULAR);
		
		SINGULBEDROCK.registerAsAvaritiaSingularity(0x1C1C1C, 0xFFFFFF);
		SINGULDIAMOND.registerAsAvaritiaSingularity(0x1C1C1C, 0x3FBDBD);
		SINGULEMERALD.registerAsAvaritiaSingularity(0x1C1C1C, 0x55C144);
		SINGULCORRUPTED.registerAsAvaritiaSingularity(0x1C1C1C, 0xCE480A);
		
		((ItemSingularity) SINGULBEDROCK.heliosItem).setSingularity(ItemSingularity.Singularities.BEDROCK);
		((ItemSingularity) SINGULDIAMOND.heliosItem).setSingularity(ItemSingularity.Singularities.BEDROCK);
		((ItemSingularity) SINGULEMERALD.heliosItem).setSingularity(ItemSingularity.Singularities.BEDROCK);
		((ItemSingularity) SINGULCORRUPTED.heliosItem).setSingularity(ItemSingularity.Singularities.CORRUPTED);
	}
	
	public void registerAsAvaritiaSingularity(int primaryColour, int secondaryColour) {
		this.heliosItem.registerItemAnimation("avaritia:singularity", "avaritia:singularity2");
		this.heliosItem.registerAvaritiaSpecialHalo(4, false, 0xFF000000, AvaritiaHaloType.REGULAR);
		this.heliosItem.registerItemMulticolour(primaryColour, secondaryColour);
	}
	
	public HeliosItem getItem() { return this.heliosItem; }
	public String getUnlocalizedName() { return this.itemName; }
}
