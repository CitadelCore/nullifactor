package xyz.towerdevs.nullifactor.items;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.helios.*;
import xyz.towerdevs.helios.base.BlockReference;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.helios.base.InventoryItem;
import xyz.towerdevs.helios.registries.SoundRegistry;

public class ItemNullifactor extends HeliosItem {
	private static String unlocalizedName = "the_nullifactor";
	public static final ItemNullifactor instance = new ItemNullifactor();
	private int tickCounter = 20;
	private boolean isActive = false;
	private Random random = new Random();
	
	public ItemNullifactor() {
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName("nullifactor:" + unlocalizedName);
		String modId = "nullifactor";
		
		soundRegistry.RegisterResource(modId, "nullifactor.spinup");
		soundRegistry.RegisterResource(modId, "nullifactor.spindown");
		soundRegistry.RegisterResource(modId, "nullifactor.ingest");
		soundRegistry.RegisterResource("minecraft", "random.click");
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 50000;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {
		this.tickCounter = 0;
		soundRegistry.stopSound("nullifactor.spinup");
		soundRegistry.playSound("nullifactor.spindown");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player != null && PlayerUtilities.playerHasItem(player, ResourceItemRegistry.QUANTUMFUELCELL.getItem())) {
			if (!player.capabilities.isCreativeMode) {
				InventoryItem fuelcell = PlayerUtilities.getPlayerItem(player, ResourceItemRegistry.QUANTUMFUELCELL.getItem());
				if (fuelcell == null) {
					this.playClickSound();
					return stack;
				}
					
				if (fuelcell.getItem().getItemDamage() >= fuelcell.getItem().getMaxDamage()) {
					player.inventory.setInventorySlotContents(fuelcell.getSlot(), new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem()));
					this.playClickSound();
					return stack;
				}
			}
			
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
			
			soundRegistry.stopSound("nullifactor.spindown");
			soundRegistry.playSound("nullifactor.spinup");
		} else {
			this.playClickSound();
			return stack;
		}
		
		return stack;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		tickCounter++;
		
		if (!player.capabilities.isCreativeMode) { 
			PlayerUtilities.damageItem(player, ResourceItemRegistry.QUANTUMFUELCELL.getItem(), 1);
			
			InventoryItem fuelcell = PlayerUtilities.getPlayerItem(player, ResourceItemRegistry.QUANTUMFUELCELL.getItem());
			if (fuelcell != null) {
				if (fuelcell.getItem().getItemDamage() >= fuelcell.getItem().getMaxDamage()) {
					player.inventory.setInventorySlotContents(fuelcell.getSlot(), new ItemStack(ResourceItemRegistry.DEPLETEDFUELCELL.getItem()));
					
					InventoryItem fuelcell2 = PlayerUtilities.getPlayerItem(player, ResourceItemRegistry.QUANTUMFUELCELL.getItem());
					if (fuelcell2 == null) {
						onPlayerStoppedUsing(stack, player.getEntityWorld(), player, count);
						
						this.playClickSound();
					}
				}
			}
		}
		
		if (tickCounter >= 40) {
			World world = player.getEntityWorld();
			int radius = tickCounter;
			int playerId = player.getEntityId();
			double suctionPower = tickCounter / 20;
			if (tickCounter >= 200)
				radius = 200;
            List<EntityLiving> mobs = world.getEntitiesWithinAABB(EntityLiving.class, player.boundingBox.expand(radius, radius, radius));
            for (EntityLiving mob : mobs) {
            	moveEntityTowardsPlayer(mob, player, suctionPower);
            	mob.spawnExplosionParticle();
            	
            	double explosChance = random.nextInt(50) + suctionPower;
            	if (explosChance >= 40) { 
            		
            	}
                
                if (mob.getDistanceToEntity(player) < 1) {
                	mob.attackEntityFrom(DamageSource.causePlayerDamage(player), 100.0F);
                	//soundRegistry.playSound("nullifactor.ingest", true);
                }
            }
            
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(radius, radius, radius));
            for (EntityItem item : items) {
            	moveEntityTowardsPlayer(item, player, suctionPower);
            }
            
            List<EntityXPOrb> xps = world.getEntitiesWithinAABB(EntityXPOrb.class, player.boundingBox.expand(radius, radius, radius));
            for (EntityXPOrb xp : xps) {
            	moveEntityTowardsPlayer(xp, player, suctionPower);
            }
            
            List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, player.boundingBox.expand(radius, radius, radius));
            for (EntityPlayer iplayer : players) {
            	if (iplayer.getEntityId() != player.getEntityId()) {
            		moveEntityTowardsPlayer(iplayer, player, suctionPower);
            		iplayer.attackEntityFrom(DamageSource.causePlayerDamage(player), 100.0F);
            	}
            }
            
            //BlockReference trace = MathUtilities.rayTracePlayerBlocks(player, (int) suctionPower);
            //if (trace != null) {
            	//double suckChance = random.nextInt(50) + suctionPower;
            	//if (suckChance >= 30) {
                    //int metadata = world.getBlockMetadata(trace.x, trace.y, trace.z);
                    //world.destroyBlockInWorldPartially(Block.getIdFromBlock(trace.refBlock), trace.x, trace.y, trace.z, 9);
                    //world.func_147480_a(trace.x, trace.y, trace.z, true);
                    
                    //trace.refBlock.onBlockHarvested(world, trace.x, trace.y, trace.z, metadata, player);
                    //trace.refBlock.removedByPlayer(world, player, trace.x, trace.y, trace.z, true);
            		//trace.refBlock.onBlockDestroyedByPlayer(world, trace.x, trace.y, trace.z, metadata);
            		//}
            //}
		}
	}
	
	private void moveEntityTowardsPlayer(Entity ent, EntityPlayer player, double suctionPower) {
		double distX = player.posX - ent.posX;
        double distZ = player.posZ - ent.posZ;
        double distY = ent.posY+1.5D - player.posY;
        double dir = Math.atan2(distZ, distX);
        double speed = (1F / ent.getDistanceToEntity(player) * 0.5) * suctionPower;

        if (distY<0)
        {
        	ent.motionY += speed;
        }

        ent.motionX = Math.cos(dir) * speed;
        ent.motionZ = Math.sin(dir) * speed;
	}
	
	private void playClickSound() {
		soundRegistry.stopSound("random.click");
		soundRegistry.playSound("random.click");
	}
}