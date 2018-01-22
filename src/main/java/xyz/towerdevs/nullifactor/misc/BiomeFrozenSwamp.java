package xyz.towerdevs.nullifactor.misc;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenSwamp;

/** Literally a frozen swamp. */
public class BiomeFrozenSwamp extends BiomeGenSwamp {

	public BiomeFrozenSwamp(int id) {
		super(id);
		
		this.setBiomeName("Frozen Swamp");
		this.enableSnow = true;
		this.enableRain = true;
		this.temperature = 0.1F;
	}
	
	@Override
	public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
    {
        return BlockFlower.field_149859_a[1];
    }

	@Override
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
    {
        double d1 = plantNoise.func_151601_a((double)p_150573_5_ * 0.25D, (double)p_150573_6_ * 0.25D);

        if (d1 > 0.0D)
        {
            int k = p_150573_5_ & 15;
            int l = p_150573_6_ & 15;
            int i1 = p_150573_3_.length / 256;

            for (int j1 = 255; j1 >= 0; --j1)
            {
                int k1 = (l * 16 + k) * i1 + j1;

                if (p_150573_3_[k1] == null || p_150573_3_[k1].getMaterial() != Material.air)
                {
                    if (j1 == 62 && p_150573_3_[k1] != Blocks.water)
                    {
                        p_150573_3_[k1] = Blocks.water;

                        if (d1 < 0.12D)
                        {
                            p_150573_3_[k1 + 1] = Blocks.waterlily;
                        }
                    }

                    break;
                }
            }
        }

        this.genBiomeTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }

    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.1D ? 5011004 : 6975545;
    }
    
    @Override
    public BiomeGenBase.TempCategory getTempCategory() {
    	return BiomeGenBase.TempCategory.COLD;
    }

    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
    {
        return 6975545;
    }

}
