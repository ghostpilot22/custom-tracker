package custom.tracker.controller.model;

import custom.tracker.entity.DollBase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class DollBaseData
{
	public DollBaseData(DollBase dollBase)
	{
		dollBaseId = dollBase.getDollBaseId();
		dollName = dollBase.getDollName();
		dollSet = dollBase.getDollSet();
		brand = dollBase.getBrand();
		size = dollBase.getSize();
		jointType = dollBase.getJointType();
		eyeType = dollBase.getEyeType();
		features = dollBase.getFeatures();
		quantityOwned = dollBase.getQuantityOwned();
	}
	private Integer dollBaseId;
	private String dollName;
	private String dollSet;
	private String brand;
	private String size;
	private String jointType;
	private String eyeType;
	private String features;
	private Integer quantityOwned;
}