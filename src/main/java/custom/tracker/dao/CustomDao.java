package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.Custom;

public interface CustomDao extends JpaRepository<Custom, Integer> {

}
