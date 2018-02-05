package xyz.towerdevs.nullifactor.common.items;

import xyz.towerdevs.helios.base.HeliosItem;

public class ItemSingularity extends HeliosItem {
	private SingularityData data;
	
	public void setSingularity(Singularities bedrock) {
		this.data = bedrock.getSingularity();
	}
	
	public SingularityData getSingularity() {
		return this.data;
	}
	
	public static class SingularityData {
		public int heatPerTick; // Heat produced by this singularity every tick active, in centegrate
		public int heatPeak; // The peak temperature this singularity can produce.
		public float fuelPerTick = 5.0F; // mB of fuel consumed per tick
		public float antimatterProductionChance = 0.001F; // Chance every tick this singularity has to produce one unit of antimatter
		public float quantumEventChance = 0.001F; // Chance singularity has every tick to produce a quantum event.
		
		public float magneticWearCoefficient = 1.0F; // Multiplier for the wear speed of the containment coils.
		
		public int requiredStartPower = 100000; // Minimum charge the singularity must have to be functional.
		public int baseCoreContainment = 500; // Power in RF it takes, per tick, to contain this singularity. (by the magnetic containment coils).
	}
	
	public enum Singularities {
		BEDROCK(100, 1500, 5.0F, 0.0001F, 0.01F, 1.0F, 100000, 500),
		CORRUPTED(250, 2500, 10.0F, 0.01F, 0.01F, 1.5F, 1000000, 1500),
		;
		
		public static final Singularities[] singularities = values();
		private SingularityData singularity;
		
		private Singularities(int hpt, int peak, float fpt, float apc, float qec, float mwc, int rsp, int bcc) {
			SingularityData s = new SingularityData();
			s.heatPerTick = hpt;
			s.fuelPerTick = fpt;
			s.antimatterProductionChance = apc;
			s.quantumEventChance = qec;
			s.magneticWearCoefficient = mwc;
			s.requiredStartPower = rsp;
			s.baseCoreContainment = bcc;
			
			this.singularity = s;
		}
		
		public SingularityData getSingularity() {
			return this.singularity;
		}
	}

}
