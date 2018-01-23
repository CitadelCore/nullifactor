package xyz.towerdevs.nullifactor.blocks;

//import Reika.ReactorCraft.API.NeutronShield;
import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlock;

public class BlockHighDensitySteel extends HeliosBlock //implements NeutronShield 
{
	public static final BlockHighDensitySteel instance = new BlockHighDensitySteel();
	public BlockHighDensitySteel() {
		super("high_density_steel", Material.iron, "nullifactor");
	}

	//@Override
	//public double getAbsorptionChance(String type) {
	//	return 90;
	//}

	//@Override
	//public double getRadiationSpawnMultiplier(World world, int x, int y, int z, String type) {
	//	return 0.1;
	//}
}
