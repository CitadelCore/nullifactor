package xyz.towerdevs.nullifactor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import xyz.towerdevs.helios.HeliosMod;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.helios.registries.ModRegistry;
import xyz.towerdevs.nullifactor.blocks.BlockHighDensitySteel;
import xyz.towerdevs.nullifactor.entities.EntityMorko;
import xyz.towerdevs.nullifactor.items.ItemNullifactor;
import xyz.towerdevs.nullifactor.items.NullifactorEntityPlacer;
import xyz.towerdevs.nullifactor.items.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.items.ResourceOreRegistry;
import xyz.towerdevs.nullifactor.misc.BiomeFrozenSwamp;
import xyz.towerdevs.nullifactor.misc.NullifactorAchievements;
import xyz.towerdevs.nullifactor.renderers.RenderMorko;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.realmsclient.dto.McoServer.WorldType;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Nullifactor.MODID, version = Nullifactor.VERSION)
public final class Nullifactor extends HeliosMod
{
    public static final String MODID = "nullifactor";
    public static final String VERSION = "1.0.0.0";
    public static final BiomeFrozenSwamp frozenSwamp = new BiomeFrozenSwamp(157);
    
    public static CreativeTabs creativeTab = new CreativeTabs("nullifactor") {
    	@Override public Item getTabIconItem() {
    		return Items.skull;
    	}
    };
    
    public static CreativeTabs resourceCreativeTab = new CreativeTabs("nullifactor_resources") {
    	@Override public Item getTabIconItem() {
    		return ResourceItemRegistry.QUANTANIUMGEM.getItem();
    	}
    };
    
    public Nullifactor() {
    	super(Nullifactor.MODID, Nullifactor.VERSION);
    }
    
    @EventHandler
    public void preInit(FMLInitializationEvent event) {
    	BiomeDictionary.registerBiomeType(frozenSwamp, Type.COLD);
		BiomeManager.addSpawnBiome(frozenSwamp);
		BiomeManager.addBiome(BiomeType.ICY, new BiomeEntry(frozenSwamp, 100));
		
		WorldChunkManager.allowedBiomes.clear();
		WorldChunkManager.allowedBiomes = new ArrayList<BiomeGenBase>(Arrays.asList(BiomeGenBase.extremeHills, frozenSwamp));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	ItemNullifactor.instance.setCreativeTab(creativeTab);
		GameRegistry.registerItem(ItemNullifactor.instance, "the_nullifactor");
		
		/** Register resource items such as ingots and ores */
		for (ResourceItemRegistry registry : ResourceItemRegistry.values()) {
			HeliosItem item = registry.getItem();
			item.setCreativeTab(resourceCreativeTab);
			GameRegistry.registerItem(item, registry.getUnlocalizedName());
		}
		
		for (ResourceOreRegistry registry : ResourceOreRegistry.values()) {
			HeliosBlock ore = registry.getOre();
			ore.setCreativeTab(resourceCreativeTab);
			GameRegistry.registerBlock(ore, registry.getUnlocalizedName());
		}
		
		GameRegistry.registerWorldGenerator(new NullifactorWorldGenerator(), 100);
		RenderingRegistry.registerEntityRenderingHandler(EntityMorko.class, new RenderMorko(new ModelBiped(), 0.4F));
		EntityRegistry.registerModEntity(EntityMorko.class, "morko", 57, "nullifactor", 128, 1, false);
		
		GameRegistry.registerItem(new NullifactorEntityPlacer("morko", 0xE18519, 0x000000).setUnlocalizedName("spawnMorko"), "spawnMorko");
		
		BlockHighDensitySteel.instance.setCreativeTab(resourceCreativeTab);
		GameRegistry.registerBlock(BlockHighDensitySteel.instance, "high_density_steel");
		
		AchievementPage.registerAchievementPage(NullifactorAchievements.instance);
    }
    
    @EventHandler
    public void preload(FMLPreInitializationEvent event) {
    	
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
    	
    }
    
    @EventHandler
    public void postload(FMLPostInitializationEvent event) {
    	NullifactorRecipes.PostloadRecipes();
    }
}
