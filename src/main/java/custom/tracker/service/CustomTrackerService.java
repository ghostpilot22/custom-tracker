package custom.tracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import custom.tracker.controller.model.CustomData;
import custom.tracker.controller.model.CustomData.CustomSuppliesData;
import custom.tracker.controller.model.CustomData.StepData;
import custom.tracker.controller.model.SuppliesData;
import custom.tracker.controller.model.CharacterData;
import custom.tracker.controller.model.DollBaseData;
import custom.tracker.dao.CharactersDao;
import custom.tracker.dao.CustomDao;
import custom.tracker.dao.CustomSuppliesDao;
import custom.tracker.dao.DollBaseDao;
import custom.tracker.dao.StepDao;
import custom.tracker.dao.SuppliesDao;
import custom.tracker.entity.Characters;
import custom.tracker.entity.Custom;
import custom.tracker.entity.CustomSupplies;
import custom.tracker.entity.DollBase;
import custom.tracker.entity.Step;
import custom.tracker.entity.Supplies;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomTrackerService 
{
	@Autowired
	private CharactersDao charactersDao;
	
	@Autowired
	private CustomDao customDao;
	
	@Autowired
	private CustomSuppliesDao customSuppliesDao;
	
	@Autowired
	private DollBaseDao dollBaseDao;
	
	@Autowired
	private StepDao stepDao;
	
	@Autowired
	private SuppliesDao suppliesDao;
	
	//------------------custom------------------------
	
	@Transactional (readOnly = false)
	public CustomData saveCustom(CustomData customData) 
	{
		log.info("Saving custom...");
		Integer customId =  customData.getCustomId();
		log.info("Custom id get: {}", customId);
		Custom custom = findOrCreateCustom(customId);
		log.info("Found or created custom...");
		copyCustomFields(custom, customData);
		log.info("Custom fields copied...");
		custom.setDollBase(findDollBaseById(customData.getDollBaseId()));
				//findDollBaseById(customData.getDollBase().getDollBaseId()));
		custom.setCharacter(findCharacterById(customData.getCharacterId()));
				//findCharacterById(customData.getCharacters().getCharacterId()));
		log.info("Linked data set...");
		return new CustomData(customDao.save(custom));
	}

	private void copyCustomFields(Custom custom, CustomData customData) 
	{
		custom.setCustomId(customData.getCustomId());
		custom.setCustomName(customData.getCustomName());
	}
	
	private Custom findOrCreateCustom(Integer customId) 
	{
		Custom custom;
		if(Objects.isNull(customId))
		{
			log.info("Custom id null, creating new custom...");
			custom = new Custom();
		}
		else
		{
			log.info("Custom id not null, finding custom by id...");
			custom = findCustomById(customId);
		}
		return custom;
	}

	private Custom findCustomById(Integer customId) 
	{
		return customDao.findById(customId)
				.orElseThrow();
	}

	
	@Transactional(readOnly = true)
	public List<CustomData> retrieveAllCustoms() 
	{
		List<Custom> customs = customDao.findAll();
		List<CustomData> result = new LinkedList<>();
		
		for (Custom custom : customs)
		{
			CustomData customData = new CustomData(custom);
			if(customData.getSteps() != null) customData.getSteps().clear();
			if(customData.getCustomSupplies() != null) customData.getCustomSupplies().clear();
			result.add(customData);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public CustomData retrieveCustom(Integer customId) 
	{
		return new CustomData(findCustomById(customId));
	}

	public void deleteCustomById(Integer customId) 
	{
		Custom custom = findCustomById(customId);
		customDao.delete(custom);
	}
	

	public Float getProgressForACustom(Integer customId) 
	{
		float totalsteps = 0;
		float completeds = 0;
		List<StepData> steps = getAllStepsForACustom(customId);
		for(StepData step : steps)
		{
			totalsteps++;
			if(step.getCompleted()) completeds++;
		}
		return (completeds/totalsteps); // Would be nice if these could return formatted as percentages.
	}

	public Float getPrepForACustom(Integer customId) 
	{
		float totalitems = 0;
		float owneditems = 0;
		List<CustomSuppliesData> csds = getAllSuppliesForACustom(customId);
		Supplies s = new Supplies();
		for(CustomSuppliesData csd : csds)
		{
			totalitems++;
			s = findSuppliesById(csd.getSupplyId());
			if(csd.getQuantityNeeded() <= s.getQuantityOwned())
			{
				owneditems++;
			}
		}
		totalitems++; // doll base counts too
		Custom custom = customDao.findById(customId).orElseThrow();
		DollBase base = custom.getDollBase();
		if(base.getQuantityOwned() > 0) owneditems++;
		return (owneditems/totalitems);// Would be nice if these could return formatted as percentages.
	}

	
	
	//--------------step------------------------
	
	@Transactional(readOnly = false)
	public StepData saveStep (Integer customId, 
			StepData stepData)
	{
		Custom custom = findCustomById(customId);
		Step step = findOrCreateStep(customId, 
				stepData.getStepId());
		copyStepFields(step, stepData);
		step.setCustom(custom);
		custom.getSteps().add(step);
		return new StepData(stepDao.save(step));
	}
	
	private Step findOrCreateStep(Integer customId,
			Integer stepId) 
	{
		Step step;
		if(Objects.isNull(stepId))
		{
			log.info("Step id null, creating new step...");
			step = new Step();
		}
		else
		{
			log.info("Step id not null, finding step by id...");
			step = findStepById(stepId);//customId, stepId);
		}
		return step;
	}
	
	private Step findStepById(//Integer customId,
			Integer stepId)
	{
		Step step = stepDao.findById(stepId)
				.orElseThrow();
		//if(step.getCustom().getCustomId() != customId)
		//	throw new IllegalArgumentException();
		return step;
	}
	
	private void copyStepFields(Step step, 
			StepData stepData)
	{
		step.setStepId(stepData.getStepId());
		step.setStepNumber(stepData.getStepNumber());
		step.setStepText(stepData.getStepText());
		step.setCompleted(stepData.getCompleted());
	}
	
	public List<StepData> getAllStepsForACustom(Integer customId)
	{
		List<Step> steps1 = stepDao.findAll();
		List<StepData> steps2 = new LinkedList<>();
		
		for(Step step : steps1)
		{
			if(step.getCustom().getCustomId() == customId)
			{
				steps2.add(new StepData(step));
			}
		}
		return steps2;
	}
	public void deleteStepById(Integer stepId) 
	{
		Step step = findStepById(stepId);
		stepDao.delete(step);
	}
	
	//--------------------customsupplies-----------------------------------
	@Transactional(readOnly = false)
	public CustomSuppliesData saveCustomSupplies (//Integer customsuppliesId, 
			CustomSuppliesData customSuppliesData)
	{
		Custom custom = findCustomById(customSuppliesData.getCustomId());
		Supplies supplies = findSuppliesById(customSuppliesData.getSupplyId());
		CustomSupplies customSupplies = findOrCreateCustomSupplies(
				customSuppliesData.getCustomId(), customSuppliesData.getSupplyId());
		copyCustomSuppliesFields(customSupplies, customSuppliesData);
		return new CustomSuppliesData(customSuppliesDao.save(customSupplies));
	}

	private void copyCustomSuppliesFields(CustomSupplies customSupplies, CustomSuppliesData customSuppliesData) 
	{
		customSupplies.setCustom(findCustomById(customSuppliesData.
				getCustomId()));
		customSupplies.setSupplies(findSuppliesById(customSuppliesData.
				getSupplyId()));
		customSupplies.setQuantityNeeded(customSuppliesData.getQuantityNeeded());
		
	}

	private CustomSupplies findOrCreateCustomSupplies(Integer customId, Integer supplyId) 
	{
		CustomSupplies customSupplies;
		if(Objects.isNull(suppliesDao.findById(supplyId)) || 
				(Objects.isNull(customDao.findById(customId)))) 
		{
			log.info("Custom or supply id null, failed.");
			return null;
		}
		else
		{
			log.info("Finding customSupplies by ids...");
			customSupplies = findCustomSuppliesByIds(customId, supplyId);
			if(customSupplies == null)	
			{
				log.info("Custom and supply not linked, creating link...");
				customSupplies = new CustomSupplies();
			}
			else log.info("CustomSupplies link found.");
		}
		return customSupplies;
	}

	private CustomSupplies findCustomSuppliesByIds(Integer customId,
			Integer supplyId)
	{
		CustomSupplies customSupplies = customSuppliesDao.
				findByCustomCustomIdAndSuppliesSupplyId(customId, supplyId);
		return customSupplies;
	}
	private CustomSupplies findCustomSuppliesById(Integer csId)
	{
		CustomSupplies customSupplies = customSuppliesDao.
				findById(csId).orElseThrow(); 
		return customSupplies;
	}
	
	public List<CustomSuppliesData> getAllSuppliesForACustom(Integer customId)
	{
		List<CustomSupplies> cs1 = customSuppliesDao.findAll();
		List<CustomSuppliesData> cs2 = new LinkedList<>();
			
		for(CustomSupplies cs : cs1)
		{
			if(cs.getCustom().getCustomId() == customId)
			{
				cs2.add(new CustomSuppliesData(cs));
			}
		}
		return cs2;
	}

	public void deleteCustomSuppliesById(Integer csId) 
	{
		CustomSupplies cs = findCustomSuppliesById(csId);
		customSuppliesDao.delete(cs);
	}
	


	//------------------supplies----------------------

	@Transactional(readOnly = false)
	public SuppliesData saveSupplies (SuppliesData supplyData)
	{
		Supplies supply = findOrCreateSupplies(
				supplyData.getSupplyId());
		copySuppliesFields(supply, supplyData);
		return new SuppliesData(suppliesDao.save(supply));
	}
	
	private Supplies findOrCreateSupplies(Integer supplyId) 
	{
		Supplies supply;
		if(Objects.isNull(supplyId))
		{
			log.info("Supply id null, creating new supply...");
			supply = new Supplies();
		}
		else
		{
			log.info("Supply id not null, finding supply by id...");
			supply = findSuppliesById(supplyId);
		}
		return supply;
	}
	
	private Supplies findSuppliesById(Integer supplyId)
	{
		Supplies supply = suppliesDao.findById(supplyId)
				.orElseThrow();
		return supply;
	}
	
	private void copySuppliesFields(Supplies supply, 
			SuppliesData supplyData)
	{
		supply.setSupplyId(supplyData.getSupplyId());
		supply.setSupplyName(supplyData.getSupplyName());
		supply.setQuantityOwned(supplyData.getQuantityOwned());
		supply.setPrice(supplyData.getPrice());
	}

	
	//------------------character-----------------------

@Transactional(readOnly = false)
	public CharacterData saveCharacter (CharacterData characterData)
	{
		Characters charactr = findOrCreateCharacter(
				characterData.getCharacterId());
		copyCharacterFields(charactr, characterData);
		return new CharacterData(charactersDao.save(charactr));
	}
	
	private Characters findOrCreateCharacter(Integer characterId) 
	{
		Characters charactr;
		if(Objects.isNull(characterId))
		{
			log.info("Character id null, creating new character...");
			charactr = new Characters();
		}
		else
		{
			log.info("Character id not null, finding character by id...");
			charactr = findCharacterById(characterId);
		}
		return charactr;
	}
	
	private Characters findCharacterById(Integer characterId)
	{
		Characters charactr = charactersDao.findById(characterId)
				.orElseThrow();
		return charactr;
	}
	
	private void copyCharacterFields(Characters charactr, 
			CharacterData characterData)
	{
		charactr.setCharacterId(characterData.getCharacterId());
		charactr.setName(characterData.getName());
		charactr.setHairColor(characterData.getHairColor());
		charactr.setSkinTone(characterData.getSkinTone());
		charactr.setEyeColor(characterData.getEyeColor());
		charactr.setGender(characterData.getGender());
		charactr.setTraits(characterData.getTraits());
		charactr.setPersonality(characterData.getPersonality());
	}
	
	@Transactional(readOnly = true)
	public List<CharacterData> retrieveAllCharacters() 
	{
		List<Characters> characters = charactersDao.findAll();
		List<CharacterData> result = new LinkedList<>();
		
		for (Characters c : characters)
		{
			CharacterData cData = new CharacterData(c);
			result.add(cData);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public CharacterData retrieveCharacter(Integer cId) 
	{
		return new CharacterData(findCharacterById(cId));
	}

	public void deleteCharacterById(Integer cId) 
	{
		Characters c = findCharacterById(cId);
		charactersDao.delete(c);
	}

	
	//------------------dollbase-------------------------


	@Transactional(readOnly = false)
	public DollBaseData saveDollBase (DollBaseData dollBaseData)
	{
		DollBase dollBase = findOrCreateDollBase(
				dollBaseData.getDollBaseId());
		copyDollBaseFields(dollBase, dollBaseData);
		return new DollBaseData(dollBaseDao.save(dollBase));
	}
	
	private DollBase findOrCreateDollBase(Integer dollBaseId) 
	{
		DollBase dollBase;
		if(Objects.isNull(dollBaseId))
		{
			log.info("DollBase id null, creating new dollBase...");
			dollBase = new DollBase();
		}
		else
		{
			log.info("DollBase id not null, finding dollBase by id...");
			dollBase = findDollBaseById(dollBaseId);
		}
		return dollBase;
	}
	
	private DollBase findDollBaseById(Integer dollBaseId)
	{
		DollBase dollBase = dollBaseDao.findById(dollBaseId)
				.orElseThrow();
		return dollBase;
	}
	
	private void copyDollBaseFields(DollBase dollBase, 
			DollBaseData dollBaseData)
	{
		dollBase.setDollBaseId(dollBaseData.getDollBaseId());
		dollBase.setDollName(dollBaseData.getDollName());
		dollBase.setDollSet(dollBaseData.getDollSet());
		dollBase.setBrand(dollBaseData.getBrand());
		dollBase.setSize(dollBaseData.getSize());
		dollBase.setJointType(dollBaseData.getJointType());
		dollBase.setEyeType(dollBaseData.getEyeType());
		dollBase.setFeatures(dollBaseData.getFeatures());
		dollBase.setQuantityOwned(dollBaseData.getQuantityOwned());
	}
	
	@Transactional(readOnly = true)
	public List<DollBaseData> retrieveAllDollBases() 
	{
		List<DollBase> dollBases = dollBaseDao.findAll();
		List<DollBaseData> result = new LinkedList<>();
		
		for (DollBase dollBase : dollBases)
		{
			DollBaseData dollBaseData = new DollBaseData(dollBase);
			result.add(dollBaseData);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public DollBaseData retrieveDollBase(Integer dollBaseId) 
	{
		return new DollBaseData(findDollBaseById(dollBaseId));
	}

	public void deleteDollBaseById(Integer dollBaseId) 
	{
		DollBase dollBase = findDollBaseById(dollBaseId);
		dollBaseDao.delete(dollBase);
	}


}
