package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.Supplies;

public interface SuppliesDao extends JpaRepository<Supplies, Integer> {

}
