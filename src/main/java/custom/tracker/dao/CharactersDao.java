package custom.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import custom.tracker.entity.Characters;

public interface CharactersDao extends JpaRepository<Characters, Integer> {

}
