package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.Step;

public interface StepDao extends JpaRepository<Step, Integer> {

}
