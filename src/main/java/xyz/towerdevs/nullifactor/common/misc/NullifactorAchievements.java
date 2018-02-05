package xyz.towerdevs.nullifactor.common.misc;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import xyz.towerdevs.nullifactor.common.registries.NullifactorAchievementRegistry;

public class NullifactorAchievements extends AchievementPage {
	
	public static final NullifactorAchievements instance = new NullifactorAchievements("Nullifactor", NullifactorAchievementRegistry.getAchievementsAsArray());
	public NullifactorAchievements(String name, Achievement[] achievements) {
		super(name, achievements);
	}

}