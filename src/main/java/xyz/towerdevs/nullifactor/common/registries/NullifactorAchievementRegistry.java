package xyz.towerdevs.nullifactor.common.registries;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import xyz.towerdevs.nullifactor.common.items.ItemNullifactor;

public enum NullifactorAchievementRegistry {
	POTATOMINE("potatomine", 2, 0, new ItemStack(Items.potato), false),
	ULTIMATEPOTATO("ultimatepotato", 4, 0, new ItemStack(Blocks.furnace), false, NullifactorAchievementRegistry.POTATOMINE.achievement),
	QUANTUMPOTATO("quantumpotato", 6, 0, new ItemStack(ResourceItemRegistry.SINGULEMERALD.getItem()), true, NullifactorAchievementRegistry.ULTIMATEPOTATO.achievement),
	
	CSINGULARITY("csingularity", 0, 2, new ItemStack(ResourceItemRegistry.SINGULBEDROCK.getItem()), false),
	CFUEL("cfuel", 2, 2, new ItemStack(ResourceItemRegistry.DIAMONDEMERALDFUEL.getItem()), false, NullifactorAchievementRegistry.CSINGULARITY.achievement),
	CNULLIFACTOR("cnullifactor", 4, 2, new ItemStack(ItemNullifactor.instance), false, NullifactorAchievementRegistry.CFUEL.achievement),
	SUCKEDIN("suckedin", 6, 2, new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()), true, NullifactorAchievementRegistry.CNULLIFACTOR.achievement),
	
	CORRUPTED("corrupted", -2, 2, new ItemStack(ResourceItemRegistry.SINGULCORRUPTED.getItem()), false, NullifactorAchievementRegistry.CSINGULARITY.achievement),
	BORDERWORLD("borderworld", -4, 2, new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()), false, NullifactorAchievementRegistry.CORRUPTED.achievement),
	NULLDRIVER("nulldriver", -6, 2, new ItemStack(ResourceItemRegistry.QUANTANIUMINGOT.getItem()), true, NullifactorAchievementRegistry.BORDERWORLD.achievement),
	SHUTDOWN("shutdown", -8, 2, new ItemStack(ResourceItemRegistry.TESTTUBEBLOOD.getItem()), true, NullifactorAchievementRegistry.NULLDRIVER.achievement),
	
	RECYCLING("recycling", 2, 4, new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem()), false, NullifactorAchievementRegistry.CFUEL.achievement),
	HIGHDENSITYALLOY("highdensityalloy", 2, 6, new ItemStack(ResourceItemRegistry.HIGHDENSITYALLOYINGOT.getItem()), false, NullifactorAchievementRegistry.RECYCLING.achievement),
	
	MORKO("morko", -4, 4, new ItemStack(ResourceItemRegistry.MASKMORKO.getItem()), false, NullifactorAchievementRegistry.BORDERWORLD.achievement),
	RAVEN("raven", -4, 6, new ItemStack(ResourceItemRegistry.MASKWHITE.getItem()), true, NullifactorAchievementRegistry.MORKO.achievement),
	;
	
	private Achievement achievement;
	public static final NullifactorAchievementRegistry[] achRegistry = values();
	private NullifactorAchievementRegistry(String achName, int col, int row, ItemStack stack, boolean isSpecial, Achievement parent) {
		this.achievement = new Achievement(achName, achName, col, row, stack, parent);
		if (isSpecial == true)
			this.achievement.setSpecial();
	}
	
	private NullifactorAchievementRegistry(String achName, int col, int row, ItemStack stack, boolean isSpecial) {
		this(achName, col, row, stack, isSpecial, null);
	}
	
	public static Achievement[] getAchievementsAsArray() {
		List<Achievement> achievements = new ArrayList<Achievement>();
		
		for (NullifactorAchievementRegistry registry : NullifactorAchievementRegistry.values()) {
			achievements.add(registry.achievement);
		}
		
		return achievements.toArray(new Achievement[0]);
	}
	
	public void trigger(EntityPlayer player) {
		if (this.achievement == null || player == null)
			return;
		
		player.triggerAchievement(this.achievement);
	}
}
