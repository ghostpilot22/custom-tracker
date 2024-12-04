package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.DollBase;

public interface DollBaseDao extends JpaRepository<DollBase, Integer> {

}
