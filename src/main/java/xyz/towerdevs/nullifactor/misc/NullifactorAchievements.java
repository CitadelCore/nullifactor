package xyz.towerdevs.nullifactor.misc;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class NullifactorAchievements extends AchievementPage {
	
	public static final NullifactorAchievements instance = new NullifactorAchievements("Nullifactor", NullifactorAchievementRegistry.getAchievementsAsArray());
	public NullifactorAchievements(String name, Achievement[] achievements) {
		super(name, achievements);
	}

}