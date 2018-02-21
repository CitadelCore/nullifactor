package xyz.towerdevs.nullifactor.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import fox.spiteful.avaritia.render.FancyHaloRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fluids.FluidRegistry;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.NullifactorGUI;
import xyz.towerdevs.nullifactor.NullifactorRecipes;
import xyz.towerdevs.nullifactor.NullifactorWorldGenerator;
import xyz.towerdevs.nullifactor.common.blocks.reactor.*;
import xyz.towerdevs.nullifactor.common.commands.CommandMorko;
import xyz.towerdevs.nullifactor.common.entities.EntityMorko;
import xyz.towerdevs.nullifactor.common.items.*;
import xyz.towerdevs.nullifactor.common.misc.NullifactorAchievements;
import xyz.towerdevs.nullifactor.common.registries.ResourceBlockRegistry;
import xyz.towerdevs.nullifactor.common.registries.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.common.registries.ResourceOreRegistry;
import xyz.towerdevs.nullifactor.common.tileentities.*;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		// Biomes.
		BiomeDictionary.registerBiomeType(Nullifactor.frozenSwamp, Type.COLD);
		BiomeManager.addSpawnBiome(Nullifactor.frozenSwamp);
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(Nullifactor.frozenSwamp, 100));
		
		//WorldChunkManager.allowedBiomes.clear();
		//WorldChunkManager.allowedBiomes = new ArrayList<BiomeGenBase>(Arrays.asList(BiomeGenBase.extremeHills, Nullifactor.frozenSwamp));
		
		// Ores.
		for (ResourceOreRegistry registry : ResourceOreRegistry.values()) {
			HeliosBlock ore = registry.getOre();
			ore.registerBlock();
			ore.setCreativeTab(Nullifactor.resourceCreativeTab);
		}
		
		// Resource blocks.
		for (ResourceBlockRegistry registry : ResourceBlockRegistry.values()) {
			HeliosBlock block = registry.getBlock();
			block.registerBlock();
			block.setCreativeTab(Nullifactor.resourceCreativeTab);
		}
		
		// Fluids.
		for (xyz.towerdevs.nullifactor.common.registries.ResourceFluidRegistry registry : xyz.towerdevs.nullifactor.common.registries.ResourceFluidRegistry.values()) {
			FluidRegistry.registerFluid(registry.getFluid());
		}
		
		// Blocks.
		BlockQuantumReactor.instance.registerBlock();
		BlockQuantumReactorSingularity.instance.registerBlock();
		BlockQuantumReactorPylon.instance.registerBlock();
		BlockQuantumReactorPylonCap.instance.registerBlock();
		BlockQuantumReactorContainmentElectromagnet.instance.registerBlock();
		BlockQuantumReactorController.instance.registerBlock();
		BlockQuantumReactorFailsafe.instance.registerBlock();
		BlockQuantumReactorFluidPort.instance.registerBlock();
		BlockQuantumReactorHeatExchanger.instance.registerBlock();
		BlockQuantumReactorPowerTap.instance.registerBlock();
		
		// Tile entities.
		GameRegistry.registerTileEntity(TileEntityQuantumBase.class, "tile_entity_quantum_base");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactor.class, "tile_entity_quantum_reactor");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorSingularity.class, "tile_entity_quantum_reactor_singularity");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorContainmentElectromagnet.class, "tile_entity_quantum_reactor_containment_electromagnet");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorController.class, "tile_entity_quantum_reactor_controller");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorFailsafe.class, "tile_entity_quantum_reactor_failsafe");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorFluidPort.class, "tile_entity_quantum_reactor_fluid_port");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorHeatExchanger.class, "tile_entity_quantum_reactor_heat_exchanger");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorPowerTap.class, "tile_entity_quantum_reactor_power_tap");
    	GameRegistry.registerTileEntity(TileEntityQuantumReactorPylon.class, "tile_entity_quantum_reactor_pylon");
	}
	
	public void init(FMLInitializationEvent event) {
		// Items.
		ItemNullifactor.instance.setCreativeTab(Nullifactor.creativeTab);
		GameRegistry.registerItem(ItemNullifactor.instance, "the_nullifactor");
		
		/** Register resource items such as ingots and ores */
		for (ResourceItemRegistry registry : ResourceItemRegistry.values()) {
			HeliosItem item = registry.getItem();
			GameRegistry.registerItem(item, registry.getUnlocalizedName());
			item.setCreativeTab(Nullifactor.resourceCreativeTab);
		}
		
		// World generators.
		GameRegistry.registerWorldGenerator(new NullifactorWorldGenerator(), 100);
		
		// Entities.
		EntityRegistry.registerModEntity(EntityMorko.class, "morko", 57, "nullifactor", 1024, 1, false);
		
		// Achievement pages.
		AchievementPage.registerAchievementPage(NullifactorAchievements.instance);
		
		// Gui handlers.
		NetworkRegistry.INSTANCE.registerGuiHandler(Nullifactor.instance, new NullifactorGUI());
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		// Registry post initialization.
		ResourceItemRegistry.PostReigsterItems();
    	NullifactorRecipes.PostloadRecipes();
    	ResourceBlockRegistry.PostReigsterBlocks();
    	
    	// Client only operations.
    	if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
    		// Renderers.
    		FancyHaloRenderer haloRenderer = new FancyHaloRenderer();
        	
        	/** Register resource item custom renderers */
    		for (ResourceItemRegistry registry : ResourceItemRegistry.values()) {
    			HeliosItem item = registry.getItem();
    			
    			if (item.hasAvaritiaHalo)
    				MinecraftForgeClient.registerItemRenderer(item, haloRenderer);
    		}
    	}
	}
	
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMorko());
	}
}
