package xyz.towerdevs.nullifactor.common.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.common.registries.ResourceItemRegistry;

public class EntityMorko extends EntityMob {
	public EntityMorko(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		//this.setAbsorptionAmount(10.0F);
		
		this.setHealth(40.0F);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(2048);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.8);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(85.0);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0);
		//this.applyEntityAttributes();
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
