package xyz.towerdevs.nullifactor.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import xyz.towerdevs.helios.ItemUtilities;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.nullifactor.Nullifactor;

public enum ResourceItemRegistry {
	// Bedrock
	DENSEBEDROCK("bedrock_dense"),
	UDENSEBEDROCK("bedrock_ultradense"),
	DENSEBEDROCKGEM("bedrock_dense_gem"),
	
	// Singularities
	SINGULBEDROCK("bedrock_singularity"),
	SINGULDIAMOND("diamond_entangled_singularity"),
	SINGULEMERALD("emerald_entangled_singularity"),
	SINGULCORRUPTED("corrupted_singularity"),
	
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
	
	// Components
	CQUANTANIUMCIRCUIT("quantanium_circuit"),
	CQUANTANIUMCONTMOD("quantanium_control_module"),
	CQUANTANIUMPWRMOD("quantanium_power_module"),
	CDIAMONDBLADE("diamond_blade"),
	CDIAMONDCHOPPER("diamond_chopper"),
	CDIAMONDCOMPTURBINE("diamond_compound_turbine"),
	CDIAMONDSAW("diamond_saw"),
	CDIAMONDTURBINE("diamond_turbine"),
	CDIAMONDTURBOFAN("diamond_turbofan"),
	
	// Other
	TESTTUBESMV("testtube_smv"),
	TESTTUBEBLOOD("testtube_blood"),
	POLONIUMJAR("polonium_jar"),
	MASKMORKO("mask_morko"),
	MASKWHITE("mask_white"),
	
	;
	
	private final HeliosItem heliosItem;
	private final String itemName;
	public static final ResourceItemRegistry[] itemRegistry = values();
	
	/** Registers an item - the texture name should be the same as the item name */
	private ResourceItemRegistry(String unlocalizedName, int maxStackSize, int maxDamage) {
		this.itemName = unlocalizedName;
		heliosItem = ItemUtilities.newResourceItem(unlocalizedName, Nullifactor.MODID + ":" + unlocalizedName);
		heliosItem.setMaxStackSize(maxStackSize);
		
		if (maxDamage != -1)
			heliosItem.setMaxDamage(maxDamage);
	}
	
	private ResourceItemRegistry(String unlocalizedName) {
		this(unlocalizedName, 64, -1);
	}
	
	public HeliosItem getItem() { return heliosItem; }
	public String getUnlocalizedName() { return itemName; }
}
