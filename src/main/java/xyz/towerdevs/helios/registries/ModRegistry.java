package xyz.towerdevs.helios.registries;

import cpw.mods.fml.common.Loader;

public class ModRegistry {
	public enum Mods {
		ROTARYCRAFT("RotaryCraft", "Reika.RotaryCraft.Registry.BlockRegistry", "Reika.RotaryCraft.Registry.ItemRegistry");
		
		public static final Mods[] mods = values();
		private final String name;
		
		private Mods(String modName, String blockClass, String itemClass) {
			name = modName;
			if (Loader.isModLoaded(modName)) {
				System.out.println("[HELIOS] Detected installed mod: " + modName + ".");
			}
		}
		
		public Boolean GetLoaded() {
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
