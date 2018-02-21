package xyz.towerdevs.nullifactor;

import java.util.Arrays;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import xyz.towerdevs.helios.base.MultiReturnRecipe;
import xyz.towerdevs.helios.helpers.CraftingHelper;
import xyz.towerdevs.helios.registries.ModRegistry;
import xyz.towerdevs.nullifactor.common.blocks.reactor.BlockQuantumReactor;
import xyz.towerdevs.nullifactor.common.blocks.reactor.BlockQuantumReactorPylon;
import xyz.towerdevs.nullifactor.common.items.ItemNullifactor;
import xyz.towerdevs.nullifactor.common.registries.ResourceBlockRegistry;
import xyz.towerdevs.nullifactor.common.registries.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.common.registries.ResourceOreRegistry;
// Avaritia
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import fox.spiteful.avaritia.crafting.CompressorManager;

public class NullifactorRecipes {
	public static void PostloadRecipes() {
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.BASEFUELCELL.getItem()), new Object[] { "r r", "p p", "r r", 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem()});
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTUMFUELCELL.getItem()), new Object[] { "rfr", "pfp", "rfr", 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem(), 'f', ResourceItemRegistry.DIAMONDEMERALDFUEL.getItem()});
		
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUMROD.getItem(), 6), new Object[] { "  q", " q ", "q  ", 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 3), new Object[] { "qqq", "   ", "   ", 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOTPANEL.getItem(), 3), new Object[] { "iii", "   ", "   ", 'i', ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem() });
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDFUEL.getItem(), 1), new Object[] { "pip", "iqi", "pip", 'p', ResourceItemRegistry.DIAMONDEMERALDINGOTPANEL.getItem(), 'i', ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem(), 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		
		GameRegistry.addRecipe(new MultiReturnRecipe(new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem()), Arrays.asList(new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem())), new ItemStack(ResourceItemRegistry.BASEFUELCELL.getItem())));
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYPANEL.getItem(), 3), new Object[] { "iii", "   ", "   ", 'i', ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem() });
		
		GameRegistry.addRecipe(new ItemStack(ItemNullifactor.instance, 1), new Object[] { "fst", " ec", "  r", 's', ResourceItemRegistry.ASBESTOSBRICK.getItem(), 'f', ResourceItemRegistry.QUANTANIUMINGOT.getItem(), 't', ResourceItemRegistry.QUANTANIUMINGOT.getItem(), 'c', ResourceItemRegistry.QUANTANIUMINGOT.getItem(), 'e', ResourceItemRegistry.BASEFUELCELL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem() });
		
		GameRegistry.addRecipe(new ItemStack(ResourceBlockRegistry.BASEMACHINEFRAME.getBlock()), new Object[] { "iqi", "q q", "iqi", 'i', Items.iron_ingot, 'q', ResourceItemRegistry.QUANTANIUMPANEL.getItem() });
		
		// Machine recipes
		GameRegistry.addRecipe(new ItemStack(BlockQuantumReactorPylon.instance), new Object[] { "isi", "pbp", "isi", 'i', Items.iron_ingot, 's', ResourceItemRegistry.SUPERCPLATE.getItem(), 'p', ResourceItemRegistry.SHIELDPLATE.getItem(), 'b', ResourceBlockRegistry.BASEMACHINEFRAME.getBlock() });
		GameRegistry.addRecipe(new ItemStack(BlockQuantumReactor.instance), new Object[] { "isi", "pbp", "isi", 'i', Items.iron_ingot, 's', ResourceItemRegistry.SUPERCPLATE.getItem(), 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'b', ResourceBlockRegistry.BASEMACHINEFRAME.getBlock() });
		//GameRegistry.addRecipe(new ItemStack(BlockQuantumReactorSingularity.instance), new Object[] { "o"});
		
		// Ore dictionary
		OreDictionary.registerOre("nullifactor:quantanium_ore", ResourceOreRegistry.QUANTANIUMORE.getOre());
		OreDictionary.registerOre("nullifactor:potato_ore", ResourceOreRegistry.POTATOORE.getOre());
		OreDictionary.registerOre("nullifactor:serpentinite_ore", ResourceOreRegistry.SERPENTINITEORE.getOre());
		
		//ThermalExpansionHelper.addSmelterRecipe(arg0, arg1, arg2, arg3);
		//ThermalExpansionHelper.
		
		if (ModRegistry.AVARITIA.IsLoaded()) {
			ItemStack diamondLatticeStack = new ItemStack(GameRegistry.findItem(ModRegistry.AVARITIA.GetModName(), "Resource"), 1, 0);
			ItemStack crystalMatrixStack = new ItemStack(GameRegistry.findItem(ModRegistry.AVARITIA.GetModName(), "Resource"), 1, 1);
			ItemStack neutroniumStack = new ItemStack(GameRegistry.findItem(ModRegistry.AVARITIA.GetModName(), "Resource"), 1, 4);
			ItemStack infinityCatalystStack = new ItemStack(GameRegistry.findItem(ModRegistry.AVARITIA.GetModName(), "Resource"), 1, 5);
			//ItemStack infinityIngotStack = new ItemStack(GameRegistry.findItem(ModRegistry.AVARITIA.GetModName(), "Resource"), 1, 6);
			
			CompressorManager.addRecipe(new ItemStack(ResourceItemRegistry.SINGULDIAMOND.getItem(), 1), 150, new ItemStack(Blocks.diamond_block, 1));
			CompressorManager.addRecipe(new ItemStack(ResourceItemRegistry.SINGULEMERALD.getItem(), 1), 150, new ItemStack(Blocks.emerald_block, 1));
			
			GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.SUPERCPLATE.getItem(), 3), new Object[] { "qlq", "lgl", "qlq", 'q', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'l', diamondLatticeStack, 'g', ResourceItemRegistry.QUANTANIUMGEM.getItem()});
			GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.SHIELDPLATE.getItem(), 3), new Object[] { "prp", "pmp", "prp", 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem(), 'm', crystalMatrixStack});
			
			if (ModRegistry.EXTRAUTILITIES.IsLoaded()) {
				Block bedrockiumBlock = GameRegistry.findBlock(ModRegistry.EXTRAUTILITIES.GetModName(), "block_bedrockium");
				if (bedrockiumBlock != null) {
					CompressorManager.addRecipe(new ItemStack(ResourceItemRegistry.SINGULBEDROCK.getItem(), 1), 30, new ItemStack(bedrockiumBlock));
					GameRegistry.addRecipe(new ItemStack(ResourceBlockRegistry.HIGHDENSITYSTEEL.getBlock(), 4), new Object[] { "aaa", "aia", "aaa", 'a', ResourceItemRegistry.HIGHDENSITYALLOYPANEL.getItem(), 'i', new ItemStack(bedrockiumBlock) });
				}
			}
			
			if (ModRegistry.COFHCORE.IsLoaded()) {
				//ItemStack steelStack = new ItemStack(GameRegistry.findItem(ModRegistry.THERMALFOUNDATION.GetModName(), "material"), 1, 160);
				
				ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Items.brick), new ItemStack(ResourceItemRegistry.ASBESTOSFIBRES.getItem(), 2), new ItemStack(ResourceItemRegistry.ASBESTOSBRICK.getItem()));
				ThermalExpansionHelper.addSmelterRecipe(6400, new ItemStack(Items.emerald), new ItemStack(ResourceItemRegistry.QUANTANIUM.getItem()), new ItemStack(ResourceItemRegistry.QUANTANIUMGEM.getItem()));
				ThermalExpansionHelper.addSmelterRecipe(8200, new ItemStack(Items.iron_ingot), new ItemStack(ResourceItemRegistry.QUANTANIUMGEM.getItem()), new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()));
				
				ThermalExpansionHelper.addSmelterRecipe(6400, new ItemStack(Items.iron_ingot), new ItemStack(Items.diamond, 2), new ItemStack(ResourceItemRegistry.DIAMONDINGOT.getItem()));
				ThermalExpansionHelper.addSmelterRecipe(6400, new ItemStack(Items.iron_ingot), new ItemStack(Items.emerald, 2), new ItemStack(ResourceItemRegistry.EMERALDINGOT.getItem()));
				ThermalExpansionHelper.addSmelterRecipe(8200, new ItemStack(ResourceItemRegistry.DIAMONDINGOT.getItem()), new ItemStack(ResourceItemRegistry.EMERALDINGOT.getItem()), new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem()));
				
				ThermalExpansionHelper.addSmelterRecipe(16000, new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem()), new ItemStack(Items.iron_ingot), new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem()));
				
				CraftingHelper.addBlockRecipe(new ItemStack(ResourceBlockRegistry.ASBESTOS.getBlock()), new ItemStack(ResourceItemRegistry.ASBESTOSBRICK.getItem()));
				CraftingHelper.addBlockRecipe(new ItemStack(ResourceBlockRegistry.QUANTANIUM.getBlock()), new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()));
			}
			
			if (ModRegistry.DRACONICEVOLUTION.IsLoaded()) {
				ItemStack draconiumStack = new ItemStack(GameRegistry.findItem(ModRegistry.DRACONICEVOLUTION.GetModName(), "draconiumIngot"));
				ItemStack awakenedDraconiumStack = new ItemStack(GameRegistry.findItem(ModRegistry.DRACONICEVOLUTION.GetModName(), "draconicIngot"));
				GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.CRYSTALMATRIXCORE.getItem()), new Object[] { "rdr", "dcd", "rdr", 'r', crystalMatrixStack, 'd', draconiumStack, 'c', GameRegistry.findItem(ModRegistry.DRACONICEVOLUTION.GetModName(), "wyvernCore")});
				GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.NEUTRONIUMCORE.getItem()), new Object[] { "nan", "aca", "dad", 'n', neutroniumStack, 'a', awakenedDraconiumStack, 'c', GameRegistry.findItem(ModRegistry.DRACONICEVOLUTION.GetModName(), "awakenedCore"), 'd', ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem()});
				GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.NULLIFACTIONCORE.getItem()), new Object[] { "qiq", "ncn", "qnq", 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem(), 'i', infinityCatalystStack, 'n', ResourceItemRegistry.NEUTRONIUMCORE.getItem(), 'c', GameRegistry.findItem(ModRegistry.DRACONICEVOLUTION.GetModName(), "chaoticCore")});
			}
		}
		
		// Handle components for RotaryCraft
		//if (Loader.isModLoaded("DragonAPI") && Loader.isModLoaded(ModRegistry.Mods.ROTARYCRAFT.GetModName())) {
			//ItemStack bedrockIngot = (ItemStack) (OreDictionary.getOres("RotaryCraft:ingotBedrock").toArray()[0]);
			//ItemStack tungstenIngot = (ItemStack) (OreDictionary.getOres("RotaryCraft:ingotBedrock").toArray()[0]);
			//ItemStack steelIngot = (ItemStack) (OreDictionary.getOres("ingotHSLA").toArray()[0]);
			//ItemStack siliconDust = (ItemStack) (OreDictionary.getOres("itemSilicon").toArray()[0]);
			//ItemStack hslaBlock = new ItemStack(ItemFetcher.getItemByUnlocalizedName("block.steel"));
			
			// Dense Bedrock Ingot recipe
			//GameRegistry.addRecipe(new ShapedOreRecipe(bedrockIngot, new Object[] { "bbb", "bob", "bbb", 'b', bedrockIngot, 'o', Blocks.obsidian }));
			
			// Compactor
			//RecipeInterface.compactor.addAPIRecipe(bedrockIngot, new ItemStack(ResourceItemRegistry.DENSEBEDROCK.getItem()), 500000, 900);
			//RecipeInterface.compactor.addAPIRecipe(new ItemStack(ResourceItemRegistry.DENSEBEDROCK.getItem()), new ItemStack(ResourceItemRegistry.UDENSEBEDROCK.getItem()), 1000000, 1200);
			//RecipeInterface.compactor.addAPIRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUM.getItem()), new ItemStack(ResourceItemRegistry.QUANTANIUMGEM.getItem()), 100000, 500);
			
			// Blast Furnace
			//RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 100.0F, 1, new ItemStack(Items.diamond), 100.0F, 1, new ItemStack(Items.gunpowder), 20.0F, 1, steelIngot, new ItemStack(ResourceItemRegistry.DIAMONDINGOT.getItem()), -1, false, 100.0F, 1200);
			//RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 100.0F, 1, new ItemStack(Items.emerald), 100.0F, 1, new ItemStack(Items.gunpowder), 50.0F, 1, steelIngot, new ItemStack(ResourceItemRegistry.EMERALDINGOT.getItem()), -1, false, 100.0F, 1350);
			//RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 20.0F, 1, new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem()), 20.0F, 1, new ItemStack(Items.gunpowder), 20.0F, 1, new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()), new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem()), -1, false, 100.0F, 1150);
			//RecipeInterface.blastfurn.addAPIRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem()), 1450, new ShapedOreRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem()), new Object[] { "eee", "eqd", "ddd", 'd', ResourceItemRegistry.DIAMONDINGOT.getItem(), 'e', ResourceItemRegistry.EMERALDINGOT.getItem(), 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() }), 5, 100.0F);
			
			// Worktable
			//WorktableAPI.addshapelessRecipe(new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem(), 3), new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem()));
			
			// Extractor
			//ExtractAPI.addCustomExtractEntry("Quantanium", OreRarity.RARE, "Gem", "nullifactor:quantanium_gem", 2, 0xdbdbdb, 0xededed, null, "nullifactor:quantanium_ore");
			//ExtractAPI.addCustomExtractEntry("Potatonium", OreRarity.COMMON, "Item", "minecraft:potato", 4, 0xcfd1b8, 0xdee0c9, null, "nullifactor:potato_ore");
			
		//}
	}
}
