package xyz.towerdevs.helios.registries;

import cpw.mods.fml.common.Loader;

public enum ModRegistry {
	EXTRAUTILITIES("ExtraUtilities"),
	AVARITIA("Avaritia"),
	DRACONICEVOLUTION("DraconicEvolution"),
	THERMALEXPANSION("ThermalExpansion"),
	THERMALFOUNDATION("ThermalFoundation"),
	THERMALDYNAMICS("ThermalDynamics"),
	COFHCORE("CoFHCore"),
	ROTARYCRAFT("RotaryCraft"),
	
	;
	
	public static final ModRegistry[] mods = values();
	private final String name;
	
	private ModRegistry(String modName) {
		this.name = modName;
		if (Loader.isModLoaded(modName)) {
			System.out.println("[HELIOS] Detected installed mod: " + modName + ".");
		}
	}
	
	public boolean IsLoaded() {
		if (Loader.isModLoaded(this.name)) {
			return true;
		}
		
		return false;
	}
	
	public String GetModName() {
		return this.name;
	}
	
	@SuppressWarnings("rawtypes")
	public Class GetModClass(String modClass) {
		try {
			return Class.forName(modClass);
		}
		catch (ClassNotFoundException e) {
			return null;
		}
	}
}
