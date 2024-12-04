package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.CustomSupplies;

public interface CustomSuppliesDao extends JpaRepository<CustomSupplies, Integer> {

}
