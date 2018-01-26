package xyz.towerdevs.nullifactor.items;

import xyz.towerdevs.helios.base.HeliosItem;
import xyz.towerdevs.nullifactor.models.SingularityData;
import xyz.towerdevs.nullifactor.models.SingularityData.Singularities;

public class ItemSingularity extends HeliosItem {
	private SingularityData data;
	
	public void setSingularity(Singularities bedrock) {
		this.data = bedrock.getSingularity();
	}
	
	public SingularityData getSingularity() {
		return this.data;
	}
}
