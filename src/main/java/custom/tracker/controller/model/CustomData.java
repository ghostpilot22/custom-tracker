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
	private Set<CustomSuppliesData> customSupplies;
	
	//@EqualsAndHashCode.Exclude
	//@ToString.Exclude
	//private Set<SuppliesData> supplies;
	// Thinking of having data objects for supplies and doll base be their
	// own separate classes, since they're not inherently linked to a custom
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<StepData> steps;
	
	public CustomData (Custom custom)
	{
		customId = custom.getCustomId();
		customName = custom.getCustomName();
		dollBase = new DollBaseData(custom.getDollBase());
		characters = new CharacterData(custom.getCharacter());
		
		customSupplies = new HashSet<CustomSuppliesData>();
		steps = new HashSet<StepData>();

		// not sure if this is the right way to do this
		// i forgot everything. oops
		if(custom.getSupplies() != null) 
		{
			for(CustomSupplies cs : custom.getSupplies())
				customSupplies.add(new CustomSuppliesData(cs, cs.getSupplies(), custom));
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
	public static class CustomSuppliesData
	{
		public CustomSuppliesData(CustomSupplies cs, Supplies supply, Custom custom)
		{
			supplyId = supply.getSupplyId();
			customId = custom.getCustomId();
			customSuppliesId = cs.getCustomSuppliesId();
			//price = supply.getPrice();
			quantityNeeded = cs.getQuantityNeeded();
		}
		public CustomSuppliesData(CustomSupplies cs)
		{
			supplyId = cs.getSupplies().getSupplyId();
			customId = cs.getCustom().getCustomId();
			customSuppliesId = cs.getCustomSuppliesId();
			quantityNeeded = cs.getQuantityNeeded();
		}
		private Integer supplyId;
		private Integer customId;
		private Integer customSuppliesId;
		//private String supplyName;
		//private Integer quantityOwned;
		//private Float price;
		private Integer quantityNeeded;
	}
	
	
	
	
}
