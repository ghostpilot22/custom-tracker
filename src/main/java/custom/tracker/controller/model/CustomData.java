package custom.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import custom.tracker.entity.Custom;
import custom.tracker.entity.Supplies;
import custom.tracker.entity.CustomSupplies;
import custom.tracker.entity.DollBase;
import custom.tracker.entity.Step;
import custom.tracker.entity.Characters;

@Data @NoArgsConstructor
public class CustomData 
{
	private Integer customId;
	private String customName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private DollBaseData dollBase;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private CharacterData characters;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<SuppliesData> supplies;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<StepData> steps;
	
	public CustomData (Custom custom)
	{
		customId = custom.getCustomId();
		customName = custom.getCustomName();
		dollBase = new DollBaseData(custom.getDollBase());
		characters = new CharacterData(custom.getCharacter());
		
		supplies = new HashSet<SuppliesData>();
		steps = new HashSet<StepData>();

		// not sure if this is the right way to do this
		// i forgot everything. oops
		if(custom.getSupplies() != null) 
		{
			for(CustomSupplies cs : custom.getSupplies())
				supplies.add(new SuppliesData(cs, cs.getSupplies()));
		}
		
		if(custom.getSteps() != null)
		{
			for(Step step : custom.getSteps())
				steps.add(new StepData(step));
		}
	}
	
	@Data @NoArgsConstructor
	public static class StepData
	{
		public StepData(Step step)
		{
			stepId = step.getStepId();
			stepNumber = step.getStepNumber();
			stepText = step.getStepText();
			completed = step.getCompleted();
		}
		private Integer stepId;
		private Integer stepNumber;
		private String stepText;
		private Boolean completed;
	}
	
	@Data @NoArgsConstructor
	public static class SuppliesData
	{
		public SuppliesData(CustomSupplies cs, Supplies supply)
		{
			supplyId = supply.getSupplyId();
			supplyName = supply.getSupplyName();
			quantityOwned = supply.getQuantityOwned();
			price = supply.getPrice();
			quantityNeeded = cs.getQuantityNeeded();
		}
		private Integer supplyId;
		private String supplyName;
		private Integer quantityOwned;
		private Float price;
		private Integer quantityNeeded;
	}
	
	@Data @NoArgsConstructor
	public static class CharacterData
	{
		public CharacterData(Characters charactr)
		{
			characterId = charactr.getCharacterId();
			name = charactr.getName();
			hairColor = charactr.getHairColor();
			eyeColor = charactr.getEyeColor();
			gender = charactr.getGender();
			traits = charactr.getTraits();
			personality = charactr.getPersonality();
		}
		private Integer characterId;
		private String name;
		private String hairColor;
		private String eyeColor;
		private String gender;
		private String traits;
		private String personality;
	}
	
	@Data @NoArgsConstructor
	public static class DollBaseData
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
}
