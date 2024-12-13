package custom.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import custom.tracker.controller.model.CustomData;
import custom.tracker.controller.model.CustomData.CustomSuppliesData;
import custom.tracker.controller.model.CustomData.StepData;
import custom.tracker.controller.model.SuppliesData;
import custom.tracker.controller.model.CharacterData;
import custom.tracker.controller.model.DollBaseData;
import custom.tracker.service.CustomTrackerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/custom_tracker")
@Slf4j
public class CustomTrackerController 
{
	@Autowired
	private CustomTrackerService customTrackerService;
	
	//-------------- custom ------------------
	// Creates a new custom
	@PostMapping("/custom")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomData saveCustom (
			@RequestBody CustomData customData)
	{
		log.info("Creating custom {}", customData);
		return customTrackerService.saveCustom(customData);
	}
	
	// Updates an existing custom
	@PutMapping("/custom/{id}")
	public CustomData updateCustom(
			@PathVariable Integer id, 
			@RequestBody CustomData customData)
	{
		customData.setCustomId(id);
		log.info("Updating custom {}", customData);
		return customTrackerService.saveCustom(customData);
	}
	
	// Returns a list of all customs.
	@GetMapping("/custom")
	public List<CustomData> retrieveAllCustoms()
	{
		return customTrackerService.retrieveAllCustoms();
	}
	
	// Returns a single custom.
	@GetMapping("/custom/{customId}")
	public CustomData retrieveCustom(
			@PathVariable Integer customId)
	{
		return customTrackerService.retrieveCustom(customId);
	}
	
	// Deletes a custom by its id number.
	@DeleteMapping("/custom/{customId}")
	public Map<String, String> deleteCustomById(
			@PathVariable Integer customId)
	{
		log.info("Deleting custom {}", customId);
		customTrackerService.deleteCustomById(customId);
		return Map.of("message", "Deletion successful");
	}
	
	// --------------- step -----------------
	// Creates a step for a custom
	@PostMapping("/custom/{customId}/step")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StepData addStepData(
			@PathVariable Integer customId,
			@RequestBody StepData stepData)
	{
		return customTrackerService.saveStep(customId, stepData);
	}
	
	//--------------------customsupplies--------------
	// Need a post and put for supplies also. And a put for this one
	// Creates a customsupplies. This is only linked one-way to the 
	// custom and the supplies, so to retrieve these you search by
	// supplies id or custom id.
	@PostMapping("/custom_supplies")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomSuppliesData addCustomSuppliesData(
			//@PathVariable Integer customSuppliesId,
			@RequestBody CustomSuppliesData customSuppliesData)
	{
		return customTrackerService.saveCustomSupplies(customSuppliesData);
	}
	
	
	//----------------supplies--------------------

	// Creates a new supplies
	@PostMapping("/supplies")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SuppliesData saveSupplies (
			@RequestBody SuppliesData suppliesData)
	{
		log.info("Creating supplies {}", suppliesData);
		return customTrackerService.saveSupplies(suppliesData);
	}
	
	//----------------character-------------------
	
	// Creates a new character
	@PostMapping("/character")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CharacterData saveCharacter (
			@RequestBody CharacterData characterData)
	{
		log.info("Creating character {}", characterData);
		return customTrackerService.saveCharacter(characterData);
	}
	
	//----------------dollBase--------------------
	

	// Creates a new doll base
	@PostMapping("/doll_base")
	@ResponseStatus(code = HttpStatus.CREATED)
	public DollBaseData saveDollBase (
			@RequestBody DollBaseData dollBaseData)
	{
		log.info("Creating doll base {}", dollBaseData);
		return customTrackerService.saveDollBase(dollBaseData);
	}
	
}
