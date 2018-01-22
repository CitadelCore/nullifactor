package xyz.towerdevs.nullifactor;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.nullifactor.items.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.items.ResourceOreRegistry;

public class NullifactorWorldGenerator implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		
		/** Run generators from the generator registry */
		for (WorldGeneratorRegistry registry : WorldGeneratorRegistry.values()) {
			if (registry.GetDimId() == world.provider.dimensionId)
				this.generateBlocks(registry.GetGenerator(), world, random, chunkX, chunkZ, registry.GetChanceToSpawn(), registry.GetMinHeight(), registry.GetMaxHeight());
		}
	}
	
	private void generateBlocks(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("[NULLIFACTOR] World generation failure - invalid height arguments.");

	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        generator.generate(world, rand, x, y, z);
	    }
	}

}
