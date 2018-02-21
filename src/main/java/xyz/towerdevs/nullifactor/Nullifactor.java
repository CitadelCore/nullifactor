package xyz.towerdevs.nullifactor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.towerdevs.helios.HeliosMod;
import xyz.towerdevs.nullifactor.common.CommonProxy;
import xyz.towerdevs.nullifactor.common.items.ItemNullifactor;
import xyz.towerdevs.nullifactor.common.misc.BiomeFrozenSwamp;
import xyz.towerdevs.nullifactor.common.registries.ResourceItemRegistry;
import xyz.towerdevs.helios.network.packets.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Nullifactor.MODID, version = Nullifactor.VERSION, dependencies = "after:Avaritia")
public final class Nullifactor extends HeliosMod
{
	public static final String MODID = "nullifactor";
    public static final String VERSION = "1.0.0.0";
    public static final String PROXY_CLIENT = "xyz.towerdevs.nullifactor.client.ClientProxy";
    public static final String PROXY_SERVER = "xyz.towerdevs.nullifactor.common.CommonProxy";
	
	@Instance(Nullifactor.MODID)
	public static Nullifactor instance;
	
	@SidedProxy(clientSide = PROXY_CLIENT, serverSide = PROXY_SERVER)
	public static CommonProxy commonProxy;
    
    public static final BiomeFrozenSwamp frozenSwamp = new BiomeFrozenSwamp(157);
    
    public static CreativeTabs creativeTab = new CreativeTabs("nullifactor") {
    	@Override public Item getTabIconItem() {
    		return ItemNullifactor.instance;
    	}
    };
    
    public static CreativeTabs resourceCreativeTab = new CreativeTabs("nullifactor_resources") {
    	@Override public Item getTabIconItem() {
    		return ResourceItemRegistry.SINGULBEDROCK.getItem();
    	}
    };
    
    public Nullifactor() {
    	super(Nullifactor.MODID, Nullifactor.VERSION);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	this.networkInit(MODID.toUpperCase() + "NW");
    	this.networkWrapper.registerMessage(TileDataPacket.Handler.class, TileDataPacket.class, 0, Side.SERVER);
    	this.networkWrapper.registerMessage(TileDataPacket.Handler.class, TileDataPacket.class, 1, Side.CLIENT);
    	commonProxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	commonProxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	commonProxy.postInit(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	commonProxy.serverLoad(event);
	}
}
