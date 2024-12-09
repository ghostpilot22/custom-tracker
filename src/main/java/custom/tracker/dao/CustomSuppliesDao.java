package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.CustomSupplies;

public interface CustomSuppliesDao extends JpaRepository<CustomSupplies, Integer> {

	//Might need to make this static and write a query.
	CustomSupplies findByCustomCustomIdAndSuppliesSupplyId(Integer customId, Integer supplyId);
}
