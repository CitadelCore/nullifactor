package xyz.towerdevs.helios.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraft.world.World;
import xyz.towerdevs.helios.registries.SoundRegistry;
import xyz.towerdevs.nullifactor.misc.NullifactorAchievementRegistry;

public class HeliosBlock extends Block {
protected SoundRegistry soundRegistry;
	
    private NullifactorAchievementRegistry brokenAchievement = null;
	public HeliosBlock(String unlocalizedName, Material blockMaterial, String modId) {
		super(blockMaterial);
		this.soundRegistry = new SoundRegistry();
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(modId + ":" + unlocalizedName);
	}
	
	public void setBrokenAchievement(NullifactorAchievementRegistry achOverride) {
		this.brokenAchievement = achOverride;
	}
	
	@Override
	public void onBlockHarvested(World world, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer player) {
		if (this.brokenAchievement != null)
			brokenAchievement.trigger(player);
	}
}
