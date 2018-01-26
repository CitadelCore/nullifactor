package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.towerdevs.helios.base.HeliosBlockContainer;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumBase;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactor;

public class BlockQuantumReactor extends BlockQuantumBase {
	public static final BlockQuantumReactor instance = new BlockQuantumReactor();
	public BlockQuantumReactor() {
		super("quantum_reactor", Material.iron, "nullifactor", TileEntityQuantumReactor.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_side", "nullifactor:quantum_reactor_side", "nullifactor:quantum_reactor_pylon_top", "nullifactor:base_machine_frame");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity selfTile = world.getTileEntity(x, y, z);
		if (selfTile == null || !(selfTile instanceof TileEntityQuantumReactor))
			return;
		
		TileEntityQuantumReactor selfCore = (TileEntityQuantumReactor) selfTile;
		
		TileEntity tile = world.getTileEntity(tileX, tileY, tileZ);
		if (tile == null || !(tile instanceof TileEntityQuantumBase)) {
			return;
		}
		
		if (tile instanceof TileEntityQuantumReactor) {
			TileEntityQuantumReactor core = (TileEntityQuantumReactor) tile;
			selfCore.addAdjacentReactor(core);
			return;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		
		if (world.isRemote)
			return true;
		
		TileEntity selfTile = world.getTileEntity(x, y, z);
		if (selfTile == null || !(selfTile instanceof TileEntityQuantumReactor))
			return true;
		
		TileEntityQuantumReactor selfCore = (TileEntityQuantumReactor) selfTile;
		
		if (selfCore.hasMaster()) {
			player.addChatMessage(new ChatComponentText("This reactor core is already linked to a master!"));
		}
		
		if (!selfCore.isCoreMaster()) {
			player.addChatMessage(new ChatComponentText("This reactor core is NOT the multiblock master."));
			//return true;
		}
		
		if (selfCore.validateMultiblock(true))
			selfCore.constructMultiblock();
		
		//player.addChatMessage(new ChatComponentText("Blocks in reactor column: " + selfCore.getTileEntitiesAbove().size()));
		//player.addChatMessage(new ChatComponentText("Connected reactor blocks: " + selfCore.getConnectedReactors().size()));
		return true;
	}
}
