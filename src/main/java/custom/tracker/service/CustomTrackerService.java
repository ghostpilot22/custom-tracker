package custom.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import custom.tracker.dao.CharactersDao;
import custom.tracker.dao.CustomDao;
import custom.tracker.dao.CustomSuppliesDao;
import custom.tracker.dao.DollBaseDao;
import custom.tracker.dao.StepDao;
import custom.tracker.dao.SuppliesDao;
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
}
