package xyz.towerdevs.helios;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.BlockReference;

public class MathUtilities {
	/* Returns the pythagorean floor of three doubles. */
	public static double py3d(double x, double y, double z) {
		double value;
		value = x * x + y * y + z * z;
		return Math.sqrt(value);
	}
	
	/** Retrieves a circular selection of blocks within a radius. */
	public static List<BlockReference> getBlocksWithinRadius(EntityPlayer player, int radius) {
		List<BlockReference> blocks = new ArrayList<BlockReference>();
		for (int x = player.chunkCoordX - radius; x <= player.chunkCoordX + radius; x++) {
            for (int y = player.chunkCoordY - radius; y <= player.chunkCoordY + radius; y++) {
                for (int z = player.chunkCoordZ - radius; z <= player.chunkCoordZ + radius; z++) {
                   blocks.add(new BlockReference(player.getEntityWorld().getBlock(x, y, z), x, y, z));
                }
            }
        }
		
        return blocks;
	}
	
	/** Traces a ray to the position that the specified player is looking at. */
	public static BlockReference rayTracePlayerBlocks(EntityPlayer player, int distance) {
		World world = player.getEntityWorld();
		Vec3 position = player.getPosition(1.0F);
        Vec3 eyepos = player.getLook(1.0F);
        Vec3 respos = position.addVector(eyepos.xCoord * distance, eyepos.yCoord * distance, eyepos.zCoord * distance);
        
        
        MovingObjectPosition pos = world.rayTraceBlocks(position, respos);
        
        if (pos == null || world == null)
        	return null;
        
        Block ref = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        
        if (ref == null)
        	return null;
        return new BlockReference(ref, pos.blockX, pos.blockY, pos.blockZ);
	}
}
