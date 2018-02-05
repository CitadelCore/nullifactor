package xyz.towerdevs.helios.registries;

public enum HeatConductivity {
	// Real-life materials
	
	// Insulators
	FIBREGLASS(0.0F, 2075),
	MINERALWOOL(0.0F, 1500),
	ASBESTOS(0.004F, 1500),
	TITANIUM(1.1F, 1668),
	INVAR(1.4F, 204),
	LEAD(1.75F, 327),
	IRON(2.9F, 1538), // Cast iron
	STEEL(2.15F, 1370), // 1% carbon steel
	OSMIUM(4.35F, 3033),
	
	// Conductors
	BRASS(5.45F, 920), // 85 Cu 15 Zn
	BRONZE(5.5F, 950),
	ZINC(5.8F, 419),
	COPPER(20.05F, 1085),
	SILVER(21.45F, 961),
	GOLD(15.5F, 1064),
	ALUMINIUM(20.5F, 660),
	PLATINUM(3.5F, 1768),
	
	// Minecraft / modded materials
	
	ELECTRUM(25.0F, 1380),
	FLUXEDELECTRUM(27.0F, 1400),
	ENDERIUM(30.0F, 1350),
	QUANTANIUM(32.0F, 1100),
	HIGHDENSITYALLOY(38.0F, 1750),
	;
	
	/** Heat, in degrees, this block transfers per tick. */
	private float heatConductivity;
	
	/** Melting point of this block. If it exceeds it, it will turn into lava. */
	private int meltingPoint;
	
	public static final HeatConductivity[] heatConductivityRegistry = values();
	private HeatConductivity(float heatConductivity, int meltingPoint) {
		this.heatConductivity = heatConductivity;
		this.meltingPoint = meltingPoint;
	}
	
	public double getHeatConductivity() { return this.heatConductivity; }
	public int getMeltingPoint() { return this.meltingPoint; }
}
