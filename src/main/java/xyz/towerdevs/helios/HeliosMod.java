package xyz.towerdevs.helios;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;

public abstract class HeliosMod {
	private String modId;
    private String version;
    
    /** Base class from which all mods using the HELIOS framework derive from. */
    public HeliosMod(String modId, String version) {
    	this.modId = modId;
    	this.version = version;
    }
    
    public String getId() { return this.modId; };
    public String getVersion() { return this.version; };
    
    @EventHandler
    public void fingerprintWarning(FMLFingerprintViolationEvent event) {
    	FMLLog.getLogger().warn("Failed to validate authenticode signature for the HeliosMod " + this.modId + ".");
    	FMLLog.getLogger().warn("Code may have been tampered with. Support has been voided. Continue at your own risk.");
    }
}
