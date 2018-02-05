package xyz.towerdevs.helios;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class HeliosMod {
	private String modId;
    private String version;
    
    public SimpleNetworkWrapper networkWrapper;
    public Logger logger;
    public String networkChannel;
    
    /** Base class from which all mods using the HELIOS framework derive from. */
    public HeliosMod(String modId, String version) {
    	this.modId = modId;
    	this.version = version;
    	
    	this.logger = FMLLog.getLogger();
    }
    
    public String getId() { return this.modId; };
    public String getVersion() { return this.version; };
    
    /** Initializes the mod's network provider with the specified network channel name. */
    protected void networkInit(String networkChannel) {
    	this.networkChannel = networkChannel;
    	this.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(this.networkChannel);
    	
    	// Should register messages here.
    }
    
    @EventHandler
    public void fingerprintWarning(FMLFingerprintViolationEvent event) {
    	FMLLog.getLogger().warn("Failed to validate authenticode signature for the HeliosMod " + this.modId + ".");
    	FMLLog.getLogger().warn("Code may have been tampered with. Support has been voided. Continue at your own risk.");
    }
}
