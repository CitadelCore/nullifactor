package xyz.towerdevs.nullifactor;

import java.util.ArrayList;
import java.util.Arrays;

import Reika.DragonAPI.Interfaces.Registry.OreType.OreRarity;
import Reika.RotaryCraft.API.ExtractAPI;
import Reika.RotaryCraft.API.ItemFetcher;
import Reika.RotaryCraft.API.RecipeInterface;
import Reika.RotaryCraft.API.WorktableAPI;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import xyz.towerdevs.helios.base.MultiReturnRecipe;
import xyz.towerdevs.helios.registries.ModRegistry;
import xyz.towerdevs.nullifactor.blocks.BlockHighDensitySteel;
import xyz.towerdevs.nullifactor.items.ItemNullifactor;
import xyz.towerdevs.nullifactor.items.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.items.ResourceOreRegistry;

public class NullifactorRecipes {
	public static void PostloadRecipes() {
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.BASEFUELCELL.getItem()), new Object[] { "r r", "p p", "r r", 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem()});
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTUMFUELCELL.getItem()), new Object[] { "rfr", "pfp", "rfr", 'p', ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem(), 'f', ResourceItemRegistry.DIAMONDEMERALDFUEL.getItem()});
		
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.SINGULBEDROCK.getItem()), new Object[] { "bbb", "beb", "bbb", 'b', ResourceItemRegistry.UDENSEBEDROCK.getItem(), 'e', Items.ender_eye });
		
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUMROD.getItem(), 6), new Object[] { "  q", " q ", "q  ", 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUMPANEL.getItem(), 3), new Object[] { "qqq", "   ", "   ", 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOTPANEL.getItem(), 3), new Object[] { "iii", "   ", "   ", 'i', ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem() });
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDFUEL.getItem(), 1), new Object[] { "pip", "iqi", "pip", 'p', ResourceItemRegistry.DIAMONDEMERALDINGOTPANEL.getItem(), 'i', ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem(), 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() });
		
		GameRegistry.addRecipe(new MultiReturnRecipe(new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem()), Arrays.asList(new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem())), new ItemStack(ResourceItemRegistry.BASEFUELCELL.getItem())));
		GameRegistry.addRecipe(new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYPANEL.getItem(), 3), new Object[] { "iii", "   ", "   ", 'i', ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem() });
		
		GameRegistry.addRecipe(new ItemStack(ItemNullifactor.instance, 1), new Object[] { "fst", " ec", "  r", 's', ResourceItemRegistry.SINGULBEDROCK.getItem(), 'f', ResourceItemRegistry.CDIAMONDTURBOFAN.getItem(), 't', ResourceItemRegistry.CDIAMONDCOMPTURBINE.getItem(), 'c', ResourceItemRegistry.CQUANTANIUMCONTMOD.getItem(), 'e', ResourceItemRegistry.BASEFUELCELL.getItem(), 'r', ResourceItemRegistry.QUANTANIUMROD.getItem() });
		
		OreDictionary.registerOre("nullifactor:quantanium_ore", ResourceOreRegistry.QUANTANIUMORE.getOre());
		OreDictionary.registerOre("nullifactor:potato_ore", ResourceOreRegistry.POTATOORE.getOre());
		
		// Handle components for RotaryCraft
		if (Loader.isModLoaded("DragonAPI") && Loader.isModLoaded(ModRegistry.Mods.ROTARYCRAFT.GetModName())) {
			ItemStack bedrockIngot = (ItemStack) (OreDictionary.getOres("RotaryCraft:ingotBedrock").toArray()[0]);
			ItemStack tungstenIngot = (ItemStack) (OreDictionary.getOres("RotaryCraft:ingotBedrock").toArray()[0]);
			ItemStack steelIngot = (ItemStack) (OreDictionary.getOres("ingotHSLA").toArray()[0]);
			ItemStack siliconDust = (ItemStack) (OreDictionary.getOres("itemSilicon").toArray()[0]);
			//ItemStack hslaBlock = new ItemStack(ItemFetcher.getItemByUnlocalizedName("block.steel"));
			
			// Dense Bedrock Ingot recipe
			GameRegistry.addRecipe(new ShapedOreRecipe(bedrockIngot, new Object[] { "bbb", "bob", "bbb", 'b', bedrockIngot, 'o', Blocks.obsidian }));
			
			// Compactor
			RecipeInterface.compactor.addAPIRecipe(bedrockIngot, new ItemStack(ResourceItemRegistry.DENSEBEDROCK.getItem()), 500000, 900);
			RecipeInterface.compactor.addAPIRecipe(new ItemStack(ResourceItemRegistry.DENSEBEDROCK.getItem()), new ItemStack(ResourceItemRegistry.UDENSEBEDROCK.getItem()), 1000000, 1200);
			RecipeInterface.compactor.addAPIRecipe(new ItemStack(ResourceItemRegistry.QUANTANIUM.getItem()), new ItemStack(ResourceItemRegistry.QUANTANIUMGEM.getItem()), 100000, 500);
			
			// Blast Furnace
			RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 100.0F, 1, new ItemStack(Items.diamond), 100.0F, 1, new ItemStack(Items.gunpowder), 20.0F, 1, steelIngot, new ItemStack(ResourceItemRegistry.DIAMONDINGOT.getItem()), -1, false, 100.0F, 1200);
			RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 100.0F, 1, new ItemStack(Items.emerald), 100.0F, 1, new ItemStack(Items.gunpowder), 50.0F, 1, steelIngot, new ItemStack(ResourceItemRegistry.EMERALDINGOT.getItem()), -1, false, 100.0F, 1350);
			RecipeInterface.blastfurn.addAPIAlloying(tungstenIngot, 20.0F, 1, new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem()), 20.0F, 1, new ItemStack(Items.gunpowder), 20.0F, 1, new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()), new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem()), -1, false, 100.0F, 1150);
			RecipeInterface.blastfurn.addAPIRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem()), 1450, new ShapedOreRecipe(new ItemStack(ResourceItemRegistry.DIAMONDEMERALDINGOT.getItem()), new Object[] { "eee", "eqd", "ddd", 'd', ResourceItemRegistry.DIAMONDINGOT.getItem(), 'e', ResourceItemRegistry.EMERALDINGOT.getItem(), 'q', ResourceItemRegistry.QUANTANIUMINGOT.getItem() }), 5, 100.0F);
			
			// Worktable
			WorktableAPI.addshapelessRecipe(new ItemStack(ResourceItemRegistry.DEPLETEDDIAMONDEMERALDFUEL.getItem(), 3), new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem()));
			
			// Extractor
			ExtractAPI.addCustomExtractEntry("Quantanium", OreRarity.RARE, "Gem", "nullifactor:quantanium_gem", 2, 0xdbdbdb, 0xededed, null, "nullifactor:quantanium_ore");
			ExtractAPI.addCustomExtractEntry("Potatonium", OreRarity.COMMON, "Item", "minecraft:potato", 4, 0xcfd1b8, 0xdee0c9, null, "nullifactor:potato_ore");
			
			GameRegistry.addRecipe(new ItemStack(BlockHighDensitySteel.instance, 1), new Object[] { "aaa", "aia", "aaa", 'a', ResourceItemRegistry.HIGHDENSITYALLOYPANEL.getItem(), 'i', Blocks.iron_block });
		}
	}
}
