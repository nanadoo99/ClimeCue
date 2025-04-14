package nki.ClimCue.repository;

import nki.ClimCue.model.member.PersonalCondition;
import nki.ClimCue.model.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalConditionRepository extends JpaRepository<PersonalCondition, Long> {
    Optional<PersonalCondition> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
