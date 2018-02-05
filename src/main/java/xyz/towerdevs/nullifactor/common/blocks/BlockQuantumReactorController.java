package xyz.towerdevs.nullifactor.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.registries.GuiRegistry;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorController;

public class BlockQuantumReactorController extends BlockQuantumBase {
	public static final BlockQuantumReactorController instance = new BlockQuantumReactorController();
	public BlockQuantumReactorController() {
		super("quantum_reactor_controller", Material.iron, "nullifactor", TileEntityQuantumReactorController.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_controller", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
			return true;
		
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		
		TileEntity ent = world.getTileEntity(x, y, z);
		if (ent != null && ent instanceof TileEntityQuantumReactorController) {
			TileEntityQuantumReactorController tile = (TileEntityQuantumReactorController) ent;
			if (!tile.checkMasterValid()) {
				player.addChatMessage(new ChatComponentText("Cannot communicate with the master reactor core. Please check the multiblock."));
				return true;
			}
			
			player.openGui(Nullifactor.instance, GuiRegistry.CONTROLLER.ordinal(), world, x, y, z);
	        return true;
		}
		
		return true;
	 }
}
