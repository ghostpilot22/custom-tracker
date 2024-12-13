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
import custom.tracker.dao.CharactersDao;
import custom.tracker.dao.CustomDao;
import custom.tracker.dao.CustomSuppliesDao;
import custom.tracker.dao.DollBaseDao;
import custom.tracker.dao.StepDao;
import custom.tracker.dao.SuppliesDao;
import custom.tracker.entity.Custom;
import custom.tracker.entity.CustomSupplies;
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
		return new CustomData(customDao.save(custom));
	}

	private void copyCustomFields(Custom custom, CustomData customData) 
	{
		custom.setCustomId(customData.getCustomId());
		custom.setCustomName(customData.getCustomName());
		// Add remaining fields
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
			step = findStepById(customId, stepId);
		}
		return step;
	}
	
	private Step findStepById(Integer customId,
			Integer stepId)
	{
		Step step = stepDao.findById(stepId)
				.orElseThrow();
		if(step.getCustom().getCustomId() != customId)
			throw new IllegalArgumentException();
		return step;
	}
	
	private void copyStepFields(Step step, 
			StepData stepData)
	{
		step.setStepId(stepData.getStepId());
		// Add remaining fields
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
		// TODO Auto-generated method stub
		
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
				//.orElseThrow(); //Not sure why this isn't working but whatever.
		return customSupplies;
	}

	private Supplies findSuppliesById(Integer supplyId) 
	{
		Supplies supply = suppliesDao.findById(supplyId)
				.orElseThrow();
		return supply;
	}

	//------------------supplies----------------------

	public SuppliesData saveSupplies(SuppliesData suppliesData) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//------------------character-----------------------
	
	//------------------dollbase-------------------------

}
