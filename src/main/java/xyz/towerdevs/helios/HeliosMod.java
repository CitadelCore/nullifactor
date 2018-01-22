package xyz.towerdevs.helios;

public abstract class HeliosMod {
	private String modId;
    private String version;
    
    /** Base class from which all mods using the HELIOS framework derive from. */
    public HeliosMod(String modId, String version) {
    	this.modId = modId;
    	this.version = version;
    }
    
    public String getId() { return modId; };
    public String getVersion() { return version; };
}
