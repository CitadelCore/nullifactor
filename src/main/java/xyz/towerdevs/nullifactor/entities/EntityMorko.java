package xyz.towerdevs.nullifactor.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.items.ResourceItemRegistry;

public class EntityMorko extends EntityCreature {
	public EntityMorko(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setHealth(20.0F);
		this.setAbsorptionAmount(10.0F);
	}
	
	@Override
	protected String getLivingSound() {
		return "morko.living";
	}
	
	@Override
	protected String getHurtSound() {
		return "morko.hurt";
	}
	
	@Override
	protected String getDeathSound() {
		return "morko.death";
	}
	
	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}
	
	@Override
	protected Item getDropItem() {
		return ResourceItemRegistry.MASKMORKO.getItem();
	}
}
