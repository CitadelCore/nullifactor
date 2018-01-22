package xyz.towerdevs.helios.registries;

import cpw.mods.fml.common.Loader;

public class ModRegistry {
	public enum Mods {
		AVARITIA("Avaritia"),
		DRACONICEVOLUTION("DraconicEvolution"),
		ROTARYCRAFT("RotaryCraft");
		
		public static final Mods[] mods = values();
		private final String name;
		
		private Mods(String modName) {
			name = modName;
			if (Loader.isModLoaded(modName)) {
				System.out.println("[HELIOS] Detected installed mod: " + modName + ".");
			}
		}
		
		public boolean GetLoaded() {
			if (Loader.isModLoaded(name)) {
				return true;
			}
			
			return false;
		}
		
		public String GetModName() {
			return name;
		}
		
		public Class GetModClass(String modClass) {
			try {
				return Class.forName(modClass);
			}
			catch (ClassNotFoundException e) {
				return null;
			}
		}
	}
}
